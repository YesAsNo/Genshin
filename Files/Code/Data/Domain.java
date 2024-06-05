package Files.Code.Data;

import Files.Code.GUIs.DomainTabGUI;

import java.util.Set;

/**
 * This class represents all Domains in the game Genshin Impact.
 */
public class Domain extends Item {
    /**
     * Type of domain.
     */
    public final String type;
    /**
     * Materials obtainable from the domain
     */
    public final Set<? extends FarmableItem> materials;
    /**
     * Whether the domain rotates, i.e. yields different materials based on the day of the week.
     */
    public final boolean rotates;

    /**
     * Constructor of Domain class
     *
     * @param domainName domain name
     * @param type domain type
     * @param domainMaterials materials obtainable in the domain
     * @param rotates whether the domain rotates (yields different materials based on the day of the week)
     */
    public Domain(String domainName, String type, Set<? extends FarmableItem> domainMaterials, boolean rotates) {
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

    /**
     * Checks if this domain is an Artifact domain
     *
     * @return true if it is, otherwise false
     */
    public boolean isArtifactDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.ARTIFACT.stringToken);
    }

    /**
     * Checks if this domain is a Talent Material domain
     *
     * @return true if it is, otherwise false
     */
    public boolean isTalentMaterialDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.TALENT.stringToken);
    }

    /**
     * Checks if this domain is a Weekly Talent Material domain
     *
     * @return true if it is, otherwise false
     */
    public boolean isWeeklyTalentMaterialDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.WEEKLY.stringToken);
    }

    /**
     * Checks if this domain is a Weapon Material domain
     *
     * @return true if it is, otherwise false
     */
    public boolean isWeaponMaterialDomain() {
        return type.equalsIgnoreCase(DomainTabGUI.DOMAIN_FILTER_OPTIONS.WEAPON_MAT.stringToken);
    }
}
