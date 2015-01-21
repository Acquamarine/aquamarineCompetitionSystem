
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

	public AbstractGameManager() {
	}
	
	
	@Override
	public void startMatch(List<ICompetitor> competitors, List<ICompetitor> teams, boolean rankedMatch) {
		List<Integer> competitorIds = new ArrayList<>();
		List<Integer> teamIds = new ArrayList<>();
		for(ICompetitor competitor:competitors) {
			competitorIds.add(competitor.getId());
		}
		if(teams!=null) {
			for(ICompetitor team:teams) {
				teamIds.add(team.getId());
			}
		}
		else {
			teamIds = null;
		}
		ICompetitionGame match = instantiateMatch(competitorIds, teamIds, rankedMatch);
		System.out.println("match started");
		lock.lock();
		try {
			for(Integer competitorId:competitorIds) {
				activeMatches.put(competitorId, match);
			}
			for(Integer competitorId:competitorIds) {
				if(conditionsMap.get(competitorId)!=null) {
					conditionsMap.get(competitorId).signal();
				}
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
				System.out.println("moving out");
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
	public synchronized ICompetitionGame getPlayerActiveMatch(Integer player) {
		return activeMatches.get(player);
	}
	
	@Override
	public synchronized ICompetitionGame getPlayerCompletedMatch(Integer player) {
		return completedMatches.get(player);
	}
	
	
	
}
