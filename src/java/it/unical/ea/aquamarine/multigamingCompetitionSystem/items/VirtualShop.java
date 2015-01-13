package it.unical.ea.aquamarine.multigamingCompetitionSystem.items;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VirtualShop {

	private static VirtualShop instance = new VirtualShop();
	private Map<ItemCategory, Set<MarketItem>> sellingItems = new HashMap<>();
	
	public static VirtualShop getInstance() {
		return instance;
	}

	private VirtualShop() {
		for(ItemCategory category: ItemCategory.values()) {
			sellingItems.put(category, new HashSet<>());
		}
	}
	

	public Map<ItemCategory, Set<MarketItem>> getSellingItems() {
		return sellingItems;
	}

	public void setSellingItems(Map<ItemCategory, Set<MarketItem>> sellingItems) {
		this.sellingItems = sellingItems;
	}
	
	public void addItemToShop(MarketItem itemToAdd) {
		sellingItems.get(itemToAdd.getCategory()).add(itemToAdd);
	}
	
	public Map<ItemCategory, Set<MarketItem>> getAvailableItems(ICompetitor competitor) {
		Map<ItemCategory, Set<IItem>> inventoryMap = competitor.getInventory().getInventoryMap();
		Map<ItemCategory, Set<MarketItem>> sellingItemsCopy = new HashMap<>();
		sellingItemsCopy.putAll(sellingItems);
		inventoryMap.keySet().stream().forEach((category) -> {
			sellingItemsCopy.get(category).removeAll(inventoryMap.get(category));
		});
		return sellingItemsCopy;
		
	}
	
	public void buyItem(ICompetitor competitor, MarketItem item) {
		if(getAvailableItems(competitor).get(item.getCategory()).contains(item)) {
			if(competitor.getVirtualPoints() >= item.getVirtualPointsPrice()) {
				competitor.gainVirtualPoints(-item.getVirtualPointsPrice());
				competitor.getInventory().addItem(item);
			}
		}
	}
	
	
}
