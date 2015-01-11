package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("team")
public class Team extends AbstractCompetitor{
	
	private Set<RegisteredUser> members;

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
	
	
}
