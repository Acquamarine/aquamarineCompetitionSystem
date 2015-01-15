package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class ItemDAOImpl implements ItemDAO{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	@Override
	public void create(IItem item){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(item);
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
