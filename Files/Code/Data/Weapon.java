package Files.Code.Data;

import javax.swing.ImageIcon;

public class Weapon extends Item{
    public final String rarity;
    public final String weaponType;
    public final String ascensionMaterial;
    public transient ImageIcon icon;
    public Weapon(String weaponName, String weaponRarity, String weaponType, String weaponAscensionMaterial){
        super(weaponName,"weapon");
        rarity = weaponRarity;
        this.weaponType = weaponType;
        ascensionMaterial = weaponAscensionMaterial;
    }
    public void printInfo(){
        System.out.println(name + " " + rarity + " " + weaponType + " "+ ascensionMaterial);
    }
}
