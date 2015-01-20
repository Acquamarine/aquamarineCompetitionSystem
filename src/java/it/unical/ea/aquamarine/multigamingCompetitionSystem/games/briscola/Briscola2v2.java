
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.briscola;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.AbstractNeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.INeapolitanCardGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.SharedFunctions;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1SummaryManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteRoundSummary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class Briscola2v2 extends AbstractNeapolitanCardGame implements INeapolitanCardGame {

	public Briscola2v2(List<Integer> players, boolean rankedMatch) {
		super(players, rankedMatch);
		players.stream().forEach((player) -> {
			for(int i = 0; i < 3; i++){
				hands.get(player).addCard(deck.poll());
			}
		});
	}	
	
	@Override
	public TressetteRoundSummary playCard(Integer playerId, NeapolitanCard card) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Map<Integer, Integer> getFinalScores() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
