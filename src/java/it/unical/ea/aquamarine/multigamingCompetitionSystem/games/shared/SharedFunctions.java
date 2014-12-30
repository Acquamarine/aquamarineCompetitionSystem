package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class SharedFunctions {
	
	
	public static Queue<NeapolitanCard> getShuffledNeapolitanDeck() {
		List<NeapolitanCard> orderedDeck = OrderedNeapolitanDeck.getInstance().getDeck();
		List<NeapolitanCard> deckCopy = new ArrayList<>();
		deckCopy.addAll(orderedDeck);
		Queue<NeapolitanCard> shuffledDeck = new LinkedList<>();
		
		Random random = new Random();
		for(int i=0;i<40;i++) {
			int nextCard = random.nextInt(40-i);
			shuffledDeck.add(deckCopy.get(nextCard));
			deckCopy.remove(nextCard);
		}
		return shuffledDeck;
	}
}
