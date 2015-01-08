package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.Map;

public class TressetteRoundSummary {
	private boolean cardPlayed;
	private int round;
	private Integer roundWinner;
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

	public TressetteRoundSummary() {
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

	void setCardsInDeck(int size) {
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

	
}
