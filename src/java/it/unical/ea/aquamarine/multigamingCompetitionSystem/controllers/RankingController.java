package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DaoProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.UserDAO;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RankingController {
	@RequestMapping(value = "/ranking", method = {RequestMethod.GET, RequestMethod.POST}, params="game")
	public String ranking(Model model, HttpServletRequest request, @RequestParam("game") String game) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
		}
		UserDAO userDAO = DaoProvider.getUserDAO();
		List<Pair<String, Integer>> usersRanking = userDAO.getUsersRanking(game);
		model.addAttribute("usersRanking",usersRanking);
		return "/ranking";
	}
}
