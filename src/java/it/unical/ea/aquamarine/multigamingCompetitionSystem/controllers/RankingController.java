package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorDAO;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.MatchResultDAO;
import java.util.LinkedList;
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
		CompetitorDAO competitorDAO = DAOProvider.getCompetitorDAO();
		MatchResultDAO matchResultsDAO = DAOProvider.getMatchResultsDAO();
		List<Pair<ICompetitor, Integer>> usersRankingWithCompetitor = competitorDAO.getCompetitorRanking(game);
		List<Pair<String, Integer>> usersRanking = new LinkedList<>();
		List<Pair<Integer, Integer>> usersDefeatsAndVictories = new LinkedList<>();
		for(Pair<ICompetitor, Integer> userRankingWithCompetitor : usersRankingWithCompetitor) {
			usersRanking.add(new Pair<>(userRankingWithCompetitor.getKey().getNickname(), userRankingWithCompetitor.getValue()));
			usersDefeatsAndVictories.add(matchResultsDAO.retrieveDefeatsAndVictories(userRankingWithCompetitor.getKey(), game));
		}
		model.addAttribute("usersDefeatsAndVictories", usersDefeatsAndVictories);
		model.addAttribute("usersRanking",usersRanking);
		return "/ranking";
	}
}
