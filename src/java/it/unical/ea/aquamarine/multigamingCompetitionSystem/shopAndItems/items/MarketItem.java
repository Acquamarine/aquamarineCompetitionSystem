
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("marketItem")
public class MarketItem extends AbstractItem {

	private int virtualPointsPrice;

	@Column(name="virtualPointsPrice")
	public int getVirtualPointsPrice() {
		return virtualPointsPrice;
	}

	public void setVirtualPointsPrice(int virtualPointsPrice) {
		this.virtualPointsPrice = virtualPointsPrice;
	}
	
	
}
