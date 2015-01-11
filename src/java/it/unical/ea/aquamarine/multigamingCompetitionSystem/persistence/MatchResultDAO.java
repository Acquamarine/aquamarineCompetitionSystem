
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import java.util.List;

public interface MatchResultDAO {

	public void create(TwoCompetitorsMatchResult score);
	public List<TwoCompetitorsMatchResult> retrieveCompetitorMatches(ICompetitor competitor, String game);

}
