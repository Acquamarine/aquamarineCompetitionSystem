package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import java.util.HashMap;
import java.util.Map;

public class TressetteResultsSummary {
	public Map<String, Integer> finalScores = new HashMap<>();

	public TressetteResultsSummary(Map<String, Integer> finalScores) {
		this.finalScores = finalScores;
	}
	
	public Map<String, Integer> getFinalScores() {
		return finalScores;
	}
	
	
}
