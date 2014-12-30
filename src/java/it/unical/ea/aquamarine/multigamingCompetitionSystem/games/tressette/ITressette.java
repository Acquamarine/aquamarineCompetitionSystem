package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.Map;

public interface ITressette {
	
	public TressetteRoundSummary playCard(String playerId, NeapolitanCard card);
	public Map<String, Integer> getFinalScores();
	public String getMatchedPlayer(String player);
	
	
}
