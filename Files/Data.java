package Files;

import java.text.MessageFormat;
import java.util.*;

public class Data {
    public static String[] characters;

    public final static String[] pyro_characters
            = {"Amber", "Xiangling", "Bennett", "Xinyan", "Yanfei", "Thoma", "Chevreuse", "Gaming", "Diluc", "Klee",
            "Hu Tao", "Yoimiya", "Dehya", "Lyney"};

    public final static String[] electro_characters
            = {"Fischl", "Beidou", "Lisa", "Razor", "Kujou Sara", "Kuki Shinobu", "Dori", "Keqing",
            "Raiden Shogun", "Yae Miko", "Cyno"};

    public final static String[] dendro_characters
            = {"Collei", "Yaoyao", "Kaveh", "Kirara", "Tighnari", "Nahida", "Alhaitham", "Baizhu"};

    public final static String[] hydro_characters
            = {"Xingqiu", "Barbara", "Candace", "Tartaglia", "Mona", "Sangonomiya Kokomi", "Kamisato Ayato", "Yelan",
            "Nilou", "Neuvilette", "Furina"};

    public final static String[] cryo_characters
            = {"Diona", "Chongyun", "Kaeya", "Rosaria", "Layla", "Mika", "Freminet", "Charlotte", "Qiqi", "Ganyu",
            "Eula", "Kamisato Ayaka", "Aloy", "Shenhe", "Wriothesley"};

    public final static String[] anemo_characters
            = {"Sucrose", "Sayu", "Heizou", "Faruzan", "Lynette", "Jean", "Venti", "Xiao", "Kaedehara Kazuha",
            "Wanderer", "Xianyun"};

    public final static String[] geo_characters
            = {"Ningguang", "Noelle", "Gorou", "Yunjin", "Zhongli", "Albedo", "Arataki Itto", "Navia", "Chiori"};

    public final static String[] misc_characters
            = {"Traveler"};

    public final static String[] artifact_sets
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

    public final static String[] artifact_domains
            = {"Bosses", "Clear Pool and Mountain Cavern", "Valley of Remembrance", "Domain of Guyun",
            "Midsummer Courtyard", "Hidden Palace of Zhou Formula", "Peak of Vindagnyr", "Ridge Watch",
            "Momiji-Dyed Court", "Slumbering Court", "The Lost Valley", "Spire of Solitary Enlightenment",
            "City of Gold", "Molten Iron Fortress", "Denouement of Sin", "Waterfall Wen"};

    public final static String[] bows_5star
            = {"Amos' Bow", "Aqua Simulacra", "Elegy for the End", "Hunter's Path", "Polar Star", "Skyward Harp",
            "The First Great Magic", "Thundering Pulse"};

    public final static String[] bows_4star
            = {"Alley Hunter", "Blackcliff Warbow", "Compound Bow", "End of the Line", "Fading Twilight",
            "Favonius Warbow", "Hamayumi", "Ibis Piercer", "King's Squire", "Mitternachts Waltz", "Mooun's Moon",
            "Predator", "Prototype Crescent", "Range Gauge", "Royal Bow", "Rust", "Sacrificial Bow",
            "Scion of the Blazing Sun", "Song of Stillness", "The Stringless", "The Viridescent Hunt", "Windblume Ode"};

    public final static String[] catalysts_5star
            = {"A Thousand Floating Dreams", "Cashflow Supervision", "Crane's Echoing Call", "Everlasting Moonglow",
            "Jadefall's Splendor", "Kagura's Verity", "Lost Prayer to the Sacred Winds", "Memory of Dust",
            "Skyward Atlas", "Tome of the Eternal Flow", "Tulaytullah's Remembrance"};

    public final static String[] catalysts_4star
            = {"Ballad of the Boundless Blue", "Blackcliff Agate", "Dodoco Tales", "Eye of Perception",
            "Favonius Codex", "Flowing Purity", "Frostbearer", "Fruit of Fulfillment", "Hakushin Ring", "Mappa Mare",
            "Oathsworn Eye", "Prototype Amber", "Royal Grimoire", "Sacrificial Fragments", "Sacrificial Jade",
            "Solar Pearl", "The Widsith", "Wandering Evenstar", "Wine and Song"};

    public final static String[] claymores_5star
            = {"Beacon of the Reed Sea", "Redhorn Stonethresher", "Skyward Pride", "Song of Broken Pines",
            "The Unforged", "Verdict", "Wolf's Gravestone"};

    public final static String[] claymores_4star
            = {"Akuoumaru", "Blackcliff Slasher", "Favonius Greatsword", "Forest Regalia", "Katsuragikiri Nagamasa",
            "Lithic Blade", "Luxurious Sea-Lord", "Mailed Flower", "Makhaira Aquamarine", "Portable Power Saw",
            "Prototype Archaic", "Rainslasher", "Royal Greatsword", "Sacrificial Greatsword", "Serpent Spine",
            "Snow-Tombed Starsilver", "Talking Stick", "The Bell", "Tidal Shadow", "Ultimate Overlord's Mega Magic Sword",
            "Whiteblind"};

    public final static String[] polearms_5star
            = {"Calamity Queller", "Engulfing Lightning", "Primodial Jade Winged-Spear", "Skyward Spine",
            "Staff of Homa", "Staff of the Scarlet Sands", "Vortex Vanquisher"};

    public final static String[] polearms_4star
            = {"Ballad of the Fjords", "Blackcliff Pole", "Crescent Pike", "Deathmatch", "Dragon's Bane",
            "Dragonspine Spear", "Favonius Lance", "Kitain Cross Spear", "Lithic Spear", "Missive Windspear",
            "Moonpiercer", "Prospector's Drill", "Prototype Starglitter", "Rightful Reward", "Royal Spear", "The Catch",
            "Wavebreaker's Fin"};

    public final static String[] swords_5star
            = {"Aquila Favonia", "Freedom-Sworn", "Haran Geppaku Futsu", "Key of Khaj-Nisut", "Light of Foliar Incision",
            "Mistsplitter Reforged", "Primordial Jade Cutter", "Skyward Blade", "Splendor of Tranquil Waters",
            "Summit Shaper"};

    public final static String[] swords_4star
            = {"Amenoma Kageuchi", "Blackcliff Longsword", "Cinnabar Spindle", "Favonius Sword", "Festering Desire",
            "Finale of the Deep", "Fleuve Cendre Ferryman", "Iron Sting", "Kagotsurube Isshin", "Lion's Roar",
            "Prototype Rancour", "Royal Longsword", "Sacrificial Sword", "Sapwood Blade", "Sword of Descension",
            "Sword of Narzissenkreuz", "The Alley Flash", "The Black Sword", "The Dockhand's Assistant",
            "The Flute", "Toukabou Shigure", "Wolf-Fang", "Xiphos' Moonlight"};

    public static List<String> character_names;
    private final static List<String> set_names = Arrays.asList(artifact_sets);

    public static void print_farmed_sets() {
        boolean check = false;

        if (mapping_characters == null) {
            System.out.println("Mapping characters has not been initialized");
            return;
        }

        for (String character : characters) {
            ArrayList<String> character_specific = mapping_characters.get(character);
            if (!character_specific.isEmpty()) {
                System.out.println(character);
                check = true;

                for (String s : character_specific) {
                    System.out.println(s);
                }
                System.out.println();
            }
        }

        if (!check) {
            System.out.println("No sets are being farmed! >:(");
        }
    }

    private static HashMap<String, ArrayList<String>> mapping_domains;
    private static HashMap<String, ArrayList<String>> mapping_characters;

    public static void main(String[] args) {
        Set<String> characters_set = new HashSet<>(Arrays.asList(pyro_characters));
        characters_set.addAll(Arrays.asList(electro_characters));
        characters_set.addAll(Arrays.asList(dendro_characters));
        characters_set.addAll(Arrays.asList(hydro_characters));
        characters_set.addAll(Arrays.asList(cryo_characters));
        characters_set.addAll(Arrays.asList(anemo_characters));
        characters_set.addAll(Arrays.asList(geo_characters));
        characters_set.addAll(Arrays.asList(misc_characters));
        System.out.println(characters_set.size());
        character_names = new ArrayList<>(characters_set);
        characters = characters_set.toArray(characters);
        Program _program = new Program();
        mapping_domains = new HashMap<>();
        mapping_characters = new HashMap<>();
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
    }
}