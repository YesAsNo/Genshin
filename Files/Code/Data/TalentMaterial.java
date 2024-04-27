package Files.Code.Data;

import java.util.List;

import static Files.Code.Data.ToolData.RESOURCE_TYPE.TALENT_BOOK;

public class TalentMaterial extends FarmableItem{
    public final List<String> usedBy;
    public TalentMaterial(String itemName, List<String> usedBy, String availability) {
        super(itemName, availability, TALENT_BOOK.stringToken);
        this.usedBy = usedBy;
    }
}
