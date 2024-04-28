package Files.Code.Data;

public class Artifact extends FarmableItem {
    public final String setDescription;

    public Artifact(String itemName, String setDescription) {
        super(itemName, "All");
        this.setDescription = setDescription;
    }
}
