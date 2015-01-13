package it.unical.ea.aquamarine.multigamingCompetitionSystem.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.MultigamingBlManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemsProvider;
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
		//ItemsProvider.getInstance().init();
		MultigamingBlManager.getInstance().startQueues();
		MultigamingBlManager.getInstance().addGameManager(Tressette1v1.class.getSimpleName(), TressetteGameManager.getInstance());
		MultigamingBlManager.getInstance().addGameManager(Tressette1v1.class.getSimpleName()+"normal", TressetteGameManager.getInstance());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
