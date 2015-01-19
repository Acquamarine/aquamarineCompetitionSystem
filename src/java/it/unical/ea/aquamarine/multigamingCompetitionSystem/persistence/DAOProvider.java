package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DAOProvider implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static CompetitorDAO getCompetitorDAO() {
		return (CompetitorDAO) context.getBean("competitorDAO");
	}

	public static MatchResultDAO getMatchResultsDAO() {
		return (MatchResultDAO) context.getBean("matchResultsDAO");
	}
	
	public static ItemDAO getItemDAO() {
		return (ItemDAO) context.getBean("itemDAO");
	}
	
	public static InvitationsDAO getInvitationsDAO() {
		return (InvitationsDAO) context.getBean("invitationsDAO");
	}

	@Override
	public void setApplicationContext(ApplicationContext context) {
		DAOProvider.context = context;
	}
	
	public static void setContext(ApplicationContext context) {
		DAOProvider.context = context;
	}

	
	
}
