package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TressetteGameManager {
	private static TressetteGameManager instance;
	private Map<String, Tressette1v1> activeMatches = new HashMap<>();
	private Map<String, String> matchedPlayers = new HashMap<>();

	public static TressetteGameManager getInstance() {
		if(instance == null) {
			instance = new TressetteGameManager();
		}
		return instance;
	}
	
	private TressetteGameManager() {
		//TODO use matchmaking algorithms
		matchedPlayers.put("ciccio","pippo");
		matchedPlayers.put("pippo","ciccio");
	}
	
	public synchronized void startMatch(String user1, String user2) {
		//TODO all checks
		if(activeMatches.get(user1)!=null) {
			return;
		}
		List<String> players = new ArrayList<>();
		players.add(user1);
		players.add(user2);
		Tressette1v1 match = new Tressette1v1(players);
		activeMatches.put(user1, match);
		activeMatches.put(user2, match);
	}
	
	public Tressette1v1 getPlayerMatch(String player) {
		return activeMatches.get(player);
	}

	public String getMatchedWith(String player) {
		return matchedPlayers.get(player);
	}
	
}
