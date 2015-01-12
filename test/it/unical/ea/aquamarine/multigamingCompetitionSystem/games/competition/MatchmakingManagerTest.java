package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.MultigamingBlManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteGameManager;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		String playerNick = "Ciccio";
		RegisteredUser player = new RegisteredUser();
		player.setNickname(playerNick);
		MatchmakingManager instance = new MatchmakingManager();
		instance.addToQueue(game, player);
		assertEquals(instance.getQueuedCompetitorsMap().get(player).getCompetitor().getNickname(),playerNick);
	}

	/**
	 * Test of removeFromQueue method, of class MatchmakingManager.
	 */
	@Test
	public void testRemoveFromQueue() {
		System.out.println("addToQueue");
		String game = "Tressette";
		String playerNick = "ciccio";
		RegisteredUser player = new RegisteredUser();
		player.setNickname(playerNick);
		MatchmakingManager instance = new MatchmakingManager();
		instance.addToQueue(game, player);
		instance.removeFromQueue(game, player);
		// TODO review the generated test code and remove the default call to fail.
		assertTrue(instance.getQueuedCompetitorsMap().isEmpty());
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
		String game = Tressette1v1.class.getSimpleName();
		MultigamingBlManager.getInstance().addGameManager(game, new TressetteGameManager());
		MatchmakingManager instance = new MatchmakingManager();
		instance.startQueuesThread();
		String player1Nick = "ciccio";
		RegisteredUser player1 = new RegisteredUser();
		player1.setNickname(player1Nick);
		player1.setId(0);
		String player2Nick = "pippo";
		RegisteredUser player2 = new RegisteredUser();
		player2.setNickname(player2Nick);
		player2.setId(1);
		instance.addToQueue(game, player1);
		instance.addToQueue(game, player2);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ex) {
			Logger.getLogger(MatchmakingManagerTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		// TODO review the generated test code and remove the default call to fail.
		assertTrue(instance.getQueues().get(game).getQueuedCompetitors().size() == 0);
	}
	
}
