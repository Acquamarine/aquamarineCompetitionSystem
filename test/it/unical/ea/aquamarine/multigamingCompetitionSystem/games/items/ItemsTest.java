package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.items;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.AbstractCompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.CompetitorInventory;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.ItemCategory;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.ItemsProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.MarketItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.VirtualShop;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ItemsTest {
	
	public ItemsTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		ItemsProvider.getInstance().init();
		GameConstants.ITEMS_CONFIG_PATH = "config/itemsTesting.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		DAOProvider.setContext(context);
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	
	@Test
	public void testItemsProvider() {
		MarketItem marketItem = ItemsProvider.getInstance().getMarketItem(1);
		assertTrue(marketItem.getName().equals("redCover"));
		assertTrue(marketItem.getDisplayName().equals("Fury Cover"));
		assertTrue(marketItem.getGame().equals("Tressette1v1"));
		assertTrue(marketItem.getCategory() == ItemCategory.CARD_COVER);
		assertTrue(marketItem.getVirtualPointsPrice()==50);
	}
    
	@Test 
	public void buyItemTest() {
		AbstractCompetitor competitor = new RegisteredUser();
		competitor.setNickname("ciccio");
		competitor.setVirtualPoints(200);
		competitor.setId(1);
		VirtualShop.getInstance().buyItem(competitor, ItemsProvider.getInstance().getMarketItem(1));
		assertTrue(competitor.getVirtualPoints() == 150);
		assertTrue(competitor.getInventory().getInventoryMap().get(ItemCategory.CARD_COVER).size()==1);
		assertTrue(competitor.getInventory().containsItem(ItemsProvider.getInstance().getMarketItem(1)));
		
		
	}
}
