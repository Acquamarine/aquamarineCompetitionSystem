package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.MatchmakingManager;
import java.util.HashMap;
import java.util.Map;

public class MultigamingBlManager {
	private static MultigamingBlManager instance;
	
	public static MultigamingBlManager getInstance() {
		if(instance == null) {
			instance = new MultigamingBlManager();
		}
		return instance;
	}
	
	private Map<String, GameManager> gameManagers = new HashMap<>();
	
	public void addGameManager(String game, GameManager gameManager) {
		gameManagers.put(game, gameManager);
	}
	
	public void createMatch(String game, ICompetitor first, ICompetitor second) {
		if(game.endsWith("normal")) {
			System.out.println("normal game");
			gameManagers.get(game).startMatch(first, second, false);
		}
		else {
			System.out.println("ranked game");
			gameManagers.get(game).startMatch(first, second, true);
			
		}
	}

	public void startQueues() {
		MatchmakingManager.getInstance().startQueuesThread();
	}
	
	public GameManager getGameManager(String id) {
		return gameManagers.get(id);
	}
	
	
}
