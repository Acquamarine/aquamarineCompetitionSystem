
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemCategory;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public abstract class AbstractItem implements IItem, Serializable{

	protected int id;
	protected String game;
	protected ItemCategory category;
	protected String name;
	protected String displayName;
	

	@Override
	public void equip(ICompetitor competitor) {
		competitor.getEquip().get(game).equipItem(this);
	}
	
	@Override
	public void unequip(ICompetitor competitor) {
		competitor.getEquip().get(game).unequipItem(this);
	}

	@Override
	public ItemCategory getCategory() {
		return category;
	}

	@Override
	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	

	@Override
	public String getGame() {
		return game;
	}

	@Override
	public void setGame(String game) {
		this.game = game;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
}
