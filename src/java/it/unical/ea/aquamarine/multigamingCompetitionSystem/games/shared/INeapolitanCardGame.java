
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteRoundSummary;
import java.util.Map;

public interface INeapolitanCardGame {
	
	public TressetteRoundSummary playCard(Integer playerId, NeapolitanCard card);
	public Map<Integer, Integer> getFinalScores();
	
}
