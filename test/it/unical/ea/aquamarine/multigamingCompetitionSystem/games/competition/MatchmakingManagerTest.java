package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.MultigamingBlManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.TressetteGameManager;
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
		MultigamingBlManager.getInstance().addGameManager(Tressette1v1.class.getSimpleName(), TressetteGameManager.getInstance());
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
	
	/**
	 * Test of removeFromQueue method, of class MatchmakingManager.
	 */
	@Test
	public void testAddAndRemoveFromQueue() {
		System.out.println("addToQueue");
		String game = "Tressette1v1";
		String playerNick = "ciccio";
		RegisteredUser player = new RegisteredUser();
		player.setNickname(playerNick);
		MatchmakingManager instance = new MatchmakingManager();
		instance.addToQueue(game, player, null);
		instance.removeFromQueue(game, player);
		assertTrue(instance.getQueuedCompetitorsMap().isEmpty());
	}

	/**
	 * Test of startQueuesThread method, of class MatchmakingManager.
	 */
	@Test
	public void testStartQueuesThread() {
		System.out.println("startQueuesThread");
		String game = Tressette1v1.class.getSimpleName();
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
		instance.addToQueue(game, player1, null);
		instance.addToQueue(game, player2, null);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			Logger.getLogger(MatchmakingManagerTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		assertTrue(instance.getQueues().get(game).getQueuedCompetitors().size() == 0);
	}
	
}
