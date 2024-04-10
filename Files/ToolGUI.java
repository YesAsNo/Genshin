package Files;

import static Files.ToolData.AVAILABLE_FONTS;
import static Files.ToolData.SAVE_LOCATION;
import static Files.ToolData.changeFont;
import static Files.ToolData.getFlattenedData;
import static Files.ToolData.getResourceIcon;
import static Files.ToolData.getTalentBookForCharacter;
import static Files.ToolData.getWeeklyBossMatForCharacter;
import static Files.WeaponTabGUI.getUnassignedFarmedWeapons;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class generates the main application window.
 */
public class ToolGUI extends JFrame {
    /**
     * If a label text exceeds this length, it will be formatted using HTML tags.
     */
    public static final int MAX_CHARACTERS_PER_LINE = 15;
    /**
     * Character limit for Notes text area.
     */
    public static final int CHARACTER_LIMIT = 50;
    /**
     * This message appears if no characters match the search parameters.
     */
    public static final String NO_CHARACTERS_MATCH_MESSAGE = "No fighters >:(";
    /**
     * Used to generate the label when no characters match the search parameters.
     */
    public static final String UNKNOWN_CHARACTER = "unknown_character";
    /**
     * Used to generate the icon of an unselected weapon, mostly in CharacterCardGUI.
     */
    public static final String UNKNOWN_WEAPON = "unknown_weapon";
    /**
     * Used to generate the icon of an unselected artifact set, mostly in CharacterGUI.
     */
    public static final String UNKNOWN_ARTIFACT = "unknown_artifact";
    /**
     * Weapon selector default option
     */
    public static final String EMPTY_WEAPON_SELECTOR = "[ Empty Weapon Selector ]";
    /**
     * Artifact set selector default option
     */
    public static final String EMPTY_SET_SELECTOR = "[ Empty Set Selector ]";
    /**
     * Weapon selector 4-star weapon delimiter. Cannot be selected
     */
    public static final String FOUR_STAR_WEAPON_DELIMITER = "[ 4-Star Weapons ]";
    /**
     * Weapon selector 5-star weapon delimiter. Cannot be selected
     */
    public static final String FIVE_STAR_WEAPON_DELIMITER = "[ 5-Star Weapons ]";
    /**
     * Weapon save file name.
     */
    public static final String WEAPON_SAVE_FILE_NAME = "saved_weapons.json";
    private JPanel mainPanel;
    private JTabbedPane mainTabbedPane;
    private JPanel mainInformationPanel;
    private JPanel devBasicInfoPanel;
    private JPanel devBasicInfoLeftPanel;
    private JLabel Welcome_Barbara;
    private JTextPane devUpdatesTextPane;
    private JLabel devCreatorsLabel;
    private JLabel devLinakoLabel;
    private JLabel devPrecisi0nLabel;
    private JPanel devBasicInfoRightPanel;
    private JLabel devWelcomeLabel;
    private JPanel devBasicInfoSpacer;
    private JTextPane devInfoTextPane;
    private JPanel welcomeTab;
    /**
     * This mapping contains all farmed weapons from all saved characters. Note that weapons unassigned to characters are not put into this mapping.
     */
    public static final TreeMap<String, Set<String>> farmedWeapons = new TreeMap<>();
    /**
     * This mapping contains all farmed artifact sets from all saved characters.
     */
    public static final TreeMap<String, Set<String>> farmedArtifacts = new TreeMap<>();
    /**
     * This mapping contains all farmed talent book names and weekly boss materials.
     */
    public static final TreeMap<String, Set<String>> farmedTalents = new TreeMap<>();
    private static final List<CharacterCard> generatedCharacterCards = new ArrayList<>();
    private static final CharacterTabGUI _characterTabGUI = new CharacterTabGUI();
    private static final WeaponTabGUI _weaponsTabGUI = new WeaponTabGUI();
    private static final DomainTabGUI __DOMAIN_TAB_GUI = new DomainTabGUI();

    /**
     * This enum is primarily used to return one of the farmed mappings.
     */
    public enum FARMED_DATATYPE {
        /**
         * Weapon map enum
         */
        WEAPONS,
        /**
         * Artifact map enum
         */
        ARTIFACTS,
        /**
         * Talent material (and weekly talent material) map enum
         */
        TALENTS
    }

    /**
     * Constructor of the GUI class.
     */
    public ToolGUI() {
        $$$setupUI$$$();
        changeFont(mainTabbedPane, AVAILABLE_FONTS.HEADER_FONT, 20.0F);
        changeFont(devWelcomeLabel, AVAILABLE_FONTS.HEADER_FONT, 20.0F);
        changeFont(devCreatorsLabel, AVAILABLE_FONTS.HEADER_FONT, 20.0F);
        changeFont(devPrecisi0nLabel, AVAILABLE_FONTS.CREATOR_FONT, 18.0F);
        changeFont(devLinakoLabel, AVAILABLE_FONTS.CREATOR_FONT, 18.0F);
        changeFont(devUpdatesTextPane, AVAILABLE_FONTS.TEXT_FONT, 12.0F);
        changeFont(devInfoTextPane, AVAILABLE_FONTS.TEXT_FONT, 12.0F);
        devInfoTextPane.setText(
                "This is a personal project to make our daily tasks a little bit more coordinated! Here's how to get started!\n\n -\uD83D\uDD38✨ Character Tab ✨\uD83D\uDD38-\n- Search by name or filter\n- Fill in the desired information (2nd artifact set is optional).\n- Checkboxes exist for characters to show up in the domains tab. Unchecking will hide a character from its chosen materials, making it easier to tell who still needs those materials. For example, if a character is done with its talents, you should uncheck the character.\n- DON'T FORGET TO SAVE\n\n\uD83D\uDD38✨ Weapon Tab ✨\uD83D\uDD38-\n Search by name or filter.\n- Only checkboxes appear. Checking a weapon will make it show up in farmed items in domains.\n- If a weapon is already listed through a character, it will be marked as \"Already Farmed\".\n\n-\uD83D\uDD38✨ Domains Tab ✨\uD83D\uDD38-\n- Search by filter or day. Results will be shown for today by default.\n- The chosen domain will show all characters/weapons checked in other tabs.");

        addTab("Characters", _characterTabGUI.getMainPanel());
        addTab("Weapons", _weaponsTabGUI.getMainPanel());
        addTab("Domains", __DOMAIN_TAB_GUI.getMainPanel());
        setContentPane(mainPanel);
        setTitle("Genshin Domain App v.1.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(Objects.requireNonNull(ToolGUI.class.getResource("/Files/Images/Icons/Program_Icon_Barbara.png"))).getImage());
        setResizable(false);
        setVisible(true);
    }

    /**
     * Creates a few custom UI components by hand.
     */
    private void createUIComponents() {
        //Place custom component creation code here
        mainPanel = new JPanel();
        initialiseFarmedArrays();
        readGeneratedCharacterCards();
    }

    private void initialiseFarmedArrays() {
        for (String talentBook : getFlattenedData(ToolData.RESOURCE_TYPE.TALENT_BOOK)) {
            farmedTalents.put(talentBook, new TreeSet<>());
        }
        for (String weeklyMat : getFlattenedData(ToolData.RESOURCE_TYPE.WEEKLY_BOSS_MATERIAL)) {
            farmedTalents.put(weeklyMat, new TreeSet<>());
        }
        for (String artifactSet : getFlattenedData(ToolData.RESOURCE_TYPE.ARTIFACT_SET)) {
            farmedArtifacts.put(artifactSet, new TreeSet<>());
        }
        for (String weaponName : getFlattenedData(ToolData.RESOURCE_TYPE.WEAPON_NAME)) {
            farmedWeapons.put(weaponName, new TreeSet<>());
        }
    }

    private void addTab(String title, JPanel jpanel) {
        mainTabbedPane.addTab(title, jpanel);
    }

    /**
     * Only used in CharacterTabGUI to add a newly generated CharacterCard to the collection.
     *
     * @param characterCard character card
     */
    public static void addCharacterCard(CharacterCard characterCard) {
        generatedCharacterCards.add(characterCard);
    }

    /**
     * Returns one of the farmed maps.
     *
     * @param fd the enum corresponding to the farmed mapping.
     * @return the corresponding farmed mapping.
     */
    public static TreeMap<String, Set<String>> getFarmedMapping(FARMED_DATATYPE fd) {
        assert fd != null;
        switch (fd) {
            case WEAPONS:
                return farmedWeapons;
            case ARTIFACTS:
                return farmedArtifacts;
            case TALENTS:
                return farmedTalents;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Reads character cards that have been saved in previous sessions.
     */
    private void readGeneratedCharacterCards() {
        File f_dir = new File(SAVE_LOCATION);
        if (f_dir.mkdir()) {
            return;
        }
        File[] savedCards = f_dir.listFiles(pathname -> !pathname.getAbsolutePath().contains(WEAPON_SAVE_FILE_NAME));
        assert savedCards != null;
        Gson gson = new Gson();

        try {
            for (File savedCard : savedCards) {
                JsonReader reader = new JsonReader(new FileReader(savedCard));
                CharacterCard card = gson.fromJson(reader, CharacterCard.class);
                card.setCharacterIcon(getResourceIcon(card.getCharacterName(), ToolData.RESOURCE_TYPE.CHARACTER));
                generatedCharacterCards.add(card);
                if (card.getTalentStatus()) {
                    farmedTalents.get(getTalentBookForCharacter(card.getCharacterName())).add(card.getCharacterName());
                    farmedTalents.get(getWeeklyBossMatForCharacter(card.getCharacterName())).add(card.getCharacterName());
                }
                if (!card.getArtifactSet1().isEmpty() && card.getArtifactSet1Status()) {
                    farmedArtifacts.get(card.getArtifactSet1()).add(card.getCharacterName());
                }
                if (!card.getArtifactSet2().isEmpty() && card.getArtifactSet2Status()) {
                    farmedArtifacts.get(card.getArtifactSet2()).add(card.getCharacterName());
                }
                if (!card.getWeapon().isEmpty() && card.getWeaponStatus()) {
                    farmedWeapons.get(card.getWeapon()).add(card.getCharacterName());
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read saved character cards.");
        }

    }

    /**
     * Returns whether a weapon has been listed for one of the characters.
     *
     * @param weapon weapon name
     * @return true, if the weapon was listed for one of the characters.
     */
    public static boolean isSomeoneFarmingForTheWeapon(String weapon) {
        return !farmedWeapons.get(weapon).isEmpty();
    }

    /**
     * Just a helper method that determines whether the given input is a weapon name.
     *
     * @param name the given input
     * @return whether the input is a weapon name.
     */
    public static boolean isThisAWeapon(String name) {
        return getFlattenedData(ToolData.RESOURCE_TYPE.WEAPON_NAME).contains(name);
    }

    /**
     * Primarily used for farmed labels in DomainCardGUI.
     *
     * @param mat the farmed material
     * @param rt  the resource type of that material
     * @return counter of characters who are farming this material.
     */
    public static int howManyAreFarmingThis(String mat, ToolData.RESOURCE_TYPE rt) {
        int counter = 0;
        for (CharacterCard characterCard : generatedCharacterCards) {
            switch (rt) {
                case TALENT_BOOK: {
                    if (characterCard.getTalentStatus() &&
                            getTalentBookForCharacter(characterCard.getCharacterName()).equalsIgnoreCase(mat)) {
                        counter++;
                    }
                    break;
                }
                case WEEKLY_BOSS_MATERIAL: {
                    if (characterCard.getTalentStatus() &&
                            getWeeklyBossMatForCharacter(characterCard.getCharacterName()).equalsIgnoreCase(mat)) {
                        counter++;
                    }
                    break;
                }
                case ARTIFACT_SET: {
                    if ((characterCard.getArtifactSet1Status() &&
                            characterCard.getArtifactSet1().equalsIgnoreCase(mat)) ||
                            characterCard.getArtifactSet2Status() &&
                                    characterCard.getArtifactSet2().equalsIgnoreCase(mat)) {
                        counter++;
                    }
                    break;
                }
                default:
                    throw new IllegalArgumentException("RESOURCE TYPE INVALID: " + rt.stringToken);
            }
        }
        return counter;
    }

    /**
     * Primarily used for farmed labels in DomainCardGUI.
     *
     * @param mat the farmed material
     * @param rt  the resource type of that material
     * @return set of characters who are farming this material.
     */
    public static Set<String> whoIsFarmingThis(String mat, ToolData.RESOURCE_TYPE rt) {
        Set<String> characterSet = new TreeSet<>();
        for (CharacterCard characterCard : generatedCharacterCards) {
            switch (rt) {
                case TALENT_BOOK: {
                    if (characterCard.getTalentStatus() &&
                            getTalentBookForCharacter(characterCard.getCharacterName()).equalsIgnoreCase(mat)) {
                        characterSet.add(characterCard.getCharacterName());
                    }
                    break;
                }
                case WEEKLY_BOSS_MATERIAL: {
                    if (characterCard.getTalentStatus() &&
                            getWeeklyBossMatForCharacter(characterCard.getCharacterName()).equalsIgnoreCase(mat)) {
                        characterSet.add(characterCard.getCharacterName());
                    }
                    break;
                }
                case ARTIFACT_SET: {
                    if ((characterCard.getArtifactSet1Status() &&
                            characterCard.getArtifactSet1().equalsIgnoreCase(mat)) || characterCard.getArtifactSet2Status() &&
                            characterCard.getArtifactSet2().equalsIgnoreCase(mat)) {
                        characterSet.add(characterCard.getCharacterName());
                    }
                    break;
                }
                default:
                    throw new IllegalArgumentException("RESOURCE TYPE INVALID: " + rt.stringToken);
            }
        }
        return characterSet;
    }

    /**
     * Updates the farmed map.
     *
     * @param dataField     what field has been updated
     * @param characterCard what character card has been updated
     * @param status        whether the character has started farming the item or not
     * @param item          the item that has been either removed or assigned to the character card.
     */
    public static void updateFarmedItemMap(ToolData.CHARACTER_CARD_DATA_FIELD dataField, CharacterCard characterCard,
                                           boolean status, String item) {
        if (item.isEmpty() || item.equalsIgnoreCase(EMPTY_SET_SELECTOR) ||
                item.equalsIgnoreCase(EMPTY_WEAPON_SELECTOR)) {
            return;
        }
        Map<String, Set<String>> mapping;
        switch (dataField) {
            case WEAPON:
            case FARMING_WEAPON_MATERIALS:
                mapping = farmedWeapons;
                break;
            case SET_ONE:
            case SET_TWO:
                mapping = farmedArtifacts;
                break;
            case FARMING_TALENT_MATERIALS:
                mapping = farmedTalents;
                break;
            default:
                throw new IllegalArgumentException("This datafield cannot be farmed " + dataField);
        }
        if (status) {
            mapping.get(item).add(characterCard.getCharacterName());
        } else {
            mapping.get(item).remove(characterCard.getCharacterName());
        }
    }

    /**
     * Saves the unassigned weapons into a json file.
     */
    public static void serializeSave() {
        Gson gson = new Gson();
        File f = new File(SAVE_LOCATION + WEAPON_SAVE_FILE_NAME);
        try {
            f.createNewFile();
            FileWriter fd = new FileWriter(f);
            gson.toJson(getUnassignedFarmedWeapons(), fd);
            fd.flush();
            fd.close();
        } catch (IOException e) {
            System.out.println("Failed to save weapons, please try again");
        }
    }

    /**
     * Checks if a card for the specified character has ever been generated by the program.
     *
     * @param charName the character name
     * @return true if the card was generated, false otherwise.
     */

    public static boolean checkIfCharacterCardHasBeenGenerated(String charName) {
        if (generatedCharacterCards.isEmpty()) {
            return false;
        }
        for (CharacterCard generatedCharacterCard : generatedCharacterCards) {
            if (generatedCharacterCard.getCharacterName().equalsIgnoreCase(charName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the card for the specified character.
     *
     * @param charName the character name
     * @return character card
     */
    public static CharacterCard getCharacterCard(String charName) {
        for (CharacterCard generatedCharacterCard : generatedCharacterCards) {
            if (generatedCharacterCard.getCharacterName().equalsIgnoreCase(charName)) {
                return generatedCharacterCard;
            }
        }
        return null;
    }

    /**
     * Removes whitespace from the name of the character, adds line break in its place and centers the name.
     *
     * @param name the character name to be changed
     * @return edited string
     */
    public static String formatString(String name) {
        final String HTML_BEGINNING = "<html><center>";
        final String HTML_END = "</center></html>";
        final String HTML_BREAK = "<br>";
        if (name.length() <= MAX_CHARACTERS_PER_LINE) {
            return HTML_BEGINNING + name + HTML_BREAK + " " + HTML_END;
        }
        List<Integer> whiteSpaceIndices = new ArrayList<>();

        int whiteSpaceIndex = name.indexOf(' ');
        while (whiteSpaceIndex >= 0) {
            whiteSpaceIndices.add(whiteSpaceIndex);
            if (whiteSpaceIndex + 1 >= name.length()) {
                break;
            } else {
                whiteSpaceIndex = name.indexOf(' ', whiteSpaceIndex + 1);
            }
        }
        if (whiteSpaceIndices.isEmpty()) {
            return name;
        } else if (whiteSpaceIndices.size() == 1) {
            return HTML_BEGINNING + name.replace(" ", HTML_BREAK) + HTML_END;
        } else {
            int chosenIndex = whiteSpaceIndices.get(whiteSpaceIndices.size() / 2);
            String namePart1 = name.substring(0, chosenIndex);
            String namePart2 = name.substring(chosenIndex + 1);
            return HTML_BEGINNING + namePart1 + HTML_BREAK + namePart2 + HTML_END;
        }

    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-2702645));
        mainPanel.setEnabled(true);
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mainInformationPanel = new JPanel();
        mainInformationPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainInformationPanel.setBackground(new Color(-468502));
        mainPanel.add(mainInformationPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mainTabbedPane = new JTabbedPane();
        Font mainTabbedPaneFont = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, mainTabbedPane.getFont());
        if (mainTabbedPaneFont != null) mainTabbedPane.setFont(mainTabbedPaneFont);
        mainTabbedPane.setTabPlacement(1);
        mainInformationPanel.add(mainTabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        welcomeTab = new JPanel();
        welcomeTab.setLayout(new GridBagLayout());
        mainTabbedPane.addTab("Welcome!", welcomeTab);
        devBasicInfoPanel = new JPanel();
        devBasicInfoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        welcomeTab.add(devBasicInfoPanel, gbc);
        devBasicInfoLeftPanel = new JPanel();
        devBasicInfoLeftPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        devBasicInfoLeftPanel.setBackground(new Color(-465419));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        devBasicInfoPanel.add(devBasicInfoLeftPanel, gbc);
        Welcome_Barbara = new JLabel();
        Welcome_Barbara.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Aesthetics/Barbara_Hello.gif")));
        Welcome_Barbara.setText("");
        devBasicInfoLeftPanel.add(Welcome_Barbara, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devUpdatesTextPane = new JTextPane();
        devUpdatesTextPane.setBackground(new Color(-465419));
        devUpdatesTextPane.setEditable(false);
        devUpdatesTextPane.setEnabled(true);
        devUpdatesTextPane.setFocusable(false);
        Font devUpdatesTextPaneFont = this.$$$getFont$$$(null, -1, -1, devUpdatesTextPane.getFont());
        if (devUpdatesTextPaneFont != null) devUpdatesTextPane.setFont(devUpdatesTextPaneFont);
        devUpdatesTextPane.setForeground(new Color(-11071434));
        devUpdatesTextPane.setMargin(new Insets(30, 20, 10, 10));
        devUpdatesTextPane.setSelectionColor(new Color(-9555638));
        devUpdatesTextPane.setText("For future updates contact one of us. Make sure to keep your save file!");
        devBasicInfoLeftPanel.add(devUpdatesTextPane, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        devCreatorsLabel = new JLabel();
        devCreatorsLabel.setBackground(new Color(-465419));
        Font devCreatorsLabelFont = this.$$$getFont$$$(null, -1, -1, devCreatorsLabel.getFont());
        if (devCreatorsLabelFont != null) devCreatorsLabel.setFont(devCreatorsLabelFont);
        devCreatorsLabel.setForeground(new Color(-11071434));
        devCreatorsLabel.setText(". * Creators * .");
        devBasicInfoLeftPanel.add(devCreatorsLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devLinakoLabel = new JLabel();
        devLinakoLabel.setBackground(new Color(-465419));
        Font devLinakoLabelFont = this.$$$getFont$$$(null, -1, -1, devLinakoLabel.getFont());
        if (devLinakoLabelFont != null) devLinakoLabel.setFont(devLinakoLabelFont);
        devLinakoLabel.setForeground(new Color(-11071434));
        devLinakoLabel.setText("Linako (yes.as.no)");
        devBasicInfoLeftPanel.add(devLinakoLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devPrecisi0nLabel = new JLabel();
        devPrecisi0nLabel.setBackground(new Color(-465419));
        Font devPrecisi0nLabelFont = this.$$$getFont$$$(null, -1, -1, devPrecisi0nLabel.getFont());
        if (devPrecisi0nLabelFont != null) devPrecisi0nLabel.setFont(devPrecisi0nLabelFont);
        devPrecisi0nLabel.setForeground(new Color(-11071434));
        devPrecisi0nLabel.setText("precisi0n");
        devBasicInfoLeftPanel.add(devPrecisi0nLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devBasicInfoRightPanel = new JPanel();
        devBasicInfoRightPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        devBasicInfoRightPanel.setBackground(new Color(-465419));
        Font devBasicInfoRightPanelFont = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 22, devBasicInfoRightPanel.getFont());
        if (devBasicInfoRightPanelFont != null) devBasicInfoRightPanel.setFont(devBasicInfoRightPanelFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devBasicInfoPanel.add(devBasicInfoRightPanel, gbc);
        devWelcomeLabel = new JLabel();
        devWelcomeLabel.setBackground(new Color(-465419));
        Font devWelcomeLabelFont = this.$$$getFont$$$(null, -1, -1, devWelcomeLabel.getFont());
        if (devWelcomeLabelFont != null) devWelcomeLabel.setFont(devWelcomeLabelFont);
        devWelcomeLabel.setForeground(new Color(-11071434));
        devWelcomeLabel.setText("° . * Welcome to GDApp! * . °");
        devBasicInfoRightPanel.add(devWelcomeLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devBasicInfoSpacer = new JPanel();
        devBasicInfoSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(20, 0, 0, 0), -1, -1));
        devBasicInfoSpacer.setBackground(new Color(-465419));
        devBasicInfoRightPanel.add(devBasicInfoSpacer, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devInfoTextPane = new JTextPane();
        devInfoTextPane.setBackground(new Color(-465419));
        devInfoTextPane.setEditable(false);
        devInfoTextPane.setFocusable(false);
        Font devInfoTextPaneFont = this.$$$getFont$$$(null, -1, -1, devInfoTextPane.getFont());
        if (devInfoTextPaneFont != null) devInfoTextPane.setFont(devInfoTextPaneFont);
        devInfoTextPane.setForeground(new Color(-11071434));
        devInfoTextPane.setMargin(new Insets(30, 20, 10, 10));
        devInfoTextPane.setText("");
        devBasicInfoRightPanel.add(devInfoTextPane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}

