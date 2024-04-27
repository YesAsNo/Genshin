package Files.Code.Data;

public abstract class FarmableItem extends Item{

    public final String availability;

    public FarmableItem (String farmableItemName, String availability, String farmableItemType){
        super(farmableItemName, farmableItemType);
        this.availability = availability;
    }
    @Override
    public void printInfo() {
        System.out.println(name+" "+type+" "+availability);
    }
}
