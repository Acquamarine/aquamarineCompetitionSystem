package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.GameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TressetteGameManager implements GameManager {
	private static TressetteGameManager instance;
	private Map<String, Tressette1v1> activeMatches = new HashMap<>();
	private Map<String, Condition> conditionsMap = new HashMap<>();
	private Map<String, String> matchedPlayers = new HashMap<>();
	private Lock lock = new ReentrantLock();

	public static TressetteGameManager getInstance() {
		if(instance == null) {
			instance = new TressetteGameManager();
		}
		return instance;
	}
	
	public TressetteGameManager() {
	}
	
	@Override
	public void startMatch(ICompetitor user1, ICompetitor user2) {
		//TODO all checks
		if(activeMatches.get(user1.getId())!=null) {
			return;
		}
		List<String> players = new ArrayList<>();
		players.add(user1.getId());
		players.add(user2.getId());
		Tressette1v1 match = new Tressette1v1(players);
		lock.lock();
		try {
			activeMatches.put(user1.getId(), match);
			activeMatches.put(user2.getId(), match);
			if(conditionsMap.get(user1.getId())!=null) {
				conditionsMap.get(user1.getId()).signal();
			}
			if(conditionsMap.get(user2.getId())!=null) {
				conditionsMap.get(user2.getId()).signal();
			}
		} finally {
			lock.unlock();
		}
	}
	
	public Tressette1v1 waitForMatch(String player) {
		lock.lock();
		if(activeMatches.get(player)==null) {
			
			Condition newCondition = lock.newCondition();
			conditionsMap.put(player, newCondition);
			try {
				newCondition.await();
			} catch (InterruptedException ex) {
				Logger.getLogger(TressetteGameManager.class.getName()).log(Level.SEVERE, null, ex);
			}finally {
				lock.unlock();
			}
		}
		return activeMatches.get(player);
	}
	
	public Tressette1v1 getPlayerMatch(String player) {
		return activeMatches.get(player);
	}

	public String getMatchedWith(String player) {
		return matchedPlayers.get(player);
	}
	
}
