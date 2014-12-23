package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared;

import java.util.ArrayList;
import java.util.List;

public class NeapolitanHand {
	private List<NeapolitanCard> handCards;

	public NeapolitanHand() {
		handCards = new ArrayList<>(10);
		for(int i=0;i<10;i++) {
			handCards.add(null);
		}
	}
	
	public int addCard(NeapolitanCard card) {
		for(int i=0;i<handCards.size();i++) {
			if(handCards.get(i) == null) {
				handCards.set(i, card);
				return i;
			}
		}
		return -1;
	}
	
	public boolean playCard(NeapolitanCard card) {
		for(int i=0;i<handCards.size();i++) {
			if(handCards.get(i) == card) {
				handCards.set(i, null);
				return true;
			}
		}
 		return false;
	}

	public boolean hasSeed(int seed) {
		return handCards.stream().anyMatch((card) -> (handCards!=null && seed == card.getSeed()));
	}

	public List<NeapolitanCard> getHandCards() {
		return handCards;
	}
	
}
