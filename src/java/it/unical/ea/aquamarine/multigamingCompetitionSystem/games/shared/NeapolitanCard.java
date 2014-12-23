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
	
	

}
