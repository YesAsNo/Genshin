package Files;

public class CharacterCard {

    private String character_name;
    private String character_notes;
    private String weapon;
    private boolean weapon_status;
    private String artifact_set1;
    private boolean artifact_set1_status;
    private String artifact_set2;
    private boolean artifact_set2_status;
    private boolean talent_status;


    public CharacterCard() {}

    public CharacterCard(String character_name) {
        this.character_name=character_name;
        character_notes=null;
        weapon=null;
        weapon_status=false;
        artifact_set1=null;
        artifact_set1_status=false;
        artifact_set2=null;
        artifact_set2_status=false;
        talent_status=false;
    }

    // Character Name
    public String getCharacter_name() {
        return character_name;
    }
    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    // Character Notes
    public String getCharacter_notes() {
        return character_notes;
    }
    public void setCharacter_notes(String character_notes) {
        this.character_notes = character_notes;
    }

    // Weapon and Weapon Status
    public String getWeapon() {
        return weapon;
    }
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public boolean getWeapon_status() {
        return weapon_status;
    }
    public void setWeapon_status(boolean weapon_status) {
        this.weapon_status = weapon_status;
    }

    // Artifact Sets 1 and 2
    public String getArtifact_set1() {
        return artifact_set1;
    }
    public void setArtifact_set1(String artifact_set1) {
        this.artifact_set1 = artifact_set1;
    }

    public boolean getArtifact_set1_status() {
        return artifact_set1_status;
    }
    public void setArtifact_set1_status(boolean artifact_set1_status) {
        this.artifact_set1_status = artifact_set1_status;
    }

    public String getArtifact_set2() {
        return artifact_set2;
    }
    public void setArtifact_set2(String artifact_set2) {
        this.artifact_set2 = artifact_set2;
    }

    public boolean getArtifact_set2_status() {
        return artifact_set2_status;
    }
    public void setArtifact_set2_status(boolean artifact_set2_status) {
        this.artifact_set2_status = artifact_set2_status;
    }

    // Talent Status
    public boolean getTalent_status() {
        return talent_status;
    }
    public void setTalent_status(boolean talent_status) {
        this.talent_status = talent_status;
    }
}

