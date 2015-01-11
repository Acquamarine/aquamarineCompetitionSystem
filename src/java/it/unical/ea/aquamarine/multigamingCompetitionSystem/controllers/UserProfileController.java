package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorDAO;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/userProfile")
public class UserProfileController {
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String userProfile(Model model, HttpServletRequest request) {
		String nick = (String) request.getSession().getAttribute("nickname");
		model.addAttribute("user", nick);
		DAOProvider.getCompetitorDAO().retrieveByNick(nick).updateElo(Tressette1v1.class.getSimpleName(), 1500);
		String bestGame = getBestGame(nick);
		model.addAttribute("game", bestGame);
		computeRank(model,nick,bestGame);
		return "/userProfile";
	}
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "user")
	public String userProfile(@RequestParam("user") String user,Model model, HttpServletRequest request) {
		model.addAttribute("user", user);
		String bestGame = getBestGame(user);
		model.addAttribute("game", bestGame);
		computeRank(model,user,bestGame);
		return "/userProfile";
	}
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"user","game"})
	public String userProfile(@RequestParam("user") String user,@RequestParam("game") String game,Model model, HttpServletRequest request) {
		model.addAttribute("user", user);
		model.addAttribute("game", game);
		computeRank(model,user,game);
		return "/userProfile";
	}
	
	

	private String getBestGame(String nick) {
		return Tressette1v1.class.getSimpleName();
	}

	private void computeRank(Model model, String nick, String game) {
		CompetitorDAO competitorDAO = DAOProvider.getCompetitorDAO();
		model.addAttribute("rankAndElo",competitorDAO.getCompetitorRankAndEloByNick(nick, game));
	}

	
	
	
	
}
