
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.AbstractCompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TwoCompetitorsMatchResult")
public class TwoCompetitorsMatchResult implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@ManyToOne(targetEntity = AbstractCompetitor.class)
	@JoinColumn(name="player1")
	private ICompetitor player1;
	@ManyToOne(targetEntity = AbstractCompetitor.class)
	@JoinColumn(name="player2")
	private ICompetitor player2;
	@Column
	private String game;
	@Column
	private int player1Score;
	@Column
	private int player2Score;
	@Column
	private boolean rankedMatch;
	@Column
	private Timestamp matchEndTime;

	
	
	public ICompetitor getPlayer1() {
		return player1;
	}

	public void setPlayer1(ICompetitor player1) {
		this.player1 = player1;
	}

	public ICompetitor getPlayer2() {
		return player2;
	}

	public void setPlayer2(ICompetitor player2) {
		this.player2 = player2;
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}

	public boolean isRankedMatch() {
		return rankedMatch;
	}

	public void setRankedMatch(boolean rankedMatch) {
		this.rankedMatch = rankedMatch;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getMatchEndTime() {
		return matchEndTime;
	}

	public void setMatchEndTime(Timestamp matchEndTime) {
		this.matchEndTime = matchEndTime;
	}
	public void setMatchEndTimeByMillis(long matchEndTime) {
		this.matchEndTime = new Timestamp(matchEndTime);
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}
	
	
	
	
}
