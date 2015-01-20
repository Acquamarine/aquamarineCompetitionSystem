package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.ITurnSummary;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared.NeapolitanGameRoundSummary;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurnGameSummaryManager {
	private final List<ITurnSummary> eventsSummaries = new ArrayList<>();

	public synchronized ITurnSummary getSummary(int eventIndex) {
		while(eventsSummaries.size()<=eventIndex) {
			try {
				this.wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(TurnGameSummaryManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return eventsSummaries.get(eventIndex);
	}

	public synchronized void addSummary(NeapolitanGameRoundSummary summary) {
		eventsSummaries.add(summary);
		this.notifyAll();
	}

	public boolean areThereSummaries() {
		return !eventsSummaries.isEmpty();
	}
}
