package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.List;
import javafx.util.Pair;

public interface CompetitorDAO {
	public void create(ICompetitor competitor);
	public ICompetitor retrieveByNick(String nick);
	public ICompetitor retrieveByUsername(String username);
	public boolean doesUserExistByUsername(String username);
	public boolean doesCompetitorExistByNick(String nick);
	public ICompetitor retrieveById(Integer competitor);
	public void updateCompetitor(ICompetitor competitor);
	public List<Pair<String,Integer>> getCompetitorRanking(String game);
	public Pair<Integer,Integer> getCompetitorRankAndEloByNick(String nickname,String game);

}
