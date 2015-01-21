
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitionGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.SharedFunctions;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.TressetteGameManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public abstract class AbstractNeapolitanCardGame implements ICompetitionGame{
	protected List<Integer> players;
	protected Map<Integer, Integer> followingPlayer = new HashMap<>();
	protected Integer turnPlayer;
	protected Map<Integer, NeapolitanHand> hands = new HashMap<>();
	protected Map<Integer, NeapolitanCard> lastPickedCards = new HashMap<>();
	protected Queue<NeapolitanCard> deck;
	protected Map<Integer, List<NeapolitanCard>> takenCards = new HashMap<>();
	protected Map<Integer, Integer> finalScores = new HashMap<>();
	protected List<NeapolitanCard> table = new ArrayList<>();
	protected boolean gameComplete = false;
	protected boolean rankedMatch;
	private final TurnGameSummaryManager summaryManager = new TurnGameSummaryManager();
	protected Integer firstPlayerOfHand;

	public AbstractNeapolitanCardGame(List<Integer> players, boolean rankedMatch) {
		this.players = players;
		for(Integer player : players){
			hands.put(player, new NeapolitanHand());
			lastPickedCards.put(player, null);
			takenCards.put(player, new LinkedList<>());
		}
		for(int i=0;i<players.size();i++) {
			followingPlayer.put(players.get(i), players.get((i+1)%players.size()));
		}
		deck = SharedFunctions.getShuffledNeapolitanDeck();		
		turnPlayer = players.iterator().next();
		firstPlayerOfHand = turnPlayer;
		this.rankedMatch = rankedMatch;
	}
	
	
	
	public synchronized void playCard(Integer playerId, NeapolitanCard card, NeapolitanGameRoundSummary summary) {
		if(playerId.equals(turnPlayer) && !gameComplete){
			summary.setActionPlayer(turnPlayer);
			if(isCardAllowed(card) && hands.get(playerId).playCard(card)){
				summary.setRound(table.size());
				table.add(card);
				summary.setCard(card.toString());
				if(table.size() < players.size()){
					turnPlayer = followingPlayer.get(playerId);
					summary.setTurnPlayer(turnPlayer);
				}else{
					handComplete(summary);
					checkGameComplete();
					if(gameComplete){
						getGameManager().gameCompletion(this);
						computeFinalScores();
						generateMatchResultsForHistory();
						computeWinnersAndAssignRewards();
					}
					summary.setCardsInDeck(deck.size());
				}
				summary.setCardPlayed(true);
				summary.setGameOver(gameComplete);
				addEventSummary(summary);
				return;
			}

		}
		summary.setCardPlayed(false);

	}
	
	@Override
	public Map<Integer, Integer> getFinalScores() {
		return finalScores;
	}
	
	protected void pickACard(Integer pickingPlayer) {
		NeapolitanCard pickedCard = deck.poll();
		lastPickedCards.put(pickingPlayer, pickedCard);
		hands.get(pickingPlayer).addCard(pickedCard);
	}
	
	@Override
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
	
	protected void checkGameComplete() {
		int takenCardsNumber = 0;
		takenCardsNumber = takenCards.values().stream().map((taken) -> taken.size()).reduce(takenCardsNumber, Integer::sum);
		gameComplete = (takenCardsNumber == 40);
	}
	
	protected void handComplete(NeapolitanGameRoundSummary summary) {
		Integer handWinner = computeHandWinner();
		summary.setRoundWinner(handWinner);
		takenCards.get(handWinner).addAll(table);
		table.clear();
		lastPickedCards.clear();
		if (!deck.isEmpty()) {
			pickCards(handWinner, summary);
		}
		turnPlayer = handWinner;
		firstPlayerOfHand = turnPlayer;
	}
	
	private void pickCards(Integer handWinner, NeapolitanGameRoundSummary summary) {
		Integer picking = handWinner;
		List<Integer> pickList = new ArrayList<>();
		List<NeapolitanCard> pickedCards = new ArrayList<>();
		do {
			pickACard(picking);
			pickedCards.add(lastPickedCards.get(picking));
			pickList.add(picking);
			picking = followingPlayer.get(picking);
		}while(!picking.equals(handWinner));
		summary.setPickList(pickList);
		summary.setPickedCards(pickedCards);
		summary.setPickSummary(true);
	}
	
	protected abstract IGameManager getGameManager();
	
	protected abstract void computeFinalScores();

	protected abstract boolean isCardAllowed(NeapolitanCard card);
	
	protected abstract void generateMatchResultsForHistory();


	protected abstract void computeWinnersAndAssignRewards();

	protected abstract Integer computeHandWinner();
			
	public ITurnSummary getSummary(int eventIndex) {
		return summaryManager.getSummary(eventIndex);
	}

	
	protected void addEventSummary(NeapolitanGameRoundSummary summary) {
		summaryManager.addSummary(summary);
	}

	public boolean areThereSummaries() {
		return summaryManager.areThereSummaries();
	}

}
