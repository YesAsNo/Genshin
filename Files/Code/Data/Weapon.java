package Files.Code.Data;

/**
 * This class represents all Weapons from the game Genshin Impact.
 */
public class Weapon extends Item {
    public final String rarity;
    public final String weaponType;
    public final String ascensionMaterial;

    public Weapon(String weaponName, String weaponRarity, String weaponType, String weaponAscensionMaterial) {
        super(weaponName);
        rarity = weaponRarity;
        this.weaponType = weaponType;
        ascensionMaterial = weaponAscensionMaterial;
    }

    public void printInfo() {
        System.out.println(name + " " + rarity + " " + weaponType + " " + ascensionMaterial);
    }
}
