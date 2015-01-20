
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.AbstractNeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import java.util.List;
import java.util.Map;


public class Briscola2v2 extends AbstractNeapolitanCardGame implements IBriscola {
	
	private final NeapolitanCard briscolaCard;

	public Briscola2v2(List<Integer> players, boolean rankedMatch) {
		super(players, rankedMatch);
		players.stream().forEach((player) -> {
			for(int i = 0; i < 3; i++){
				hands.get(player).addCard(deck.poll());
			}
		});
		briscolaCard = deck.poll();
		deck.add(briscolaCard);
	}	
	
	@Override
	public BriscolaRoundSummary playCard(Integer playerId, NeapolitanCard card) {
		BriscolaRoundSummary summary = new BriscolaRoundSummary();
		playCard(playerId, card, summary);
		return summary;
	}

	@Override
	public Map<Integer, Integer> getFinalScores() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Integer getOpponent(Integer competitor) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected IGameManager getGameManager() {
		return BriscolaGameManager.getInstance();
	}

	@Override
	protected void computeFinalScores() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected boolean isCardAllowed(NeapolitanCard card) {
		return true;
	}

	@Override
	protected void generateMatchResultsForHistory() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void handComplete(NeapolitanGameRoundSummary summary) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void addEventSummary(NeapolitanGameRoundSummary summary) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void computeWinnersAndAssignRewards() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected Integer computeHandWinner() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
