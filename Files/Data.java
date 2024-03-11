package Files;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Data {
    public final static String[] characters
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

    public final static String[] all_weapons
            ={"Royal Greatsword", "Royal Grimoire", "Royal Longsword", "Royal Spear", "Rust", "Sacrificial Bow",
            "Sacrificial Fragments", "Sacrificial Greatsword", "Sacrificial Jade", "Sacrificial Sword",
            "Sapwood Blade", "Scion of the Blazing Sun", "Serpent Spine", "Skyward Atlas", "Skyward Blade",
            "Skyward Harp", "Skyward Pride", "Skyward Spine", "Snow-Tombed Starsilver", "Solar Pearl",
            "Song of Broken Pines", "Song of Stillness", "Splendor of Tranquil Waters", "Staff of Homa",
            "Staff of the Scarlet Sands", "Summit Shaper", "Sword of Descension", "Sword of Narzissenkreuz",
            "Talking Stick", "The Alley Flash", "The Bell", "The Black Sword", "The Catch", "The Dockhand's Assistant",
            "The First Great Magic", "The Flute", "The Stringless", "The Unforged", "The Viridescent Hunt",
            "The Widsith", "Thundering Pulse", "Tidal Shadow", "Tome of the Eternal Flow", "Toukabou Shigure",
            "Tulaytullah's Remembrance", "Ultimate Overlord's Mega Magic Sword", "Verdict", "Vortex Vanquisher",
            "Wandering Evenstar", "Wavebreaker's Fin", "Whiteblind", "Windblume Ode", "Wine and Song", "Wolf-Fang",
            "Wolf's Gravestone", "Xiphos' Moonlight", "A Thousand Floating Dreams", "Akuoumaru", "Alley Hunter",
            "Amenoma Kageuchi", "Amos' Bow", "Aqua Simulacra", "Aquila Favonia", "Ballad of the Boundless Blue",
            "Ballad of the Fjords", "Beacon of the Reed Sea", "Blackcliff Agate", "Blackcliff Longsword",
            "Blackcliff Pole", "Blackcliff Slasher", "Blackcliff Warbow", "Calamity Queller", "Cashflow Supervision",
            "Cinnabar Spindle", "Compound Bow", "Crane's Echoing Call", "Crescent Pike", "Deathmatch",
            "Dodoco Tales", "Dragon's Bane", "Dragonspine Spear", "Elegy for the End", "End of the Line",
            "Engulfing Lighting", "Everlasting Moonglow", "Eye of Perception", "Fading Twilight", "Favonius Codex",
            "Favonius Greatsword", "Favonius Lance", "Favonius Sword", "Favonius Warbow", "Festering Desire",
            "Finale of the Deep", "Fleuve Cendre Ferryman", "Flowing Purity", "Forest Regalia", "Freedom-Sworn",
            "Frostbearer", "Fruit of Fulfillment", "Hakushin Ring", "Hamayumi", "Haran Geppaku Futsu",
            "Hunter's Path", "Ibis Piercer", "Iron Sting", "Jadefall's Splendor", "Kagotsurube Isshin",
            "Kagura's Verity", "Katsuragikiri Nagamasa", "Key of Khaj-Nisut", "King's Squire", "Kitain Cross Spear",
            "Light of Foliar Incision", "Lion's Roar", "Lithic Blade", "Lithic Spear",
            "Lost Prayer to the Sacred Winds", "Luxurious Sea-Lord", "Mailed Flower", "Makhaira Aquamarine",
            "Mappa Mare", "Memory of Dust", "Missive Windspear", "Mistsplitter Reforged", "Mitternachts Waltz",
            "Moonpiercer", "Mouun's Moon", "Oathsworn Eye", "Polar Star", "Portable Power Saw", "Predator",
            "Primodial Jade Winged-Spear", "Primordial Jade Cutter", "Prospector's Drill", "Prototype Amber",
            "Prototype Archaic", "Prototype Crescent", "Prototype Rancour", "Prototype Starglitter", "Rainslasher",
            "Range Gauge", "Redhorn Stonethresher", "Rightful Reward", "Royal Bow"};

    public final static List<String> character_names = Arrays.asList(characters);
    private final static List<String> set_names = Arrays.asList(artifact_sets);

    private static final int character_count = character_names.size();
    private static final int set_count = set_names.size();

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

    public static void print_characters() {
        for (int i = 0; i < character_names.size(); i++) {
            System.out.print(MessageFormat.format("{0}.{1} ", i + 1, character_names.get(i)));
            if (i % 7 == 6) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void print_sets() {
        for (int i = 0; i < set_names.size(); i++) {
            System.out.print(MessageFormat.format("{0}.{1} ", i + 1, set_names.get(i)));
            if (i % 7 == 6) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private static HashMap<String, ArrayList<String>> mapping_domains;
    private static HashMap<String, ArrayList<String>> mapping_characters;

    public static void main(String[] args) {
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

        System.out.println("------- Characters! -------");
        print_characters();
        System.out.println();
        System.out.println("------- Sets! -------");
        print_sets();

    }
}