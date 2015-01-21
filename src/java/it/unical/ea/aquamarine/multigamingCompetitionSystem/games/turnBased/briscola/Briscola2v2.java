package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.AbstractNeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.TurnGameSummaryManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Briscola2v2 extends AbstractNeapolitanCardGame implements IBriscola {

	private final static int strenghtScale[] = {2, 4, 5, 6, 7, 8, 9, 10, 3, 1};
	private final NeapolitanCard briscolaCard;
	private final List<Integer> teams;
	private final Map<Integer, Integer> teamsMap = new HashMap<>();

	public Briscola2v2(List<Integer> players, List<Integer> teams, boolean rankedMatch) {
		super(players, rankedMatch);
		for(int i = 0; i < 4; i++){
			teamsMap.put(players.get(i), teams.get(i / 2));
		}
		disposePlayers();
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
	public Integer getOpponent(Integer competitor) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected IGameManager getGameManager() {
		return BriscolaGameManager.getInstance();
	}

	public Map<Integer, Integer> getTeamsMap() {
		return teamsMap;
	}

	@Override
	protected void computeFinalScores() {
		takenCards.keySet().stream().forEach((player) -> {
			int pointCounter = 0;
			pointCounter = takenCards.get(player).stream().map((card) -> getCardValue(card)).reduce(pointCounter, Integer::sum);
			finalScores.put(player, pointCounter);
		});
		for(int i=0;i<teams.size();i++) {
			int teamPoints =0;
			teamPoints+=finalScores.get(players.get(0+i));
			teamPoints+=finalScores.get(players.get(2+i));
			finalScores.put(teams.get(i), teamPoints);
		}

	}

	@Override
	protected boolean isCardAllowed(NeapolitanCard card) {
		return true;
	}

	public NeapolitanCard getBriscolaCard() {
		return briscolaCard;
	}

	
	
	@Override
	protected void generateMatchResultsForHistory() {
		TwoCompetitorsMatchResult score = new TwoCompetitorsMatchResult();
		score.setPlayer1(CompetitionManager.getInstance().getCompetitor(teams.get(0)));
		score.setPlayer2(CompetitionManager.getInstance().getCompetitor(teams.get(1)));
		score.setPlayer1Score(finalScores.get(teams.get(0)));
		score.setPlayer2Score(finalScores.get(teams.get(1)));
		score.setRankedMatch(rankedMatch);
		score.setGame(Briscola2v2.class.getSimpleName());
		score.setMatchEndTimeByMillis(System.currentTimeMillis());
		ICompetitor winner = CompetitionManager.getInstance().getCompetitor(teams.get(0));
		if (finalScores.get(teams.get(0)) < finalScores.get(teams.get(1))) {
			winner = CompetitionManager.getInstance().getCompetitor(teams.get(1));

		}
		score.setWinner(winner);
		DAOProvider.getMatchResultsDAO().create(score);
	}

	@Override
	protected void computeWinnersAndAssignRewards() {
		Integer winner = teams.get(0);
		Integer loser = teams.get(1);
		int team1Score = finalScores.get(teams.get(0));
		int team2Score = finalScores.get(teams.get(1));
		if(team1Score < team2Score){
			Integer temp = winner;
			winner = loser;
			loser = temp;
		}
		CompetitionManager.getInstance().giveVirtualPoints(winner, loser, Tressette1v1.class.getSimpleName());
		if(rankedMatch){
			CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName(), winner, loser);
		}else{
			CompetitionManager.getInstance().eloUpdate(Tressette1v1.class.getSimpleName() + "normal", winner, loser);
		}
	}

	@Override
	protected Integer computeHandWinner() {
		Integer winner = turnPlayer;
		Integer currentPlayer = turnPlayer;
		NeapolitanCard winnerCard = table.get(0);
		for(int i = 1; i < 4; i++){
			NeapolitanCard nextCard = table.get(i);
			currentPlayer = followingPlayer.get(currentPlayer);
			if(winnerCard.getSeed() != briscolaCard.getSeed() && nextCard.getSeed() == briscolaCard.getSeed()){
				winnerCard = nextCard;
				winner = currentPlayer;
			}else if(winnerCard.getSeed() == nextCard.getSeed() && strongerValue(nextCard.getNumber(), winnerCard.getNumber())){
				winnerCard = nextCard;
				winner = currentPlayer;
			}
		}
		return winner;

	}

	private int getCardValue(NeapolitanCard card) {
		int number = card.getNumber();
		if(number >= 8 && number <= 10){
			return number - 6;
		}
		if(number == 1){
			return 11;
		}
		//number == 3
		return 10;
	}

	private boolean strongerValue(int number1, int number2) {
		int strengthPosition1 = 0;
		int strengthPosition2 = 0;
		for(int i = 0; i < strenghtScale.length; i++){
			if(number1 == strenghtScale[i]){
				strengthPosition1 = i;
			}
			if(number2 == strenghtScale[i]){
				strengthPosition2 = i;
			}
		}
		return strengthPosition1 > strengthPosition2;

	}

	public List<ICompetitor> getPlayingOrder(Integer me) {
		List<ICompetitor> returningList = new ArrayList<>();
		Integer currentPlayerId = me;
		do{
			returningList.add(CompetitionManager.getInstance().getCompetitor(currentPlayerId));
			currentPlayerId = followingPlayer.get(currentPlayerId);
		}while(!currentPlayerId.equals(me));
		return returningList;
	}


	public List<NeapolitanCard> getTableFrom(Integer me) {
		List<NeapolitanCard> returningList = new ArrayList<>();
		int turnPositionWRTMe = 0;
		int currentPlayer = me;
		while(currentPlayer != turnPlayer){
			currentPlayer = followingPlayer.get(currentPlayer);
			turnPositionWRTMe++;
		}
		int myPositionWRTTurn = 4 - turnPositionWRTMe;
		for(int i = 0; i < 4; i++){
			if((myPositionWRTTurn + i) % 4 >= table.size()){
				returningList.add(null);
			}else{
				returningList.add(table.get((myPositionWRTTurn + i) % 4));
			}
		}
		return returningList;
	}

	private void disposePlayers() {
		Integer secondPlayer = players.get(1);
		players.set(1,players.get(2));
		players.set(2,secondPlayer);
		
	}
}
