import java.util.HashMap;
import java.util.ArrayList;

    private final static String[] characters
            = {"Xianyun", "Gaming", "Chevreuse", "Navia", "Furina", "Charlotte", "Wriothesley", "Neuvillette",
            "Freminet", "Traveler", "Lyney", "Lynette", "Kirara", "Baizhu", "Kaveh", "Mika", "Dehya", "Alhaitham",
            "Yaoyao", "Wanderer", "Faruzan", "Layla", "Nahida", "Nilou", "Cyno", "Candace", "Dori", "Tighnari",
            "Collei", "Heizou", "Kuki Shinobu", "Yelan", "Kamisato Ayato", "Yae Miko", "Shenhe", "Yun Jin",
            "Arataki Itto", "Gorou", "Thoma", "Kokomi", "Raiden Shogun", "Aloy", "Kujou Sara", "Yoimiya", "Sayu",
            "Kamisato Ayaka", "Kaedehara Kazuha", "Eula", "Yanfei", "Rosaria", "Hu Tao", "Xiao", "Ganyu", "Albedo",
            "Zhongli", "Xinyan", "Tartaglia", "Diona", "Klee", "Diluc", "Mona", "Keqing", "Jean", "Venti", "Qiqi",
            "Amber", "Bennett", "Xiangling", "Barbara", "Xingqiu", "Beidou", "Fischl", "Lisa", "Razor", "Sucrose",
            "Chongyun", "Kaeya", "Ningguang", "Noelle"};
    private final static String[] artifact_sets
            = {"Gladiator's Finale", "Wanderer's Troupe",
            "Noblesse Oblige", "Bloodstained Chivalry",
            "Maiden Beloved", "Viridescent Venerer",
            "Archaic Petra", "Retracing Bolide",
            "Thundersoother", "Thundering Fury",
            "Lavawalker", "Crimson Witch of Flames",
            "Blizzard Strayer", "Heart of Depth",
            "Tenacity of the Millelith", "Pale Flame",
            "Shimenawa's Reminiscence", "Emblem of Severed Fate",
            "Husk of Opulent Dreams", "Ocean-Hued Clam",
            "Vermillion Hereafter", "Echoes of an Offering",
            "Deepwood Memories", "Gilded Dreams",
            "Desert Pavilion Chronicle", "Flower of Paradise Lost",
            "Nymph's Dream", "Vourukasha's Glow",
            "Marechaussee Hunter", "Golden Troupe",
            "Song of Days Past", "Nighttime Whispers in the Echoing Woods"};

    private final static String[] artifact_domains
            = {"Bosses", "Clear Pool and Mountain Cavern", "Valley of Remembrance", "Domain of Guyun",
            "Midsummer Courtyard", "Hidden Palace of Zhou Formula", "Peak of Vindagnyr", "Ridge Watch",
            "Momiji-Dyed Court", "Slumbering Court", "The Lost Valley", "Spire of Solitary Enlightenment",
            "City of Gold", "Molten Iron Fortress", "Denouement of Sin", "Waterfall Wen"};



    private static HashMap<String, ArrayList<String>> mapping_domains;
    private static HashMap<String, ArrayList<String>> mapping_characters;
    public static void main(String[] args) {
        mapping_domains = new HashMap<>();
        mapping_characters = new HashMap<>();

        for (int i = 0; i < artifact_domains.length; i++) {
            ArrayList<String> possible_artifacts = new ArrayList<>();
            possible_artifacts.add(artifact_sets[i * 2]);
            possible_artifacts.add(artifact_sets[i * 2 + 1]);
            mapping_domains.put(artifact_domains[i], possible_artifacts);
        }

        for (String character : characters) {
            ArrayList<String> chosen_artifacts = new ArrayList<>();
            mapping_characters.put(character, chosen_artifacts);
        }

    }

