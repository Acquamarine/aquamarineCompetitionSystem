package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.Player;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MatchmakingManagerTest {
	
	public MatchmakingManagerTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
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
	 * Test of addToQueue method, of class MatchmakingManager.
	 */
	@Test
	public void testAddToQueue() {
		System.out.println("addToQueue");
		String game = "Tressette";
		String player = "Ciccio";
		ICompetitor competitor = new Player(player);
		MatchmakingManager instance = MatchmakingManager.getInstance();
		instance.addToQueue(game, competitor);
		assertEquals(instance.getQueuedCompetitorsMap().get(competitor).getCompetitor().getId(),player);
	}

	/**
	 * Test of removeFromQueue method, of class MatchmakingManager.
	 */
	@Test
	public void testRemoveFromQueue() {
		System.out.println("addToQueue");
		String game = "Tressette";
		String player = "Ciccio";
		ICompetitor competitor = new Player(player);
		MatchmakingManager instance = MatchmakingManager.getInstance();
		instance.addToQueue(game, competitor);
		assertEquals(instance.getQueuedCompetitorsMap().get(competitor).getCompetitor().getId(),player);
		instance.removeFromQueue(game, competitor);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getQueuedCompetitorsMap method, of class MatchmakingManager.
	 */
	@Test
	public void testGetQueuedCompetitorsMap() {
		System.out.println("getQueuedCompetitorsMap");
		MatchmakingManager instance = new MatchmakingManager();
		HashMap<ICompetitor, QueuedCompetitor> expResult = null;
		HashMap<ICompetitor, QueuedCompetitor> result = instance.getQueuedCompetitorsMap();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of possiblyCreateMatches method, of class MatchmakingManager.
	 */
	@Test
	public void testPossiblyCreateMatches() {
		System.out.println("possiblyCreateMatches");
		MatchmakingManager instance = new MatchmakingManager();
		instance.possiblyCreateMatches();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of startQueuesThread method, of class MatchmakingManager.
	 */
	@Test
	public void testStartQueuesThread() {
		System.out.println("startQueuesThread");
		MatchmakingManager instance = new MatchmakingManager();
		instance.startQueuesThread();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
	
}
