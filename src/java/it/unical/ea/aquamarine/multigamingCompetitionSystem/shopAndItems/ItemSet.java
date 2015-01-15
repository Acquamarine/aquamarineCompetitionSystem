
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.AbstractItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

@Entity
@Table(name="ItemSet")
public class ItemSet implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@ManyToMany(targetEntity = AbstractItem.class, fetch = FetchType.EAGER)
	private Set<IItem> items = new HashSet<>();

	public Set<IItem> getItems() {
		return items;
	}

	public void setItems(Set<IItem> items) {
		this.items = items;
	}

	public boolean contains(IItem item) {
		return items.contains(item);
	}

	public void add(IItem item) {
		items.add(item);
	}

	public int size() {
		return items.size();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	
}
