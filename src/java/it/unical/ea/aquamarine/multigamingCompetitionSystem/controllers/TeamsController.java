
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.invitations.Invitation;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import java.util.HashMap;
import java.util.LinkedList;
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
		if((boolean)request.getSession().getAttribute("loggedIn") == false){
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
		List<Team> usersTeams = new LinkedList<>();
		for(Team team: user.getTeams() ){
			usersTeams.add((Team)CompetitionManager.getInstance().getCompetitorByNick(team.getNickname()));
		}
		model.addAttribute("usersTeams", usersTeams);
		model.addAttribute("myInvitations", myInvitations);
		return "/teams";
	}
	
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "createTeam")
	public String inviteUserInTeam(Model model, HttpServletRequest request, @RequestParam("createTeam") String teamNick) {
		if((boolean)request.getSession().getAttribute("loggedIn") == false){
			return "redirect:login";
		}
		//TODO check whether the inviting user is in the inviting team
		Team team;
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		if(DAOProvider.getCompetitorDAO().retrieveByNick(teamNick)==null){
			team = new Team();
			team.setNickname(teamNick);
			team.addMember(user);
			user.addTeam(team);
			DAOProvider.getCompetitorDAO().updateCompetitor(user);
		}
		return "redirect:teams";
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"team", "invitedUser"})
	public String inviteUserInTeam(Model model, HttpServletRequest request, @RequestParam("team") String teamNick, @RequestParam("invitedUser") String userNick) {
		if((boolean)request.getSession().getAttribute("loggedIn") == false){
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
		if((boolean)request.getSession().getAttribute("loggedIn") == false){
			return "redirect:login";
		}
		//TODO check whether the invited user has really been invited in the inviting team
		Team team = (Team)CompetitionManager.getInstance().getCompetitorByNick(teamNick);
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		acceptOrDeclineTeamInvitation(team, user, true);
		return "redirect:teams";
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"decline"})
	public String declineTeamInvitation(Model model, HttpServletRequest request, @RequestParam("decline") String teamNick) {
		if((boolean)request.getSession().getAttribute("loggedIn") == false){
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
