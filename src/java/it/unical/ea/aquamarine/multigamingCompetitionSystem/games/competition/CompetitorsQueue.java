package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CompetitorsQueue {
	private TreeMap<Integer, Set<QueuedCompetitor>> queuedCompetitors = new TreeMap<>();
	private String game;

	public CompetitorsQueue(String game) {
		this.game = game;
	}
	
	public void addCompetitor(QueuedCompetitor competitor) {
		Set<QueuedCompetitor> sameEloSet = queuedCompetitors.get(competitor.getCompetitor().getElo(game));
		if(sameEloSet == null) {
			sameEloSet = new HashSet<>();
		}
		sameEloSet.add(competitor);
		queuedCompetitors.put(competitor.getCompetitor().getElo(game), sameEloSet);
	}
	
	public void removeCompetitor(QueuedCompetitor competitor) {
		queuedCompetitors.get(competitor.getCompetitor().getElo(game)).remove(competitor);
	}

	public TreeMap<Integer, Set<QueuedCompetitor>> getQueuedCompetitors() {
		return queuedCompetitors;
	}

}
