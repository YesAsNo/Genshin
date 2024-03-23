package Files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ToolData {
    private static Map<String,String> artifactSetDescriptions = new TreeMap<>();
    public static Map<String, Set<String>> farmedWeapons = new TreeMap<>();
    public static Map<String, Set<String>> farmedArtifacts = new TreeMap<>();
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
        TALENT_CHAR("TalentBook_Character"),
        TALENTDOMAIN_TALENT("TalentDomain_TalentBook"),
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
    public enum WEAPON_FILTER_OPTIONS{
        NO_FILTER("[ Filter ]"),
        CLAYMORE("Claymore"),
        BOW("Bow"),
        POLEARM("Polearm"),
        SWORD("Sword"),
        CATALYST("Catalyst");

        public static final Map<WEAPON_FILTER_OPTIONS, String> ALL_OPTIONS = new TreeMap<>();

        static {
            for (WEAPON_FILTER_OPTIONS e: values()) {
                ALL_OPTIONS.put(e, e.filterOption);
            }
        }

        public final String filterOption;
        private WEAPON_FILTER_OPTIONS(String filterOption) {
            this.filterOption = filterOption;
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
    public static void main(String[] args) throws Exception {

        parseDataJsonFiles();
        new ToolGUI();
        //new Program();

        }
    }
