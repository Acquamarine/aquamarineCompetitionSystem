
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CompetitorInventory {

	private Map<ItemCategory, Set<IItem>> inventoryMap = new HashMap<>();
	
	public boolean containsItem(IItem item) {
		return  inventoryMap.get(item.getCategory()).contains(item);
	}

	public Map<ItemCategory, Set<IItem>> getInventoryMap() {
		return inventoryMap;
	}

	public void setInventoryMap(Map<ItemCategory, Set<IItem>> inventoryMap) {
		this.inventoryMap = inventoryMap;
	}

	public void addItem(IItem item) {
		Set<IItem> sameCategorySet = inventoryMap.get(item.getCategory());
		if(sameCategorySet == null) {
			sameCategorySet = new HashSet<>();
		}
		sameCategorySet.add(item);
		inventoryMap.put(item.getCategory(), sameCategorySet);
	}
	
	
	
	
}
