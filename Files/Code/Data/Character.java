package Files.Code.Data;

import static Files.Code.Data.ToolData.DATA_CATEGORY.CHARACTER;

public class Character extends Item {
    public final String element;
    public final String weaponType;
    public final String talentMaterial;
    public final String weeklyTalentMaterial;
    public Character(String charName, String charElement, String charWeaponType, String charTalentMaterial, String charWeeklyTalentMaterial){
        super(charName, CHARACTER.stringToken);
        element = charElement;
        weaponType = charWeaponType;
        talentMaterial = charTalentMaterial;
        weeklyTalentMaterial = charWeeklyTalentMaterial;
    }
    public void printInfo() {
        System.out.println(name + " "+ element + " "+weaponType + " "+ talentMaterial+" "+weeklyTalentMaterial);
    }
}
