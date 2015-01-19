
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.invitations.Invitation;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/teams")
public class TeamsController {

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String teams(Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
			return "redirect:login";
		}
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		Map<String, List<Invitation>> invitationsMap = new HashMap<>();
		for(Team team: user.getTeams()) {
			invitationsMap.put(team.getNickname(), DAOProvider.getInvitationsDAO().retrieve(team));
		}
		model.addAttribute("invitationsMap", invitationsMap);
		List<Invitation> myInvitations = DAOProvider.getInvitationsDAO().retrieve(user);
		model.addAttribute("myInvitations", myInvitations);
		return "/teams";
	}
	
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"team", "invitedUser"})
	public String inviteUserInTeam(Model model, HttpServletRequest request, @RequestParam("team") String teamNick, @RequestParam("invitedUser") String userNick) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
			return "redirect:login";
		}
		//TODO check whether the inviting user is in the inviting team
		Team team = (Team) CompetitionManager.getInstance().getCompetitorByNick(teamNick);   //(Team)DAOProvider.getCompetitorDAO().retrieveByNick(teamNick);
		RegisteredUser user = (RegisteredUser) CompetitionManager.getInstance().getCompetitorByNick(userNick);
		DAOProvider.getInvitationsDAO().create(new Invitation(user, team));
		return "redirect:teams";
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"accept"})
	public String acceptTeamInvitation(Model model, HttpServletRequest request, @RequestParam("accept") String teamNick) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
			return "redirect:login";
		}
		//TODO check whether the invited user has really been invited in the inviting team
		Team team = (Team)DAOProvider.getCompetitorDAO().retrieveByNick(teamNick);
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		acceptOrDeclineTeamInvitation(team, user, true);
		return "redirect:teams";
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"decline"})
	public String declineTeamInvitation(Model model, HttpServletRequest request, @RequestParam("decline") String teamNick) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
			return "redirect:login";
		}
		//TODO check whether the invited user has really been invited in the inviting team
		Team team = (Team)DAOProvider.getCompetitorDAO().retrieveByNick(teamNick);
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		acceptOrDeclineTeamInvitation(team, user, false);
		return "redirect:teams";
	}

	private void acceptOrDeclineTeamInvitation(Team team, RegisteredUser user, boolean accept) {
		DAOProvider.getInvitationsDAO().delete(team, user);
		if(accept) {
			team.addMember(user);
			user.addTeam(team);
		}
		OnDemandPersistenceManager.getInstance().updateCompetitor(user);
		OnDemandPersistenceManager.getInstance().updateCompetitor(team);
		
	}
	
	
}
