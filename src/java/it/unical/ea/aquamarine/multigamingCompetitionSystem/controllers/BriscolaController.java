package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.MatchmakingManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola.Briscola2v2;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.briscola.BriscolaGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.ITurnSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.TressetteGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/briscola")
public class BriscolaController {

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String briscola() {
		return "/briscola";
	}

	@RequestMapping(value = "/gioca", method = {RequestMethod.GET, RequestMethod.POST})
	public String play(Model model, HttpServletRequest request) {
		model.addAttribute("userForm", new RegisteredUser());
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		Briscola2v2 playerGame = (Briscola2v2) BriscolaGameManager.getInstance().getPlayerActiveMatch(me);
		if(playerGame == null){
			playerGame = (Briscola2v2) BriscolaGameManager.getInstance().getPlayerCompletedMatch(me);
		}
		if(playerGame == null){
			return "redirect:/briscola";
		}
		List<ICompetitor> playingOrder = playerGame.getPlayingOrder(me);
		request.getSession().setAttribute("players", playingOrder);
		Map<Integer, Integer> teamsMap = playerGame.getTeamsMap();
		Map<ICompetitor,ICompetitor> playersTeamsMap = new HashMap<>();
		for(ICompetitor player: playingOrder){
			playersTeamsMap.put(player, CompetitionManager.getInstance().getCompetitor(teamsMap.get(player.getId())));
		}
		for(ICompetitor team : playersTeamsMap.values()){
			OnDemandPersistenceManager.getInstance().initializeEquip(team);
		}
		request.getSession().setAttribute("playersTeamsMap", playersTeamsMap);
		if(!playerGame.areThereSummaries()){
			request.getSession().setAttribute("eventIndex", 0);
			request.getSession().setAttribute("deck", 28);
			request.getSession().setAttribute("reloaded", false);
		}
		List<NeapolitanHand> hands = new ArrayList<>();
		for(ICompetitor competitor: playingOrder){
			hands.add(playerGame.getHands().get(competitor.getId()));
		}
		model.addAttribute("hands", hands);
		model.addAttribute("cardsOnTable", playerGame.getTableFrom(me));
		Integer turnPlayer = playerGame.getTurnPlayer();
		model.addAttribute("turn", CompetitionManager.getInstance().getCompetitor(turnPlayer));
		model.addAttribute("briscola", playerGame.getBriscolaCard());
		return "/briscola/gioca";
	}

	@RequestMapping(value = "/gioca", method = RequestMethod.GET, params = "cardId")
	public void makeMove(@RequestParam("cardId") String cardId, Model model, HttpServletRequest request) {
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		NeapolitanCard toPlay = new NeapolitanCard(cardId);
		Briscola2v2 playerGame = (Briscola2v2) BriscolaGameManager.getInstance().getPlayerActiveMatch(me);
		playerGame.playCard(me, toPlay);
	}

	@RequestMapping(value = "/gioca", method = RequestMethod.GET, params = "eventIndex")
	public @ResponseBody
	String askForEvent(@RequestParam("eventIndex") int eventIndex, HttpServletRequest request) {
		request.getSession().setAttribute("eventIndex", eventIndex);
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		Briscola2v2 playerGame = (Briscola2v2) BriscolaGameManager.getInstance().getPlayerActiveMatch(me);
		ITurnSummary summary = playerGame.getSummary(eventIndex);
		JSONObject summaryJson = new JSONObject();
		summary.buildJsonRepresentation(summaryJson, request);
		return summaryJson.toString();
	}
	
	@RequestMapping( method = {RequestMethod.GET, RequestMethod.POST}, params = {"addToRankedQueue", "team"})
	public void putCompetitorInQueue(HttpServletRequest request, @RequestParam("addToRankedQueue") boolean ranked, @RequestParam("team") String teamNick) {
		Team team = null;
		if(CompetitionManager.getInstance().doesTeamExistByNick(teamNick)){
			team = (Team) CompetitionManager.getInstance().getCompetitorByNick(teamNick);
		}
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		if(user.getTeams().contains(team)){
			if(ranked){
				MatchmakingManager.getInstance().addToQueue(Briscola2v2.class.getSimpleName(), (RegisteredUser) CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId")), team); //new Player(competitor)
			}else{
				MatchmakingManager.getInstance().addToQueue(Briscola2v2.class.getSimpleName() + "normal", (RegisteredUser) CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId")), team);  //new Player(competitor)
			}
		}
	}

	@RequestMapping( method = {RequestMethod.GET, RequestMethod.POST}, params = "undoQueue")
	public String removeCompetitorFromQueue(HttpServletRequest request, @RequestParam("undoQueue") boolean ranked) {
		if(ranked){
			MatchmakingManager.getInstance().removeFromQueue(Briscola2v2.class.getSimpleName(), CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId"))); //new Player(competitor)
		}else{
			MatchmakingManager.getInstance().removeFromQueue(Briscola2v2.class.getSimpleName() + "normal", CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId"))); //new Player(competitor)
		}
		return "/briscola";
	}

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"inQueue"})
	public void getMatch(HttpServletRequest request) {
		BriscolaGameManager.getInstance().waitForMatch((Integer) request.getSession().getAttribute("playerId"));
	}
}
