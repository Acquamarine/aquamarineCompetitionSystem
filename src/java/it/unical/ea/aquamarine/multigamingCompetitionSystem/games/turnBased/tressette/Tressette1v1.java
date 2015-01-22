package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.AbstractNeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import java.util.List;
import java.util.Map;

public class Tressette1v1 extends AbstractNeapolitanCardGame implements ITressette {

	

	public Tressette1v1(List<Integer> players, boolean rankedMatch) {
		super(players, rankedMatch);
		players.stream().forEach((player) -> {
			for (int i = 0; i < 10; i++) {
				hands.get(player).addCard(deck.poll());
			}
		});
	}

	@Override
	public synchronized NeapolitanGameRoundSummary playCard(Integer playerId, NeapolitanCard card) {
		NeapolitanGameRoundSummary summary = new NeapolitanGameRoundSummary();
		playCard(playerId, card, summary);
		return summary;

	}

	@Override
	protected void computeFinalScores() {
		takenCards.keySet().stream().forEach((player) -> {
			int pointCounter = 0;
			pointCounter = takenCards.get(player).stream().map((card) -> getCardValue(card)).reduce(pointCounter, Integer::sum);
			if (turnPlayer.equals(player) && surrenderer==null) {
				pointCounter += 3;
			}
			finalScores.put(player, pointCounter / 3);
		});

	}

	@Override
	protected Integer computeHandWinner() {
		if (strongerCard(table.get(1), table.get(0))) {
			return turnPlayer;
		}
		return followingPlayer.get(turnPlayer);
	}

	@Override
	protected boolean isCardAllowed(NeapolitanCard card) {
		if (table.isEmpty()) {
			return true;
		}
		int tableSeed = table.iterator().next().getSeed();
		if (card.getSeed() == tableSeed) {
			return true;
		}
		return !hands.get(turnPlayer).hasSeed(tableSeed);
	}

	private boolean strongerCard(NeapolitanCard second, NeapolitanCard first) {
		if (first.getSeed() != second.getSeed()) {
			return false;
		}
		int valueFirst = first.getNumber();
		if (valueFirst <= 3) {
			valueFirst *= 11;
		}
		int valueSecond = second.getNumber();
		if (valueSecond <= 3) {
			valueSecond *= 11;
		}
		return valueSecond > valueFirst;
	}

	private int getCardValue(NeapolitanCard card) {
		if (card.getNumber() == 1) {
			return 3;
		}
		if (card.getNumber() <= 3 || card.getNumber() >= 8) {
			return 1;
		}
		return 0;
	}

	@Override
	public Integer getOpponent(Integer player) {
		return followingPlayer.get(player);
	}

	@Override
	protected void generateMatchResultsForHistory() {
		TwoCompetitorsMatchResult score = new TwoCompetitorsMatchResult();
		populateResults(score, players);
		if(surrenderer!=null) {
			score.setWinner(CompetitionManager.getInstance().getCompetitor(followingPlayer.get(surrenderer)));
		}
		score.setGame(Tressette1v1.class.getSimpleName());
		DAOProvider.getMatchResultsDAO().create(score);
	}

	public synchronized NeapolitanCard getFirstCardOnTable() {
		if (table.size() == 1) {
			return table.get(0);
		}
		return null;
	}

	@Override
	public IGameManager getGameManager() {
		return TressetteGameManager.getInstance();
	}

	@Override
	protected void computeWinnersAndAssignRewards() {
		Integer winner = players.get(0);
		Integer loser = players.get(1);
		int player1Score = finalScores.get(players.get(0));
		int player2Score = finalScores.get(players.get(1));
		if (player1Score < player2Score) {
			Integer temp = winner;
			winner = loser;
			loser = temp;
		}
		assignRewards(winner, loser);
	}

	@Override
	protected void assignRewards(Integer winner, Integer loser) {
		CompetitionManager.getInstance().giveVirtualPoints(winner, loser, Tressette1v1.class.getSimpleName());
		if (rankedMatch) {
			CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName(), winner, loser);
		} else {
			CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName() + "normal", winner, loser);
		}
	}

	@Override
	protected void assignRewardsAfterSurrender() {
		Integer loser = surrenderer;
		Integer winner = followingPlayer.get(loser);
		assignRewards(winner, loser);
	}
	
	
	
	
}
