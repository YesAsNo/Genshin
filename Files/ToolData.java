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

public class ToolData {
    private static Map<String,String> artifactSetDescriptions = new TreeMap<>();
    private static final Map<String,Font> fonts = new TreeMap<>();
    public static final String SAVE_LOCATION = "./UserData/";
    private static final String UNKNOWN_WEAPON_PATH = "./Files/Images/Weapons/unknown_weapon.png";
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
    public enum knownMappings{
        ARTIDOMAIN_ARTISET("ArtifactDomain_ArtifactSet"),
        ARTISET_ARTISETDESC("ArtifactSet_ArtifactSetDescription"),
        DAY_AVAILABLEMATS("Day_AvailableMaterials"),
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
        WEAPON_NAME("WeaponName"),
        ARTIFACT_SET("ArtifactSet"),
        WEAPON_MATERIAL("WeaponMaterial"),
        CHARACTER("Character"),
        TALENT_BOOK("TalentBook"),
        WEEKLY_BOSS_MATERIAL("WeeklyBossMaterial");
        public final String stringToken;
        RESOURCE_TYPE(String data) {
            this.stringToken = data;
        }
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

    public enum AVAILABLE_FONTS{
        HEADER_FONT(fonts.get("SourceCodePro-Bold")),
        TEXT_FONT(fonts.get("SourceCodePro-Light")),
        CREATOR_FONT(fonts.get("SourceCodePro-Semibold")),
        REGULAR_FONT(fonts.get("SourceCodePro-Regular")),
        BLACK_FONT(fonts.get("SourceCodePro-Black"));

        final public Font fontName;

        AVAILABLE_FONTS(Font font) {
            this.fontName = font;
        }
    }

    public static void changeFont(JComponent jcomponent, AVAILABLE_FONTS desiredFont, float size){
        jcomponent.setFont(desiredFont.fontName.deriveFont(size));
    }


    public static List<String> getFlattenedData(RESOURCE_TYPE dc){
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
     *
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

     /* Grabs all weapons based on their rarity and type.
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
    public static ImageIcon getResourceIcon(String resourceName, RESOURCE_TYPE rt){
        assert getFlattenedData(rt).contains(resourceName);
        return parsedResourceIcons.get(rt.stringToken).get(resourceName);
    }
    public static ImageIcon getResizedResourceIcon(String resourceName, RESOURCE_TYPE rt, int size){
        assert getFlattenedData(rt).contains(resourceName);
        ImageIcon originalIcon = getResourceIcon(resourceName,rt);
        return new ImageIcon(originalIcon.getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
    }
    public static void main(String[] args) throws Exception {

        parseDataJsonFiles();
        parseFonts();
        parseResourceIcons();
        new ToolGUI();
        //new Program();

        }
    }