package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.Player;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.Map;

public interface ITressette {
	
	public boolean playCard(Player player, NeapolitanCard card);
	public Map<String, Integer> getFinalScores();
	
	
}
