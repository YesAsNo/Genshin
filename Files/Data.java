package Files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

import java.util.*;

public class Data {
    public static Map<String,List<String>> charactersElementsMap = new TreeMap<>();
    public static List<String> characters_flattened = new ArrayList<>();
    private static final String PATH_TO_CHARACTER_JSON = "./characters.json";
    private static final String PATH_TO_WEAPONS_JSON = "./weapons.json";
    private static final String PATH_TO_DOMAIN_MAPPINGS = "./mapping_domains.json";
    public static HashMap<String, List<String>> mapping_domains = new HashMap<>();
    public static List<String> artifacts_flattened = new ArrayList<>();
    private static void parseCharacters(Gson gson) throws Exception{

        JsonReader reader = new JsonReader(new FileReader(PATH_TO_CHARACTER_JSON));
        charactersElementsMap = gson.fromJson(reader, charactersElementsMap.getClass());
        List<List<String>> val_arrays = new ArrayList<>(charactersElementsMap.values());
        val_arrays.forEach(characters_flattened::addAll);
        Collections.sort(characters_flattened);
    }
    private static void parseDomainMapping(Gson gson) throws Exception{

        JsonReader reader = new JsonReader(new FileReader(PATH_TO_DOMAIN_MAPPINGS));
        mapping_domains = gson.fromJson(reader, mapping_domains.getClass());
        List<List<String>> val_arrays = new ArrayList<>(mapping_domains.values());
        val_arrays.forEach(artifacts_flattened::addAll);
        Collections.sort(artifacts_flattened);
    }
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        Program _program = new Program();
        parseCharacters(gson);
        parseDomainMapping(gson);
        }
    }
