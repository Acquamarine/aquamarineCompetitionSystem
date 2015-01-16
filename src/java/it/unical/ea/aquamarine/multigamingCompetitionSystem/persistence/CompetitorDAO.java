package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.AbstractCompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.CompetitorEquip;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public interface CompetitorDAO {
	public void create(ICompetitor competitor);
	public ICompetitor retrieveByNick(String nick);
	public ICompetitor retrieveByUsername(String username);
	public boolean doesUserExistByUsername(String username);
	public boolean doesCompetitorExistByNick(String nick);
	public ICompetitor retrieveById(Integer competitor);
	public void updateCompetitor(ICompetitor competitor);
	public List<Pair<ICompetitor,Integer>> getCompetitorRanking(String game);
	public Pair<Integer,Integer> getCompetitorRankAndEloByNick(String nickname,String game);
	public void initializeEquip(ICompetitor competitor);
	public void initializeInventory(ICompetitor competitor);

}
