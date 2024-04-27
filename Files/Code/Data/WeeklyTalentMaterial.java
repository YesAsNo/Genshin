package Files.Code.Data;

import java.util.List;

import static Files.Code.Data.ToolData.RESOURCE_TYPE.WEEKLY_BOSS_MATERIAL;

public class WeeklyTalentMaterial extends FarmableItem{
    public final List<String> usedBy;
    public WeeklyTalentMaterial(String itemName, String availability, List<String> usedBy) {
        super(itemName, availability, WEEKLY_BOSS_MATERIAL.stringToken);
        this.usedBy = usedBy;
    }
}
