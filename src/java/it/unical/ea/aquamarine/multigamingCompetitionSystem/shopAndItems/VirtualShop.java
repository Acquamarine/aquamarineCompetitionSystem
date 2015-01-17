package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.MarketItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
		OnDemandPersistenceManager.getInstance().initializeInventory(competitor);
		Map<ItemCategory, ItemSet> inventoryMap = competitor.getInventory().getInventoryMap();
		Map<ItemCategory, Set<MarketItem>> sellingItemsCopy = new HashMap<>();
		sellingItemsCopy.putAll(sellingItems);
		inventoryMap.keySet().stream().forEach((category) -> {
			sellingItemsCopy.get(category).removeAll(inventoryMap.get(category).getItems());
		});
		return sellingItemsCopy;
		
	}
        
        public Map<ItemCategory, Set<MarketItem>> getAvailableItems(ICompetitor competitor, String game) {
            Map<ItemCategory, Set<MarketItem>> availableItems = getAvailableItems(competitor);
            for(Set<MarketItem> items: availableItems.values()) {
                Iterator<MarketItem> iterator = items.iterator();
                while(iterator.hasNext()) {
                    if(!iterator.next().getGame().equals(game)) {
                        iterator.remove();
                    }
                }
            }
            return availableItems;
		
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
