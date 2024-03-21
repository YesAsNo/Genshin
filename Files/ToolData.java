package Files;

import static Files.ToolGUI.WEAPON_SAVE_FILE_NAME;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ToolData {
    public static Map<String,List<String>> charactersElementsMap = new TreeMap<>();
    public static List<String> charactersFlattened = new ArrayList<>();
    public static Map<String, List<String>> mappingDomains = new TreeMap<>();
    public static List<String> artifactsFlattened = new ArrayList<>();
    public static Map<String, List<String>> weaponsRaritiesMap = new TreeMap<>();
    public static List<String> weaponsFlattened = new ArrayList<>();
    public static Map<String, List<String>> characterWeaponsMap = new TreeMap<>();
    public static Map<String,String> artifactSetDescriptionsMap = new TreeMap<>();
    public static Map<String,List<String>> weaponMaterialMap = new TreeMap<>();
    public static Map<String, Set<String>> farmedWeapons = new TreeMap<>();
    public static Map<String, Set<String>> farmedArtifacts = new TreeMap<>();

    private static final String PATH_TO_CHARACTER_JSON = "./characters.json";
    private static final String PATH_TO_WEAPONS_JSON = "./weapons.json";
    private static final String PATH_TO_DOMAIN_MAPPINGS = "./mapping_domains.json";
    private static final String PATH_TO_CHARACTER_MAPPINGS = "./mapping_characters.json";
    private static final String PATH_TO_ARTIFACT_SET_MAPPINGS = "./set_descriptions.json";
    public static final String SAVE_LOCATION = "./UserData/";
    public static final String  PATH_TO_WEAPON_MATERIAL_MAPPINGS = "./mapping_wp_materials.json";

    public enum WEAPON_RARITY {
        FIVE_STAR,
        FOUR_STAR
    }
    public enum RESOURCE_TYPE {
        WEAPON,
        ARTIFACT,
        WEAPON_MATERIAL,
        CHARACTER
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

    /**
     * Parses Character-Element mapping from the supplied json file.
     * Additionally, retrieves the characters from the mapping, puts them into a 1d array and sorts by alphabet.
     * @param gson gson object
     * @param reader Reader object with the path to the json file.
     */
    private static void parseCharacters(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        charactersElementsMap = gson.fromJson(reader, charactersElementsMap.getClass());
        List<List<String>> val_arrays = new ArrayList<>(charactersElementsMap.values());
        val_arrays.forEach(charactersFlattened::addAll);
        Collections.sort(charactersFlattened);
    }
    /**
     * Parses Domain-Artifact set mapping from the supplied json file.
     * Additionally, retrieves the artifacts from the mapping, puts them into a 1d array and sorts by alphabet.
     * @param gson gson object
     * @param reader Reader object with the path to the json file.
     */
    private static void parseDomainMapping(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        mappingDomains = gson.fromJson(reader, mappingDomains.getClass());
        List<List<String>> val_arrays = new ArrayList<>(mappingDomains.values());
        val_arrays.forEach(artifactsFlattened::addAll);
        Collections.sort(artifactsFlattened);
    }
    /**
     * Parses Weapon-Rarity/Type mapping from the supplied json file.
     * @param gson gson object
     * @param reader Reader object with the path to the json file.
     */
    private static void parseWeapons(Gson gson, JsonReader reader){
        assert gson != null;
        assert reader != null;
        weaponsRaritiesMap = gson.fromJson(reader,weaponsRaritiesMap.getClass());
        List<List<String>> val_arrays = new ArrayList<>(weaponsRaritiesMap.values());
        val_arrays.forEach(weaponsFlattened::addAll);
        Collections.sort(weaponsFlattened);
    }
    /**
     * Parses Character-Weapons mapping from the supplied json file.
     * @param gson gson object
     * @param reader Reader object with the path to the json file.
     */
    private static void parseCharacterMapping(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        characterWeaponsMap = gson.fromJson(reader,characterWeaponsMap.getClass());

    }
    /**
     * Parses Artifact Set - Set Description mapping from the supplied json file.
     * @param gson gson object
     * @param reader Reader object with the path to the json file.
     */
    private static void parseArtifactSetDescriptionMapping(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        artifactSetDescriptionsMap = gson.fromJson(reader,artifactSetDescriptionsMap.getClass());
    }
    private static void parseWeaponMaterialMapping(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        weaponMaterialMap = gson.fromJson(reader,weaponMaterialMap.getClass());
    }
    private static void parseFarmedWeapons(Gson gson, File f)throws FileNotFoundException {
        if (!f.exists()){
            return;
        }
        assert gson != null;
        Type setType = new TypeToken<Map<String,TreeSet<String>>>(){}.getType();
        JsonReader reader = new JsonReader(new FileReader(f));
        farmedWeapons = gson.fromJson(reader,setType);
    }

    public static void main(String[] args) throws Exception {

        Gson gson = new Gson();
        parseCharacters(gson,new JsonReader(new FileReader(PATH_TO_CHARACTER_JSON)));
        parseDomainMapping(gson,new JsonReader(new FileReader(PATH_TO_DOMAIN_MAPPINGS)));
        parseWeapons(gson, new JsonReader(new FileReader(PATH_TO_WEAPONS_JSON)));
        parseCharacterMapping(gson, new JsonReader(new FileReader(PATH_TO_CHARACTER_MAPPINGS)));
        parseArtifactSetDescriptionMapping(gson, new JsonReader(new FileReader(PATH_TO_ARTIFACT_SET_MAPPINGS)));
        parseWeaponMaterialMapping(gson, new JsonReader(new FileReader(PATH_TO_WEAPON_MATERIAL_MAPPINGS)));
        parseFarmedWeapons(gson, new File(SAVE_LOCATION + WEAPON_SAVE_FILE_NAME ));
        new ToolGUI();
        //new Program();

        }
    }
