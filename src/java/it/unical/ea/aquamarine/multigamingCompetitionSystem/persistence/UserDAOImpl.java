package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDAOImpl implements UserDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(RegisteredUser user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(user);
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
	public RegisteredUser retrieveByNick(String nick) {
		Session session = sessionFactory.openSession();
		String queryString = "from AbstractCompetitor where nickname = :user";
		Query query = session.createQuery(queryString);
		query.setParameter("user", nick);
		RegisteredUser u = (RegisteredUser) query.uniqueResult();
		session.close();
		return u;
	}

	@Override
	public RegisteredUser retrieveByUsername(String username) {
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
	public boolean doesUserExistByNick(String nick) {
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


}