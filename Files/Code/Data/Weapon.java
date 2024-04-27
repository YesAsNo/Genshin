package Files.Code.Data;

import javax.swing.ImageIcon;

import static Files.Code.Data.ToolData.RESOURCE_TYPE.WEAPON_NAME;

public class Weapon extends Item{
    public final String rarity;
    public final String weaponType;
    public final String ascensionMaterial;
    public Weapon(String weaponName, String weaponRarity, String weaponType, String weaponAscensionMaterial){
        super(weaponName,WEAPON_NAME.stringToken);
        rarity = weaponRarity;
        this.weaponType = weaponType;
        ascensionMaterial = weaponAscensionMaterial;
    }
    public void printInfo(){
        System.out.println(name + " " + rarity + " " + weaponType + " "+ ascensionMaterial);
    }
}
