package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SummaryManager {
	private final List<TressetteRoundSummary> eventsSummaries = new ArrayList<>();
	private TressetteResultsSummary resultsSummary;

	public synchronized TressetteRoundSummary getSummary(int eventIndex) {
		while(eventsSummaries.size()<=eventIndex) {
			try {
				this.wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(SummaryManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return eventsSummaries.get(eventIndex);
	}

	public synchronized void addSummary(TressetteRoundSummary summary) {
		eventsSummaries.add(summary);
		this.notifyAll();
	}

	public void setResultsSummary(TressetteResultsSummary resultsSummary) {
		this.resultsSummary = resultsSummary;
	}
	
	public TressetteResultsSummary getResults() {
		return resultsSummary;
	}

	boolean areThereSummaries() {
		return !eventsSummaries.isEmpty();
	}
}
