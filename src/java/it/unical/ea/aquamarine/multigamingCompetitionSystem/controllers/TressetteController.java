package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.MatchmakingManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.TressetteGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitionGame;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.ITurnSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TressetteController {

	@RequestMapping(value = "/tressette", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model, HttpServletRequest request) {
		return "/tressette";
	}
	

	@RequestMapping(value = "/tressette/gioca", method = {RequestMethod.GET, RequestMethod.POST})
	public String play(Model model, HttpServletRequest request) {
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		String myNickname = CompetitionManager.getInstance().getCompetitor(me).getNickname();
		Tressette1v1 playerGame = (Tressette1v1) TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		if(playerGame == null){
			playerGame = (Tressette1v1) TressetteGameManager.getInstance().getPlayerCompletedMatch(me);
		}
		if(playerGame == null){
			return "redirect:/tressette";
		}
		Integer matchedPlayerId = playerGame.getOpponent(me);
		String matchedPlayer = CompetitionManager.getInstance().getCompetitor(matchedPlayerId).getNickname();
		ICompetitor matchedCompetitor = CompetitionManager.getInstance().getCompetitor(matchedPlayerId);
		OnDemandPersistenceManager.getInstance().initializeEquip(matchedCompetitor);
		request.getSession().setAttribute(me+"", myNickname);
		request.getSession().setAttribute(matchedPlayerId+"", matchedPlayer);
		
		if(!playerGame.areThereSummaries() || request.getSession().getAttribute("eventIndex")==null){
			request.getSession().setAttribute("eventIndex", 0);
			request.getSession().setAttribute("deck", 20);
			request.getSession().setAttribute("reloaded", false);
		}
		request.getSession().setAttribute("matched", matchedPlayer);
		request.getSession().setAttribute("matchedCompetitor", matchedCompetitor);
		NeapolitanHand myHand = playerGame.getHands().get(me);
		NeapolitanHand matchedPlayerHand = playerGame.getHands().get(matchedPlayerId);
		List<NeapolitanCard> cards = myHand.getHandCards();
		List<NeapolitanCard> matchedPlayerCards = matchedPlayerHand.getHandCards();
		model.addAttribute("cards", cards);
		model.addAttribute("matchedPlayerCards", matchedPlayerCards);
		model.addAttribute("userForm", new RegisteredUser());
		model.addAttribute("cardOnTable", playerGame.getFirstCardOnTable());
		Integer turnPlayer = playerGame.getTurnPlayer();
		model.addAttribute("turn", request.getSession().getAttribute(turnPlayer+""));
		return "/tressette/gioca";
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "cardId")
	public void makeMove(@RequestParam("cardId") String cardId, Model model, HttpServletRequest request) {

		Integer me = (Integer) request.getSession().getAttribute("playerId");
		//model.addAttribute("userForm", new User());
		NeapolitanCard toPlay = new NeapolitanCard(cardId);
		Tressette1v1 playerGame = (Tressette1v1) TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		playerGame.playCard(me, toPlay);
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "eventIndex")
	public @ResponseBody
	String askForEvent(@RequestParam("eventIndex") int eventIndex, HttpServletRequest request) {
		request.getSession().setAttribute("eventIndex", eventIndex);
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		Tressette1v1 playerGame = (Tressette1v1) TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		ITurnSummary summary = playerGame.getSummary(eventIndex);
		JSONObject summaryJson = new JSONObject();
		summary.buildJsonRepresentation(summaryJson, request);
		return summaryJson.toString();
	}

	@RequestMapping(value = "/tressette", method = {RequestMethod.GET, RequestMethod.POST}, params = "addToRankedQueue")
	public void putCompetitorInQueue(HttpServletRequest request, @RequestParam("addToRankedQueue") boolean ranked) {
		if(ranked){
			MatchmakingManager.getInstance().addToQueue(Tressette1v1.class.getSimpleName(), (RegisteredUser) CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId")), null); //new Player(competitor)
		}else{
			MatchmakingManager.getInstance().addToQueue(Tressette1v1.class.getSimpleName()+"normal", (RegisteredUser) CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId")), null); //new Player(competitor)
		}
	}

	@RequestMapping(value = "/tressette", method = {RequestMethod.GET, RequestMethod.POST}, params = {"inQueue"})
	public void getMatch(HttpServletRequest request) {
		TressetteGameManager.getInstance().waitForMatch((Integer) request.getSession().getAttribute("playerId"));
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "gameComplete")
	public @ResponseBody
	String gameComplete(HttpServletRequest request) {
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		Tressette1v1 playerGame = (Tressette1v1) TressetteGameManager.getInstance().getPlayerCompletedMatch(me);
		Map<Integer, Integer> finalScores = playerGame.getFinalScores();
		Map<String, Integer> finalScoreWithNicks = new HashMap<>();
		finalScores.keySet().stream().forEach((id) -> {			
			finalScoreWithNicks.put((String)request.getSession().getAttribute(id+""), finalScores.get(id));
		});
		JSONObject json = new JSONObject();
		try{
			json.put("results", finalScoreWithNicks);
		}catch(JSONException ex){
			Logger.getLogger(TressetteController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/tressette", method = {RequestMethod.GET, RequestMethod.POST}, params = "undoQueue")
	public String removeCompetitorFromQueue(HttpServletRequest request, @RequestParam("undoQueue") boolean ranked) {
		if(ranked){
			MatchmakingManager.getInstance().removeFromQueue(Tressette1v1.class.getSimpleName(), CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId"))); //new Player(competitor)
		}else{
			MatchmakingManager.getInstance().removeFromQueue(Tressette1v1.class.getSimpleName()+"normal", CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId"))); //new Player(competitor)
		}
		return "/tressette";
	}
	
	@RequestMapping(value = "/tressette/gioca", method = {RequestMethod.GET, RequestMethod.POST}, params = "surrender")
	public void surrenderMatch(HttpServletRequest request) {
		System.out.println("surrender started");
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		ICompetitionGame playerActiveMatch = TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		if(playerActiveMatch!=null) {
			playerActiveMatch.surrenderMatch(me);
		}
	}
	
}
