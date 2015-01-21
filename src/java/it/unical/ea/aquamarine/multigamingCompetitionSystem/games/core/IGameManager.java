package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import java.util.List;

public interface IGameManager {
	public ICompetitionGame instantiateMatch(List<Integer> players, boolean rankedMatch);
	public void startMatch(ICompetitor competitor1, ICompetitor competitor2, boolean rankedMatch);
	public boolean isCompetitorInGame(ICompetitor competitor);
	public ICompetitionGame getPlayerActiveMatch(Integer player);
	public ICompetitionGame getPlayerCompletedMatch(Integer player);
	public void gameCompletion(ICompetitionGame game);
}
