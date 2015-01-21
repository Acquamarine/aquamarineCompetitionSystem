package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Tressette1v1Test {
	
	public Tressette1v1Test() {
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
	 * Test of playCard method, of class Tressette1v1.
	 */
	@Test
	public void testHandsSize() {
		System.out.println("test hands size");
		List<Integer> players = new ArrayList<>();
		players.add(0);
		players.add(1);
		Tressette1v1 instance = new Tressette1v1(players,true);
		assertEquals(10, instance.getHands().get(0).getHandCards().size());
	}


	
	
}
