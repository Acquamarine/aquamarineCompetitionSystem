package it.unical.ea.aquamarine.multigamingCompetitionSystem.core;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.MultigamingBlManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemsProvider;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resource = loader.getResource("/../config/items.xml");
		try{
			GameConstants.ITEMS_CONFIG_PATH=resource.toURI();
		}catch(URISyntaxException ex){
			Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		ItemsProvider.getInstance().init();
		MultigamingBlManager.getInstance().startQueues();
		MultigamingBlManager.getInstance().addGameManager(Tressette1v1.class.getSimpleName(), TressetteGameManager.getInstance());
		MultigamingBlManager.getInstance().addGameManager(Tressette1v1.class.getSimpleName()+"normal", TressetteGameManager.getInstance());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
