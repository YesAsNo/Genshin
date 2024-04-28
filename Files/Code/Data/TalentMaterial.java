package Files.Code.Data;

import java.util.List;

public class TalentMaterial extends FarmableItem {
    public final List<String> usedBy;

    public TalentMaterial(String itemName, String availability, List<String> usedBy) {
        super(itemName, availability);
        this.usedBy = usedBy;
    }
}
