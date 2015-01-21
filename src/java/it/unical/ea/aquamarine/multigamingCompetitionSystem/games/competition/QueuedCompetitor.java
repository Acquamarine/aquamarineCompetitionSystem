package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import java.util.List;
import java.util.Objects;

public class QueuedCompetitor {
	private final Team team;
	private final List<RegisteredUser> players;
	private final ICompetitor leadingCompetitor;
	private final long queueStartTime;

	public QueuedCompetitor(Team team, List<RegisteredUser> players) {
		this.team = team;
		this.players = players;
		queueStartTime = System.currentTimeMillis();
		if(team!=null) {
			leadingCompetitor = team;
		}
		else {
			leadingCompetitor = players.iterator().next();
		}
	}
	
	public ICompetitor getCompetitor() {
		return leadingCompetitor;
	}

	public Team getTeam() {
		return team;
	}

	public List<RegisteredUser> getPlayers() {
		return players;
	}

	public long getQueueStartTime() {
		return queueStartTime;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.leadingCompetitor);
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
		if (!Objects.equals(this.leadingCompetitor, other.leadingCompetitor)) {
			return false;
		}
		return true;
	}
	
	
	
}
