package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared;

import java.util.ArrayList;
import java.util.List;

public class NeapolitanHand {
	private List<NeapolitanCard> handCards  = new ArrayList<>(3);
	
	public void addCard(NeapolitanCard card) {
		for(int i=0;i<handCards.size();i++) {
			if(handCards.get(i) == null) {
				handCards.set(i, card);
			}
		}
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
	
}
