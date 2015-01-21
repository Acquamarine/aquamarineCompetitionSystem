
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.shared;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public interface ITurnSummary {
	public void buildJsonRepresentation(JSONObject summaryJson, HttpServletRequest request);
}
