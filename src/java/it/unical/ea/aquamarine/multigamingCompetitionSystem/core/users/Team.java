package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("team")
public class Team extends AbstractCompetitor{
	
	private Set<RegisteredUser> members;
	private RegisteredUser leader;

	public Team() {
		 members = new HashSet<>();
	}
	
	public void addMember(RegisteredUser us){
		members.add(us);
	}
	public void removeMember(RegisteredUser us){
		members.remove(us);
	}

	@ManyToMany(mappedBy = "teams", fetch = FetchType.EAGER)
	public Set<RegisteredUser> getMembers() {
		return members;
	}

	public void setMembers(Set<RegisteredUser> members) {
		this.members = members;
	}
	
	@Override
	@Transient
	public boolean isTeam() {
		return true;
	}

	@ManyToOne
	@JoinColumn(name = "leader")
	public RegisteredUser getLeader() {
		return leader;
	}

	public void setLeader(RegisteredUser leader) {
		this.leader = leader;
	}
	
	
}
