package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers.TressetteController;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class NeapolitanGameRoundSummary implements ITurnSummary {
	private boolean cardPlayed;
	private int round;
	private Integer roundWinner;
	private Integer roundLoser;
	private Integer actionPlayer;
	private String card;
	private int cardsInDeck;
	private boolean gameOver = false;
	private Map<Integer, NeapolitanCard> pickedCards;

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

	public Map<Integer, NeapolitanCard> getPickedCards() {
		return pickedCards;
	}

	public void setPickedCards(Map<Integer, NeapolitanCard> pickedCards) {
		this.pickedCards = pickedCards;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public void setCardsInDeck(int size) {
		this.cardsInDeck=size;
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

	public Integer getRoundLoser() {
		return roundLoser;
	}

	public void setRoundLoser(Integer roundLoser) {
		this.roundLoser = roundLoser;
	}
	
	

	@Override
	public void buildJsonRepresentation(JSONObject summaryJson, HttpServletRequest request) {
		try {
			summaryJson.put("played", cardPlayed);
			summaryJson.put("card", card);
			summaryJson.put("round", round);
			summaryJson.put("gameover", gameOver);
			summaryJson.put("actionPlayer", request.getSession().getAttribute(actionPlayer+""));
			if(getRound() == 1){
				Integer winner = roundWinner;
				summaryJson.put("winner", request.getSession().getAttribute(winner+""));
				Integer loser = roundLoser;
				summaryJson.put("loser", request.getSession().getAttribute(loser+""));
				if(getPickedCards() != null){
					summaryJson.put("picked0", getPickedCards().get(winner));
					summaryJson.put("picked1", getPickedCards().get((loser)));
					summaryJson.put("deck", cardsInDeck);
				}
				request.getSession().setAttribute("deck", cardsInDeck);
			}
			
		}catch(JSONException ex){
			Logger.getLogger(NeapolitanGameRoundSummary.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	
}
