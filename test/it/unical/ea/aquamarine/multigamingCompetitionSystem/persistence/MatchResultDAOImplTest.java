package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Denise
 */
public class MatchResultDAOImplTest {
	
	private static MatchResultDAOImpl matchInstance;
	private static CompetitorsDAOImpl userInstance;
	
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
		matchInstance = new MatchResultDAOImpl();
		userInstance = new CompetitorsDAOImpl();
		Configuration configuration = new Configuration().configure("hibernate.cfg.test.xml");
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		matchInstance.setSessionFactory(sessionFactory);
		userInstance.setSessionFactory(sessionFactory);
	
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
