package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.Objects;

public class QueuedCompetitor {
	private ICompetitor competitor;
	private long queueStartTime;

	public QueuedCompetitor(ICompetitor competitor) {
		this.competitor = competitor;
		queueStartTime = System.currentTimeMillis();
	}

	public ICompetitor getCompetitor() {
		return competitor;
	}

	public long getQueueStartTime() {
		return queueStartTime;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.competitor);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final QueuedCompetitor other = (QueuedCompetitor) obj;
		if (!Objects.equals(this.competitor, other.competitor)) {
			return false;
		}
		return true;
	}
	
	
	
}
