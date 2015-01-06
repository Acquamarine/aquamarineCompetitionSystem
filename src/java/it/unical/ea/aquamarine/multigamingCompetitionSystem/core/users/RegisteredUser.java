/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author Denise
 */
@Entity
@DiscriminatorValue("registeredUser")
public class RegisteredUser extends AbstractCompetitor implements Serializable {
	
	@Column(name="username")
	protected String username;

	@Column(name="password")
	protected String password;
	
	@ManyToMany(mappedBy = "members")
	 @JoinTable(name="team", joinColumns=@JoinColumn(name="member_id"), 
    inverseJoinColumns=@JoinColumn(name="team_id"))
	Set<Team> teams = new HashSet<>();
	
	public RegisteredUser(){
		username = "";
		password = "";
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}	

	

	
	
	
	
	
}
