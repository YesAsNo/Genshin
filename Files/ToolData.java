package Files;

import static Files.ToolGUI.UNKNOWN_ARTIFACT;
import static Files.ToolGUI.UNKNOWN_CHARACTER;
import static Files.ToolGUI.UNKNOWN_WEAPON;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Main class of the application. Parses all static data from the json files, taken from the Genshin Fandom Wiki. */
public class ToolData {
    private static Map<String,String> artifactSetDescriptions = new TreeMap<>();
    private static final Map<String,Font> fonts = new TreeMap<>();
    /** Save location of user data. */
    public static final String SAVE_LOCATION = "./UserData/";
    private static final String UNKNOWN_WEAPON_PATH = "./Files/Images/Weapons/unknown_weapon.png";
    /** Locations of all data json files. */
    public static final Map<String, Boolean> PATHS_TO_DATA_FILES;
    static {
        PATHS_TO_DATA_FILES = new TreeMap<>();
        PATHS_TO_DATA_FILES.put("./Files/JSONs/Element_Character.json",true);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/WeeklyBossMaterial_Character.json",false);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/TalentBook_Character.json",false);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/ArtifactDomain_ArtifactSet.json",true);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/WeaponType_Character.json",false);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/TalentDomain_TalentBook.json",true);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/WeaponDomain_WeaponMaterial.json",true);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/WeeklyDomain_WeeklyBossMaterial.json",true);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/WeaponMaterial_WeaponName.json",true);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/ArtifactSet_ArtifactSetDescription.json",false);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/WeaponRarityAndType_WeaponName.json",false);
        PATHS_TO_DATA_FILES.put("./Files/JSONs/Day_AvailableMaterials.json",false);
    }
    private static final Map<String,Map<String,List<String>>> parsedMappings = new TreeMap<>();
    private static final Map<String,Map<String,ImageIcon>> parsedResourceIcons = new TreeMap<>();
    private static final Map<String,List<String>> parsedFlattenedData = new TreeMap<>();

    /** Enum that represents known mappings. All methods should use it instead of String values. */
    public enum knownMappings{
        /** Artifact Domain -> Artifact Set available there. */
        ARTIDOMAIN_ARTISET("ArtifactDomain_ArtifactSet"),
        /** Artifact Set -> Artifact Set Description. */
        ARTISET_ARTISETDESC("ArtifactSet_ArtifactSetDescription"),
        /** Day of the week -> Available materials on that day. Note it does not contain Sunday as all materials are available then. */
        DAY_AVAILABLEMATS("Day_AvailableMaterials"),
        /** Element -> All characters of that element. */
        ELEM_CHAR("Element_Character"),
        /** Talent Book Name -> All characters that need it for their talents. */
        TALENTBOOK_CHAR("TalentBook_Character"),
        /** Talent Domain Name -> All Talent Books available there. */
        TALENTDOMAIN_TALENTBOOK("TalentDomain_TalentBook"),
        /** Weapon Domain Name -> All Weapon Materials available there. */
        WEPDOMAIN_WEPMAT("WeaponDomain_WeaponMaterial"),
        /** Weapon Material Name -> All names of Weapons that need it for ascension. */
        WEPMAT_WEPNAME("WeaponMaterial_WeaponName"),
        /**
         * Weapon Rarity and Type -> All weapons of that rarity and type.
         * The keys are in the form of (Four/Five-Star *type*)
         */
        WEPRARITYANDTYPE_WEPNAME("WeaponRarityAndType_WeaponName"),
        /** Weapon Type -> All characters who can equip weapons of this type. */
        WEPTYPE_CHAR("WeaponType_Character"),
        /** Weekly Boss Material -> All Characters who can use it for their talents. */
        WEEKLYBOSSMAT_CHAR("WeeklyBossMaterial_Character"),

        /** Weekly Boss Domain -> All Weekly Boss Materials available there. */
        WEEKLYDOMAIN_WEEKLYBOSSMAT("WeeklyDomain_WeeklyBossMaterial");

        /** The string token used to look up the mapping. */
        public final String stringToken;
        knownMappings(String data) {
            this.stringToken = data;
        }
    }

    /** Enum that represents weapon rarities (FIVE and FOUR). It doesn't consider THREE_STAR. */
    public enum WEAPON_RARITY {
        /** 5 Star rarity! */
        FIVE_STAR,

        /** 4 Star rarity! */
        FOUR_STAR
    }

    /** Resource type for JSONs and icons. */
    public enum RESOURCE_TYPE {
        /** Weapon resource type (icon/JSON)*/
        WEAPON_NAME("WeaponName"),
        /** Artifact set resource type (icon/JSON)*/
        ARTIFACT_SET("ArtifactSet"),
        /** Weapon material resource type (icon/JSON)*/
        WEAPON_MATERIAL("WeaponMaterial"),
        /** Character resource type (icon/JSON)*/
        CHARACTER("Character"),
        /** Talent book resource type (icon/JSON)*/
        TALENT_BOOK("TalentBook"),
        /** Weekly boss talent resource type (icon/JSON)*/
        WEEKLY_BOSS_MATERIAL("WeeklyBossMaterial");

        /** The string token used to look up the mapping. */
        public final String stringToken;
        RESOURCE_TYPE(String data) {
            this.stringToken = data;
        }
    }

    /** All the fields listed in the character card window. */
    public enum CHARACTER_CARD_DATA_FIELD{
        /** Simply the chosen character's name. */
        NAME,
        /** The chosen character's held weapon. */
        WEAPON,
        /** The chosen character's artifact set 1. */
        SET_ONE,
        /** The chosen character's artifact set 2. */
        SET_TWO,
        /** The chosen character's notes field (desired stats, for example). */
        NOTES,
        /** Whether the character is farming weapon materials for their weapon (check box). */
        FARMING_WEAPON_MATERIALS,
        /** Whether the character is farming talent materials (both weekly boss and book materials) (check box) */
        FARMING_TALENT_MATERIALS,
        /** Whether the character is farming the equipped artifact set 1 (not good enough?) */
        FARMING_SET_ONE,
        /** Whether the character is farming the equipped artifact set 2 (not good enough?) */
        FARMING_SET_TWO

    }

    /** Filter options for weapon tab's search. */
    public enum WEAPON_FILTER_OPTIONS {
        /** Search contains all objects */
        NO_FILTER("[ Filter ]"),
        /** Search contains claymores only */
        CLAYMORE("Claymore"),
        /** Search contains bows only */
        BOW("Bow"),
        /** Search contains polearms only */
        POLEARM("Polearm"),
        /** Search contains swords only */
        SWORD("Sword"),
        /** Search contains catalysts only */
        CATALYST("Catalyst");

        /** Mapping for weapon filter options (enum to String). */
        public static final Map<WEAPON_FILTER_OPTIONS, String> ALL_OPTIONS_BY_ENUM = new TreeMap<>();

        /** Mapping for weapon filter options (String to enum). */
        public static final Map<String,WEAPON_FILTER_OPTIONS> ALL_OPTIONS_BY_STRING = new TreeMap<>();

        static {
            for (WEAPON_FILTER_OPTIONS e: values()) {
                ALL_OPTIONS_BY_ENUM.put(e, e.stringToken);
                ALL_OPTIONS_BY_STRING.put(e.stringToken,e);
            }
        }

        /** The string token used to look up the mapping. */
        public final String stringToken;
        WEAPON_FILTER_OPTIONS(String stringToken) {
            this.stringToken = stringToken;
        }
    }

    /** Fonts used in the entire program */
    public enum AVAILABLE_FONTS{
        /** Bold font used in headers among the other things. */
        HEADER_FONT(fonts.get("SourceCodePro-Bold")),
        /** Font used in paragraphs mostly. */
        TEXT_FONT(fonts.get("SourceCodePro-Light")),
        /** Semibold font used in "Creators" text on the main page among other places. */
        CREATOR_FONT(fonts.get("SourceCodePro-Semibold")),
        /** Simply default version of the font. */
        REGULAR_FONT(fonts.get("SourceCodePro-Regular")),
        /** The boldest option of the font. */
        BLACK_FONT(fonts.get("SourceCodePro-Black"));

        /** String that contains the name of the font */
        final public Font fontName;

        AVAILABLE_FONTS(Font font) {
            this.fontName = font;
        }
    }

    /**
     * Method for changing the font of any JComponent
     * @param jcomponent any component that needs their font changed.
     * @param desiredFont any font from AVAILABLE_FONTS
     * @param size of the desired font, duh!
     */
    public static void changeFont(JComponent jcomponent, AVAILABLE_FONTS desiredFont, float size){
        jcomponent.setFont(desiredFont.fontName.deriveFont(size));
    }

    /**
     * Get the list of desired resources (characters, weapons etc)
     * @param rt (resource type)
     * @return the list of desired resource type
     */
    public static List<String> getFlattenedData(RESOURCE_TYPE rt){
        return parsedFlattenedData.get(rt.stringToken);
    }

    /**
     * Get a known data mapping
     * @param mapping enum of the known mapping
     * @return the mapping, duh!
     */
    public static Map<String,List<String>> getMapping(knownMappings mapping){
        return parsedMappings.get(mapping.stringToken);
    }

    /**
     * Get the set description of desired artifact set.
     * @param artifactSetName the desired artifact set (duh).
     * @return set description of the desired artifact set.
     */
    public static String getArtifactSetDescription(String artifactSetName){
        assert artifactSetDescriptions.containsKey(artifactSetName);
        return artifactSetDescriptions.get(artifactSetName);
    }

    /**
     * Get the materials needed for the desired weapon.
     * @param weaponName name of the desired weapon.
     * @return materials needed for the desired weapon.
     */
    public static String getWeaponMaterialForWeapon(String weaponName){
        Map<String, List<String>> mapping = getMapping(knownMappings.WEPMAT_WEPNAME);
        for (String weaponMat: mapping.keySet()){
            if (mapping.get(weaponMat).contains(weaponName)){
                return weaponMat;
            }
        }
        return "";
    }
    private static void parseDataJsonFiles() throws FileNotFoundException {
        Gson gson = new Gson();
        for (String path : PATHS_TO_DATA_FILES.keySet()){
            JsonReader reader = new JsonReader(new FileReader(path));
            String categoryName = (String) path.subSequence(path.lastIndexOf('/') + 1,path.lastIndexOf("."));
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
     * @param resourceName the name of the resource
     * @param resourceType the type of the resource
     * @return character icon path
     */
    private static String generateResourceIconPath(String resourceName, RESOURCE_TYPE resourceType) {
        assert resourceName != null;
        return switch (resourceType){
            case WEAPON_NAME -> {
                if (resourceName.equalsIgnoreCase(UNKNOWN_WEAPON))
                {
                    yield UNKNOWN_WEAPON_PATH;
                }
                WeaponInfo wi = lookUpWeaponRarityAndType(resourceName);
                yield switch (wi.getRarity()) {
                    case FOUR_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_4star/" + resourceName + ".png";
                    case FIVE_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_5star/" + resourceName + ".png";
                };
            }
            case ARTIFACT_SET -> "./Files/Images/Artifacts/" + resourceName + ".png";
            case WEAPON_MATERIAL -> "./Files/Images/Weapon Materials/" + resourceName + ".png";
            case CHARACTER -> "./Files/Images/Characters/" + resourceName + ".png";
            case TALENT_BOOK -> "./Files/Images/Talent Materials/" + resourceName + ".png";
            case WEEKLY_BOSS_MATERIAL -> "./Files/Images/Weekly Bosses/" + resourceName + ".png";
        };

    }
    /**
     * Looks up what weapon category is assigned to the character, i.e. what type of weapons the character can wield.
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
      * Grabs all weapons based on their rarity and type.
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

    /**
     * Get the needed talent book materials for desired character.
     * @param characterName desired character!
     * @return the talent book materials the character needs.
     */
    public static String getTalentBookForCharacter(String characterName){
        Map<String, List<String>> mapping = getMapping(knownMappings.TALENTBOOK_CHAR);
        for (String key:mapping.keySet()){
            if (mapping.get(key).contains(characterName)){
                return key;
            }
        }
        return "";
    }

    /**
     * Get the needed weekly boss materials for desired character.
      * @param characterName desired character!
     * @return the weekly boss materials the chosen character needs.
     */
    public static String getWeeklyBossMatForCharacter(String characterName){
        Map<String, List<String>> mapping = getMapping(knownMappings.WEEKLYBOSSMAT_CHAR);
        for (String key:mapping.keySet()){
            if (mapping.get(key).contains(characterName)){
                return key;
            }
        }
        return "";
    }
    private static void parseFonts(){
        final String address = "./Files/Fonts/";
        File fd = new File(address);
        File[] fontFiles = fd.listFiles();

        if (fontFiles != null) {
            for (File fontFile : fontFiles) {
                try {
                    String fontName = (String)fontFile.getPath().subSequence(fontFile.getPath().lastIndexOf('\\') + 1,fontFile.getPath().lastIndexOf('.'));
                    fonts.put(fontName, Font.createFont(Font.TRUETYPE_FONT, fontFile));
                } catch (IOException e) {
                    System.out.println("Failed to parse font: " + fontFile.getAbsolutePath());
                } catch (FontFormatException e) {
                    System.out.println(
                            e.getMessage() + fontFile.getAbsolutePath());
                }
            }
        }
    }

    private static void parseResourceIcons(){
        for (RESOURCE_TYPE data : RESOURCE_TYPE.values()){
            parsedResourceIcons.put(data.stringToken,new LinkedHashMap<>());
            if (data == RESOURCE_TYPE.ARTIFACT_SET){
                parsedResourceIcons.get(data.stringToken).put(UNKNOWN_ARTIFACT,new ImageIcon(generateResourceIconPath(UNKNOWN_ARTIFACT,RESOURCE_TYPE.ARTIFACT_SET)));
            }
            else if (data == RESOURCE_TYPE.WEAPON_NAME){
                parsedResourceIcons.get(data.stringToken).put(UNKNOWN_WEAPON,new ImageIcon(generateResourceIconPath(UNKNOWN_WEAPON,RESOURCE_TYPE.WEAPON_NAME)));
            }
            else if (data == RESOURCE_TYPE.CHARACTER){
                parsedResourceIcons.get(data.stringToken).put(UNKNOWN_CHARACTER,new ImageIcon(generateResourceIconPath(UNKNOWN_CHARACTER,RESOURCE_TYPE.CHARACTER)));
            }
            for (String set : getFlattenedData(data)){
                parsedResourceIcons.get(data.stringToken).put(set,new ImageIcon(generateResourceIconPath(set,data)));
            }
        }
    }

    /**
     * Get icon for the desired resource
     * @param resourceName name of the resource (character/weapon/material etc)
     * @param rt (resource type)
     * @return the icon of the resource
     */
    public static ImageIcon getResourceIcon(String resourceName, RESOURCE_TYPE rt){
        assert getFlattenedData(rt).contains(resourceName);
        return parsedResourceIcons.get(rt.stringToken).get(resourceName);
    }

    /**
     * Resize resource icon
     * @param resourceName name of the desired resource
     * @param rt (resource type)
     * @param size desired size, duh!
     * @return the resized new icon
     */
    public static ImageIcon getResizedResourceIcon(String resourceName, RESOURCE_TYPE rt, int size){
        assert getFlattenedData(rt).contains(resourceName);
        ImageIcon originalIcon = getResourceIcon(resourceName,rt);
        return new ImageIcon(originalIcon.getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
    }

    /**
     * Main method
     * @param args Not used
     * @throws Exception Exception!
     */
    public static void main(String[] args) throws Exception {

        parseDataJsonFiles();
        parseFonts();
        parseResourceIcons();
        new ToolGUI();
        //new Program();

        }
    }