package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;

public interface GameManager {
	public void startMatch(ICompetitor competitor1, ICompetitor competitor2, boolean rankedMatch);
	public boolean isCompetitorInGame(ICompetitor competitor);
}
