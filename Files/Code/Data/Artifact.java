package Files.Code.Data;

import static Files.Code.Data.ToolData.RESOURCE_TYPE.ARTIFACT;

public class Artifact extends FarmableItem{
    public final String setDescription;
    public Artifact(String itemName, String setDescription) {
        super(itemName, "All",ARTIFACT.stringToken);
        this.setDescription = setDescription;
    }
}
