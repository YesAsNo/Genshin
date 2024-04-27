package Files;

import java.util.List;

public class Domain extends Item{
    public final String domainType;
    public final List<Item> materials;
    public final boolean rotates;
    public Domain(String domainName, String domainType, List<Item> domainMaterials, boolean rotates){
        super(domainName,"domain");
        this.domainType = domainType;
        materials = domainMaterials;
        this.rotates = rotates;
    }

    @Override
    public void printInfo() {

    }
}
