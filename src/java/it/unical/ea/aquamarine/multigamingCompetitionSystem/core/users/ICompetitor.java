package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.CompetitorEquip;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.CompetitorInventory;
import java.util.Map;

public interface ICompetitor {
	
	public int getId();
	
	public String getNickname();

	public Integer getElo(String game);

	public void updateElo(String game, int winnerNewElo);

	public int getVirtualPoints();
	
	public void gainVirtualPoints(Integer value);
	
	public Map<String, CompetitorEquip> getEquip();
	
	public CompetitorInventory getInventory();

	public CompetitorEquip getEquip(String game);
	
}
