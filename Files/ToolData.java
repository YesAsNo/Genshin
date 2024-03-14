package Files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.source.tree.Tree;

import java.io.FileReader;

import java.util.*;

public class ToolData {
    public static Map<String,List<String>> charactersElementsMap = new TreeMap<>();
    public static List<String> charactersFlattened = new ArrayList<>();
    public static Map<String, List<String>> mappingDomains = new TreeMap<>();
    public static List<String> artifactsFlattened = new ArrayList<>();
    public static Map<String, List<String>> weaponsRaritiesMap = new TreeMap<>();
    public static Map<String, List<String>> characterWeaponsMap = new TreeMap<>();
    public static Map<String,String> artifactSetDescriptionsMap = new TreeMap<>();

    private static final String PATH_TO_CHARACTER_JSON = "./characters.json";
    private static final String PATH_TO_WEAPONS_JSON = "./weapons.json";
    private static final String PATH_TO_DOMAIN_MAPPINGS = "./mapping_domains.json";
    private static final String PATH_TO_CHARACTER_MAPPINGS = "./mapping_characters.json";
    private static final String PATH_TO_ARTIFACT_SET_MAPPINGS = "./set_descriptions.json";

    public enum RARITY {
        FIVE_STAR,
        FOUR_STAR
    }

    private static void parseCharacters(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        charactersElementsMap = gson.fromJson(reader, charactersElementsMap.getClass());
        List<List<String>> val_arrays = new ArrayList<>(charactersElementsMap.values());
        val_arrays.forEach(charactersFlattened::addAll);
        Collections.sort(charactersFlattened);
    }
    private static void parseDomainMapping(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        mappingDomains = gson.fromJson(reader, mappingDomains.getClass());
        List<List<String>> val_arrays = new ArrayList<>(mappingDomains.values());
        val_arrays.forEach(artifactsFlattened::addAll);
        Collections.sort(artifactsFlattened);
    }
    private static void parseWeapons(Gson gson, JsonReader reader){
        assert gson != null;
        assert reader != null;
        weaponsRaritiesMap = gson.fromJson(reader,weaponsRaritiesMap.getClass());

    }
    private static void parseCharacterMapping(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        characterWeaponsMap = gson.fromJson(reader,characterWeaponsMap.getClass());

    }
    private static void parseArtifactSetDescriptionMapping(Gson gson,JsonReader reader){
        assert gson != null;
        assert reader != null;
        artifactSetDescriptionsMap = gson.fromJson(reader,artifactSetDescriptionsMap.getClass());
    }

    public static void main(String[] args) throws Exception {

        Gson gson = new Gson();
        parseCharacters(gson,new JsonReader(new FileReader(PATH_TO_CHARACTER_JSON)));
        parseDomainMapping(gson,new JsonReader(new FileReader(PATH_TO_DOMAIN_MAPPINGS)));
        parseWeapons(gson, new JsonReader(new FileReader(PATH_TO_WEAPONS_JSON)));
        parseCharacterMapping(gson, new JsonReader(new FileReader(PATH_TO_CHARACTER_MAPPINGS)));
        parseArtifactSetDescriptionMapping(gson, new JsonReader(new FileReader(PATH_TO_ARTIFACT_SET_MAPPINGS)));
        new ToolGUI();

        }
    }
