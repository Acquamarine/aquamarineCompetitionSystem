
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

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


public abstract class AbstractGameManager implements IGameManager{
	protected Map<Integer, ICompetitionGame> activeMatches = new HashMap<>();
	protected Map<Integer, ICompetitionGame> completedMatches = new HashMap<>();
	private final Map<Integer, Condition> conditionsMap = new HashMap<>();
	private final Lock lock = new ReentrantLock();
	
	@Override
	public void startMatch(ICompetitor user1, ICompetitor user2, boolean rankedMatch) {
		List<Integer> players = new ArrayList<>();
		players.add(user1.getId());
		players.add(user2.getId());
		ICompetitionGame match = instantiateMatch(players, rankedMatch);
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
	
	
	public void waitForMatch(Integer player) {
		lock.lock();
		if(activeMatches.get(player)==null) {
			
			Condition newCondition = lock.newCondition();
			conditionsMap.put(player, newCondition);
			try {
				newCondition.await();
			} catch (InterruptedException ex) {
				Logger.getLogger(AbstractGameManager.class.getName()).log(Level.SEVERE, null, ex);
			}finally {
				lock.unlock();
			}
		}
		else {
			lock.unlock();
		}
	}
	
	@Override
	public synchronized boolean isCompetitorInGame(ICompetitor competitor) {
		return activeMatches.get(competitor.getId()) !=null;
		
	}

	@Override
	public synchronized void gameCompletion(ICompetitionGame game) {
		for(Integer playerId:game.getPlayers()) {
			completedMatches.put(playerId, game);
			activeMatches.remove(playerId);
		}
	}
	
	@Override
	public abstract ICompetitionGame instantiateMatch(List<Integer> players, boolean rankedMatch);
	
	@Override
	public synchronized ICompetitionGame getPlayerActiveMatch(Integer player) {
		return activeMatches.get(player);
	}
	
	@Override
	public synchronized ICompetitionGame getPlayerCompletedMatch(Integer player) {
		return completedMatches.get(player);
	}
	
	
	
}
