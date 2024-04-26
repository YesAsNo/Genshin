package Files;

import javax.swing.ImageIcon;

public class Character {
    public final String name;
    public final String element;
    public final String weaponType;
    public final String talentMaterial;
    public final String weeklyTalentMaterial;
    public transient ImageIcon icon;
    public Character(String charName, String charElement, String charWeaponType, String charTalentMaterial, String charWeeklyTalentMaterial){
        name = charName;
        element = charElement;
        weaponType = charWeaponType;
        talentMaterial = charTalentMaterial;
        weeklyTalentMaterial = charWeeklyTalentMaterial;
    }
    public void printInfo() {
        System.out.println(name + " "+ element + " "+weaponType + " "+ talentMaterial+" "+weeklyTalentMaterial);
    }
}
