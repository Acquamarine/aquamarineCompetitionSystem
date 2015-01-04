package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player implements ICompetitor{

	private String nickname;
	private Map<String, Integer> soloQueueProfile = new HashMap<>();

	public Player(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String getId() {
		return nickname;
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
		if (!Objects.equals(this.nickname, other.nickname)) {
			return false;
		}
		return true;
	}
	
	
	
}
