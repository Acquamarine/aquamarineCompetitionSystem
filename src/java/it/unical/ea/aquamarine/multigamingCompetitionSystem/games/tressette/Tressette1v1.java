package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.Player;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.SharedFunctions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Tressette1v1 implements ITressette{
	
	private List<String> players;
	private Map<String, String> followingPlayer = new HashMap<>();
	private String turnPlayer;
	private Map<String, NeapolitanHand> hands = new HashMap<>();
	private Map<String, NeapolitanCard> lastPickedCards = new HashMap<>();;
	private Queue<NeapolitanCard> deck;
	private Map<String, List<NeapolitanCard>> takenCards = new HashMap<>();;
	private List<NeapolitanCard> table = new ArrayList<>();
	private boolean gameComplete = false;

	public Tressette1v1(List<String> players) {
		this.players = players;
		for(String player:players) {
			hands.put(player, new NeapolitanHand());
			lastPickedCards.put(player, null);
			takenCards.put(player, new LinkedList<>());
		}
		followingPlayer.put(players.get(0), players.get(1));
		followingPlayer.put(players.get(1), players.get(0));
		deck = SharedFunctions.getShuffledNeapolitanDeck();
		players.stream().forEach((player) -> {
			for(int i=0;i<10;i++) {
				hands.get(player).addCard(deck.poll());
			}
		});
		turnPlayer = players.iterator().next();
	}
	

	@Override
	public boolean playCard(String playerId, NeapolitanCard card) {
		if(playerId.equals(turnPlayer)) {
			if(hands.get(playerId).playCard(card) && isCardAllowed(card)) {
				table.add(card);
				if(table.size() == 1) {
					turnPlayer = followingPlayer.get(playerId);		
				}
				else {
					handComplete();
					checkGameComplete();
				}
				return true;
			}
			
		}
		return false;
		
	}

	@Override
	public Map<String, Integer> getFinalScores() {
		Map<String, Integer> returningMap = new HashMap<>();
		takenCards.keySet().stream().forEach((player) -> {
			int pointCounter = 0;
			pointCounter = takenCards.get(player).stream().map((card) -> getCardValue(card)).reduce(pointCounter, Integer::sum);
			returningMap.put(player, pointCounter/3);
		});
		return returningMap;
	}

	private void handComplete() {
		String handWinner = computeHandWinner();
		takenCards.get(handWinner).addAll(table);
		table.clear();
		lastPickedCards.clear();
		if(!deck.isEmpty()) {
			pickCards(handWinner);		
		}
		turnPlayer = handWinner;
	}

	private String computeHandWinner() {
		if(strongerCard(table.get(1), table.get(0))) {
			return turnPlayer;
		}
		return followingPlayer.get(turnPlayer);
	}

	private boolean isCardAllowed(NeapolitanCard card) {
		if(table.isEmpty()) {
			return true;
		}
		int tableSeed  = table.iterator().next().getSeed();
		if(card.getSeed() == tableSeed) {
			return true;
		}
		return !hands.get(turnPlayer).hasSeed(tableSeed);
	}

	private boolean strongerCard(NeapolitanCard second, NeapolitanCard first) {
		if(first.getSeed() != second.getSeed()) {
			return false;
		}
		int valueFirst = first.getNumber();
		if(valueFirst <=3) {
			valueFirst*=11;
		}
		int valueSecond = second.getNumber();
		if(valueSecond <=3) {
			valueSecond*=11;
		}
		return valueSecond > valueFirst;
	}

	private void checkGameComplete() {
		int takenCardsNumber = 0;
		takenCardsNumber = takenCards.values().stream().map((taken) -> taken.size()).reduce(takenCardsNumber, Integer::sum);
		gameComplete = (takenCardsNumber == 40);
	}

	private void pickCards(String handWinner) {
		pickACard(handWinner);
		pickACard(followingPlayer.get(handWinner));
	}

	private void pickACard(String pickingPlayer) {
		NeapolitanCard pickedCard = deck.poll();
		lastPickedCards.put(pickingPlayer, pickedCard);
		hands.get(pickingPlayer).addCard(pickedCard);
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public Map<String, String> getFollowingPlayer() {
		return followingPlayer;
	}

	public void setFollowingPlayer(Map<String, String> followingPlayer) {
		this.followingPlayer = followingPlayer;
	}

	public String getTurnPlayer() {
		return turnPlayer;
	}

	public void setTurnPlayer(String turnPlayer) {
		this.turnPlayer = turnPlayer;
	}

	public Map<String, NeapolitanHand> getHands() {
		return hands;
	}

	public Map<String, NeapolitanCard> getLastPickedCards() {
		return lastPickedCards;
	}

	public Queue<NeapolitanCard> getDeck() {
		return deck;
	}

	public Map<String, List<NeapolitanCard>> getTakenCards() {
		return takenCards;
	}

	public List<NeapolitanCard> getTable() {
		return table;
	}

	public boolean isGameComplete() {
		return gameComplete;
	}
	
	private int getCardValue(NeapolitanCard card) {
		if(card.getNumber() == 1) {
			return 3;
		}
		if(card.getNumber() <= 3 || card.getNumber()>=8) {
			return 1;
		}
		return 0;
	}
	
}
