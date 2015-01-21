package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.MatchmakingManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultigamingBlManager {
	private static MultigamingBlManager instance;
	
	public static MultigamingBlManager getInstance() {
		if(instance == null) {
			instance = new MultigamingBlManager();
		}
		return instance;
	}
	
	private final Map<String, IGameManager> gameManagers = new HashMap<>();
	
	public void addGameManager(String game, IGameManager gameManager) {
		gameManagers.put(game, gameManager);
	}
	
	public void createMatch(String game, List<ICompetitor> players, List<ICompetitor> teams) {
		if(game.endsWith("normal")) {
			System.out.println("normal game");
			gameManagers.get(game).startMatch(players, teams, false);
		}
		else {
			System.out.println("ranked game");
			gameManagers.get(game).startMatch(players, teams, true);
			
		}
	}

	public void startQueues() {
		MatchmakingManager.getInstance().startQueuesThread();
	}
	
	public IGameManager getGameManager(String id) {
		return gameManagers.get(id);
	}
	
	
}
