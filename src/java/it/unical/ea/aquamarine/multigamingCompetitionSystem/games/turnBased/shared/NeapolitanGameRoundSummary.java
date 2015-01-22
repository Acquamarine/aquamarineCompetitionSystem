package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers.TressetteController;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class NeapolitanGameRoundSummary implements ITurnSummary {
	private boolean cardPlayed;
	private int round;
	private Integer roundWinner = null;
	private Integer actionPlayer;
	private String card;
	private int cardsInDeck;
	private boolean gameOver = false;
	private List<NeapolitanCard> pickedCards;
	private List<Integer> pickList;
	private boolean pickSummary = false;
	private int turnPlayer;
	private Integer surrenderer = null;

	public Integer getActionPlayer() {
		return actionPlayer;
	}

	public void setActionPlayer(Integer actionPlayer) {
		this.actionPlayer = actionPlayer;
	}

	public boolean isCardPlayed() {
		return cardPlayed;
	}

	public void setCardPlayed(boolean cardPlayed) {
		this.cardPlayed = cardPlayed;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public Integer getRoundWinner() {
		return roundWinner;
	}

	public void setRoundWinner(Integer roundWinner) {
		this.roundWinner = roundWinner;
	}

	public void setPickedCards(List<NeapolitanCard> pickedCards) {
		this.pickedCards = pickedCards;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public void setCardsInDeck(int size) {
		this.cardsInDeck = size;
	}

	public void setTurnPlayer(int turnPlayer) {
		this.turnPlayer = turnPlayer;
	}

	
	
	public int getCardsInDeck() {
		return cardsInDeck;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setPickList(List<Integer> pickList) {
		this.pickList = pickList;
	}

	public void setPickSummary(boolean pickSummary) {
		this.pickSummary = pickSummary;
	}
	
	public void setSurrender(Integer surrenderer) {
		this.surrenderer = surrenderer;
	}

	
	@Override
	public void buildJsonRepresentation(JSONObject summaryJson, HttpServletRequest request) {
		try{
			summaryJson.put("gameover", gameOver);
			summaryJson.put("surrenderer", surrenderer);
			if(surrenderer==null) {
				summaryJson.put("played", cardPlayed);
				summaryJson.put("card", card);
				summaryJson.put("round", round);
				summaryJson.put("actionPlayer", CompetitionManager.getInstance().getCompetitor(actionPlayer).getNickname());
				if(roundWinner != null){
					Integer winner = roundWinner;
					summaryJson.put("winner", CompetitionManager.getInstance().getCompetitor(winner).getNickname());
					if(pickSummary){
						List<String> pickListWithNicks = new ArrayList<>();
						for(Integer playerId : pickList){
							pickListWithNicks.add(CompetitionManager.getInstance().getCompetitor(playerId).getNickname());
						}

						List<String> pickedCardsByStrings = new ArrayList<>();
						for(NeapolitanCard pickedCard : pickedCards){
							pickedCardsByStrings.add(pickedCard.toString());
						}
						summaryJson.put("pickedCards", pickedCardsByStrings);
						summaryJson.put("pickList",pickListWithNicks);
						summaryJson.put("deck", cardsInDeck);
					}
					request.getSession().setAttribute("deck", cardsInDeck);
				}
			}

		}catch(JSONException ex){
			Logger.getLogger(NeapolitanGameRoundSummary.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	

}
