package Files.Code.Data;

import java.util.List;

import static Files.Code.Data.ToolData.RESOURCE_TYPE.WEAPON_MATERIAL;

public class WeaponMaterial extends FarmableItem{
    public final List<String> usedBy;
    public WeaponMaterial(String itemName, String availability, List<String> usedBy) {
        super(itemName, availability, WEAPON_MATERIAL.stringToken);
        this.usedBy = usedBy;
    }
}
