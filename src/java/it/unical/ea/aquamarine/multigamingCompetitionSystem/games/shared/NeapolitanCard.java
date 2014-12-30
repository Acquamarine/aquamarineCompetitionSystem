package it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared;

public class NeapolitanCard {
	public static int CUPS=0;
	public static int COINS=1;
	public static int CLUBS=2;
	public static int SWORDS=3;
	private int seed;
	private int number;

	public NeapolitanCard(int seed, int number) {
		this.seed = seed;
		this.number = number;
	}

	public NeapolitanCard(String card){
		String splitting[] = card.split("_");
		number = Integer.parseInt(splitting[0]);
		
		switch(splitting[1]) {
			case "cups":
				seed = 0;
				break;
			case "coins":
				seed = 1;
				break;
			case "clubs":
				seed = 2;
				break;
			case "swords":
				seed = 3;
				break;
		}
	}
	
	public int getSeed() {
		return seed;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		String seedString = "";
		switch(seed) {
			case 0:
				seedString = "cups";
				break;
			case 1:
				seedString = "coins";
				break;
			case 2:
				seedString = "clubs";
				break;
			case 3:
				seedString = "swords";
				break;
		}
		return number+"_"+seedString;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		final NeapolitanCard other = (NeapolitanCard) obj;
		if(this.seed != other.seed){
			return false;
		}
		if(this.number != other.number){
			return false;
		}
		return true;
	}
	
	

}
