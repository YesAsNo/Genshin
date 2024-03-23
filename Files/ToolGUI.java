package Files;

import static Files.ToolData.SAVE_LOCATION;
import static Files.ToolData.farmedArtifacts;
import static Files.ToolData.farmedWeapons;
import static Files.ToolData.flattenedDataCategory;
import static Files.ToolData.getArtifactSetDescription;
import static Files.ToolData.getFlattenedData;
import static Files.ToolData.getMapping;
import static Files.ToolData.knownMappings;

import Files.ToolData.RESOURCE_TYPE;
import Files.ToolData.WEAPON_RARITY;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ToolGUI extends JFrame implements ActionListener {

    public static final String NO_CHARACTERS_MATCH_MESSAGE = "No fighters >:(";
    public static final String UNKNOWN_CHARACTER = "unknown_character";
    public static final int MAX_CHARACTERS_PER_LINE = 15;
    public static final String TOOLTIP_FOR_LABELS_WITHOUT_ICON = "Here will be the chosen set name shown";
    public static final String TOOLTIP_FOR_LABELS_WITH_ICON = "Here will be the chosen set icon shown";
    public static final String TOOLTIP_FOR_WEAPON_NAME_LABEL = "Here will be the chosen weapon name shown";
    public static final String TOOLTIP_FOR_WEAPON_ICON_LABEL = "Here will be the chosen weapon icon shown";
    public static final String UNKNOWN_SET_MESSAGE = "[ Empty Set Selector ]";
    public static final String FOUR_STAR_WEAPON_DELIMITER = "[ 4-Star Weapons ]";
    public static final String FIVE_STAR_WEAPON_DELIMITER = "[ 5-Star Weapons ]";
    public static final String UNKNOWN_ARTIFACT = "unknown_artifact";
    public static final String UNKNOWN_WEAPON_MESSAGE = "[ Empty Weapon Selector ]";
    public static final String UNKNOWN_WEAPON_PATH = "./Files/Images/Weapons/unknown_weapon.png";
    public static final String WEAPON_FILTER = "[ Filter ]";
    public static final String CLOSE_TEXT = "CLOSE";
    public static final int CHARACTER_LIMIT = 150;
    public static final String WEAPON_SAVE_FILE_NAME = "saved_weapons.json";
    private JPanel mainPanel;
    private JTabbedPane characterTabPane;
    private JPanel mainInformationPanel;
    private JPanel characterTab;
    private JPanel weaponsTab;
    private JTextField characterSelectorField;
    private JButton searchConfirmButton;
    private JPanel selectedCharacterPanel;
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
    private JPanel devDomainsTab;
    private JPanel devDomainsTabPanel;
    private JScrollPane devDomainsScrollPane;
    private JPanel devDomainCardViewport;
    private JButton domainSearchButton;
    private JComboBox<String> domainFilterBox;
    private final JScrollPane characterSearchScrollPane = new JScrollPane();
    private final JScrollPane devWeaponTabScrollPane = new JScrollPane();
    private final JTextField devWeaponsTabSearchbar = new JTextField();
    private final JButton devWeaponTabSearchButton = new JButton();
    private final JPanel devWeaponTabScrollPanePanel = new JPanel();
    private static final List<CharacterCard> generatedCharacterCards = new ArrayList<>();
    private static final JComboBox<String> devFilterComboBox = new JComboBox<>();

    /**
     * Constructor of the GUI class.
     */

    public ToolGUI() {
        $$$setupUI$$$();
        setUpWeaponsPanel();
        setContentPane(mainPanel);
        setTitle("Genshin Domain App!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Creates a few custom UI components by hand.
     */
    private void createUIComponents() {
        //Place custom component creation code here
        selectedCharacterPanel = new JPanel(new GridBagLayout());
        mainPanel = new JPanel();
        searchConfirmButton = new JButton();
        searchConfirmButton.addActionListener(this);
        devWeaponTabSearchButton.addActionListener(this);
        characterSelectorField = new JTextField();
        characterSelectorField.addMouseListener(new SearchBarListener());
        readGeneratedCharacterCards();
        parseCharacterWeapons();
        parseCharacterArtifacts();
    }

    public void addDomainCardToViewport(JPanel jPanel, GridBagConstraints gbc) {
        devDomainCardViewport.add(jPanel, gbc);
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
                card.setCharacterIcon();
                generatedCharacterCards.add(card);

            }
        } catch (IOException e) {
            System.out.println("Could not read saved character cards.");
        }
    }

    /**
     * Parses character weapons.
     */
    private void parseCharacterWeapons() {

        for (CharacterCard characterCard : generatedCharacterCards) {
            String charWeapon = characterCard.getWeapon();
            if (!charWeapon.isEmpty() && characterCard.getWeaponStatus()) {
                Set<String> keys = farmedWeapons.keySet();
                if (keys.contains(charWeapon)) {
                    farmedWeapons.get(charWeapon).add(characterCard.getCharacterName());
                } else {
                    Set<String> chars = new TreeSet<>();
                    chars.add(characterCard.getCharacterName());
                    farmedWeapons.put(charWeapon, chars);
                }
            }
        }
        System.out.println(farmedWeapons.keySet().size());
    }

    /**
     * Parses character artifacts.
     */
    private void parseCharacterArtifacts() {
        for (CharacterCard characterCard : generatedCharacterCards) {
            String charArtifactSet1 = characterCard.getArtifactSet1();
            String charArtifactSet2 = characterCard.getArtifactSet2();
            if (!charArtifactSet1.isEmpty() && characterCard.getArtifactSet1Status()) {
                Set<String> keys = farmedArtifacts.keySet();
                if (keys.contains(charArtifactSet1)) {
                    farmedArtifacts.get(charArtifactSet1).add(characterCard.getCharacterName());
                } else {
                    Set<String> chars = new TreeSet<>();
                    chars.add(characterCard.getCharacterName());
                    farmedArtifacts.put(charArtifactSet1, chars);
                }
            }
            if (!charArtifactSet2.isEmpty() && characterCard.getArtifactSet2Status()) {
                Set<String> keys = farmedArtifacts.keySet();
                if (keys.contains(charArtifactSet2)) {
                    farmedArtifacts.get(charArtifactSet2).add(characterCard.getCharacterName());
                } else {
                    Set<String> chars = new TreeSet<>();
                    chars.add(characterCard.getCharacterName());
                    farmedArtifacts.put(charArtifactSet2, chars);
                }
            }
        }
        System.out.println(farmedArtifacts.keySet().size());
    }

    /**
     * Listener for the searchConfirmButton.
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {

        String userFieldInput;
        int matchedCount = 0;
        if (e.getSource() == searchConfirmButton) {
            userFieldInput = characterSelectorField.getText().toLowerCase();
            selectedCharacterPanel.removeAll();
            characterSearchScrollPane.updateUI();
            for (String s : getFlattenedData(flattenedDataCategory.CHARACTER)) {
                if (s.toLowerCase().contains(userFieldInput)) {
                    {
                        generateCharacterButton(s, matchedCount);
                    }
                    matchedCount++;

                }
            }
            if (matchedCount == 0) {
                generateCharacterButton(UNKNOWN_CHARACTER, matchedCount);
            } else {
                characterSearchScrollPane.setViewportView(selectedCharacterPanel);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 3;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;

                characterTab.add(characterSearchScrollPane, gbc);

            }
        } else {
            userFieldInput = devWeaponsTabSearchbar.getText().toLowerCase();
            devWeaponTabScrollPanePanel.removeAll();
            devWeaponTabScrollPane.updateUI();
            for (String s : getFlattenedData(flattenedDataCategory.WEAPON_NAME)) {
                String weaponCategory = lookUpWeaponRarityAndType(s).getWeaponType();
                String filterOption = (String) devFilterComboBox.getSelectedItem();
                assert weaponCategory != null;
                assert filterOption != null;
                if (s.toLowerCase().contains(userFieldInput) && (filterOption.equalsIgnoreCase(weaponCategory) ||
                        filterOption.equalsIgnoreCase(WEAPON_FILTER))) {
                    generateWeaponCard(s, matchedCount);
                    matchedCount++;
                }

            }
        }
    }

    /**
     * Updates the farmed map.
     *
     * @param farmedMap The map to update
     * @param itemName The name of the item that is farmed.
     * @param charName The name of the character that farms the item.
     * @param isFarming Flag that designates whether the character has started (true) or stopped (false) farming the item.
     */
    public static void updateFarmedItemMap(Map<String, Set<String>> farmedMap, String itemName, String charName,
                                           boolean isFarming) {
        if (farmedMap.containsKey(itemName)) {
            Set<String> charactersFarmingSet = farmedMap.get(itemName);
            if (isFarming) {
                charactersFarmingSet.add(charName);
            } else {
                charactersFarmingSet.remove(charName);
            }
        } else {
            if (isFarming) {
                Set<String> charactersFarmingSet = new TreeSet<>();
                charactersFarmingSet.add(charName);
                farmedMap.put(itemName, charactersFarmingSet);
            }
        }
    }

    /**
     * Generates a character button for the character specified by name and the index of the match.
     *
     * @param characterName the name of the character
     * @param index which character by count it is
     */
    private void generateCharacterButton(String characterName, int index) {
        if (characterName.equalsIgnoreCase(UNKNOWN_CHARACTER)) {
            JLabel unknownCharacterLabel = new JLabel(generateResourceIconPath(UNKNOWN_CHARACTER, RESOURCE_TYPE.CHARACTER));
            unknownCharacterLabel.setText(NO_CHARACTERS_MATCH_MESSAGE);
            unknownCharacterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            unknownCharacterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            setFont(unknownCharacterLabel);
            selectedCharacterPanel.add(unknownCharacterLabel);
            selectedCharacterPanel.updateUI();
            return;
        }
        JButton characterButton = getjButton(characterName);
        addCharacterButtonToSelectedCharacterPanel(characterButton, index);

    }

    /**
     * Sets the hardcoded font for the specified component.
     *
     * @param c component whose font is to be changed
     */
    public void setFont(Component c) {
        Font font = $$$getFont$$$("Source Code Pro Black", Font.BOLD, 16, characterTabPane.getFont());
        if (font != null) {
            c.setFont(font);
        }
    }

    /**
     * Gets the card for the specified character.
     *
     * @param charName the character name
     * @return character card
     */
    public CharacterCard getCharacterCard(String charName) {
        for (CharacterCard generatedCharacterCard : generatedCharacterCards) {
            if (generatedCharacterCard.getCharacterName().equalsIgnoreCase(charName)) {
                return generatedCharacterCard;
            }
        }
        return null;
    }

    /**
     * Creates a JButton for the specified character.
     *
     * @param characterName the name of the character
     * @return JButton for the character.
     */
    private JButton getjButton(String characterName) {
        JButton characterButton = new JButton();
        CharacterCard characterCard;
        if (!checkIfCharacterCardHasBeenGenerated(characterName)) {
            characterCard = new CharacterCard(characterName);
            generatedCharacterCards.add(characterCard);
        } else {
            characterCard = getCharacterCard(characterName);
        }
        characterButton.setIcon(characterCard.getCharacterIcon());
        characterButton.setText(formatString(characterName));
        characterButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        characterButton.setHorizontalTextPosition(SwingConstants.CENTER);
        setFont(characterButton);
        characterButton.setOpaque(false);
        characterButton.setBorderPainted(false);
        characterButton.setContentAreaFilled(false);
        characterButton.addActionListener(e -> {
            if (isWindowAlreadyOpen(characterName)) {
                getOpenWindow(characterName).setVisible(true);
            } else {
                new CharacterCardGUI(characterCard);
            }
        });

        return characterButton;
    }

    /**
     * Verifies if a window for the specified character has already been opened.
     *
     * @param charName the specified character
     * @return true if the window is open, otherwise false.
     */

    public boolean isWindowAlreadyOpen(String charName) {
        Frame[] createdWindows = Frame.getFrames();
        for (Frame window : createdWindows) {
            if (window != null && window.getTitle().contains(charName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the open window for the specified character.
     *
     * @param charName the name of the character
     * @return window with the loaded character card for the specified character
     */
    public Window getOpenWindow(String charName) {
        assert isWindowAlreadyOpen(charName);
        Frame[] createdWindows = Frame.getFrames();
        for (Frame frame : createdWindows) {
            if (frame != null && frame.getTitle().contains(charName)) {
                return frame;
            }
        }
        return null;
    }

    /**
     * Generates a path to the specified resource.
     *
     * @param resourceName the name of the resource
     * @param resourceType the type of the resource
     * @return character icon path
     */
    public static String generateResourceIconPath(String resourceName, RESOURCE_TYPE resourceType) {
        assert resourceName != null;
        if (resourceType == RESOURCE_TYPE.CHARACTER) {
            return "./Files/Images/Characters/" + resourceName + ".png";
        } else if (resourceType == RESOURCE_TYPE.WEAPON_MATERIAL) {
            return "./Files/Images/Weapon Materials/" + resourceName + ".png";
        } else if (resourceType == RESOURCE_TYPE.ARTIFACT) {
            return "./Files/Images/Artifacts/" + resourceName + ".png";
        } else if (resourceType == RESOURCE_TYPE.WEAPON) {
            if (resourceName.equalsIgnoreCase(UNKNOWN_WEAPON_MESSAGE)) {
                return UNKNOWN_WEAPON_PATH;
            }
            WeaponInfo wi = lookUpWeaponRarityAndType(resourceName);
            return switch (wi.getRarity()) {
                case FOUR_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_4star/" + resourceName + ".png";
                case FIVE_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_5star/" + resourceName + ".png";
            };
        } else {
            return "";
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
     * Adds a character button to the selected character panel (after triggering actionPerformed)
     *
     * @param charButton the button to add
     * @param index the index of the match
     */
    private void addCharacterButtonToSelectedCharacterPanel(JButton charButton, int index) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = index % 6;
        gbc.gridy = (index - gbc.gridx) / 6;
        selectedCharacterPanel.add(charButton, gbc);
        selectedCharacterPanel.updateUI();

    }

    /**
     * Removes whitespace from the name of the character, adds line break in its place and centers the name.
     *
     * @param name the character name to be changed
     * @return edited string
     */
    public String formatString(String name) {
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
     * Looks up what weapon category is assigned to the character, i.e. what type of weapons the character can wield.
     *
     * @param charName the name of the character
     * @return the string that represents the category (one of five possibilities)
     */

    public static String lookUpWeaponCategoryForCharacter(String charName) {
        Map<String, List<String>> weaponMapping = getMapping(knownMappings.WEPTYPE_CHAR);
        for (String key : weaponMapping.keySet()) {
            List<String> weapons = weaponMapping.get(key);
            if (weapons.contains(charName)) {
                return key;
            }

        }
        return null;
    }

    /**
     * Looks up weapon material for the specified weapon.
     *
     * @param weaponName the weapon name
     * @return the weapon material.
     */

    public static String lookUpWeaponMaterial(String weaponName) {
        assert getFlattenedData(flattenedDataCategory.WEAPON_NAME).contains(weaponName);
        Map<String, List<String>> mapping = getMapping(knownMappings.WEPMAT_WEPNAME);
        for (String key : mapping.keySet()) {
            List<String> weapons = mapping.get(key);
            if (weapons.contains(weaponName)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Grabs all weapons based on their rarity and type.
     *
     * @param WEAPONRarity rarity of the weapon
     * @param weaponType type of the weapon
     * @return list of the weapons with the specified rarity.
     */
    public static List<String> lookUpWeapons(WEAPON_RARITY WEAPONRarity, String weaponType) {

        final String FOUR_STAR_WEAPON_KEY = "Four-Star " + weaponType;
        final String FIVE_STAR_WEAPON_KEY = "Five-Star " + weaponType;
        Map<String, List<String>> weaponMapping = getMapping(knownMappings.WEPRARITYANDTYPE_WEPNAME);

        return switch (WEAPONRarity) {
            case FOUR_STAR -> weaponMapping.get(FOUR_STAR_WEAPON_KEY);
            case FIVE_STAR -> weaponMapping.get(FIVE_STAR_WEAPON_KEY);
        };
    }

    /**
     * Looks up the rarity and type of specified weapon.
     *
     * @param weaponName the weapon name
     * @return WeaponInfo object with rarity and type of the weapon.
     */
    public static WeaponInfo lookUpWeaponRarityAndType(String weaponName) {

        Map<String, List<String>> weaponMapping = getMapping(knownMappings.WEPRARITYANDTYPE_WEPNAME);
        for (String key : weaponMapping.keySet()) {
            List<String> weapons = weaponMapping.get(key);
            if (weapons.contains(weaponName)) {
                return new WeaponInfo(key);
            }
        }
        return new WeaponInfo("");
    }

    /**
     * Looks up a set description from the name.
     *
     * @param setName the name of the set
     * @return the description of it as String.
     */
    public static String lookUpSetDescription(String setName) {
        return getArtifactSetDescription(setName);
    }

    private void generateWeaponCard(String weaponName, int matchedCount) {
        JPanel devWeaponCard = new JPanel();
        devWeaponCard.setLayout(new GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        devWeaponCard.setBackground(new Color(-1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = matchedCount % 3;
        gbc.gridy = (matchedCount - gbc.gridx) / 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        devWeaponTabScrollPanePanel.add(devWeaponCard, gbc);
        devWeaponCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        JLabel devWeaponIcon = new JLabel();
        Font devWeaponIconFont = $$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWeaponIcon.getFont());
        if (devWeaponIconFont != null) {
            devWeaponIcon.setFont(devWeaponIconFont);
        }
        devWeaponIcon.setHorizontalAlignment(0);
        devWeaponIcon.setHorizontalTextPosition(0);
        devWeaponIcon.setIcon(new ImageIcon(generateResourceIconPath(weaponName, RESOURCE_TYPE.WEAPON)));
        devWeaponIcon.setText(formatString(weaponName));
        devWeaponIcon.setVerticalAlignment(0);
        devWeaponIcon.setVerticalTextPosition(3);
        devWeaponCard.add(devWeaponIcon,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JCheckBox devWepMatListingCheckbox = new JCheckBox();
        devWepMatListingCheckbox.setBackground(new Color(-1));
        if (farmedWeapons.containsKey(weaponName)) {
            devWepMatListingCheckbox.setSelected(true);
            devWepMatListingCheckbox.setEnabled(false);
            devWepMatListingCheckbox.setText("Already Farmed");
        } else {
            devWepMatListingCheckbox.setText("List Weapon?");
        }
        devWepMatListingCheckbox.addItemListener(new WeaponCardListener(weaponName, farmedWeapons));

        devWeaponCard.add(devWepMatListingCheckbox,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JLabel devWepMaterialPreview = new JLabel();
        Font devWepMaterialPreviewFont =
                $$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWepMaterialPreview.getFont());
        if (devWepMaterialPreviewFont != null) {
            devWepMaterialPreview.setFont(devWepMaterialPreviewFont);
        }
        devWepMaterialPreview.setHorizontalAlignment(0);
        devWepMaterialPreview.setHorizontalTextPosition(0);
        devWepMaterialPreview.setIcon(new ImageIcon(
                generateResourceIconPath(lookUpWeaponMaterial(weaponName), RESOURCE_TYPE.WEAPON_MATERIAL)));
        devWepMaterialPreview.setText("");
        devWepMaterialPreview.setVerticalAlignment(0);
        devWepMaterialPreview.setVerticalTextPosition(3);
        devWeaponCard.add(devWepMaterialPreview,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JLabel devWepTypeLabel = new JLabel();
        Font devWepTypeLabelFont = $$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWepTypeLabel.getFont());
        if (devWepTypeLabelFont != null) {
            devWepTypeLabel.setFont(devWepTypeLabelFont);
        }
        devWepTypeLabel.setText("Type: " + lookUpWeaponRarityAndType(weaponName).getWeaponType());
        devWeaponCard.add(devWepTypeLabel,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
    }

    private void setUpWeaponsPanel() {
        JPanel devWeaponTabPanel = new JPanel();
        devWeaponTabPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        weaponsTab.add(devWeaponTabPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devWeaponTabPanel.add(devWeaponTabScrollPane, gbc);
        devWeaponTabScrollPanePanel.setLayout(new GridBagLayout());
        devWeaponTabScrollPane.setViewportView(devWeaponTabScrollPanePanel);
        devWeaponTabScrollPane.updateUI();

        devWeaponsTabSearchbar.setEnabled(true);
        Font devWeaponsTabSearchbarFont =
                $$$getFont$$$("Source Code Pro Black", Font.BOLD, 18, devWeaponsTabSearchbar.getFont());
        if (devWeaponsTabSearchbarFont != null) {
            devWeaponsTabSearchbar.setFont(devWeaponsTabSearchbarFont);
        }
        devWeaponsTabSearchbar.setInheritsPopupMenu(false);
        devWeaponsTabSearchbar.setMaximumSize(new Dimension(240, 33));
        devWeaponsTabSearchbar.setMinimumSize(new Dimension(240, 33));
        devWeaponsTabSearchbar.setPreferredSize(new Dimension(240, 33));
        devWeaponsTabSearchbar.setText("Search by name or type!");
        devWeaponsTabSearchbar.addMouseListener(new SearchBarListener());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        weaponsTab.add(devWeaponsTabSearchbar, gbc);

        devWeaponTabSearchButton.setMaximumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setMinimumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setPreferredSize(new Dimension(50, 30));
        devWeaponTabSearchButton.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        weaponsTab.add(devWeaponTabSearchButton, gbc);
        JButton devSaveAllWeapons = new JButton();
        devSaveAllWeapons.setBackground(new Color(-6039919));
        Font devSaveAllWeaponsFont = $$$getFont$$$("Source Code Pro", Font.BOLD, -1, devSaveAllWeapons.getFont());
        if (devSaveAllWeaponsFont != null) {
            devSaveAllWeapons.setFont(devSaveAllWeaponsFont);
        }
        devSaveAllWeapons.setForeground(new Color(-394241));
        devSaveAllWeapons.setText("SAVE all weapons");
        devSaveAllWeapons.addActionListener(new WeaponCardListener(farmedWeapons));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 0, 0);
        weaponsTab.add(devSaveAllWeapons, gbc);

        devFilterComboBox.setBackground(new Color(-2702645));
        devFilterComboBox.setEnabled(true);
        Font devFilterComboBoxFont = $$$getFont$$$("Source Code Pro Black", Font.BOLD, -1, devFilterComboBox.getFont());
        if (devFilterComboBoxFont != null) {
            devFilterComboBox.setFont(devFilterComboBoxFont);
        }
        final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        defaultComboBoxModel1.addElement("[ Filter ]");
        defaultComboBoxModel1.addElement("Claymore");
        defaultComboBoxModel1.addElement("Bow");
        defaultComboBoxModel1.addElement("Polearm");
        defaultComboBoxModel1.addElement("Sword");
        defaultComboBoxModel1.addElement("Catalyst");

        devFilterComboBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        weaponsTab.add(devFilterComboBox, gbc);
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
        mainPanel.add(spacer1,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
                        GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mainInformationPanel = new JPanel();
        mainInformationPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainInformationPanel.setBackground(new Color(-3689540));
        mainPanel.add(mainInformationPanel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        characterTabPane = new JTabbedPane();
        Font characterTabPaneFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, characterTabPane.getFont());
        if (characterTabPaneFont != null) {
            characterTabPane.setFont(characterTabPaneFont);
        }
        characterTabPane.setTabPlacement(1);
        mainInformationPanel.add(characterTabPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
                        new Dimension(200, 200), null, 0, false));
        welcomeTab = new JPanel();
        welcomeTab.setLayout(new GridBagLayout());
        characterTabPane.addTab("Welcome!", welcomeTab);
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
        devBasicInfoLeftPanel.add(Welcome_Barbara,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devUpdatesTextPane = new JTextPane();
        devUpdatesTextPane.setBackground(new Color(-465419));
        devUpdatesTextPane.setEditable(false);
        devUpdatesTextPane.setEnabled(true);
        devUpdatesTextPane.setFocusable(false);
        Font devUpdatesTextPaneFont =
                this.$$$getFont$$$("Source Code Pro Semibold", -1, -1, devUpdatesTextPane.getFont());
        if (devUpdatesTextPaneFont != null) {
            devUpdatesTextPane.setFont(devUpdatesTextPaneFont);
        }
        devUpdatesTextPane.setForeground(new Color(-11071434));
        devUpdatesTextPane.setMargin(new Insets(30, 20, 10, 10));
        devUpdatesTextPane.setSelectionColor(new Color(-9555638));
        devUpdatesTextPane.setText("For future updates contact one of us. Make sure to keep your save file!");
        devBasicInfoLeftPanel.add(devUpdatesTextPane,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null,
                        new Dimension(150, 50), null, 0, false));
        devCreatorsLabel = new JLabel();
        devCreatorsLabel.setBackground(new Color(-465419));
        Font devCreatorsLabelFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, devCreatorsLabel.getFont());
        if (devCreatorsLabelFont != null) {
            devCreatorsLabel.setFont(devCreatorsLabelFont);
        }
        devCreatorsLabel.setForeground(new Color(-11071434));
        devCreatorsLabel.setText("✨ Creators ✨");
        devBasicInfoLeftPanel.add(devCreatorsLabel,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devLinakoLabel = new JLabel();
        devLinakoLabel.setBackground(new Color(-465419));
        Font devLinakoLabelFont = this.$$$getFont$$$("Source Code Pro Semibold", -1, 18, devLinakoLabel.getFont());
        if (devLinakoLabelFont != null) {
            devLinakoLabel.setFont(devLinakoLabelFont);
        }
        devLinakoLabel.setForeground(new Color(-11071434));
        devLinakoLabel.setText("Linako (yes.as.no)");
        devBasicInfoLeftPanel.add(devLinakoLabel,
                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devPrecisi0nLabel = new JLabel();
        devPrecisi0nLabel.setBackground(new Color(-465419));
        Font devPrecisi0nLabelFont =
                this.$$$getFont$$$("Source Code Pro Semibold", -1, 18, devPrecisi0nLabel.getFont());
        if (devPrecisi0nLabelFont != null) {
            devPrecisi0nLabel.setFont(devPrecisi0nLabelFont);
        }
        devPrecisi0nLabel.setForeground(new Color(-11071434));
        devPrecisi0nLabel.setText("precisi0n");
        devBasicInfoLeftPanel.add(devPrecisi0nLabel,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devBasicInfoRightPanel = new JPanel();
        devBasicInfoRightPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        devBasicInfoRightPanel.setBackground(new Color(-465419));
        Font devBasicInfoRightPanelFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 22, devBasicInfoRightPanel.getFont());
        if (devBasicInfoRightPanelFont != null) {
            devBasicInfoRightPanel.setFont(devBasicInfoRightPanelFont);
        }
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devBasicInfoPanel.add(devBasicInfoRightPanel, gbc);
        devWelcomeLabel = new JLabel();
        devWelcomeLabel.setBackground(new Color(-465419));
        Font devWelcomeLabelFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, devWelcomeLabel.getFont());
        if (devWelcomeLabelFont != null) {
            devWelcomeLabel.setFont(devWelcomeLabelFont);
        }
        devWelcomeLabel.setForeground(new Color(-11071434));
        devWelcomeLabel.setText("✨ Welcome to GDApp! ✨");
        devBasicInfoRightPanel.add(devWelcomeLabel,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devBasicInfoSpacer = new JPanel();
        devBasicInfoSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(20, 0, 0, 0), -1, -1));
        devBasicInfoSpacer.setBackground(new Color(-465419));
        devBasicInfoRightPanel.add(devBasicInfoSpacer,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devInfoTextPane = new JTextPane();
        devInfoTextPane.setBackground(new Color(-465419));
        devInfoTextPane.setEditable(false);
        devInfoTextPane.setFocusable(false);
        Font devInfoTextPaneFont = this.$$$getFont$$$("Source Code Pro Semibold", -1, -1, devInfoTextPane.getFont());
        if (devInfoTextPaneFont != null) {
            devInfoTextPane.setFont(devInfoTextPaneFont);
        }
        devInfoTextPane.setForeground(new Color(-11071434));
        devInfoTextPane.setMargin(new Insets(30, 20, 10, 10));
        devInfoTextPane.setText(
                "This is a personal project to make our daily tasks a little bit more coordinated! Here's how to get started!\n\n-\uD83D\uDD38✨ Character Tab ✨\uD83D\uDD38-\n\n- Search by name, filter or type \"debug\" to see all characters\n- Fill in the desired information (2nd artifact set is optional)\n- Checkboxes exist for characters to show up in the domains tab. Unchecking will hide a character from its chosen materials, making it easier to tell who still needs those materials. For example, if a character is done with its talents, you should uncheck the character.\n\n-\uD83D\uDD38✨ Weapon Tab ✨\uD83D\uDD38-\n\n- Search by name, filter or type \"debug\" to see all weapons.\n- Only checkboxes appear. Checking a weapon will make it show up in farmed items in domains.\n\n-\uD83D\uDD38✨ Domains Tab ✨\uD83D\uDD38-\n\n- Search by name, filter or type \"debug\" to see all domains.\n- The chosen domain will show all characters/weapons checked in other tabs.");
        devBasicInfoRightPanel.add(devInfoTextPane,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null,
                        new Dimension(150, 50), null, 0, false));
        characterTab = new JPanel();
        characterTab.setLayout(new GridBagLayout());
        characterTab.setBackground(new Color(-1));
        characterTab.setEnabled(true);
        characterTab.setFocusCycleRoot(false);
        Font characterTabFont = this.$$$getFont$$$(null, -1, -1, characterTab.getFont());
        if (characterTabFont != null) {
            characterTab.setFont(characterTabFont);
        }
        characterTab.setOpaque(true);
        characterTab.setRequestFocusEnabled(true);
        characterTabPane.addTab("Characters", characterTab);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        characterTab.add(selectedCharacterPanel, gbc);
        searchConfirmButton.setMaximumSize(new Dimension(30, 30));
        searchConfirmButton.setMinimumSize(new Dimension(30, 30));
        searchConfirmButton.setPreferredSize(new Dimension(50, 30));
        searchConfirmButton.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        characterTab.add(searchConfirmButton, gbc);
        characterSelectorField.setEnabled(true);
        Font characterSelectorFieldFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 18, characterSelectorField.getFont());
        if (characterSelectorFieldFont != null) {
            characterSelectorField.setFont(characterSelectorFieldFont);
        }
        characterSelectorField.setInheritsPopupMenu(false);
        characterSelectorField.setMaximumSize(new Dimension(240, 33));
        characterSelectorField.setMinimumSize(new Dimension(240, 33));
        characterSelectorField.setPreferredSize(new Dimension(240, 33));
        characterSelectorField.setText("Choose your fighter!");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 250, 0, 0);
        characterTab.add(characterSelectorField, gbc);
        weaponsTab = new JPanel();
        weaponsTab.setLayout(new GridBagLayout());
        weaponsTab.setBackground(new Color(-1));
        characterTabPane.addTab("Weapons", weaponsTab);
        devDomainsTab = new JPanel();
        devDomainsTab.setLayout(new GridBagLayout());
        devDomainsTab.setBackground(new Color(-1));
        characterTabPane.addTab("Domains", devDomainsTab);
        devDomainsTabPanel = new JPanel();
        devDomainsTabPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devDomainsTab.add(devDomainsTabPanel, gbc);
        devDomainsScrollPane = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devDomainsTabPanel.add(devDomainsScrollPane, gbc);
        devDomainCardViewport = new JPanel();
        devDomainCardViewport.setLayout(new GridBagLayout());
        devDomainsScrollPane.setViewportView(devDomainCardViewport);
        domainFilterBox = new JComboBox();
        domainFilterBox.setBackground(new Color(-2702645));
        domainFilterBox.setEnabled(true);
        Font domainFilterBoxFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, -1, domainFilterBox.getFont());
        if (domainFilterBoxFont != null) {
            domainFilterBox.setFont(domainFilterBoxFont);
        }
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("[ Filter ]");
        defaultComboBoxModel1.addElement("Artifacts");
        defaultComboBoxModel1.addElement("Talents");
        defaultComboBoxModel1.addElement("Weekly Talents");
        defaultComboBoxModel1.addElement("Weapons");
        domainFilterBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 10);
        devDomainsTab.add(domainFilterBox, gbc);
        domainSearchButton = new JButton();
        domainSearchButton.setMaximumSize(new Dimension(30, 30));
        domainSearchButton.setMinimumSize(new Dimension(30, 30));
        domainSearchButton.setPreferredSize(new Dimension(50, 30));
        domainSearchButton.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        devDomainsTab.add(domainSearchButton, gbc);
    }

    /** @noinspection ALL */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) {
            return null;
        }
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
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
                size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) :
                new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}

