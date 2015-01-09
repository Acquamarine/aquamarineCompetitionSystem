package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Table(name="Competitor")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING) 
public abstract class AbstractCompetitor extends AbstractUser implements ICompetitor{
	
	protected Map<String, Integer> competitionProfile = new HashMap<>();
	
	@Override
	public Integer getElo(String game) {
		competitionProfile.putIfAbsent(game, 1200);
		return competitionProfile.get(game);
	}

	@Override
	public void updateElo(String game, int elo) {
		System.out.println(nickname + "'s elo is now "+elo);
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
	
	
}
