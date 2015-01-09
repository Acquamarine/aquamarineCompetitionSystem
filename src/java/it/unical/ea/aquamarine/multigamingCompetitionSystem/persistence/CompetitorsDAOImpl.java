package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CompetitorsDAOImpl implements CompetitorDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(ICompetitor competitor) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(competitor);
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
	public ICompetitor retrieveByNick(String nick) {
		Session session = sessionFactory.openSession();
		String queryString = "from AbstractCompetitor where nickname = :user";
		Query query = session.createQuery(queryString);
		query.setParameter("user", nick);
		RegisteredUser u = (RegisteredUser) query.uniqueResult();
		session.close();
		return u;
	}

	@Override
	public ICompetitor retrieveByUsername(String username) {
		Session session = sessionFactory.openSession();
		String queryString = "from AbstractCompetitor where username = :user";
		Query query = session.createQuery(queryString);
		query.setParameter("user", username);
		RegisteredUser u = (RegisteredUser) query.uniqueResult();
		session.close();
		return u;
	}

	@Override
	public boolean doesUserExistByUsername(String username) {
		Session session = sessionFactory.openSession();
		String queryString = "from AbstractCompetitor where username = :user";
		Query query = session.createQuery(queryString);
		query.setParameter("user", username);
		if(query.list().isEmpty()){
			session.close();
			return false;
		}
		session.close();
		return true;
	}

	@Override
	public boolean doesCompetitorExistByNick(String nick) {
		Session session = sessionFactory.openSession();
		String queryString = "from AbstractCompetitor where nickname = :nickname";
		Query query = session.createQuery(queryString);
		query.setParameter("nickname", nick);
		if(query.list().isEmpty()){
			session.close();
			return false;
		}
		session.close();
		return true;
	}

	@Override
	public List<Pair<String, Integer>> getCompetitorRanking(String game) {
		Session session = sessionFactory.openSession();
		String queryString = "select c.nickname, VALUE(c.competitionProfile) from AbstractCompetitor as c  where KEY(c.competitionProfile)= :game order by VALUE(c.competitionProfile) desc";
		Query query = session.createQuery(queryString);
		query.setParameter("game", game);
		List<Object[]> usersRanking = (List<Object[]>)query.list();
		List<Pair<String, Integer>> returningList = new ArrayList<>();
		usersRanking.stream().forEach((userRanking) -> {
			returningList.add(new Pair<>((String) userRanking[0], (Integer) userRanking[1] ));
		});
		session.close();
		return returningList;
	}
	
	@Override
	public ICompetitor retrieveById(Integer id) {
		Session session = sessionFactory.openSession();
		String queryString = "from AbstractCompetitor where id = :id";
		Query query = session.createQuery(queryString);
		query.setParameter("id", id);
		RegisteredUser u = (RegisteredUser) query.uniqueResult();
		session.close();
		return u;
	}

	@Override
	public void updateCompetitor(ICompetitor competitor) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.update(competitor);
			tx.commit();
		}catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
		}finally{
			session.close();
		}
	}


}