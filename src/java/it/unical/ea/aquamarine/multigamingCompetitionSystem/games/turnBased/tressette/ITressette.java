package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.INeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;

public interface ITressette extends INeapolitanCardGame {
	
	
	public NeapolitanGameRoundSummary playCard(Integer playerId, NeapolitanCard card);
	
}
