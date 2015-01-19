
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class MatchResultDAOImpl implements MatchResultDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void create(TwoCompetitorsMatchResult score) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(score);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(tx != null){
				tx.rollback();
			}
		}finally{
			session.close();
		}
	}

	@Override
	public List<TwoCompetitorsMatchResult> retrieveCompetitorMatches(ICompetitor competitor, String game) {
		Session session = sessionFactory.openSession();
		String queryString = "from TwoCompetitorsMatchResult as res  where (res.player1= :player or res.player2= :player) and game= :game order by res.matchEndTime desc";
		Query query = session.createQuery(queryString);
		query.setParameter("player", competitor);
		query.setParameter("game", game);
		List<Object> competitorMatches = (List<Object>)query.list();
		List<TwoCompetitorsMatchResult> returningList = new ArrayList<>();
		competitorMatches.stream().forEach((competitorMatch) -> {
			returningList.add((TwoCompetitorsMatchResult)competitorMatch);
		});
		session.close();
		return returningList;
	}

	@Override
	public Pair<Integer, Integer> retrieveDefeatsAndVictories(ICompetitor competitor, String game) {
		List<TwoCompetitorsMatchResult> matchHistory = retrieveCompetitorMatches(competitor, game);
		int victory = 0;
		int defeats = 0;
		for(TwoCompetitorsMatchResult result:matchHistory) {
			if(result.getWinner().equals(competitor)) {
				victory++;
			}
			else {
				defeats++;
			}
		}
		return new Pair<>(defeats, victory);
	}

	
	
	

}
