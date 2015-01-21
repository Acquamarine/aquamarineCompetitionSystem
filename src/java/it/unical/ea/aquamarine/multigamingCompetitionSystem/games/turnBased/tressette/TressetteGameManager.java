package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.AbstractGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitionGame;
import java.util.List;

public class TressetteGameManager extends AbstractGameManager implements IGameManager {
	private static TressetteGameManager instance;
	
	public static TressetteGameManager getInstance() {
		if(instance == null) {
			instance = new TressetteGameManager();
		}
		return instance;
	}

	@Override
	public ICompetitionGame instantiateMatch(List<Integer> players, boolean rankedMatch) {
		return new Tressette1v1(players, rankedMatch);
	}
}
