
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("eloRewardItem")
public class EloRewardItem extends AbstractItem {

	private int unlockingElo;
	
	@Column(name="unlockingElo")
	public int getUnlockingElo() {
		return unlockingElo;
	}

	public void setUnlockingElo(int unlockingElo) {
		this.unlockingElo = unlockingElo;
	}
	
	

}
