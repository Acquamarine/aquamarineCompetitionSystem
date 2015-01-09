
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;

public interface MatchResultDAO {

	public void create(TwoCompetitorsMatchResult score);

}
