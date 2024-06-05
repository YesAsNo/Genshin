package Files.Code.Data;

/**
 * This class represents all Characters in the game Genshin Impact.
 */
public class Character extends Item {
    /**
     * Character Element
     */
    public final String element;
    /**
     * Weapon type used by character.
     */
    public final String weaponType;
    /**
     * Talent material used by character.
     */
    public final String talentMaterial;
    /**
     * Weekly talent material used by character.
     */
    public final String weeklyTalentMaterial;

    /**
     * Constructor of Character class.
     *
     * @param charName character name
     * @param charElement character element
     * @param charWeaponType character weapon type
     * @param charTalentMaterial character talent material
     * @param charWeeklyTalentMaterial character weekly talent material
     */
    public Character(String charName, String charElement, String charWeaponType, String charTalentMaterial,
                     String charWeeklyTalentMaterial) {
        super(charName);
        element = charElement;
        weaponType = charWeaponType;
        talentMaterial = charTalentMaterial;
        weeklyTalentMaterial = charWeeklyTalentMaterial;
    }

    public void printInfo() {
        System.out.println(name + " " + element + " " + weaponType + " " + talentMaterial + " " + weeklyTalentMaterial);
    }
}
