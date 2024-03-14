package Files;

public class CharacterCard {

    private String characterName;
    private String characterNotes;
    private String weapon;
    private boolean weaponStatus;
    private String artifactSet1;
    private boolean artifactSet1Status;
    private String artifactSet2;
    private boolean artifactSet2Status;
    private boolean talentStatus;


    public CharacterCard(String character_name) {
        this.characterName=character_name;
        characterNotes=null;
        weapon=null;
        weaponStatus =false;
        artifactSet1 =null;
        artifactSet1Status=false;
        artifactSet2=null;
        artifactSet2Status=false;
        talentStatus=false;
    }

    // Character Name
    public String getCharacterName() {
        return characterName;
    }
    public void setCharacterName(String character_name) {
        this.characterName = character_name;
    }

    // Character Notes
    public String getCharacterNotes() {
        return characterNotes;
    }
    public void setCharacterNotes(String character_notes) {
        this.characterNotes = character_notes;
    }

    // Weapon and Weapon Status
    public String getWeapon() {
        return weapon;
    }
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public boolean getWeaponStatus() {
        return weaponStatus;
    }
    public void setWeaponStatus(boolean weapon_status) {
        this.weaponStatus = weapon_status;
    }

    // Artifact Sets 1 and 2
    public String getArtifactSet1() {
        return artifactSet1;
    }
    public void setArtifactSet1(String artifactSet1) {
        this.artifactSet1 = artifactSet1;
    }

    public boolean getArtifactSet1_status() {
        return artifactSet1Status;
    }
    public void setArtifactSet1Status(boolean artifact_set1_status) {
        this.artifactSet1Status = artifact_set1_status;
    }

    public String getArtifactSet2() {
        return artifactSet2;
    }
    public void setArtifact_set2(String artifact_set2) {
        this.artifactSet2 = artifact_set2;
    }

    public boolean getArtifactSet2Status() {
        return artifactSet2Status;
    }
    public void setArtifact_set2_status(boolean artifact_set2_status) {
        this.artifactSet2Status = artifact_set2_status;
    }

    // Talent Status
    public boolean getTalentStatus() {
        return talentStatus;
    }
    public void setTalent_status(boolean talent_status) {
        this.talentStatus = talent_status;
    }
}

