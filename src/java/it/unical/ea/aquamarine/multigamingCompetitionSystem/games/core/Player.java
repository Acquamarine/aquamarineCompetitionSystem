package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player extends  User implements ICompetitor{

	private Map<String, Integer> soloQueueProfile = new HashMap<>();

	public Player() {
		super();
	}

	@Override
	public Integer getElo(String game) {
		soloQueueProfile.putIfAbsent(game, 1200);
		return soloQueueProfile.get(game);
	}

	@Override
	public void updateElo(String game, int elo) {
		System.out.println(nickname + "'s elo is now "+elo);
		soloQueueProfile.put(game, elo);
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
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	
	
}
