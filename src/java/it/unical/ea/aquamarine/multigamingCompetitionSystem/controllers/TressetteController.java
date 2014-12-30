/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteGameManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.TressetteRoundSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.User;
import java.util.List;
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
		model.addAttribute("userForm", new User());
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
		}
		return "/tressette";
	}

	@RequestMapping(value = "/tressette/gioca", method = {RequestMethod.GET, RequestMethod.POST})
	public String play(Model model, HttpServletRequest request) {
		String me = (String) request.getSession().getAttribute("username"); //TODO get from session
		String otherPlayer = TressetteGameManager.getInstance().getMatchedWith(me);
		TressetteGameManager.getInstance().startMatch(me, otherPlayer);
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerMatch(me);
		//TODO move to login
		request.getSession().setAttribute("user", me);
		NeapolitanHand myHand = playerGame.getHands().get(me);
		List<NeapolitanCard> cards = myHand.getHandCards();
		model.addAttribute("cards", cards);
		model.addAttribute("userForm", new User());
		return "/tressette/gioca";
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "cardId")
	public void makeMove(@RequestParam("cardId") String cardId, Model model, HttpServletRequest request) {
		String me = (String) request.getSession().getAttribute("username");
		//model.addAttribute("userForm", new User());
		NeapolitanCard toPlay = new NeapolitanCard(cardId);
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerMatch(me);
		playerGame.playCard(me, toPlay);
	}

	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET, params = "eventIndex")
	public @ResponseBody
	String askForEvent(@RequestParam("eventIndex") int eventIndex, HttpServletRequest request) {
		String me = (String) request.getSession().getAttribute("username");
		Tressette1v1 playerGame = TressetteGameManager.getInstance().getPlayerMatch(me);
		//TODO be sureto ask for events only if the game has been created
		TressetteRoundSummary summary = playerGame.getSummary(eventIndex);
		JSONObject json = new JSONObject();
		try{
			json.put("played", summary.isCardPlayed());
			json.put("card", summary.getCard());
			json.put("actionPlayer", summary.getActionPlayer());
			json.put("round", summary.getRound());
			if(summary.getRound() == 1){
				String winner = summary.getRoundWinner();
				json.put("winner", winner);
				json.put("picked0", summary.getPickedCards().get(winner));
				json.put("picked1", summary.getPickedCards().get(playerGame.getMatchedPlayer(winner)));
			}
		}catch(JSONException ex){
			Logger.getLogger(TressetteController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return json.toString();
	}

}
