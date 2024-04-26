package Files;

import java.util.List;

public class Domain {
    public final String name;
    public final String type;
    public final List<Item> materials;
    public final boolean availableEveryDay;
    public Domain(String domainName, String domainType, List<Item> domainMaterials, boolean availableEveryDay){
        name = domainName;
        type = domainType;
        materials = domainMaterials;
        this.availableEveryDay = availableEveryDay;
    }
}
