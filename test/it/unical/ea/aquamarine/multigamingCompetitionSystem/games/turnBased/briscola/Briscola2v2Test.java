package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.IGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Bernardoc
 */
public class Briscola2v2Test {
	
	public Briscola2v2Test() {
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
	public void computeHandWinner() {
		System.out.println("test hands size");
		List<Integer> players = new ArrayList<>();
		List<Integer> teams = new ArrayList<>();
		players.add(0);
		players.add(1);
		players.add(2);
		players.add(3);
		teams.add(4);
		teams.add(5);
		Briscola2v2 instance = new Briscola2v2(players, teams, true);
		NeapolitanCard briscolaCard = instance.getBriscolaCard();
		instance.getTable().add(new NeapolitanCard((briscolaCard.getSeed()+2)%4, 2));
		instance.getTable().add(new NeapolitanCard((briscolaCard.getSeed()+1)%4, 3));
		instance.getTable().add(new NeapolitanCard((briscolaCard.getSeed())%4, 10));
		instance.getTable().add(new NeapolitanCard((briscolaCard.getSeed()+3)%4, 1));
		assertTrue(instance.computeHandWinner()==1);
	}
	
}
