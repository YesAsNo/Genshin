package Files;
import Files.ToolData.*;
public class WeaponInfo {

    private final WEAPON_RARITY WEAPONRarity;
    private final String weaponType;
    public WeaponInfo(String key){

        if (key.contains("Five")){
            WEAPONRarity = WEAPON_RARITY.FIVE_STAR;
        }
        else{
            WEAPONRarity = WEAPON_RARITY.FOUR_STAR;
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
        return WEAPONRarity;
    }
    public String getWeaponType(){
        return weaponType;
    }
}
