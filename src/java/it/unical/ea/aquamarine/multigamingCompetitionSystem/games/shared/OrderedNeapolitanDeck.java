package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared;

import java.util.ArrayList;
import java.util.List;

public class OrderedNeapolitanDeck {
	private static OrderedNeapolitanDeck instance;
	private List<NeapolitanCard> deck = new ArrayList<>(40); 

	public static OrderedNeapolitanDeck getInstance() {
		if(instance == null) {
			instance = new OrderedNeapolitanDeck();
		}
		return instance;
	}
	public OrderedNeapolitanDeck() {
		for(int i=0;i<40;i++) {
			int nextSeed = i/4;
			int nextNumber = i%10+1;
			deck.set(i,new NeapolitanCard(nextSeed, nextNumber));
		}
	}
	
	public List<NeapolitanCard> getDeck() {
		return deck;
	}
	
	
	
	
}
