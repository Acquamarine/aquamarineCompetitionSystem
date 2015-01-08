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
	@RequestMapping(value = "/tressette/ranking", method = {RequestMethod.GET, RequestMethod.POST})
	public String ranking(Model model, HttpServletRequest request) {
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
		String me = (String) request.getSession().getAttribute("nickname"); //TODO get from session
		//TODO input validation
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		if(playerGame == null){
			playerGame = TressetteGameManager.getInstance().getPlayerCompletedMatch(me);
		}
		if(playerGame == null){
			return "redirect:/tressette";
		}
		String matchedPlayer = playerGame.getMatchedPlayer(me);
		if(!playerGame.areThereSummaries()){
			request.getSession().setAttribute("eventIndex", 0);
			request.getSession().setAttribute("deck", 20);
			request.getSession().setAttribute("reloaded", false);
		}
		//TODO move to login
		request.getSession().setAttribute("matched", matchedPlayer);
		NeapolitanHand myHand = playerGame.getHands().get(me);
		NeapolitanHand matchedPlayerHand = playerGame.getHands().get(matchedPlayer);
		List<NeapolitanCard> cards = myHand.getHandCards();
		List<NeapolitanCard> matchedPlayerCards = matchedPlayerHand.getHandCards();
		model.addAttribute("cards", cards);
		model.addAttribute("matchedPlayerCards", matchedPlayerCards);
		model.addAttribute("userForm", new RegisteredUser());
		String turnPlayer = playerGame.getTurnPlayer();
		model.addAttribute("turn", turnPlayer);
		return "/tressette/gioca";
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "cardId")
	public void makeMove(@RequestParam("cardId") String cardId, Model model, HttpServletRequest request) {

		String me = (String) request.getSession().getAttribute("nickname");
		//model.addAttribute("userForm", new User());
		NeapolitanCard toPlay = new NeapolitanCard(cardId);
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		playerGame.playCard(me, toPlay);
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "eventIndex")
	public @ResponseBody
	String askForEvent(@RequestParam("eventIndex") int eventIndex, HttpServletRequest request) {
		request.getSession().setAttribute("eventIndex", eventIndex);
		String me = (String) request.getSession().getAttribute("nickname");
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerActiveMatch(me);
		//TODO be sureto ask for events only if the game has been created
		TressetteRoundSummary summary = playerGame.getSummary(eventIndex);
		JSONObject json = new JSONObject();
		try{
			json.put("played", summary.isCardPlayed());
			json.put("card", summary.getCard());
			json.put("actionPlayer", summary.getActionPlayer());
			json.put("round", summary.getRound());
			json.put("gameover", summary.isGameOver());
			if(summary.getRound() == 1){
				String winner = summary.getRoundWinner();
				json.put("winner", winner);
				String looser = playerGame.getMatchedPlayer(winner);
				json.put("looser", looser);
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
			MatchmakingManager.getInstance().addToQueue(Tressette1v1.class.getCanonicalName(), CompetitionManager.getInstance().getCompetitor((String) request.getSession().getAttribute("nickname"))); //new Player(competitor)
		}else{
			//TODO: addToUnrankedQueue
		}
	}

	@RequestMapping(value = "/tressette", method = {RequestMethod.GET, RequestMethod.POST}, params = {"inQueue"})
	public void getMatch(HttpServletRequest request) {
		TressetteGameManager.getInstance().waitForMatch((String) request.getSession().getAttribute("nickname"));
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "gameComplete")
	public @ResponseBody
	String gameComplete(HttpServletRequest request) {
		String me = (String) request.getSession().getAttribute("nickname");
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerCompletedMatch(me);
		Map<String, Integer> finalScores = playerGame.getFinalScores();
		JSONObject json = new JSONObject();
		try{
			json.put("results", finalScores);
		}catch(JSONException ex){
			Logger.getLogger(TressetteController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return json.toString();
	}
}
