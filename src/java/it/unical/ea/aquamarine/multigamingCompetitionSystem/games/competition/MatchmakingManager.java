package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.MultigamingBlManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchmakingManager {

	private static MatchmakingManager instance = new MatchmakingManager();
	private HashMap<ICompetitor, QueuedCompetitor> queuedCompetitorsMap = new HashMap<>();
	private static final int ELO_EXPANSION_PER_SECOND = 20;

	public static MatchmakingManager getInstance() {
		return instance;
	}
	private Map<String, CompetitorsQueue> queues = new HashMap<>();

	public synchronized void addToQueue(String game, ICompetitor competitor) {
		if (queues.get(game) == null) {
			queues.put(game, new CompetitorsQueue());
		}
		QueuedCompetitor queuedCompetitor = new QueuedCompetitor(competitor);
		queues.get(game).addCompetitor(queuedCompetitor);
		queuedCompetitorsMap.put(competitor, queuedCompetitor);
	}

	public synchronized void removeFromQueue(String game, ICompetitor competitor) {
		if (queues.get(game) != null) {
			queues.get(game).removeCompetitor(queuedCompetitorsMap.get(competitor));
			queuedCompetitorsMap.remove(competitor);
		}
	}

	public HashMap<ICompetitor, QueuedCompetitor> getQueuedCompetitorsMap() {
		return queuedCompetitorsMap;
	}
	
	public synchronized void possiblyCreateMatches() {
		for (String game : queues.keySet()) {
			TreeMap<Integer, Set<QueuedCompetitor>> queuedCompetitorsTree = queues.get(game).getQueuedCompetitors();
			Iterator<Map.Entry<Integer, Set<QueuedCompetitor>>> entryIterator = queuedCompetitorsTree.entrySet().iterator();
			while(entryIterator.hasNext()) {
				Set<QueuedCompetitor> sameEloSet = entryIterator.next().getValue();
				Iterator<QueuedCompetitor> it = sameEloSet.iterator();
				while(sameEloSet.size()>1) {
					QueuedCompetitor first = it.next();
					it.remove();
					QueuedCompetitor second = it.next();
					it.remove();		
					createMatch(game, first, second);
				}
				if(sameEloSet.isEmpty()) {
					entryIterator.remove();
				}
				else {
					QueuedCompetitor candidate = sameEloSet.iterator().next();
					long queueStartTime = candidate.getQueueStartTime();
					int queueTime = (int) (System.currentTimeMillis() - queueStartTime);
					int eloRange = queueTime/1000 * ELO_EXPANSION_PER_SECOND;
					Map.Entry<Integer, Set<QueuedCompetitor>> lowerEntry = queuedCompetitorsTree.lowerEntry(candidate.getCompetitor().getElo());
					Map.Entry<Integer, Set<QueuedCompetitor>> higherEntry = queuedCompetitorsTree.higherEntry(candidate.getCompetitor().getElo());
					Map.Entry<Integer, Set<QueuedCompetitor>> bestEntry = null;
					int minimum = Integer.MAX_VALUE;
					if(lowerEntry != null && candidate.getCompetitor().getElo() - lowerEntry.getKey() < minimum) {
						minimum = candidate.getCompetitor().getElo() - lowerEntry.getKey();
						bestEntry = lowerEntry;
					}
					if(higherEntry != null && candidate.getCompetitor().getElo() - higherEntry.getKey() < minimum) {
						minimum = higherEntry.getKey() - candidate.getCompetitor().getElo();
						bestEntry = higherEntry;
					}
					
					if(bestEntry!=null) {
						if(minimum <=eloRange) {
							Set<QueuedCompetitor> goodElements = bestEntry.getValue();
							QueuedCompetitor matched = goodElements.iterator().next();
							goodElements.remove(matched);
							if(goodElements.isEmpty()) {
								queuedCompetitorsTree.remove(bestEntry.getKey());
							}
							createMatch(game, matched, candidate);
							sameEloSet.clear();
							entryIterator.remove();
						}
					}
				}
					
			}
		}

	}

	private void createMatch(String game, QueuedCompetitor first, QueuedCompetitor second) {
		MultigamingBlManager.getInstance().createMatch(game, first.getCompetitor(), second.getCompetitor());
	}

	public void startQueuesThread() {
		Thread matchmakingThread = new Thread(new MatchmakingTrigger());
		matchmakingThread.start();
	}

	private class MatchmakingTrigger implements Runnable {

		@Override
		public void run() {
			while (true) {
				possiblyCreateMatches();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(MatchmakingTrigger.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
