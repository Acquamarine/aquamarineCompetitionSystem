
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

public enum ItemCategory {
	CARD_COVER("CARD_COVER"), AVATAR("AVATAR");
	
	private final String categoryName;       

    private ItemCategory(String name) {
        this.categoryName = name;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:categoryName.equals(otherName);
    }

	@Override
    public String toString(){
       return categoryName;
    }
	
	public static ItemCategory getItemCategory(String category) {
		switch(category) {
			case "CARD_COVER":
				return CARD_COVER;
			default:
				return AVATAR;
				
		}
	}
	
}
