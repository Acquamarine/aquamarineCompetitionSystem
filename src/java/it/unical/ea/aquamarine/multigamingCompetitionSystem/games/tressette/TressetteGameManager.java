package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.GameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TressetteGameManager implements GameManager {
	private static TressetteGameManager instance;
	private Map<String, Tressette1v1> activeMatches = new HashMap<>();
	private Map<String, String> matchedPlayers = new HashMap<>();

	public static TressetteGameManager getInstance() {
		if(instance == null) {
			instance = new TressetteGameManager();
		}
		return instance;
	}
	
	public TressetteGameManager() {
	}
	
	@Override
	public synchronized void startMatch(ICompetitor user1, ICompetitor user2) {
		//TODO all checks
		if(activeMatches.get(user1.getId())!=null) {
			return;
		}
		List<String> players = new ArrayList<>();
		players.add(user1.getId());
		players.add(user2.getId());
		Tressette1v1 match = new Tressette1v1(players);
		activeMatches.put(user1.getId(), match);
		activeMatches.put(user2.getId(), match);
	}
	
	public Tressette1v1 getPlayerMatch(String player) {
		return activeMatches.get(player);
	}

	public String getMatchedWith(String player) {
		return matchedPlayers.get(player);
	}
	
}
