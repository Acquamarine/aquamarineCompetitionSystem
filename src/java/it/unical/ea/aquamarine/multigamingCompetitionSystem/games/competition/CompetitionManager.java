package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.Player;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import java.util.HashMap;
import java.util.Map;

public class CompetitionManager {
	private static CompetitionManager instance;
	private Map<String, ICompetitor> activeCompetitors = new HashMap<String, ICompetitor>();
	
	public static CompetitionManager getInstance() {
		if(instance == null) {
			instance = new CompetitionManager();
		}
		return instance;
	}
	
	public void eloUpdate(String game, String winnerId, String loserId) {
		ICompetitor winner = activeCompetitors.get(winnerId);
		ICompetitor loser = activeCompetitors.get(loserId);
		int winnerPreviousElo = winner.getElo(game);
		int loserPreviousElo = loser.getElo(game);
		double eloDifference = winnerPreviousElo-loserPreviousElo;
		double expectedResult = 1/(1+Math.pow(10,(eloDifference/400)));
		int winnerNewElo = (int) Math.round(winnerPreviousElo + 30 * (1 - expectedResult));
		int loserNewElo = (int) Math.round(loserPreviousElo + 30 * (0 - expectedResult));
		winner.updateElo(game, winnerNewElo);
		loser.updateElo(game, loserNewElo);
	}

	public ICompetitor getCompetitor(String competitor) {
		if(competitor.equals("pippo")) {
			Player player = new Player(competitor);
			player.updateElo(Tressette1v1.class.getCanonicalName(), 1500);
			activeCompetitors.put(competitor, player);
			return player;
		}
			
		activeCompetitors.putIfAbsent(competitor, new Player(competitor));
		return activeCompetitors.get(competitor);
	}
	
}
