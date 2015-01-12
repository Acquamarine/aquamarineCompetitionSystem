package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

public interface ICompetitor {
	
	public int getId();
	
	public String getNickname();

	public Integer getElo(String game);

	public void updateElo(String game, int winnerNewElo);
	
}
