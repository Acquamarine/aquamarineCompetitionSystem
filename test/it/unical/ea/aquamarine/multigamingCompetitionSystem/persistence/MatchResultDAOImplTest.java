package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemsProvider;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Denise
 */
public class MatchResultDAOImplTest {
	
	private static MatchResultDAO matchInstance;
	private static CompetitorDAO userInstance;
	
	public MatchResultDAOImplTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		GameConstants.ITEMS_CONFIG_PATH = new File("itemsTesting.xml").toURI();
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		DAOProvider.setContext(context);
                ItemsProvider.getInstance().init();
		userInstance = DAOProvider.getCompetitorDAO();
		matchInstance = DAOProvider.getMatchResultsDAO();
	
	}
	
	@After
	public void tearDown() {
	}

    @Test
	public void testCreate() {
		System.out.println("create");
		RegisteredUser user = new RegisteredUser();
		user.setNickname("ciccioN");
		user.setUsername("ciccioU");
		user.setPassword("ciccioP");
		userInstance.create(user);
		TwoCompetitorsMatchResult res = new TwoCompetitorsMatchResult();
		res.setPlayer1(user);
		res.setPlayer2(user);
		res.setGame(Tressette1v1.class.getSimpleName());
		res.setPlayer1Score(5);
		res.setPlayer2Score(7);
		res.setRankedMatch(true);
		res.setMatchEndTimeByMillis(System.currentTimeMillis());
		matchInstance.create(res);
		try{
			Thread.sleep(1000);
		}catch(InterruptedException ex){
			Logger.getLogger(MatchResultDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		TwoCompetitorsMatchResult res2 = new TwoCompetitorsMatchResult();
		res2.setPlayer1(user);
		res2.setPlayer2(user);
		res2.setGame(Tressette1v1.class.getSimpleName());
		res2.setPlayer1Score(4);
		res2.setPlayer2Score(8);
		res2.setRankedMatch(true);
		res2.setMatchEndTimeByMillis(System.currentTimeMillis());
		matchInstance.create(res2);
		List<TwoCompetitorsMatchResult> retrieveCompetitorMatches = matchInstance.retrieveCompetitorMatches(user,Tressette1v1.class.getSimpleName());
		assertTrue(4==retrieveCompetitorMatches.get(0).getPlayer1Score());
		assertTrue(5==retrieveCompetitorMatches.get(1).getPlayer1Score());
		
	}
}
