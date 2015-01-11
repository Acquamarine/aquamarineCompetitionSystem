package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("registeredUser")
public class RegisteredUser extends AbstractCompetitor implements Serializable {

	
	
	
	
	protected String username;
	
	protected String password;
	
	private Set<Team> teams;
	
	public RegisteredUser(){
		username = "";
		password = "";
		teams = new HashSet<>();
	}
	
	@Column(name="username")
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}	

	public void addTeam(Team team) {
		teams.add(team);
	}

	@ManyToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(name="team_users", joinColumns=@JoinColumn(name="member_id"), 
    inverseJoinColumns=@JoinColumn(name="team_id"))
	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	

	

	
	
	
	
	
}
