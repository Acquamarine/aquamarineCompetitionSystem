package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.MatchmakingManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola.Briscola2v2;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.TressetteGameManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/briscola")
public class BriscolaController {

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String briscola() {
		return "/briscola";
	}

	@RequestMapping(value = "/gioca", method = {RequestMethod.GET, RequestMethod.POST})
	public String play(HttpServletRequest request, Model model) {
		model.addAttribute("Me", "Me");
		model.addAttribute("MyTeammate", "MyTeammate");
		model.addAttribute("OpponentFirst", "OpponentFirst");
		model.addAttribute("OpponentSecond", "OpponentSecond");
		List<LinkedList<NeapolitanCard>> playersCards = new ArrayList<>();
		LinkedList<NeapolitanCard> myCards = new LinkedList<>();
		myCards.add(new NeapolitanCard(0, 5));
		myCards.add(new NeapolitanCard(0, 5));
		myCards.add(new NeapolitanCard(0, 5));
		playersCards.add(myCards);
		LinkedList<NeapolitanCard> opponentSecondCards = new LinkedList<>();
		opponentSecondCards.add(new NeapolitanCard(2, 5));
		opponentSecondCards.add(new NeapolitanCard(2, 5));
		opponentSecondCards.add(new NeapolitanCard(2, 5));
		playersCards.add(opponentSecondCards);
		LinkedList<NeapolitanCard> myTeammateCards = new LinkedList<>();
		myTeammateCards.add(new NeapolitanCard(1, 5));
		myTeammateCards.add(new NeapolitanCard(1, 5));
		myTeammateCards.add(new NeapolitanCard(1, 5));
		playersCards.add(myTeammateCards);
		LinkedList<NeapolitanCard> opponentFirstCards = new LinkedList<>();
		opponentFirstCards.add(new NeapolitanCard(3, 5));
		opponentFirstCards.add(new NeapolitanCard(3, 5));
		opponentFirstCards.add(new NeapolitanCard(3, 5));
		playersCards.add(opponentFirstCards);
		model.addAttribute("playersCards", playersCards);
		return "/briscola/gioca";
	}

	@RequestMapping(value = "/briscola", method = {RequestMethod.GET, RequestMethod.POST}, params = {"addToRankedQueue", "team"})
	public void putCompetitorInQueue(HttpServletRequest request, @RequestParam("addToRankedQueue") boolean ranked, @RequestParam("team") String teamNick) {
		ICompetitor team = null;
		if(CompetitionManager.getInstance().doesTeamExistByNick(teamNick)){
			team = CompetitionManager.getInstance().getCompetitorByNick(teamNick);
		}
		if(((RegisteredUser) request.getSession().getAttribute("registeredUser")).getTeams().contains((Team)team)){
			request.getSession().setAttribute("teamInQueueOnBriscola", teamNick);
			if(ranked){
				MatchmakingManager.getInstance().addToQueue(Briscola2v2.class.getSimpleName(), team); //new Player(competitor)
			}else{
				MatchmakingManager.getInstance().addToQueue(Briscola2v2.class.getSimpleName() + "normal", team);  //new Player(competitor)
			}
		}
	}

	@RequestMapping(value = "/briscola", method = {RequestMethod.GET, RequestMethod.POST}, params = {"inQueue"})
	public void getMatch(HttpServletRequest request) {
		TressetteGameManager.getInstance().waitForMatch((Integer) request.getSession().getAttribute("playerId"));
	}
}
