
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core;

import java.util.List;
import java.util.Map;

public interface ICompetitionGame {

	public Map<Integer, Integer> getFinalScores();
	public Integer getOpponent(Integer competitor);
	public List<Integer> getPlayers();
	public void surrenderMatch(Integer competitor);
}
