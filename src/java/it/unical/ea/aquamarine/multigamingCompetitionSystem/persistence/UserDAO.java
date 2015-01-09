package it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.util.List;
import javafx.util.Pair;

public interface UserDAO {
	public void create(RegisteredUser u);
	public RegisteredUser retrieveByNick(String nick);
	public RegisteredUser retrieveByUsername(String username);
	public boolean doesUserExistByUsername(String username);
	public boolean doesUserExistByNick(String nick);
	public ICompetitor retrieveById(Integer competitor);
	public List<Pair<String,Integer>> getUsersRanking(String game);
}
