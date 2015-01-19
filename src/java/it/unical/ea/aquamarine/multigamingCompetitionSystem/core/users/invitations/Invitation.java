package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.invitations;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Invitation")
public class Invitation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="invitedUser")
	private RegisteredUser invitedUser;
	
	@ManyToOne
	@JoinColumn(name="invitingTeam")
	private Team invitingTeam;

	public Invitation() {
	}

	public Invitation(RegisteredUser invitedUser, Team invitingTeam) {
		this.invitedUser = invitedUser;
		this.invitingTeam = invitingTeam;
	}
	
	

	public RegisteredUser getInvitedUser() {
		return invitedUser;
	}

	public void setInvitedUser(RegisteredUser invitedUser) {
		this.invitedUser = invitedUser;
	}

	public Team getInvitingTeam() {
		return invitingTeam;
	}

	public void setInvitingTeam(Team invitingTeam) {
		this.invitingTeam = invitingTeam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
