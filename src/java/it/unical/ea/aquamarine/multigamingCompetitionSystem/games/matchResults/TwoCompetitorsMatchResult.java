
package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.matchResults;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.core.ICompetitor;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TwoCompetitorsMatchResult")
public class TwoCompetitorsMatchResult implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@ManyToOne
	@JoinColumn(name="player1")
	private ICompetitor player1;
	@ManyToOne
	@JoinColumn(name="player2")
	private ICompetitor player2;
	@Column
	private int player1Score;
	@Column
	private int player2Score;
	@Column
	private boolean rankedMatch;

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

	public boolean isRankedGame() {
		return rankedMatch;
	}

	public void setRankedMatch(boolean rankedMatch) {
		this.rankedMatch = rankedMatch;
	}
	
	
	
	
}
