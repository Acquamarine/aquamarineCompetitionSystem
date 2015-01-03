package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.MatchmakingManager;
import java.util.Map;

public class MultigamingBlManager {
	private static MultigamingBlManager instance;
	
	public static MultigamingBlManager getInstance() {
		if(instance == null) {
			instance = new MultigamingBlManager();
		}
		return instance;
	}
	
	private Map<String, GameManager> gameManagers;
	
	public void createMatch(String game, ICompetitor first, ICompetitor second) {
		gameManagers.get(game).startMatch(first, second);
	}

	public void startQueues() {
		MatchmakingManager.getInstance().startQueuesThread();
	}
}
