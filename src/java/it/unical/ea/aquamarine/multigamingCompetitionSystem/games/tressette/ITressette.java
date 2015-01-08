package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.Map;

public interface ITressette {
	
	public TressetteRoundSummary playCard(Integer playerId, NeapolitanCard card);
	public Map<Integer, Integer> getFinalScores();
	public Integer getMatchedPlayer(Integer player);
	
	
}
