
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.invitations.Invitation;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class InvitationsDAOImpl implements InvitationsDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void create(Invitation invitation) {
		if(doesInvitationExists(invitation.getInvitingTeam(), invitation.getInvitedUser())){
			return;
		}
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(invitation);
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
	public void delete(Team team, RegisteredUser user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String queryString = "delete from Invitation where invitedUser = :user and invitingTeam = :team";
			Query query = session.createQuery(queryString);
			query.setParameter("user", user);
			query.setParameter("team", team);
			query.executeUpdate();
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
	public List<Invitation> retrieve(RegisteredUser invited) {
		Session session = sessionFactory.openSession();
		String queryString = "from Invitation where invitedUser = :invited";
		Query query = session.createQuery(queryString);
		query.setParameter("invited", invited);
		List<Invitation> invitations = (List<Invitation>) query.list();
		session.close();
		return invitations;
	}

	@Override
	public List<Invitation> retrieve(Team inviting) {
		Session session = sessionFactory.openSession();
		String queryString = "from Invitation where invitingTeam = :inviting";
		Query query = session.createQuery(queryString);
		query.setParameter("inviting", inviting);
		List<Invitation> invitations = (List<Invitation>) query.list();
		session.close();
		return invitations;
	}

	@Override
	public boolean doesInvitationExists(Team invitingTeam, RegisteredUser invitedUser) {
		Session session = sessionFactory.openSession();
		String queryString = "from Invitation where invitingTeam = :inviting and invitedUser = :invited";
		Query query = session.createQuery(queryString);
		query.setParameter("inviting", invitingTeam);
		query.setParameter("invited", invitedUser);
		Invitation invitation = (Invitation) query.uniqueResult();
		session.close();
		return invitation!=null;
	}
	
}
