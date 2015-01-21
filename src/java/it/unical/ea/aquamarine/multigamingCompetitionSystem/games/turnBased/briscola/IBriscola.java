
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.INeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;

public interface IBriscola extends INeapolitanCardGame{
	public BriscolaRoundSummary playCard(Integer playerId, NeapolitanCard card);
}
