package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;

public class OnDemandPersistenceManager {
	
	private final static OnDemandPersistenceManager instance = new OnDemandPersistenceManager();
	
	public static OnDemandPersistenceManager getInstance(){
		return instance;
	}
	
	public void updateCompetitor(ICompetitor competitor){
		DAOProvider.getCompetitorDAO().updateCompetitor(competitor);
	}
	
	public void initializeEquip(ICompetitor competitor){
		DAOProvider.getCompetitorDAO().initializeEquip(competitor);
	}

	public void initializeInventory(ICompetitor competitor) {
		DAOProvider.getCompetitorDAO().initializeInventory(competitor);
				
	}
	
}
