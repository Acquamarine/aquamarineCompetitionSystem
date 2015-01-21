
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.AbstractGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitionGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import java.util.List;


public class BriscolaGameManager extends AbstractGameManager implements IGameManager {
	private static BriscolaGameManager instance;
	
	public static BriscolaGameManager getInstance() {
		if(instance == null) {
			instance = new BriscolaGameManager();
		}
		return instance;
	}

	@Override
	public ICompetitionGame instantiateMatch(List<Integer> players, List<Integer> teams, boolean rankedMatch) {
		return new Briscola2v2(players, teams, rankedMatch);
	}
	
	@Override
	public int getTeamSize() {
		return 2;
	}
}