package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.SharedFunctions;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Tressette1v1 implements ITressette {

	private List<Integer> players;
	private Map<Integer, Integer> followingPlayer = new HashMap<>();
	private Integer turnPlayer;
	private Map<Integer, NeapolitanHand> hands = new HashMap<>();
	private Map<Integer, NeapolitanCard> lastPickedCards = new HashMap<>();
	private Queue<NeapolitanCard> deck;
	private Map<Integer, List<NeapolitanCard>> takenCards = new HashMap<>();
	private Map<Integer, Integer> finalScores = new HashMap<>();
	private List<NeapolitanCard> table = new ArrayList<>();
	private final SummaryManager summaryManager = new SummaryManager();
	private boolean gameComplete = false;
	private boolean rankedMatch;

	public Tressette1v1(List<Integer> players, boolean rankedMatch) {
		this.players = players;
		for(Integer player : players){
			hands.put(player, new NeapolitanHand());
			lastPickedCards.put(player, null);
			takenCards.put(player, new LinkedList<>());
		}
		followingPlayer.put(players.get(0), players.get(1));
		followingPlayer.put(players.get(1), players.get(0));
		deck = SharedFunctions.getShuffledNeapolitanDeck();
		players.stream().forEach((player) -> {
			for(int i = 0; i < 10; i++){
				hands.get(player).addCard(deck.poll());
			}
		});
		turnPlayer = players.iterator().next();
		this.rankedMatch = rankedMatch;
	}

	@Override
	public TressetteRoundSummary playCard(Integer playerId, NeapolitanCard card) {
		TressetteRoundSummary summary = new TressetteRoundSummary();
		if(playerId.equals(turnPlayer) && !gameComplete){
			summary.setActionPlayer(turnPlayer);
			if(isCardAllowed(card) && hands.get(playerId).playCard(card)){
				summary.setRound(table.size());
				table.add(card);
				summary.setCard(card.toString());
				if(table.size() == 1){
					turnPlayer = followingPlayer.get(playerId);
				}else{
					handComplete(summary);
					checkGameComplete();
					if(gameComplete){
						//remove from active matches, elo updates
						TressetteGameManager.getInstance().gameCompletion(this);
						computeFinalScores();
						generateMatchResultsForHistory();
						Integer winner = players.get(0);
						Integer loser = players.get(1);
						int player1Score = finalScores.get(players.get(0));
						int player2Score = finalScores.get(players.get(1));
						if(player1Score < player2Score){
							Integer temp = winner;
							winner = loser;
							loser = temp;
						}
						if(rankedMatch){
							CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName(), winner, loser);
						}
					}
					summary.setCardsInDeck(deck.size());
				}
				summary.setCardPlayed(true);
				summary.setGameOver(gameComplete);
				addEventSummary(summary);
				return summary;
			}

		}
		summary.setCardPlayed(false);
		return summary;

	}

	public void computeFinalScores() {
		takenCards.keySet().stream().forEach((player) -> {
			int pointCounter = 0;
			pointCounter = takenCards.get(player).stream().map((card) -> getCardValue(card)).reduce(pointCounter, Integer::sum);
			if(turnPlayer.equals(player)){
				pointCounter += 3;
			}
			finalScores.put(player, pointCounter / 3);
		});
		//TODO get two values score for match history

	}

	@Override
	public Map<Integer, Integer> getFinalScores() {
		return finalScores;
	}

	private void handComplete(TressetteRoundSummary summary) {
		Integer handWinner = computeHandWinner();
		summary.setRoundWinner(handWinner);
		takenCards.get(handWinner).addAll(table);
		table.clear();
		lastPickedCards.clear();
		if(!deck.isEmpty()){
			pickCards(handWinner);
			summary.setPickedCards(lastPickedCards);
		}
		turnPlayer = handWinner;
	}

	private Integer computeHandWinner() {
		if(strongerCard(table.get(1), table.get(0))){
			return turnPlayer;
		}
		return followingPlayer.get(turnPlayer);
	}

	private boolean isCardAllowed(NeapolitanCard card) {
		if(table.isEmpty()){
			return true;
		}
		int tableSeed = table.iterator().next().getSeed();
		if(card.getSeed() == tableSeed){
			return true;
		}
		return !hands.get(turnPlayer).hasSeed(tableSeed);
	}

	private boolean strongerCard(NeapolitanCard second, NeapolitanCard first) {
		if(first.getSeed() != second.getSeed()){
			return false;
		}
		int valueFirst = first.getNumber();
		if(valueFirst <= 3){
			valueFirst *= 11;
		}
		int valueSecond = second.getNumber();
		if(valueSecond <= 3){
			valueSecond *= 11;
		}
		return valueSecond > valueFirst;
	}

	private void checkGameComplete() {
		int takenCardsNumber = 0;
		takenCardsNumber = takenCards.values().stream().map((taken) -> taken.size()).reduce(takenCardsNumber, Integer::sum);
		gameComplete = (takenCardsNumber == 40);
	}

	private void pickCards(Integer handWinner) {
		pickACard(handWinner);
		pickACard(followingPlayer.get(handWinner));
	}

	private void pickACard(Integer pickingPlayer) {
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

	private int getCardValue(NeapolitanCard card) {
		if(card.getNumber() == 1){
			return 3;
		}
		if(card.getNumber() <= 3 || card.getNumber() >= 8){
			return 1;
		}
		return 0;
	}

	@Override
	public Integer getMatchedPlayer(Integer player) {
		return followingPlayer.get(player);
	}

	public TressetteRoundSummary getSummary(int eventIndex) {
		return summaryManager.getSummary(eventIndex);
	}

	private void addEventSummary(TressetteRoundSummary summary) {
		summaryManager.addSummary(summary);
	}

	public boolean areThereSummaries() {
		return summaryManager.areThereSummaries();
	}

	private void generateMatchResultsForHistory() {
		TwoCompetitorsMatchResult score = new TwoCompetitorsMatchResult();
		score.setPlayer1(CompetitionManager.getInstance().getCompetitor(players.get(0)));
		score.setPlayer2(CompetitionManager.getInstance().getCompetitor(players.get(1)));
		score.setPlayer1Score(finalScores.get(players.get(0)));
		score.setPlayer2Score(finalScores.get(players.get(1)));
		score.setRankedMatch(rankedMatch);
		score.setMatchEndTimeByMillis(System.currentTimeMillis());
		DAOProvider.getMatchResultsDAO().create(score);
		
	}
}
