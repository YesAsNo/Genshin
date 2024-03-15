package Files;
import Files.ToolData.*;
public class WeaponInfo {

    private final RARITY rarity;
    private final String weaponType;
    public WeaponInfo(String key){

        if (key.contains("Five")){
            rarity = RARITY.FIVE_STAR;
        }
        else{
            rarity = RARITY.FOUR_STAR;
        }
        String[] subkeys = key.split(" ");
        if (subkeys.length > 1){
            weaponType = subkeys[1];
        }
        else {
            weaponType = "unknown_weapon";
        }

    }
    public RARITY getRarity(){
        return rarity;
    }
    public String getWeaponType(){
        return weaponType;
    }
}
