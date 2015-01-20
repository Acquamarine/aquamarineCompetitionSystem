
package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.invitations.Invitation;
import java.util.List;

public interface InvitationsDAO {
	
	public void create(Invitation invitation);
	public void delete(Team team, RegisteredUser user);
	public List<Invitation> retrieve(RegisteredUser invited);
	public List<Invitation> retrieve(Team inviting);
	public boolean doesInvitationExists(Team invitingTeam, RegisteredUser invitedUser);
}
