package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
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

public class UserDAOImplTest {
	
	private static UserDAOImpl instance = new UserDAOImpl();
	public UserDAOImplTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		Configuration configuration = new Configuration().configure("hibernate.cfg.test.xml");
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		instance.setSessionFactory(configuration.buildSessionFactory(builder.build()));
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
		RegisteredUser retrievedUser = instance.retrieveByNick("ciccioN");
		assertEquals("ciccioU", retrievedUser.getUsername());
		assertEquals("ciccioN", retrievedUser.getNickname());
	}

	/**
	 * Test of retrieveByNick method, of class UserDAOImpl.
	 */
	@Test
	public void testRetrieveByNick() {
		System.out.println("retrieveByNick");
		String nick = "";
		RegisteredUser expResult = null;
		RegisteredUser result = instance.retrieveByNick(nick);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of retrieveByUsername method, of class UserDAOImpl.
	 */
	@Test
	public void testRetrieveByUsername() {
		System.out.println("retrieveByUsername");
		String username = "";
		RegisteredUser expResult = null;
		RegisteredUser result = instance.retrieveByUsername(username);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of doesUserExistByUsername method, of class UserDAOImpl.
	 */
	@Test
	public void testDoesUserExistByUsername() {
		System.out.println("doesUserExistByUsername");
		String username = "";
		boolean expResult = false;
		boolean result = instance.doesUserExistByUsername(username);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of doesUserExistByNick method, of class UserDAOImpl.
	 */
	@Test
	public void testDoesUserExistByNick() {
		System.out.println("doesUserExistByNick");
		String nick = "";
		boolean expResult = false;
		boolean result = instance.doesUserExistByNick(nick);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
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
		RegisteredUser retrievedUser = instance.retrieveByNick("ciccioEloTestN");
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
		List<Pair<String, Integer>> userRanking = instance.getUsersRanking("tressette1v1");
		assertTrue(1500==userRanking.get(0).getValue());
		assertTrue(1100==userRanking.get(1).getValue());
		System.out.println("");
	}
	
	
	
}
