package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.Map;

public class TressetteRoundSummary {
	private boolean cardPlayed;
	private int round;
	private String roundWinner;
	private String actionPlayer;
	private String card;
	private int cardsInDeck;

	public String getActionPlayer() {
		return actionPlayer;
	}

	public void setActionPlayer(String actionPlayer) {
		this.actionPlayer = actionPlayer;
	}
	private Map<String, NeapolitanCard> pickedCards;

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

	public String getRoundWinner() {
		return roundWinner;
	}

	public void setRoundWinner(String roundWinner) {
		this.roundWinner = roundWinner;
	}

	public Map<String, NeapolitanCard> getPickedCards() {
		return pickedCards;
	}

	public void setPickedCards(Map<String, NeapolitanCard> pickedCards) {
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


	
	
}
