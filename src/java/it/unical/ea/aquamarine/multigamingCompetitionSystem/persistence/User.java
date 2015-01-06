/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Denise
 */
@Entity
@Table(name="User")
public class User implements Serializable {
	@Id
	protected int id;
	@Column(name="username")
	protected String username;
	@Column(name="nickname")
	protected String nickname;
	@Column(name="password")
	protected String password;
	
	public User(){
		nickname = "";
		username = "";
		password = "";
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
