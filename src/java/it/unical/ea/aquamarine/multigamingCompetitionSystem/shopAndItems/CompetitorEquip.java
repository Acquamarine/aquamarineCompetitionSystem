package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.AbstractItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;
import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "CompetitorEquip")
public class CompetitorEquip implements Serializable {
	
	@ManyToMany(targetEntity = AbstractItem.class,fetch = FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<ItemCategory, IItem> equipMap = new HashMap<>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isItemEquipped(IItem item){
		if(equipMap.get(item.getCategory())==null){
			return false;
		}
		return equipMap.get(item.getCategory()).equals(item);
	}
}
