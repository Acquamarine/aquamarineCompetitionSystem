/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bernardoc
 */
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
		List<String> players = new ArrayList<>();
		players.add("ciccio");
		players.add("pippo");
		Tressette1v1 instance = new Tressette1v1(players);
		assertEquals(10, instance.getHands().get("ciccio").getHandCards().size());
	}


	
	
}
