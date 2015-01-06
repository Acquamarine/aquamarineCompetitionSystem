package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

public interface ICompetitor {
	public String getNickname();

	public Integer getElo(String game);

	public void updateElo(String game, int winnerNewElo);
}
