package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults.TwoCompetitorsMatchResult;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorDAO;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.MatchResultDAO;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import javafx.util.Pair;
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
		
		/*ICompetitor comp=DAOProvider.getCompetitorDAO().retrieveByNick(nick);
		comp.updateElo(Tressette1v1.class.getSimpleName(), 1500);
		TwoCompetitorsMatchResult match1 = new TwoCompetitorsMatchResult();
		match1.setGame("Tressette1v1");
		match1.setPlayer1(comp);
		match1.setPlayer2(comp);
		match1.setPlayer1Score(6);
		match1.setPlayer2Score(5);
		match1.setRankedMatch(false);
		match1.setMatchEndTimeByMillis(System.currentTimeMillis());
		DAOProvider.getMatchResultsDAO().create(match1);
		TwoCompetitorsMatchResult match2 = new TwoCompetitorsMatchResult();
		match2.setGame("Tressette1v1");
		match2.setPlayer1(comp);
		match2.setPlayer2(comp);
		match2.setPlayer1Score(6);
		match2.setPlayer2Score(5);
		match2.setRankedMatch(true);
		match2.setMatchEndTimeByMillis(System.currentTimeMillis());
		DAOProvider.getMatchResultsDAO().create(match2);
		*/
		String bestGame = getBestGame(nick);
		buildModel(model, nick, bestGame);
		return "/userProfile";
	}
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "user")
	public String userProfile(@RequestParam("user") String user,Model model, HttpServletRequest request) {
		String bestGame = getBestGame(user);
		buildModel(model, user, bestGame);
		return "/userProfile";
	}
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"user","game"})
	public String userProfile(@RequestParam("user") String user,@RequestParam("game") String game,Model model, HttpServletRequest request) {
		buildModel(model, user, game);
		return "/userProfile";
	}
	
	private String getBestGame(String nick) {
		//TODO get best
		return Tressette1v1.class.getSimpleName();
	}

	private Pair<Integer,Integer> computeRank(String nick, String game) {
		CompetitorDAO competitorDAO = DAOProvider.getCompetitorDAO();
		return competitorDAO.getCompetitorRankAndEloByNick(nick, game);
	}

	private List<TwoCompetitorsMatchResult> getMatchHistory(String user, String game) {
		
		MatchResultDAO matchResultsDAO = DAOProvider.getMatchResultsDAO();
		ICompetitor competitor = DAOProvider.getCompetitorDAO().retrieveByNick(user);
		return matchResultsDAO.retrieveCompetitorMatches(competitor,game);
		
	}

	private void buildModel(Model model, String nick, String bestGame) {
		model.addAttribute("user", nick);
		model.addAttribute("game", bestGame);
		model.addAttribute("matchHistory", getMatchHistory(nick,bestGame));
		model.addAttribute("rankAndElo",computeRank(nick,bestGame));
	}

	
	
	
	
}
