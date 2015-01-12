package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.GameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
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
	private Map<Integer, Tressette1v1> activeMatches = new HashMap<>();
	private Map<Integer, Tressette1v1> completedMatches = new HashMap<>();
	private Map<Integer, Condition> conditionsMap = new HashMap<>();
	private Map<Integer, Integer> matchedPlayers = new HashMap<>();
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
	public void startMatch(ICompetitor user1, ICompetitor user2, boolean rankedMatch) {
		//TODO all checks
		if(activeMatches.get(user1.getId())!=null) {
			return;
		}
		List<Integer> players = new ArrayList<>();
		players.add(user1.getId());
		players.add(user2.getId());
		Tressette1v1 match = new Tressette1v1(players, rankedMatch);
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
	
	public Tressette1v1 waitForMatch(Integer player) {
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
	
	public Tressette1v1 getPlayerCompletedMatch(Integer player) {
		return completedMatches.get(player);
	}
	
	public Tressette1v1 getPlayerActiveMatch(Integer player) {
		return activeMatches.get(player);
	}

	public Integer getMatchedWith(Integer player) {
		return matchedPlayers.get(player);
	}

	public void gameCompletion(Tressette1v1 tressetteGame) {
		completedMatches.put(tressetteGame.getPlayers().get(0), tressetteGame);
		completedMatches.put(tressetteGame.getPlayers().get(1), tressetteGame);
		activeMatches.remove(tressetteGame.getPlayers().get(0));
		activeMatches.remove(tressetteGame.getPlayers().get(1));
		
	}
	
}
