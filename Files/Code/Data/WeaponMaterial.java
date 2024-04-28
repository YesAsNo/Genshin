package Files.Code.Data;

import java.util.List;

public class WeaponMaterial extends FarmableItem {
    public final List<String> usedBy;

    public WeaponMaterial(String itemName, String availability, List<String> usedBy) {
        super(itemName, availability);
        this.usedBy = usedBy;
    }
}
