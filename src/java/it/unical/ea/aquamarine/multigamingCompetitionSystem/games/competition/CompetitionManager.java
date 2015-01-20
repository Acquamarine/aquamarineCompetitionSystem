package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorDAO;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants;
import java.util.HashMap;
import java.util.Map;

public class CompetitionManager {
	private static CompetitionManager instance;
	private final Map<Integer, ICompetitor> activeCompetitors = new HashMap<>();
	private final Map<String, ICompetitor> activeCompetitorsByUsername = new HashMap<>();
	private final Map<String, ICompetitor> activeCompetitorsByNick = new HashMap<>();
	float HIGHER_BOUND = 2000;
	float LOWER_BOUND = 600;
	float HIGHER_ELO_CONSTANT = 10;
	float LOWER_ELO_CONSTANT = 30;

	public static CompetitionManager getInstance() {
		if(instance == null){
			instance = new CompetitionManager();
		}
		return instance;
	}

	public void eloUpdate(String game, Integer winnerId, Integer loserId) {
		ICompetitor winner = activeCompetitors.get(winnerId);
		ICompetitor loser = activeCompetitors.get(loserId);
		int winnerPreviousElo = winner.getElo(game);
		int loserPreviousElo = loser.getElo(game);
		double eloDifference = winnerPreviousElo - loserPreviousElo;
		double loserExpectedWinningProbability = 1 / (1 + Math.pow(10, (eloDifference / 400)));
		int winnerNewElo = (int) Math.round(winnerPreviousElo + getEloDynamicConstant(winnerPreviousElo) * (loserExpectedWinningProbability));
		int loserNewElo = (int) Math.round(loserPreviousElo + getEloDynamicConstant(loserPreviousElo) * (-loserExpectedWinningProbability));
		winner.updateElo(game, winnerNewElo);
		loser.updateElo(game, loserNewElo);
		OnDemandPersistenceManager.getInstance().updateCompetitor(loser);
		OnDemandPersistenceManager.getInstance().updateCompetitor(winner);
	}

	public ICompetitor getCompetitor(Integer competitorId) {
		if(activeCompetitors.containsKey(competitorId)){
			return activeCompetitors.get(competitorId);
		}
		CompetitorDAO competitorDAO = DAOProvider.getCompetitorDAO();
		ICompetitor competitor = competitorDAO.retrieveById(competitorId);
		if(competitor != null){
			activeCompetitors.put(competitorId, competitor);
			if(!competitor.isTeam()){
				activeCompetitorsByUsername.put(((RegisteredUser) competitor).getUsername(), competitor);
			}
			activeCompetitorsByNick.put(competitor.getNickname(), competitor);
		}
		return competitor;

	}

	public ICompetitor getCompetitorByNick(String nick) {
		if(activeCompetitorsByNick.containsKey(nick)){
			return activeCompetitorsByNick.get(nick);
		}
		CompetitorDAO competitorDAO = DAOProvider.getCompetitorDAO();
		ICompetitor competitor = competitorDAO.retrieveByNick(nick);
		if(competitor != null){
			activeCompetitorsByNick.put(nick, competitor);
			if(!competitor.isTeam()){
				activeCompetitorsByUsername.put(((RegisteredUser) competitor).getUsername(), competitor);
			}
			activeCompetitors.put(competitor.getId(), competitor);
		}
		return competitor;

	}

	public ICompetitor getCompetitorByUsername(String username) {
		if(activeCompetitorsByUsername.containsKey(username)){
			return activeCompetitorsByUsername.get(username);
		}
		CompetitorDAO competitorDAO = DAOProvider.getCompetitorDAO();
		ICompetitor competitor = competitorDAO.retrieveByUsername(username);
		if(competitor != null){
			activeCompetitorsByUsername.put(username, competitor);
			activeCompetitors.put(competitor.getId(), competitor);
			activeCompetitorsByNick.put(competitor.getNickname(), competitor);
		}
		return competitor;

	}

	private double getEloDynamicConstant(int startingElo) {

		if(startingElo >= HIGHER_BOUND){
			return HIGHER_ELO_CONSTANT;
		}else if(startingElo <= LOWER_BOUND){
			return LOWER_ELO_CONSTANT;
		}
		double t = (startingElo - LOWER_BOUND) / (HIGHER_BOUND - LOWER_BOUND) * (LOWER_ELO_CONSTANT - HIGHER_ELO_CONSTANT);
		return LOWER_ELO_CONSTANT - t;

	}

	public void giveVirtualPoints(Integer winnerId, Integer loserId, String simpleName) {
		ICompetitor winner = activeCompetitors.get(winnerId);
		ICompetitor loser = activeCompetitors.get(loserId);
		winner.gainVirtualPoints(GameConstants.virtualPointsRewards.get(simpleName).getValue());
		loser.gainVirtualPoints(GameConstants.virtualPointsRewards.get(simpleName).getKey());
		OnDemandPersistenceManager.getInstance().updateCompetitor(loser);
		OnDemandPersistenceManager.getInstance().updateCompetitor(winner);
	}

	public boolean doesTeamExistByNick(String teamNickname) {
		ICompetitor competitor = activeCompetitorsByNick.get(teamNickname);
		if(competitor != null){
			return competitor.isTeam();
		}
		competitor = DAOProvider.getCompetitorDAO().retrieveByNick(teamNickname);
		if(competitor != null){
			return competitor.isTeam();
		}
		return false;
	}
	public boolean doesUserExistByNick(String userNickname) {
		ICompetitor competitor = activeCompetitorsByNick.get(userNickname);
		if(competitor != null){
			return !competitor.isTeam();
		}
		competitor = DAOProvider.getCompetitorDAO().retrieveByNick(userNickname);
		if(competitor != null){
			return !competitor.isTeam();
		}
		return false;
	}
}
