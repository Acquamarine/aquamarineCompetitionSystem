package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("team")
public class Team extends AbstractCompetitor{
	
	@ManyToMany(mappedBy = "teams")
	Set<RegisteredUser> members = new HashSet<>();
	
	public void addMember(RegisteredUser us){
		members.add(us);
	}
	public void removeMember(RegisteredUser us){
		members.remove(us);
	}
	
}
