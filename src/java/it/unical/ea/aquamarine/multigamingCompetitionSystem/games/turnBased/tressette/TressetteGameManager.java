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

	public TressetteGameManager() {
	}
	
	
	@Override
	public ICompetitionGame instantiateMatch(List<Integer> players, List<Integer> teams, boolean rankedMatch) {
		return new Tressette1v1(players, rankedMatch);
	}

	@Override
	public int getTeamSize() {
		return 1;
	}
	
	

}
