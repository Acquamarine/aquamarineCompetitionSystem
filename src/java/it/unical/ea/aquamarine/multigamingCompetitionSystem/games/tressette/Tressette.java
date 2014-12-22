package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.Player;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import java.util.List;
import java.util.Map;

public class Tressette implements ITressette{
	
	private List<Player> players;
	private Player turnPlayer;
	private Map<Player, NeapolitanHand> hands;
	private List<NeapolitanCard> deck;
	private Map<Player, List<NeapolitanCard>> takenCards;
	

	@Override
	public boolean playCard(Player player, NeapolitanCard card) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Map<String, Integer> getFinalScores() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
