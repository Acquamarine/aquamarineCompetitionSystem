
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shared;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;


public class GameConstants {

	public static URI ITEMS_CONFIG_PATH;
	public static int UNRANKED_ELO = -1;
	public static int UNRANKED_RANK = -1;
	public static Map<String, Pair<Integer, Integer>> virtualPointsRewards = initVirtualPointRewards();

	private static Map<String, Pair<Integer, Integer>> initVirtualPointRewards() {
		Map<String, Pair<Integer, Integer>> map = new HashMap<>();
		map.put(Tressette1v1.class.getSimpleName(), new Pair<>(50, 100));
		return map;
	}
	
	
}
