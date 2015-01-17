
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems;

public enum ItemCategory {
	CARD_COVER("Card cover"), AVATAR("Avatar");
	
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
			case "Card cover":
				return CARD_COVER;
			default:
				return AVATAR;
				
		}
	}
	
}
