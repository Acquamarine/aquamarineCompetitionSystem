package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.AbstractNeapolitanCardGame;
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

public class Tressette1v1 extends AbstractNeapolitanCardGame implements ITressette {

	private final Tressette1v1SummaryManager summaryManager = new Tressette1v1SummaryManager();

	public Tressette1v1(List<Integer> players, boolean rankedMatch) {
		super(players, rankedMatch);
		players.stream().forEach((player) -> {
			for(int i = 0; i < 10; i++){
				hands.get(player).addCard(deck.poll());
			}
		});
	}

	@Override
	public synchronized TressetteRoundSummary playCard(Integer playerId, NeapolitanCard card) {
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
						CompetitionManager.getInstance().giveVirtualPoints(winner, loser, Tressette1v1.class.getSimpleName());
						if(rankedMatch){
							CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName(), winner, loser);
						}
						else {
							CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName()+"normal", winner, loser);
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
		score.setGame(Tressette1v1.class.getSimpleName());
		score.setMatchEndTimeByMillis(System.currentTimeMillis());
		ICompetitor winner = CompetitionManager.getInstance().getCompetitor(players.get(0));
		if(finalScores.get(players.get(0)) < finalScores.get(players.get(1))) {
			winner = CompetitionManager.getInstance().getCompetitor(players.get(1));
			
		}
		score.setWinner(winner);
		DAOProvider.getMatchResultsDAO().create(score);
		
	}

	public synchronized NeapolitanCard getFirstCardOnTable() {
		if(table.size() == 1) {
			return table.get(0);
		}
		return null;
	}
}
