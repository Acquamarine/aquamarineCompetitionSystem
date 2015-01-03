package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

public class Player implements ICompetitor{

	private String nickname;

	public Player(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String getId() {
		return nickname;
	}

	@Override
	public Integer getElo() {
		return 1200;
	}
	
}
