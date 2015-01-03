package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CompetitorsQueue {
	private TreeMap<Integer, Set<QueuedCompetitor>> queuedCompetitors = new TreeMap<>();
	
	public void addCompetitor(QueuedCompetitor competitor) {
		Set<QueuedCompetitor> sameEloSet = queuedCompetitors.get(competitor.getCompetitor().getElo());
		if(sameEloSet == null) {
			sameEloSet = new HashSet<>();
		}
		sameEloSet.add(competitor);
	}
	
	public void removeCompetitor(QueuedCompetitor competitor) {
		queuedCompetitors.get(competitor.getCompetitor().getElo()).remove(competitor);
	}

	public TreeMap<Integer, Set<QueuedCompetitor>> getQueuedCompetitors() {
		return queuedCompetitors;
	}

}
