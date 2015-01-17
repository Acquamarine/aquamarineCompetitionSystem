
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CompetitorInventory")
public class CompetitorInventory implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="inventoryId")
	@MapKeyEnumerated(EnumType.STRING)
	private Map<ItemCategory, ItemSet> inventoryMap = new HashMap<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean containsItem(IItem item) {
		if(inventoryMap.get(item.getCategory())==null){
			return false;
		}
		return  inventoryMap.get(item.getCategory()).contains(item);
	}

	public Map<ItemCategory, ItemSet> getInventoryMap() {
		return inventoryMap;
	}

	public void setInventoryMap(Map<ItemCategory, ItemSet> inventoryMap) {
		this.inventoryMap = inventoryMap;
	}

	public void addItem(IItem item) {
		ItemSet sameCategorySet = inventoryMap.get(item.getCategory());
		if(sameCategorySet == null) {
			sameCategorySet = new ItemSet();
		}
		sameCategorySet.add(item);
		inventoryMap.put(item.getCategory(), sameCategorySet);
	}
	
	
	
}
