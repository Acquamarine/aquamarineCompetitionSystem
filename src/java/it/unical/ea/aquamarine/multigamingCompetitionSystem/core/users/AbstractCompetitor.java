package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.CompetitorEquip;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.CompetitorInventory;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.Hibernate;

@Entity
@Table(name = "Competitor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractCompetitor extends AbstractUser implements ICompetitor {

	public AbstractCompetitor() {
		super();
	}

	protected int virtualPoints = 0;
	protected Map<String, Integer> competitionProfile = new HashMap<>();
	protected Map<String, CompetitorEquip> equip = new HashMap<>();
	protected CompetitorInventory inventory = new CompetitorInventory();

	@Override
	public Integer getElo(String game) {
		competitionProfile.putIfAbsent(game, 1200);
		return competitionProfile.get(game);
	}

	@Override
	public void updateElo(String game, int elo) {
		System.out.println(nickname + "'s elo is now " + elo + " at " + game);
		competitionProfile.put(game, elo);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Override
	public int getId() {
		return super.getId(); //To change body of generated methods, choose Tools | Templates.
	}

	@Column(name = "nickname", nullable = false, unique = true)
	@Override
	public String getNickname() {
		return super.getNickname(); //To change body of generated methods, choose Tools | Templates.
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "UserElo", joinColumns = @JoinColumn(name = "userId"))
	public Map<String, Integer> getCompetitionProfile() {
		return competitionProfile;
	}

	public void setCompetitionProfile(Map<String, Integer> competitionProfile) {
		this.competitionProfile = competitionProfile;
	}

	public void setVirtualPoints(int virtualPoints) {
		this.virtualPoints = virtualPoints;
	}

	@Column(name = "virtualPoints")
	@Override
	public int getVirtualPoints() {
		return virtualPoints;
	}

	@Override
	public void gainVirtualPoints(Integer earnedPoints) {
		this.virtualPoints += earnedPoints;
		DAOProvider.getCompetitorDAO().updateCompetitor(this);
	}

	@Override
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "competitorId")
	public Map<String, CompetitorEquip> getEquip() {
		return equip;
	}

	public void setEquip(Map<String, CompetitorEquip> equip) {
		this.equip = equip;
	}

	@Override
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "inventory")
	public CompetitorInventory getInventory() {
		return inventory;
	}

	public void setInventory(CompetitorInventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public CompetitorEquip getEquip(String game) {
		if(!equip.containsKey(game)){
			equip.put(game, new CompetitorEquip());
		}
		return equip.get(game);
	}

}
