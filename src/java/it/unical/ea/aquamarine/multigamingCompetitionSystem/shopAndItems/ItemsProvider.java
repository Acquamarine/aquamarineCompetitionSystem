package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.ItemDAO;
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
		ConfigReader xmlReader = new ConfigReader(GameConstants.ITEMS_CONFIG_PATH);
		Collection<ConfigReader> itemsReaders = xmlReader.getConfigReaderList("item");
		ItemDAO itemDAO = DAOProvider.getItemDAO();
		for(ConfigReader itemReader : itemsReaders){
			IItem readingItem;
			String itemType = itemReader.getString("type");
			switch(itemType){
				case "market":
					readingItem = new MarketItem();
					readingItem.setId(itemReader.getInteger("id"));
					shopItemsMap.put(readingItem.getId(), (MarketItem) readingItem);
					((MarketItem) readingItem).setVirtualPointsPrice(itemReader.getInteger("price"));
					break;
				default: //eloReward
					readingItem = new EloRewardItem();
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
			itemDAO.create(readingItem);
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

	public IItem getItem(int id) {
		if(eloRewardItemsMap.containsKey(id)){
			return eloRewardItemsMap.get(id);
		}
		return shopItemsMap.get(id);
	}
}
