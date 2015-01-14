
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.AbstractItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="ItemSet")
public class ItemSet implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@ElementCollection(targetClass = AbstractItem.class)
	@CollectionTable(name="Items", joinColumns=@JoinColumn(name="itemset_id"))
	@AttributeOverrides({
	   @AttributeOverride(name="id", column=@Column(name="item_id"))
	})
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
