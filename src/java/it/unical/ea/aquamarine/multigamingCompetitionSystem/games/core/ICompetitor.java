package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

public interface ICompetitor {
	public String getId();

	public Integer getElo(String game);

	public void updateElo(String game, int winnerNewElo);
}
