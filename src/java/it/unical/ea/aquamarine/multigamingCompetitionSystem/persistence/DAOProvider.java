package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DaoProvider implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static CompetitorDAO getUserDAO() {
		return (CompetitorDAO) context.getBean("userDAO");
	}

	@Override
	public void setApplicationContext(ApplicationContext context) {
		DaoProvider.context = context;
	}

	
	
}
