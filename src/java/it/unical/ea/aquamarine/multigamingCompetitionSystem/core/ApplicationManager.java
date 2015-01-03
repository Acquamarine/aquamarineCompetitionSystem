package it.unical.ea.aquamarine.multigamingCompetitionSystem.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.MultigamingBlManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ApplicationManager implements ServletContextListener {
	private static ApplicationManager instance;
	
	public static ApplicationManager getInstance() {
		if(instance == null) {
			instance = new ApplicationManager();
		}
		return instance;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		MultigamingBlManager.getInstance().startQueues();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
