package Files.Code.Data;

import java.util.List;

public class WeeklyTalentMaterial extends FarmableItem {
    public final List<String> usedBy;

    public WeeklyTalentMaterial(String itemName, String availability, List<String> usedBy) {
        super(itemName, availability);
        this.usedBy = usedBy;
    }
}
