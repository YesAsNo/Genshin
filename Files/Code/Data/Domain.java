package Files.Code.Data;

import Files.Code.GUIs.DomainTabGUI;

import java.util.List;

public class Domain extends Item {
    public final String type;
    public final List<? extends FarmableItem> materials;
    public final boolean rotates;

    public Domain(String domainName, String type, List<? extends FarmableItem> domainMaterials, boolean rotates) {
        super(domainName);
        this.type = type;
        materials = domainMaterials;
        this.rotates = rotates;
    }

    @Override
    public void printInfo() {
        System.out.println(name + " " + type + " " + rotates);
        for (FarmableItem item : materials) {
            item.printInfo();
        }
    }

    public boolean isArtifactDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.ARTIFACT.stringToken);
    }

    public boolean isTalentBookDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.TALENT.stringToken);
    }

    public boolean isWeeklyTalentDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.WEEKLY.stringToken);
    }

    public boolean isWeaponMaterialDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.WEAPON_MAT.stringToken);
    }
}
