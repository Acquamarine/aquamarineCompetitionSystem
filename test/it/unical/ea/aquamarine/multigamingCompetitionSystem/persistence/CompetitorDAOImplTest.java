package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.CompetitorInventory;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemsProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.MarketItem;
import java.io.File;
import java.util.List;
import javafx.util.Pair;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CompetitorDAOImplTest {
	
	private static CompetitorsDAOImpl instance;
	private static ItemDAOImpl itemInstance;
	
	public CompetitorDAOImplTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		GameConstants.ITEMS_CONFIG_PATH = new File("itemsTesting.xml").toURI();
		//ItemsProvider.getInstance().init();
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		instance = new CompetitorsDAOImpl();
		itemInstance = new ItemDAOImpl();
		Configuration configuration = new Configuration().configure("hibernate.cfg.test.xml");
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory buildSessionFactory = configuration.buildSessionFactory(builder.build());
		instance.setSessionFactory(buildSessionFactory);
		itemInstance.setSessionFactory(buildSessionFactory);
	}
	
	@After
	public void tearDown() {
		
	}


	/**
	 * Test of create method, of class CompetitorDAOImpl.
	 */
	@Test
	public void testCreate() {
		System.out.println("create");
		RegisteredUser user = new RegisteredUser();
		user.setNickname("ciccioN");
		user.setUsername("ciccioU");
		user.setPassword("ciccioP");
		Team ciccioTeam = new Team();
		ciccioTeam.setNickname("CiccioTeam");				
		ciccioTeam.addMember(user);
		user.addTeam(ciccioTeam);
		//MarketItem marketItem = ItemsProvider.getInstance().getMarketItem(1);
		assertTrue(user.getInventory().getInventoryMap().isEmpty());
		/*user.getInventory().addItem(marketItem);
		marketItem.equip(user);
		itemInstance.create(marketItem);*/
		instance.create(user);
		RegisteredUser retrievedUser = (RegisteredUser) instance.retrieveByNick("ciccioN");
		assertEquals("ciccioU", retrievedUser.getUsername());
		assertEquals("ciccioN", retrievedUser.getNickname());
		//assertTrue(retrievedUser.getEquip("Tressette1v1").getEquipMap().size()==1);
		instance.updateCompetitor(retrievedUser);
		Team retrievedTeam = (Team) instance.retrieveByNick("CiccioTeam");
		assertEquals("CiccioTeam", retrievedTeam.getNickname());
		
	}

	/**
	 * Test of retrieveByNick method, of class CompetitorDAOImpl.
	 */
	@Test
	public void testRetrieveByNick() {
		System.out.println("retrieveByNick");
		RegisteredUser user = new RegisteredUser();
		user.setUsername("ciccio");
		user.setNickname("pasticcio");
		instance.create(user);
		ICompetitor competitor = instance.retrieveByNick("pasticcio");
		assertTrue(competitor.getNickname().equals(user.getNickname()));
	}

	/**
	 * Test of retrieveByUsername method, of class CompetitorDAOImpl.
	 */
	@Test
	public void testRetrieveByUsername() {
		System.out.println("doesUserExistByUsername");
		RegisteredUser user = new RegisteredUser();
		user.setUsername("ciccio");
		user.setNickname("pasticcio");
		instance.create(user);
		ICompetitor competitor = instance.retrieveByUsername("ciccio");
		assertTrue(competitor.getNickname().equals(user.getNickname()));
	}

	/**
	 * Test of doesUserExistByUsername method, of class CompetitorDAOImpl.
	 */
	@Test
	public void testDoesUserExistByUsername() {
		System.out.println("doesUserExistByUsername");
		RegisteredUser user = new RegisteredUser();
		user.setUsername("ciccio");
		user.setNickname("pasticcio");
		instance.create(user);
		boolean result = instance.doesUserExistByUsername("ciccio");
		assertTrue(result);
		
	}

	/**
	 * Test of doesUserExistByNick method, of class CompetitorDAOImpl.
	 */
	@Test
	public void testDoesUserExistByNick() {
		System.out.println("doesUserExistByNick");
		RegisteredUser user = new RegisteredUser();
		user.setUsername("ciccio");
		user.setNickname("pasticcio");
		instance.create(user);
		boolean result = instance.doesCompetitorExistByNick("pasticcio");
		assertTrue(result);
	}
	@Test
	public void testUserElo() {
		RegisteredUser user = new RegisteredUser();
		user.setNickname("ciccioEloTestN");
		user.setUsername("ciccioEloTestU");
		user.setPassword("ciccioEloTestP");
		user.getCompetitionProfile().put("tressette1v1",1500);
		instance.create(user);
		// TODO review the generated test code and remove the default call to fail.
		RegisteredUser retrievedUser = (RegisteredUser) instance.retrieveByNick("ciccioEloTestN");
		assertTrue(1500==retrievedUser.getElo("tressette1v1"));
	}
	
	@Test
	public void testUserRanking() {
		RegisteredUser user = new RegisteredUser();
		RegisteredUser user1 = new RegisteredUser();
		user.setNickname("pasticcioRankingTestN");
		user.setUsername("pasticcioRankingTestU");
		user.setPassword("pasticcioRankingTestP");
		user1.setNickname("cioRankingTestN");
		
		user.getCompetitionProfile().put("tressette1v1",1500);
		user1.getCompetitionProfile().put("tressette1v1",1100);
		instance.create(user);
		instance.create(user1);
		// TODO review the generated test code and remove the default call to fail.
		List<Pair<ICompetitor, Integer>> userRanking = instance.getCompetitorRanking("tressette1v1");
		assertTrue(1500==userRanking.get(0).getValue());
		assertTrue(1100==userRanking.get(1).getValue());
		System.out.println("");
	}
	
	@Test
	public void testUpdateCompetitor() {
		RegisteredUser userToUpdate = new RegisteredUser();
		userToUpdate.setNickname("ciccio");
		instance.create(userToUpdate);
		userToUpdate.getCompetitionProfile().put("tressette1v1", 1500);
		instance.updateCompetitor(userToUpdate);
		assertTrue(1500 == instance.retrieveByNick("ciccio").getElo("tressette1v1"));
		userToUpdate.getCompetitionProfile().put("tressette1v1", 2000);
		instance.updateCompetitor(userToUpdate);
		assertTrue(2000 == instance.retrieveByNick("ciccio").getElo("tressette1v1"));
		
		
	}
	
	@Test
	public void testVirtualPoints() {
		RegisteredUser userToUpdate = new RegisteredUser();
		userToUpdate.setNickname("ciccio");
		instance.create(userToUpdate);
		userToUpdate.getCompetitionProfile().put("tressette1v1", 1500);
		userToUpdate.setVirtualPoints(200);
		instance.updateCompetitor(userToUpdate);
		assertTrue(1500 == instance.retrieveByNick("ciccio").getElo("tressette1v1"));
		assertTrue(200 == instance.retrieveByNick("ciccio").getVirtualPoints());
		userToUpdate.getCompetitionProfile().put("tressette1v1", 2000);
		instance.updateCompetitor(userToUpdate);
		assertTrue(2000 == instance.retrieveByNick("ciccio").getElo("tressette1v1"));
		
		
	}
	
}
