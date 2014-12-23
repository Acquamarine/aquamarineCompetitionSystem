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
	
	private List<Player> players;
	private Map<String, Player> playersMap = new HashMap<>();
	private Map<String, Player> followingPlayer = new HashMap<>();
	private Player turnPlayer;
	private Map<String, NeapolitanHand> hands = new HashMap<>();
	private Map<String, NeapolitanCard> lastPickedCards = new HashMap<>();;
	private Queue<NeapolitanCard> deck;
	private Map<String, List<NeapolitanCard>> takenCards = new HashMap<>();;
	private List<NeapolitanCard> table = new ArrayList<>();
	private boolean gameComplete = false;

	public Tressette1v1(List<Player> players) {
		this.players = players;
		for(Player player:players) {
			playersMap.put(player.getId(), player);
			hands.put(player.getId(), new NeapolitanHand());
			lastPickedCards.put(player.getId(), null);
			takenCards.put(player.getId(), new LinkedList<>());
			
			
		}
		followingPlayer.put(players.get(0).getId(), players.get(1));
		followingPlayer.put(players.get(1).getId(), players.get(0));
		deck = SharedFunctions.getShuffledNeapolitanDeck();
		turnPlayer = players.iterator().next();
	}
	

	@Override
	public boolean playCard(String playerId, NeapolitanCard card) {
		if(playersMap.get(playerId) == turnPlayer) {
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
		Player handWinner = computeHandWinner();
		takenCards.get(handWinner.getId()).addAll(table);
		table.clear();
		lastPickedCards.clear();
		if(!deck.isEmpty()) {
			pickCards(handWinner);		
		}
		turnPlayer = handWinner;
	}

	private Player computeHandWinner() {
		if(strongerCard(table.get(1), table.get(0))) {
			return turnPlayer;
		}
		return followingPlayer.get(turnPlayer.getId());
	}

	private boolean isCardAllowed(NeapolitanCard card) {
		if(table.isEmpty()) {
			return true;
		}
		int tableSeed  = table.iterator().next().getSeed();
		if(card.getSeed() == tableSeed) {
			return true;
		}
		return !hands.get(turnPlayer.getId()).hasSeed(tableSeed);
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

	private void pickCards(Player handWinner) {
		pickACard(handWinner);
		pickACard(followingPlayer.get(handWinner.getId()));
	}

	private void pickACard(Player pickingPlayer) {
		NeapolitanCard pickedCard = deck.poll();
		lastPickedCards.put(pickingPlayer.getId(), pickedCard);
		hands.get(pickingPlayer.getId()).addCard(pickedCard);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Map<String, Player> getPlayersMap() {
		return playersMap;
	}

	public void setPlayersMap(Map<String, Player> playersMap) {
		this.playersMap = playersMap;
	}

	public Map<String, Player> getFollowingPlayer() {
		return followingPlayer;
	}

	public void setFollowingPlayer(Map<String, Player> followingPlayer) {
		this.followingPlayer = followingPlayer;
	}

	public Player getTurnPlayer() {
		return turnPlayer;
	}

	public void setTurnPlayer(Player turnPlayer) {
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
