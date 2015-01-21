package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import java.util.List;

public interface IGameManager {
	public ICompetitionGame instantiateMatch(List<Integer> players, List<Integer> teams, boolean rankedMatch);
	public void startMatch(List<ICompetitor> competitors, List<ICompetitor> teams, boolean rankedMatch);
	public boolean isCompetitorInGame(ICompetitor competitor);
	public ICompetitionGame getPlayerActiveMatch(Integer player);
	public ICompetitionGame getPlayerCompletedMatch(Integer player);
	public void gameCompletion(ICompetitionGame game);
	public int getTeamSize();
}
