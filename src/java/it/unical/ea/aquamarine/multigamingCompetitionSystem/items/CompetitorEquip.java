package it.unical.ea.aquamarine.multigamingCompetitionSystem.items;

import java.io.Serializable;
import java.util.Map;

public class CompetitorEquip implements Serializable {
	private Map<ItemCategory, IItem> equipMap;

	public Map<ItemCategory, IItem> getEquipMap() {
		return equipMap;
	}

	public void setEquipMap(Map<ItemCategory, IItem> equipMap) {
		this.equipMap = equipMap;
	}

	public void equipItem(IItem item) {
		equipMap.put(item.getCategory(), item);
	}
	
	public void unequipItem(IItem item) {
		equipMap.remove(item.getCategory());
	}
	
	
}
