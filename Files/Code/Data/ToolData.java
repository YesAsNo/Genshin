package Files.Code.Data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Main class of the application. Parses all static data from the json files, taken from the Genshin Fandom Wiki. */
public class ToolData {

    public static final String[] placeholderImageKeys = {"artifact", "character", "weapon"};
    private static final Map<String, Font> fonts = new TreeMap<>();
    /** Save location of user data. */
    public static final String SAVE_LOCATION = "./UserData/";
    /** Locations of all data json files. */
    public static final Map<String, String> artifactSetDescriptions = new TreeMap<>();
    public static final List<Character> characters = new ArrayList<>();
    public static final List<Domain> domains = new ArrayList<>();
    public static final List<Weapon> weapons = new ArrayList<>();
    public static final List<Item> artifacts = new ArrayList<>();
    public static final List<Item> weaponMaterials = new ArrayList<>();
    public static final List<Item> talentBooks = new ArrayList<>();
    public static final List<Item> weeklyTalents = new ArrayList<>();
    private static final Map<String, ImageIcon> placeholderIcons = new TreeMap<>();

    /** Enum that represents known mappings. All methods should use it instead of String values. */
    public enum DATA_CATEGORY {
        /** Artifact Set Descriptions */
        SET_DESCRIPTION("setDescriptions", "/Files/JSONs/setDescriptions.json"),
        /** All characters */
        CHARACTER("characters", "/Files/JSONs/characters.json"),
        /** All domains */
        DOMAIN("domains", "/Files/JSONs/domains.json"),
        /** All weapons */
        WEAPON("weapons", "/Files/JSONs/weapons.json");

        /** The string token used to look up the mapping. */
        public final String stringToken;
        private final String datapath;

        DATA_CATEGORY(String dataCategory, String dataPath) {
            this.stringToken = dataCategory;
            this.datapath = dataPath;
        }
    }

    /** Enum that represents weapon rarities (FIVE and FOUR). It doesn't consider THREE_STAR. */
    public enum WEAPON_RARITY {
        /** 5 Star rarity! */
        FIVE_STAR("5-Star"),

        /** 4 Star rarity! */
        FOUR_STAR("4-Star");
        public final String stringToken;
        public static final Map<String, WEAPON_RARITY> byString = new TreeMap<>();

        static {
            for (WEAPON_RARITY rt : WEAPON_RARITY.values()) {
                byString.put(rt.stringToken, rt);
            }
        }

        WEAPON_RARITY(String token) {
            stringToken = token;
        }
    }

    /** Resource type for JSONs and icons. */
    public enum RESOURCE_TYPE {
        /** Weapon resource type (icon/JSON) */
        WEAPON_NAME("WeaponName"),
        /** Artifact set resource type (icon/JSON) */
        ARTIFACT("Artifact"),
        /** Weapon material resource type (icon/JSON) */
        WEAPON_MATERIAL("Weapon Material"),
        /** Character resource type (icon/JSON) */
        CHARACTER("Character"),
        /** Talent book resource type (icon/JSON) */
        TALENT_BOOK("Talent Book"),
        /** Weekly boss talent resource type (icon/JSON) */
        WEEKLY_BOSS_MATERIAL("Weekly Boss Material");

        /** The string token used to look up the mapping. */
        public final String stringToken;
        public static final Map<String, RESOURCE_TYPE> byString = new TreeMap<>();

        static {
            for (RESOURCE_TYPE rt : RESOURCE_TYPE.values()) {
                byString.put(rt.stringToken, rt);
            }
        }

        RESOURCE_TYPE(String data) {
            this.stringToken = data;
        }
    }

    /** All the fields listed in the character card window. */
    public enum CHARACTER_CARD_DATA_FIELD {
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
    public enum WEAPON_TYPE {
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
        public static final Map<WEAPON_TYPE, String> ALL_OPTIONS_BY_ENUM = new TreeMap<>();

        /** Mapping for weapon filter options (String to enum). */
        public static final Map<String, WEAPON_TYPE> ALL_OPTIONS_BY_STRING = new TreeMap<>();

        static {
            for (WEAPON_TYPE e : values()) {
                ALL_OPTIONS_BY_ENUM.put(e, e.stringToken);
                ALL_OPTIONS_BY_STRING.put(e.stringToken, e);
            }
        }

        /** The string token used to look up the mapping. */
        public final String stringToken;

        WEAPON_TYPE(String stringToken) {
            this.stringToken = stringToken;
        }
    }

    /**
     * Used font names.
     */
    public static String[] fontPaths =
            {"SourceCodePro-Bold", "SourceCodePro-Light", "SourceCodePro-Semibold", "SourceCodePro-Regular",
                    "SourceCodePro-Black"};

    /** Fonts used in the entire program */
    public enum AVAILABLE_FONTS {
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
     *
     * @param jcomponent any component that needs their font changed.
     * @param desiredFont any font from AVAILABLE_FONTS
     * @param size of the desired font, duh!
     */
    public static void changeFont(JComponent jcomponent, AVAILABLE_FONTS desiredFont, float size) {
        jcomponent.setFont(desiredFont.fontName.deriveFont(size));
    }

    /**
     * Get the set description of desired artifact set.
     *
     * @param artifactSetName the desired artifact set (duh).
     * @return set description of the desired artifact set.
     */
    public static String getArtifactSetDescription(String artifactSetName) {
        assert artifactSetDescriptions.containsKey(artifactSetName);
        return artifactSetDescriptions.get(artifactSetName);
    }

    private static void parseDataJsonFiles() throws IOException {
        Gson gson = new Gson();
        for (DATA_CATEGORY dataCategory : DATA_CATEGORY.values()) {
            URL url = ToolData.class.getResource(dataCategory.datapath);
            assert url != null;

            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(url.openStream())));
            switch (dataCategory) {
                case DOMAIN:
                    domains.addAll(gson.fromJson(reader, domains.getClass()));
                    System.out.println(dataCategory.stringToken);
                    System.out.println(domains);
                    break;
                case CHARACTER:
                    characters.addAll(gson.fromJson(reader, characters.getClass()));
                    System.out.println(dataCategory.stringToken);
                    System.out.println(characters);
                    break;
                case WEAPON:
                    weapons.addAll(gson.fromJson(reader, weapons.getClass()));
                    System.out.println(dataCategory.stringToken);
                    System.out.println(weapons);
                    break;
                case SET_DESCRIPTION:
                    artifactSetDescriptions.putAll(gson.fromJson(reader, artifactSetDescriptions.getClass()));
                    System.out.println(dataCategory.stringToken);
                    System.out.println(artifactSetDescriptions.keySet());
                    break;
                default:
                    throw new IOException("parsing data " + dataCategory.stringToken + " went wrong.");
            }
        }
    }

    /**
     * Generates a path to the specified resource.
     *
     * @param resource the name of the resource
     * @param resourceType the type of the resource
     * @return character icon path
     */
    private static URL generateResourceIconPath(Object resource, RESOURCE_TYPE resourceType) {
        assert resource != null;
        switch (resourceType) {
            case WEAPON_NAME: {
                assert resource instanceof Weapon;
                Weapon weapon = (Weapon) resource;
                return ToolData.class.getResource(
                        "/Files/Images/Weapons/" + weapon.weaponType + "/" + weapon.rarity + "/" + weapon.name +
                                ".png");
            }
            case ARTIFACT:
            case WEAPON_MATERIAL:
            case TALENT_BOOK:
            case WEEKLY_BOSS_MATERIAL: {
                assert resource instanceof FarmableItem;
                FarmableItem artifact = (FarmableItem) resource;
                return ToolData.class.getResource("/Files/Images/" + artifact.type + "/" + artifact.name + ".png");
            }
            case CHARACTER: {
                assert resource instanceof Character;
                Character character = (Character) resource;
                return ToolData.class.getResource("/Files/Images/Characters/" + character.name + ".png");
            }
        }
        throw new IllegalArgumentException();
    }

    private static void parseFonts() {
        for (String fontName : fontPaths) {
            URL address = ToolData.class.getResource("/Files/Fonts/" + fontName + ".ttf");
            try {
                assert address != null;
                Font f = Font.createFont(Font.TRUETYPE_FONT, address.openStream());
                fonts.put(fontName, f);
            } catch (FontFormatException e) {
                System.out.println("Failed to parse font: " + fontName);
            } catch (IOException e) {
                System.out.println(e.getMessage() + fontName);
            }
        }
    }

    public static Character getCharacter(String name) {
        for (Character character : characters) {
            if (character.name.equalsIgnoreCase(name)) {
                return character;
            }
        }
        throw new IllegalArgumentException(name + "is not a character name");
    }

    public static Weapon getWeapon(String name) {
        for (Weapon weapon : weapons) {
            if (weapon.name.equalsIgnoreCase(name)) {
                return weapon;
            }
        }
        throw new IllegalArgumentException(name + "is not a weapon name");
    }

    public static Item getWeaponMaterial(String name) {
        for (Item weaponMaterial : weaponMaterials) {
            if (weaponMaterial.name.equalsIgnoreCase(name)) {
                return weaponMaterial;
            }
        }
        throw new IllegalArgumentException(name + "is not a weapon material name");
    }

    public static Item getTalentBook(String name) {
        for (Item talentBook : talentBooks) {
            if (talentBook.name.equalsIgnoreCase(name)) {
                return talentBook;
            }
        }
        throw new IllegalArgumentException(name + "is not a talent book name");
    }

    public static Item getArtifact(String name) {
        for (Item artifact : artifacts) {
            if (artifact.name.equalsIgnoreCase(name)) {
                return artifact;
            }
        }
        throw new IllegalArgumentException(name + "is not an artifact name");
    }

    public static Item getWeeklyTalentMaterial(String name) {
        for (Item weeklyTalentMaterial : weeklyTalents) {
            if (weeklyTalentMaterial.name.equalsIgnoreCase(name)) {
                return weeklyTalentMaterial;
            }
        }
        throw new IllegalArgumentException(name + "is not a weekly talent material name");
    }

    public static List<Weapon> lookUpWeapons(WEAPON_RARITY rarity, WEAPON_TYPE type) {
        List<Weapon> filtered = new ArrayList<>();
        for (Weapon weapon : weapons) {
            if (rarity.stringToken.equalsIgnoreCase(weapon.rarity) &&
                    type.stringToken.equalsIgnoreCase(weapon.weaponType)) {
                filtered.add(weapon);
            }
        }
        return filtered;
    }

    /**
     * Resize resource icon
     *
     * @param originalIcon original icon
     * @param size desired size, duh!
     * @return the resized new icon
     */
    public static ImageIcon getResizedResourceIcon(ImageIcon originalIcon, int size) {
        return new ImageIcon(originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    private static void fetchIcons() {
        for (Character character : characters) {
            character.icon = new ImageIcon(generateResourceIconPath(character, RESOURCE_TYPE.CHARACTER));
        }
        for (Weapon weapon : weapons) {
            weapon.icon = new ImageIcon(generateResourceIconPath(weapon, RESOURCE_TYPE.WEAPON_NAME));
        }
        for (Domain domain : domains) {
            for (Item material : domain.materials) {
                RESOURCE_TYPE materialType = RESOURCE_TYPE.byString.get(domain.domainType);
                material.icon = new ImageIcon(generateResourceIconPath(material, materialType));
                switch (materialType) {
                    case ARTIFACT:
                        artifacts.add(material);
                        break;
                    case WEEKLY_BOSS_MATERIAL:
                        weeklyTalents.add(material);
                        break;
                    case TALENT_BOOK:
                        talentBooks.add(material);
                        break;
                    case WEAPON_MATERIAL:
                        weaponMaterials.add(material);
                        break;
                }
            }
        }
        for (String name : placeholderImageKeys) {
            placeholderIcons.put(name, new ImageIcon("/Files/Images/Placeholders/" + name + ".png"));
        }
    }

    public static ImageIcon getPlaceholderIcon(String key) {
        assert placeholderIcons.containsKey(key);
        return placeholderIcons.get(key);
    }

    /**
     * Main method
     *
     * @param args unused
     * @throws Exception thrown exception.
     */
    public static void main(String[] args) throws Exception {

        parseDataJsonFiles();
        fetchIcons();
        parseFonts();
        //new ToolGUI();
        //new Program();

    }
}