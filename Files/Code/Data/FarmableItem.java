package Files.Code.Data;

public abstract class FarmableItem extends Item {

    public final String availability;

    public FarmableItem(String farmableItemName, String availability) {
        super(farmableItemName);
        this.availability = availability;
    }

    @Override
    public void printInfo() {
        System.out.println(name + " " + availability);
    }

}
