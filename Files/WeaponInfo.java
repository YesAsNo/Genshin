package Files;

import Files.ToolData.WEAPON_RARITY;

/**
 * This class retrieves weapon information from the key in WeaponRarityAndType_Weapon.json
 * As such, it must not be called with the weapon name, rather with the key whose value contains the weapon name.
 */
public class WeaponInfo {

    private final WEAPON_RARITY weaponRarity;
    private final String weaponType;

    /**
     * Constructor of the class
     * @param key Key in the form "Four/Five-Star *weapon type*"
     */
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

    /**
     * Returns the rarity of the weapon in the form of enum.
     * @return weapon rarity
     */
    public WEAPON_RARITY getRarity(){
        return weaponRarity;
    }

    /**
     * Returns the weapon type (sword, claymore, bow, catalyst or polearm)
     * @return weapon type as string.
     */
    public String getWeaponType(){
        return weaponType;
    }
}
