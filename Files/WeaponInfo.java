package Files;

import Files.ToolData.WEAPON_RARITY;
public class WeaponInfo {

    private final WEAPON_RARITY weaponRarity;
    private final String weaponType;
    public WeaponInfo(String key){

        if (key.contains("Five")){
            weaponRarity = WEAPON_RARITY.FIVE_STAR;
        }
        else{
            weaponRarity = WEAPON_RARITY.FOUR_STAR;
        }
        String[] subkeys = key.split(" ");
        if (subkeys.length > 1){
            weaponType = subkeys[1];
        }
        else {
            weaponType = "unknown_weapon";
        }

    }
    public WEAPON_RARITY getRarity(){
        return weaponRarity;
    }
    public String getWeaponType(){
        return weaponType;
    }
}
