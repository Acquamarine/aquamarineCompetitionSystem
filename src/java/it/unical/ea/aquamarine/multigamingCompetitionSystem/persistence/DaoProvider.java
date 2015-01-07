package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.UserDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DaoProvider implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static UserDAO getUserDAO() {
		return (UserDAO) context.getBean("userDAO");
	}

	@Override
	public void setApplicationContext(ApplicationContext context) {
		DaoProvider.context = context;
	}

	
	
}
