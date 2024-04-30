package Files.Code.Data;

/**
 * This class represents all materials obtainable from domains.
 */
public abstract class FarmableItem extends Item {
    /**
     * Availability of the material (i.e. on what days it is obtainable from the corresponding domain). Note that all materials are assumed to be available on Sundays.
     */
    public final String availability;

    /**
     * Constructor of Farmable Item. Note this is an abstract class and all inheritors must use this constructor.
     * @param farmableItemName item name
     * @param availability item availability
     */

    public FarmableItem(String farmableItemName, String availability) {
        super(farmableItemName);
        this.availability = availability;
    }

    @Override
    public void printInfo() {
        System.out.println(name + " " + availability);
    }

}
