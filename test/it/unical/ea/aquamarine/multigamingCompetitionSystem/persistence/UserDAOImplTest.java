package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.List;
import javafx.util.Pair;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOImplTest {
	
	private static CompetitorsDAOImpl instance;
	
	public UserDAOImplTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		instance = new CompetitorsDAOImpl();
		Configuration configuration = new Configuration().configure("hibernate.cfg.test.xml");
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		instance.setSessionFactory(configuration.buildSessionFactory(builder.build()));
	}
	
	@After
	public void tearDown() {
		
	}


	/**
	 * Test of create method, of class UserDAOImpl.
	 */
	@Test
	public void testCreate() {
		System.out.println("create");
		RegisteredUser user = new RegisteredUser();
		user.setNickname("ciccioN");
		user.setUsername("ciccioU");
		user.setPassword("ciccioP");
		instance.create(user);
		// TODO review the generated test code and remove the default call to fail.
		RegisteredUser retrievedUser = (RegisteredUser) instance.retrieveByNick("ciccioN");
		assertEquals("ciccioU", retrievedUser.getUsername());
		assertEquals("ciccioN", retrievedUser.getNickname());
	}

	/**
	 * Test of retrieveByNick method, of class UserDAOImpl.
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
	 * Test of retrieveByUsername method, of class UserDAOImpl.
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
	 * Test of doesUserExistByUsername method, of class UserDAOImpl.
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
	 * Test of doesUserExistByNick method, of class UserDAOImpl.
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
		List<Pair<String, Integer>> userRanking = instance.getCompetitorRanking("tressette1v1");
		assertTrue(1500==userRanking.get(0).getValue());
		assertTrue(1100==userRanking.get(1).getValue());
		System.out.println("");
	}
	
	@Test
	public void testUpdateCompetitor() {
		RegisteredUser userToUpdate = new RegisteredUser();
		userToUpdate.setNickname("ciccio");
		instance.create(userToUpdate);
		userToUpdate.updateElo("tressette1v1", 1500);
		instance.updateCompetitor(userToUpdate);
		assertTrue(1500 == instance.retrieveByNick("ciccio").getElo("tressette1v1"));
		userToUpdate.updateElo("tressette1v1", 2000);
		instance.updateCompetitor(userToUpdate);
		assertTrue(2000 == instance.retrieveByNick("ciccio").getElo("tressette1v1"));
		
		
	}
	
}
