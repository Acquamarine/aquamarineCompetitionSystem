
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.AbstractNeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import java.util.List;
import java.util.Map;


public class Briscola2v2 extends AbstractNeapolitanCardGame implements IBriscola {
	
	private final static int strenghtScale [] ={2,4,5,6,7,8,9,10,3,1}; 
	private final NeapolitanCard briscolaCard;
	private final List<Integer> teams;

	public Briscola2v2(List<Integer> players, List<Integer> teams, boolean rankedMatch) {
		super(players, rankedMatch);
		this.teams = teams;
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
		takenCards.keySet().stream().forEach((player) -> {
			int pointCounter = 0;
			pointCounter = takenCards.get(player).stream().map((card) -> getCardValue(card)).reduce(pointCounter, Integer::sum);
			finalScores.put(player, pointCounter);
		});
		
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
		Integer winner = teams.get(0);
		Integer loser = teams.get(1);
		int team1Score = finalScores.get(teams.get(0));
		int team2Score = finalScores.get(teams.get(1));
		if (team1Score < team2Score) {
			Integer temp = winner;
			winner = loser;
			loser = temp;
		}
		CompetitionManager.getInstance().giveVirtualPoints(winner, loser, Tressette1v1.class.getSimpleName());
		if (rankedMatch) {
			CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName(), winner, loser);
		} else {
			CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName() + "normal", winner, loser);
		}
	}

	@Override
	protected Integer computeHandWinner() {
		Integer winner = turnPlayer;
		Integer currentPlayer = turnPlayer;
		NeapolitanCard winnerCard = table.get(0);
		for(int i=1;i<4;i++) {
			NeapolitanCard nextCard = table.get(i);
			currentPlayer = followingPlayer.get(currentPlayer);
			if(winnerCard.getSeed()!=briscolaCard.getSeed() && nextCard.getSeed() == briscolaCard.getSeed()) {
				winnerCard = nextCard;
				winner = currentPlayer;
			}
			else if(winnerCard.getSeed()==nextCard.getSeed() && strongerValue(nextCard.getNumber(),winnerCard.getNumber())) {
				winnerCard = nextCard;
				winner = currentPlayer;
			}
		}
		return winner;
		
	}

	private int getCardValue(NeapolitanCard card) {
		int number = card.getNumber();
		if(number>=8 && number<=10) {
			return number-6;
		}
		if(number == 1) {
			return 11;
		}
		//number == 3
		return 10;
	}

	private boolean strongerValue(int number1, int number2) {
		int strengthPosition1=0;
		int strengthPosition2=0;
		for(int i=0;i<strenghtScale.length;i++) {
			if(number1 == strenghtScale[i]) {
				strengthPosition1 = i;
			}
			if(number2 == strenghtScale[i]) {
				strengthPosition2 = i;
			}
		}
		return strengthPosition1 > strengthPosition2;
		
	}
}
