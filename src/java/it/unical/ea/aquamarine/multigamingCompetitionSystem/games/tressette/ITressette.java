package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.INeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.Map;

public interface ITressette extends INeapolitanCardGame {
	
	public Integer getMatchedPlayer(Integer player);
	
}
