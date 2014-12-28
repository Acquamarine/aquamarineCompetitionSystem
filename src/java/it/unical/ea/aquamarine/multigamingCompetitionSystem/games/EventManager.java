package it.unical.ea.aquamarine.multigamingCompetitionSystem.games;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class EventManager {
	private static EventManager instance;
	private Map<String,CyclicBarrier> roundBarrier = new HashMap<>();

	public static EventManager getInstance() {
		if(instance==null){
			instance=new EventManager();
		}
		return instance;
	}
	
	public void addBarrier(String str, CyclicBarrier barrier){
		
	}
	
}
