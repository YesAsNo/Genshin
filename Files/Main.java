import java.text.MessageFormat;
import java.util.*;

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

    private final static List<String> character_names = Arrays.asList(characters);
    private final static List<String> set_names = Arrays.asList(artifact_sets);

    private static int character_count = character_names.size();
    private static int set_count = set_names.size();

    public static void print_farmed_sets(){
        boolean check = false;

        if (mapping_characters==null) {
            System.out.println("Mapping characters has not been initialized");
            return;
        }

        for (int i = 0; i < characters.length; i++){
            ArrayList<String> character_specific = mapping_characters.get(characters[i]);
            if (!character_specific.isEmpty()) {
                System.out.println(characters[i]);
                check = true;

                for (int i2 = 0; i2 < character_specific.size(); i2++) {
                    System.out.println(character_specific.get(i2));
                }
                System.out.println();
            }
        }

        if (!check) {
            System.out.println("No sets are being farmed! >:(");
        }
    }

    public static void print_characters() {
        for (int i = 0; i < character_names.size(); i++) {
            System.out.print(MessageFormat.format("{0}.{1} ", i + 1, character_names.get(i)));
            if (i%7==6) {
                System.out.println();
            }
        }
        System.out.println();
    }
    public static void print_sets() {
        for (int i = 0; i < set_names.size(); i++) {
            System.out.print(MessageFormat.format("{0}.{1} ", i + 1, set_names.get(i)));
            if (i%7==6) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private static HashMap<String, ArrayList<String>> mapping_domains;
    private static HashMap<String, ArrayList<String>> mapping_characters;

    public static void main(String[] args) {
        mapping_domains = new HashMap<>();
        mapping_characters = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        Collections.sort(character_names);
        Collections.sort(set_names);

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

        //mapping_characters.get("Kamisato Ayaka").add("Blizzard Strayer");
        //mapping_characters.get("Kamisato Ayaka").add("Pale Flame");
        //mapping_characters.get("Razor").add("Pale Flame");
        System.out.println("------- Characters! -------");
        print_characters();
        System.out.println();
        System.out.println("------- Sets! -------");
        print_sets();

        int replyCharacterID;
        int replySetID;
        while (true) {
            System.out.println("Type character ID");
            replyCharacterID = sc.nextInt();
            if (replyCharacterID > character_count || replyCharacterID < 1) {
                break;
            }

            System.out.println("Type set ID");
            replySetID = sc.nextInt();

            while(replySetID <= set_count && replySetID >= 1) {
                mapping_characters.get(character_names.get(replyCharacterID-1)).add(set_names.get(replySetID-1));
                replySetID = sc.nextInt();
            }


        }
        print_farmed_sets();
    }

