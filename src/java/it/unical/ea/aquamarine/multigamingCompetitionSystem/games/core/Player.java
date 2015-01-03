package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 37 * hash + Objects.hashCode(this.nickname);
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
		final Player other = (Player) obj;
		if (!Objects.equals(this.nickname, other.nickname)) {
			return false;
		}
		return true;
	}
	
	
	
}
