
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemCategory;


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
	
	public String getDescription();
	
	public void setDescription(String description);
	
	
}
