/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.MatchmakingManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
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

/**
 *
 * @author Denise
 */
@Controller
public class TressetteController {

	@RequestMapping(value = "/tressette", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
		}
		return "/tressette";
	}
	

	@RequestMapping(value = "/tressette/gioca", method = {RequestMethod.GET, RequestMethod.POST})
	public String play(Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
		}
		Integer me = (Integer) request.getSession().getAttribute("playerId"); //TODO get from session
		String myNickname = CompetitionManager.getInstance().getCompetitor(me).getNickname();
		//TODO input validation
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		if(playerGame == null){
			playerGame = TressetteGameManager.getInstance().getPlayerCompletedMatch(me);
		}
		if(playerGame == null){
			return "redirect:/tressette";
		}
		Integer matchedPlayerId = playerGame.getMatchedPlayer(me);
		String matchedPlayer = CompetitionManager.getInstance().getCompetitor(matchedPlayerId).getNickname();
		request.getSession().setAttribute(me+"", myNickname);
		request.getSession().setAttribute(matchedPlayerId+"", matchedPlayer);
		
		if(!playerGame.areThereSummaries()){
			request.getSession().setAttribute("eventIndex", 0);
			request.getSession().setAttribute("deck", 20);
			request.getSession().setAttribute("reloaded", false);
		}
		//TODO move to login
		request.getSession().setAttribute("matched", matchedPlayer);
		NeapolitanHand myHand = playerGame.getHands().get(me);
		NeapolitanHand matchedPlayerHand = playerGame.getHands().get(matchedPlayerId);
		List<NeapolitanCard> cards = myHand.getHandCards();
		List<NeapolitanCard> matchedPlayerCards = matchedPlayerHand.getHandCards();
		model.addAttribute("cards", cards);
		model.addAttribute("matchedPlayerCards", matchedPlayerCards);
		model.addAttribute("userForm", new RegisteredUser());
		Integer turnPlayer = playerGame.getTurnPlayer();
		model.addAttribute("turn", request.getSession().getAttribute(turnPlayer+""));
		return "/tressette/gioca";
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "cardId")
	public void makeMove(@RequestParam("cardId") String cardId, Model model, HttpServletRequest request) {

		Integer me = (Integer) request.getSession().getAttribute("playerId");
		//model.addAttribute("userForm", new User());
		NeapolitanCard toPlay = new NeapolitanCard(cardId);
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		playerGame.playCard(me, toPlay);
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "eventIndex")
	public @ResponseBody
	String askForEvent(@RequestParam("eventIndex") int eventIndex, HttpServletRequest request) {
		request.getSession().setAttribute("eventIndex", eventIndex);
		Integer me = (Integer) request.getSession().getAttribute("playerId");
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		//TODO be sureto ask for events only if the game has been created
		TressetteRoundSummary summary = playerGame.getSummary(eventIndex);
		JSONObject json = new JSONObject();
		try{
			json.put("played", summary.isCardPlayed());
			json.put("card", summary.getCard());
			Integer actionPlayer = summary.getActionPlayer();
			json.put("actionPlayer", request.getSession().getAttribute(actionPlayer+""));
			json.put("round", summary.getRound());
			json.put("gameover", summary.isGameOver());
			if(summary.getRound() == 1){
				Integer winner = summary.getRoundWinner();
				json.put("winner", request.getSession().getAttribute(winner+""));
				Integer looser = playerGame.getMatchedPlayer(winner);
				json.put("looser", request.getSession().getAttribute(looser+""));
				if(summary.getPickedCards() != null){
					json.put("picked0", summary.getPickedCards().get(winner));
					json.put("picked1", summary.getPickedCards().get((looser)));
					json.put("deck", summary.getCardsInDeck());
				}
				request.getSession().setAttribute("deck", summary.getCardsInDeck());
			}
		}catch(JSONException ex){
			Logger.getLogger(TressetteController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return json.toString();
	}

	@RequestMapping(value = "/tressette", method = {RequestMethod.GET, RequestMethod.POST}, params = "addToRankedQueue")
	public void putCompetitorInQueue(HttpServletRequest request, @RequestParam("addToRankedQueue") boolean ranked) {
		if(ranked){
			MatchmakingManager.getInstance().addToQueue(Tressette1v1.class.getSimpleName(), CompetitionManager.getInstance().getCompetitor((Integer) request.getSession().getAttribute("playerId"))); //new Player(competitor)
		}else{
			//TODO: addToUnrankedQueue
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
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerCompletedMatch(me);
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
}
