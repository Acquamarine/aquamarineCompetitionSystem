
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
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
			if(tx != null){
				tx.rollback();
			}
		}finally{
			session.close();
		}
	}

	@Override
	public List<TwoCompetitorsMatchResult> retrieveCompetitorMatches(ICompetitor competitor) {
		Session session = sessionFactory.openSession();
		String queryString = "from TwoCompetitorsMatchResult as res  where res.player1= :player or res.player2= :player order by res.matchEndTime desc";
		Query query = session.createQuery(queryString);
		query.setParameter("player", competitor);
		List<Object> competitorMatches = (List<Object>)query.list();
		List<TwoCompetitorsMatchResult> returningList = new ArrayList<>();
		competitorMatches.stream().forEach((competitorMatch) -> {
			returningList.add((TwoCompetitorsMatchResult)competitorMatch);
		});
		session.close();
		return returningList;
	}

}
