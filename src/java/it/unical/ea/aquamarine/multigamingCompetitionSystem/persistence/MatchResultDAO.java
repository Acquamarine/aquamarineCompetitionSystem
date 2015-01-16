
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import java.util.List;
import javafx.util.Pair;

public interface MatchResultDAO {

	public void create(TwoCompetitorsMatchResult score);
	public List<TwoCompetitorsMatchResult> retrieveCompetitorMatches(ICompetitor competitor, String game);
	public Pair<Integer, Integer> retrieveDefeatsAndVictories(ICompetitor competitor, String game);

}
