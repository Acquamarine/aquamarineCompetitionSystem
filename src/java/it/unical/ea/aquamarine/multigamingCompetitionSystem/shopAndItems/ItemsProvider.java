
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.EloRewardItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.MarketItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.ConfigReader;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ItemsProvider {
	private static ItemsProvider instance = new ItemsProvider();
	private Map<Integer, MarketItem> shopItemsMap = new HashMap<>();
	private Map<Integer, EloRewardItem> eloRewardItemsMap = new HashMap<>();
	
	public static ItemsProvider getInstance() {
		return instance;
	}
	
	public void init() {
		File catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile();
		File xmlFile = new File( catalinaBase, GameConstants.ITEMS_CONFIG_PATH );
		ConfigReader xmlReader = new ConfigReader(xmlFile.getAbsolutePath());
		Collection<ConfigReader> itemsReaders = xmlReader.getConfigReaderList("item");
		for(ConfigReader itemReader:itemsReaders) {
			IItem readingItem;
			String itemType = itemReader.getString("type");
			switch(itemType) {
				case "market":
					readingItem = new MarketItem();
					//TODO remove, use DAO
					readingItem.setId(itemReader.getInteger("id"));
					shopItemsMap.put(readingItem.getId(), (MarketItem) readingItem);
					((MarketItem) readingItem).setVirtualPointsPrice(itemReader.getInteger("price"));
					break;
				default: //eloReward
					readingItem = new EloRewardItem();
					//TODO remove, use DAO
					readingItem.setId(itemReader.getInteger("id"));
					eloRewardItemsMap.put(readingItem.getId(), (EloRewardItem) readingItem);
					((EloRewardItem) readingItem).setUnlockingElo(itemReader.getInteger("elo"));
					break;
			}
			
			readingItem.setGame(itemReader.getString("game"));
			readingItem.setName(itemReader.getString("name"));
			readingItem.setDisplayName(itemReader.getString("displayName"));
			String category = itemReader.getString("category");
			readingItem.setCategory(ItemCategory.getItemCategory(category));
		}
		initShop();
	}
	
	public MarketItem getMarketItem(int id) {
		return shopItemsMap.get(id);
	}
	
	public EloRewardItem getEloRewardItem(int id) {
		return eloRewardItemsMap.get(id);
	}

	private void initShop() {
		shopItemsMap.values().stream().forEach((marketItem) -> {
			VirtualShop.getInstance().addItemToShop(marketItem);
		});
	}
	
}
