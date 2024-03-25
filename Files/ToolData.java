package Files;

import static Files.ToolGUI.UNKNOWN_WEAPON_MESSAGE;
import static Files.ToolGUI.UNKNOWN_WEAPON_PATH;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ToolData {
    private static Map<String,String> artifactSetDescriptions = new TreeMap<>();
    public static final String SAVE_LOCATION = "./UserData/";
    public static final Map<String, Boolean> PATHS_TO_DATA_FILES;
    static {
        PATHS_TO_DATA_FILES = new TreeMap<>();
        PATHS_TO_DATA_FILES.put("./Element_Character.json",true);
        PATHS_TO_DATA_FILES.put("./WeeklyBossMaterial_Character.json",false);
        PATHS_TO_DATA_FILES.put("./TalentBook_Character.json",false);
        PATHS_TO_DATA_FILES.put("./ArtifactDomain_ArtifactSet.json",true);
        PATHS_TO_DATA_FILES.put("./WeaponType_Character.json",false);
        PATHS_TO_DATA_FILES.put("./TalentDomain_TalentBook.json",true);
        PATHS_TO_DATA_FILES.put("./WeaponDomain_WeaponMaterial.json",true);
        PATHS_TO_DATA_FILES.put("./WeeklyDomain_WeeklyBossMaterial.json",true);
        PATHS_TO_DATA_FILES.put("./WeaponMaterial_WeaponName.json",true);
        PATHS_TO_DATA_FILES.put("./ArtifactSet_ArtifactSetDescription.json",false);
        PATHS_TO_DATA_FILES.put("./WeaponRarityAndType_WeaponName.json",false);
    }
    private static final Map<String,Map<String,List<String>>> parsedMappings = new TreeMap<>();
    private static final Map<String,List<String>> parsedFlattenedData = new TreeMap<>();
    public enum flattenedDataCategory {
        ARTIFACT_SET("ArtifactSet"),
        CHARACTER("Character"),
        TALENT_BOOK("TalentBook"),
        WEAPON_MATERIAL("WeaponMaterial"),
        WEAPON_NAME("WeaponName"),
        WEEKLY_BOSS_MATERIAL("WeeklyBossMaterial");
        public final String stringToken;
        flattenedDataCategory(String data) {
            this.stringToken = data;
        }
    }
    public enum knownMappings{
        ARTIDOMAIN_ARTISET("ArtifactDomain_ArtifactSet"),
        ARTISET_ARTISETDESC("ArtifactSet_ArtifactSetDescription"),
        ELEM_CHAR("Element_Character"),
        TALENTBOOK_CHAR("TalentBook_Character"),
        TALENTDOMAIN_TALENTBOOK("TalentDomain_TalentBook"),
        WEPDOMAIN_WEPMAT("WeaponDomain_WeaponMaterial"),
        WEPMAT_WEPNAME("WeaponMaterial_WeaponName"),
        WEPRARITYANDTYPE_WEPNAME("WeaponRarityAndType_WeaponName"),
        WEPTYPE_CHAR("WeaponType_Character"),
        WEEKLYBOSSMAT_CHAR("WeeklyBossMaterial_Character"),
        WEEKLYDOMAIN_WEEKLYBOSSMAT("WeeklyDomain_WeeklyBossMaterial");
        public final String stringToken;
        knownMappings(String data) {
            this.stringToken = data;
        }
    }

    public enum WEAPON_RARITY {
        FIVE_STAR,
        FOUR_STAR
    }
    public enum RESOURCE_TYPE {
        WEAPON,
        ARTIFACT,
        WEAPON_MATERIAL,
        CHARACTER,
        TALENT_BOOK,
        WEEKLY_BOSS_MATERIAL
    }
    public enum CHARACTER_CARD_DATA_FIELD{
        NAME,
        WEAPON,
        SET_ONE,
        SET_TWO,
        NOTES,
        FARMING_WEAPON_MATERIALS,
        FARMING_TALENT_MATERIALS,
        FARMING_SET_ONE,
        FARMING_SET_TWO

    }
    public enum WEAPON_FILTER_OPTIONS{
        NO_FILTER("[ Filter ]"),
        CLAYMORE("Claymore"),
        BOW("Bow"),
        POLEARM("Polearm"),
        SWORD("Sword"),
        CATALYST("Catalyst");

        public static final Map<WEAPON_FILTER_OPTIONS, String> ALL_OPTIONS_BY_ENUM = new TreeMap<>();
        public static final Map<String,WEAPON_FILTER_OPTIONS> ALL_OPTIONS_BY_STRING = new TreeMap<>();

        static {
            for (WEAPON_FILTER_OPTIONS e: values()) {
                ALL_OPTIONS_BY_ENUM.put(e, e.stringToken);
                ALL_OPTIONS_BY_STRING.put(e.stringToken,e);
            }
        }

        public final String stringToken;
        WEAPON_FILTER_OPTIONS(String stringToken) {
            this.stringToken = stringToken;
        }
    }

    public static List<String> getFlattenedData(flattenedDataCategory dc){
        return parsedFlattenedData.get(dc.stringToken);
    }
    public static Map<String,List<String>> getMapping(knownMappings mapping){
        return parsedMappings.get(mapping.stringToken);
    }
    public static String getArtifactSetDescription(String artifactSetName){
        assert artifactSetDescriptions.containsKey(artifactSetName);
        return artifactSetDescriptions.get(artifactSetName);
    }
    public static String getWeaponMaterialForWeapon(String weaponName){
        Map<String, List<String>> mapping = getMapping(knownMappings.WEPMAT_WEPNAME);
        for (String weaponMat: mapping.keySet()){
            if (mapping.get(weaponMat).contains(weaponName)){
                return weaponMat;
            }
        }
        return "";
    }
    /**
     * Looks up a set description from the name.
     *
     * @param setName the name of the set
     * @return the description of it as String.
     */
    public static String lookUpSetDescription(String setName) {
        return getArtifactSetDescription(setName);
    }
    private static void parseDataJsonFiles() throws FileNotFoundException {
        Gson gson = new Gson();
        for (String path : PATHS_TO_DATA_FILES.keySet()){
            JsonReader reader = new JsonReader(new FileReader(path));
            String categoryName = (String) path.subSequence(path.indexOf('/') + 1,path.lastIndexOf("."));
            if (categoryName.equalsIgnoreCase(knownMappings.ARTISET_ARTISETDESC.stringToken)){
                artifactSetDescriptions = gson.fromJson(reader,artifactSetDescriptions.getClass());
                continue;
            }
            TreeMap<String,List<String>> parsedMapping = new TreeMap<>();
            parsedMapping = gson.fromJson(reader, parsedMapping.getClass());
            parsedMappings.put(categoryName,parsedMapping);
            if (PATHS_TO_DATA_FILES.get(path)){
                List<List<String>> val_arrays = new ArrayList<>(parsedMapping.values());
                List<String> valuesFlattened = new ArrayList<>();
                val_arrays.forEach(valuesFlattened::addAll);
                Collections.sort(valuesFlattened);
                parsedFlattenedData.put((String) path.subSequence(path.indexOf('_') + 1,path.lastIndexOf(".")),valuesFlattened);
            }

        }

    }
    /**
     * Generates a path to the specified resource.
     *
     * @param resourceName the name of the resource
     * @param resourceType the type of the resource
     * @return character icon path
     */
    public static String generateResourceIconPath(String resourceName, RESOURCE_TYPE resourceType) {
        assert resourceName != null;
        return switch (resourceType){
            case WEAPON -> {
                if (resourceName.equalsIgnoreCase(UNKNOWN_WEAPON_MESSAGE))
                {
                    yield UNKNOWN_WEAPON_PATH;
                }
                WeaponInfo wi = lookUpWeaponRarityAndType(resourceName);
                yield switch (wi.getRarity()) {
                    case FOUR_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_4star/" + resourceName + ".png";
                    case FIVE_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_5star/" + resourceName + ".png";
                };
            }
            case ARTIFACT -> "./Files/Images/Artifacts/" + resourceName + ".png";
            case WEAPON_MATERIAL -> "./Files/Images/Weapon Materials/" + resourceName + ".png";
            case CHARACTER -> "./Files/Images/Characters/" + resourceName + ".png";
            case TALENT_BOOK -> "./Files/Images/Talent Materials/" + resourceName + ".png";
            case WEEKLY_BOSS_MATERIAL -> "./Files/Images/Weekly Bosses/" + resourceName + ".png";
        };

    }
    /**
     * Looks up what weapon category is assigned to the character, i.e. what type of weapons the character can wield.
     *
     * @param charName the name of the character
     * @return the string that represents the category (one of five possibilities)
     */

    public static String lookUpWeaponCategoryForCharacter(String charName) {
        Map<String, List<String>> weaponMapping = getMapping(knownMappings.WEPTYPE_CHAR);
        for (String key : weaponMapping.keySet()) {
            List<String> weapons = weaponMapping.get(key);
            if (weapons.contains(charName)) {
                return key;
            }

        }
        return null;
    }
    /**
     * Looks up weapon material for the specified weapon.
     *
     * @param weaponName the weapon name
     * @return the weapon material.
     */

    public static String lookUpWeaponMaterial(String weaponName) {
        assert getFlattenedData(flattenedDataCategory.WEAPON_NAME).contains(weaponName);
        Map<String, List<String>> mapping = getMapping(knownMappings.WEPMAT_WEPNAME);
        for (String key : mapping.keySet()) {
            List<String> weapons = mapping.get(key);
            if (weapons.contains(weaponName)) {
                return key;
            }
        }
        return null;
    }
    /**
     * Grabs all weapons based on their rarity and type.
     *
     * @param WEAPONRarity rarity of the weapon
     * @param weaponType type of the weapon
     * @return list of the weapons with the specified rarity.
     */
    public static List<String> lookUpWeapons(WEAPON_RARITY WEAPONRarity, String weaponType) {

        final String FOUR_STAR_WEAPON_KEY = "Four-Star " + weaponType;
        final String FIVE_STAR_WEAPON_KEY = "Five-Star " + weaponType;
        Map<String, List<String>> weaponMapping = getMapping(knownMappings.WEPRARITYANDTYPE_WEPNAME);

        return switch (WEAPONRarity) {
            case FOUR_STAR -> weaponMapping.get(FOUR_STAR_WEAPON_KEY);
            case FIVE_STAR -> weaponMapping.get(FIVE_STAR_WEAPON_KEY);
        };
    }
    /**
     * Looks up the rarity and type of specified weapon.
     *
     * @param weaponName the weapon name
     * @return WeaponInfo object with rarity and type of the weapon.
     */
    public static WeaponInfo lookUpWeaponRarityAndType(String weaponName) {

        Map<String, List<String>> weaponMapping = getMapping(knownMappings.WEPRARITYANDTYPE_WEPNAME);
        for (String key : weaponMapping.keySet()) {
            List<String> weapons = weaponMapping.get(key);
            if (weapons.contains(weaponName)) {
                return new WeaponInfo(key);
            }
        }
        return new WeaponInfo("");
    }
    public static String getTalentBookForCharacter(String characterName){
        Map<String, List<String>> mapping = getMapping(knownMappings.TALENTBOOK_CHAR);
        for (String key:mapping.keySet()){
            if (mapping.get(key).contains(characterName)){
                return key;
            }
        }
        return "";
    }
    public static void main(String[] args) throws Exception {

        parseDataJsonFiles();
        new ToolGUI();
        //new Program();

        }
    }
