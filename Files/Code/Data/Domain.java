package Files.Code.Data;

import Files.Code.GUIs.DomainTabGUI;

import java.util.List;

public class Domain extends Item {
    public final String domainType;
    public final List<? extends FarmableItem> materials;
    public final boolean rotates;

    public Domain(String domainName, String domainType, List<? extends FarmableItem> domainMaterials, boolean rotates) {
        super(domainName, "domain");
        this.domainType = domainType;
        materials = domainMaterials;
        this.rotates = rotates;
    }

    @Override
    public void printInfo() {

    }

    public boolean isArtifactDomain() {
        return domainType.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.ARTIFACT.stringToken);
    }

    public boolean isTalentBookDomain() {
        return domainType.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.TALENT.stringToken);
    }

    public boolean isWeeklyTalentDomain() {
        return domainType.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.WEEKLY.stringToken);
    }

    public boolean isWeaponMaterialDomain() {
        return domainType.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.WEAPON_MAT.stringToken);
    }
}
