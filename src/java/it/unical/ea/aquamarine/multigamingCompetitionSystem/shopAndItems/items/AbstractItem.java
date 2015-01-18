
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemCategory;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="Item")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING) 
public abstract class AbstractItem implements IItem, Serializable{
	
	protected int id;
	protected String game;
	protected ItemCategory category;
	protected String name;
	protected String displayName;
	

	@Override
	public void equip(ICompetitor competitor) {
		competitor.getEquip(game).equipItem(this);
		DAOProvider.getCompetitorDAO().updateCompetitor(competitor);
	}
	
	@Override
	public void unequip(ICompetitor competitor) {
		competitor.getEquip().get(game).unequipItem(this);
		DAOProvider.getCompetitorDAO().updateCompetitor(competitor);
	}

	@Enumerated(EnumType.STRING)
	@Override
	public ItemCategory getCategory() {
		return category;
	}

	@Override
	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	@Id
	@Column(name = "id")
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	

	@Column(name = "game", nullable = false)
	@Override
	public String getGame() {
		return game;
	}

	@Override
	public void setGame(String game) {
		this.game = game;
	}

	@Column(name = "name", nullable = false, unique = true)
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "displayName", nullable = false, unique = true)
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 47 * hash + this.id;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		final AbstractItem other = (AbstractItem) obj;
		if(this.id != other.id){
			return false;
		}
		return true;
	}
	
	
}
