package Files.Code.Data;

public class Artifact extends FarmableItem {
    public final String description_2piece;
    public final String description_4piece;

    public Artifact(String itemName, String descr_2p, String descr_4p) {
        super(itemName, "All");
        description_2piece = descr_2p;
        description_4piece = descr_4p;
    }
}
