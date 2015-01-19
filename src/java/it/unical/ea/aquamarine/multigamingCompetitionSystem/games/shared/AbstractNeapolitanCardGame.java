
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1SummaryManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class AbstractNeapolitanCardGame {
	protected List<Integer> players;
	protected Map<Integer, Integer> followingPlayer = new HashMap<>();
	protected Integer turnPlayer;
	protected Map<Integer, NeapolitanHand> hands = new HashMap<>();
	protected Map<Integer, NeapolitanCard> lastPickedCards = new HashMap<>();
	protected Queue<NeapolitanCard> deck;
	protected Map<Integer, List<NeapolitanCard>> takenCards = new HashMap<>();
	protected Map<Integer, Integer> finalScores = new HashMap<>();
	protected List<NeapolitanCard> table = new ArrayList<>();
	protected final Tressette1v1SummaryManager summaryManager = new Tressette1v1SummaryManager();
	protected boolean gameComplete = false;
	protected boolean rankedMatch;

	public AbstractNeapolitanCardGame(List<Integer> players, boolean rankedMatch) {
		this.players = players;
		for(Integer player : players){
			hands.put(player, new NeapolitanHand());
			lastPickedCards.put(player, null);
			takenCards.put(player, new LinkedList<>());
		}
		followingPlayer.put(players.get(0), players.get(1));
		followingPlayer.put(players.get(1), players.get(0));
		deck = SharedFunctions.getShuffledNeapolitanDeck();		
		turnPlayer = players.iterator().next();
		this.rankedMatch = rankedMatch;
	}
	
	protected void pickACard(Integer pickingPlayer) {
		NeapolitanCard pickedCard = deck.poll();
		lastPickedCards.put(pickingPlayer, pickedCard);
		hands.get(pickingPlayer).addCard(pickedCard);
	}
	
	public List<Integer> getPlayers() {
		return players;
	}

	public void setPlayers(List<Integer> players) {
		this.players = players;
	}

	public Map<Integer, Integer> getFollowingPlayer() {
		return followingPlayer;
	}

	public void setFollowingPlayer(Map<Integer, Integer> followingPlayer) {
		this.followingPlayer = followingPlayer;
	}

	public Integer getTurnPlayer() {
		return turnPlayer;
	}

	public void setTurnPlayer(Integer turnPlayer) {
		this.turnPlayer = turnPlayer;
	}

	public Map<Integer, NeapolitanHand> getHands() {
		return hands;
	}

	public Map<Integer, NeapolitanCard> getLastPickedCards() {
		return lastPickedCards;
	}

	public Queue<NeapolitanCard> getDeck() {
		return deck;
	}

	public Map<Integer, List<NeapolitanCard>> getTakenCards() {
		return takenCards;
	}

	public List<NeapolitanCard> getTable() {
		return table;
	}

	public boolean isGameComplete() {
		return gameComplete;
	}

}
