package Files;

import static Files.ToolGUI.CHARACTER_LIMIT;
import static Files.ToolGUI.EMPTY_SET_SELECTOR;
import static Files.ToolGUI.EMPTY_WEAPON_SELECTOR;

/**
 * This class is a data structure for a character. The data here is used in CharacterTabGUI.java to construct the GUI.
 */
public class CharacterListing {

    private final String characterName;
    private String characterNotes;
    private String weapon;
    private boolean weaponStatus;
    private String artifactSet1;
    private boolean artifactSet1Status;
    private String artifactSet2;
    private boolean artifactSet2Status;
    private boolean talentStatus;

    /**
     * Constructor of the CharacterCard class.
     * @param character_name character name. Note that this is almost never called on its own, due to character cards being saved and read from json files.
     */
    public CharacterListing(String character_name) {
        assert character_name != null;
        this.characterName = character_name;
        characterNotes="";
        weapon="";
        weaponStatus =false;
        artifactSet1 ="";
        artifactSet1Status=false;
        artifactSet2="";
        artifactSet2Status=false;
        talentStatus=false;

    }

    /**
     * Returns the character name.
     * @return name
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * Returns the character notes (String value, up to 150 characters)
     * @return notes
     */
    public String getCharacterNotes() {
        return characterNotes;
    }

    /**
     * Sets character notes (String value, up to 150 characters)
     * @param character_notes the character notes
     */
    public void setCharacterNotes(String character_notes) {
        assert character_notes.length() <= CHARACTER_LIMIT;
        this.characterNotes = character_notes;
    }

    /**
     * Returns the weapon assigned.
     * @return weapon name
     */
    public String getWeapon() {
        return weapon;
    }

    /**
     * Sets the weapon name.
     * @param weapon the new weapon name
     */
    public void setWeapon(String weapon) {
        if (weapon.equalsIgnoreCase(EMPTY_WEAPON_SELECTOR)){
            this.weapon = "";
        }
        else{
            this.weapon = weapon;
        }

    }

    /**
     * Returns whether the weapon is farmed (to be precise, the weapon material for it is farmed).
     * @return the status
     */
    public boolean getWeaponStatus() {
        return weaponStatus;
    }

    /**
     * Sets the weapon status (whether it is farmed).
     * @param weapon_status whether the weapon is farmed.
     */
    public void setWeaponStatus(boolean weapon_status) {
        this.weaponStatus = weapon_status;
    }

    /**
     * Returns the equipped artifact set 1. May be empty if none is equipped.
     * @return artifact set 1.
     */
    public String getArtifactSet1() {
        return artifactSet1;
    }

    /**
     * Sets the artifact set 1 to the one provided
     * @param artifactSet1 set name
     */
    public void setArtifactSet1(String artifactSet1) {
        if (artifactSet1.equalsIgnoreCase(EMPTY_SET_SELECTOR)){
            this.artifactSet1 = "";
        }
        else{
            this.artifactSet1 = artifactSet1;
        }
    }

    /**
     * Returns whether the artifact set 1 is farmed. Will be false if no set equipped.
     * @return status
     */
    public boolean getArtifactSet1Status() {
        return artifactSet1Status;
    }

    /**
     * Sets the status of whether the artifact set 1 is farmed.
     * @param artifact_set1_status new status
     */
    public void setArtifactSet1Status(boolean artifact_set1_status) {
        this.artifactSet1Status = artifact_set1_status;
    }

    /**
     * Returns the equipped artifact set 2. May be empty if none is equipped.
     * @return artifact set 2.
     */
    public String getArtifactSet2() {
        return artifactSet2;
    }
    /**
     * Sets the artifact set 1 to the one provided
     * @param artifactSet2 set name
     */
    public void setArtifactSet2(String artifactSet2) {
        if (artifactSet2.equalsIgnoreCase(EMPTY_SET_SELECTOR)){
            this.artifactSet2 = "";
        }
        else{
            this.artifactSet2 = artifactSet2;
        }
    }
    /**
     * Returns whether the artifact set 2 is farmed. Will be false if no set equipped.
     * @return status
     */
    public boolean getArtifactSet2Status() {
        return artifactSet2Status;
    }
    /**
     * Sets the status of whether the artifact set 2 is farmed.
     * @param artifact_set2_status new status
     */
    public void setArtifactSet2Status(boolean artifact_set2_status) {
        this.artifactSet2Status = artifact_set2_status;
    }

    /**
     * Returns whether the talent books are farmed for this character. Note this also includes weekly talent materials.
     * @return status
     */
    public boolean getTalentStatus() {
        return talentStatus;
    }

    /**
     * Sets whether the talent books are farmed for this character. This can also include weekly talent materials.
     * @param talent_status new status
     */
    public void setTalentStatus(boolean talent_status) {
        this.talentStatus = talent_status;
    }
}

