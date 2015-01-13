
package it.unical.ea.aquamarine.multigamingCompetitionSystem.items;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;


public interface IItem {

	public void equip(ICompetitor competitor);
	
	public void unequip(ICompetitor competitor);
	
	public ItemCategory getCategory();
	
	public void setCategory(ItemCategory itemCategory);
	
	public String getGame();
	
	public void setGame(String game);
	
	public String getName();
	
	public void setName(String name);
	
	public String getDisplayName();
	
	public void setDisplayName(String displayName);
	
	public int getId();
	
	public void setId(int id);
	
	
}
