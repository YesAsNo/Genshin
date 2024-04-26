package Files;

import javax.swing.ImageIcon;

public class Weapon {
    public final String name;
    public final String rarity;
    public final String type;
    public final String ascensionMaterial;
    public transient ImageIcon icon;
    public Weapon(String weaponName, String weaponRarity, String weaponType, String weaponAscensionMaterial){
        name = weaponName;
        rarity = weaponRarity;
        type = weaponType;
        ascensionMaterial = weaponAscensionMaterial;
    }
    public void printInfo(){
        System.out.println(name + " " + rarity + " " + type + " "+ ascensionMaterial);
    }
}
