package Files;

import static Files.ToolData.SAVE_LOCATION;
import static Files.ToolData.artifactSetDescriptionsMap;
import static Files.ToolData.artifactsFlattened;
import static Files.ToolData.characterWeaponsMap;
import static Files.ToolData.charactersFlattened;
import static Files.ToolData.weaponsFlattened;
import static Files.ToolData.weaponsRaritiesMap;

import Files.ToolData.RARITY;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ToolGUI extends JFrame implements ActionListener {

    public static final String NO_CHARACTERS_MATCH_MESSAGE = "No fighters >:(";
    public static final String UNKNOWN_CHARACTER_PLACEHOLDER_NAME = "unknown_character";
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
    public static final String CLOSE_TEXT = "CLOSE";
    public static final int CHARACTER_LIMIT = 150;

    private JPanel panel1;
    private JLabel titleLabel;
    private JTabbedPane characterTabPane;
    private JPanel mainInformationPanel;
    private JPanel characterTab;
    private JPanel weaponsTab;
    private JTextField characterSelectorField;
    private JButton searchConfirmButton;
    private JPanel selectedCharacterPanel;
    private final JScrollPane characterSearchScrollPane = new JScrollPane();
    private final JTextField devWeaponsTabSearchbar = new JTextField();
    private final JButton devWeaponTabSearchButton = new JButton();
    private final JPanel devWeaponTabScrollPanePanel = new JPanel();
    private static final List<CharacterCard> generatedCharacterCards = new ArrayList<>();

    /**
     * Constructor of the GUI class.
     */

    public ToolGUI() {
        $$$setupUI$$$();
        setUpWeaponsPanel();
        setContentPane(panel1);
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
        panel1 = new JPanel();
        searchConfirmButton = new JButton();
        searchConfirmButton.addActionListener(this);
        devWeaponTabSearchButton.addActionListener(this);
        characterSelectorField = new JTextField();
        characterSelectorField.addMouseListener(new SearchBarListener());
        readGeneratedCharacterCards();
    }

    /**
     * Reads character cards that have been saved in previous sessions.
     */
    void readGeneratedCharacterCards() {
        File f_dir = new File(SAVE_LOCATION);
        if (f_dir.mkdir()) {
            return;
        }
        File[] savedCards = f_dir.listFiles();
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
            for (String s : charactersFlattened) {
                if (s.toLowerCase().contains(userFieldInput)) {
                    if (!userFieldInput.isEmpty()) {
                        generateCharacterButton(s, matchedCount);
                    }
                    matchedCount++;

                }

            }
            if (matchedCount == 0 || userFieldInput.isEmpty()) {
                generateCharacterButton(UNKNOWN_CHARACTER_PLACEHOLDER_NAME, matchedCount);
            } else {
                characterSearchScrollPane.setViewportView(selectedCharacterPanel);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 3;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
                characterSearchScrollPane.updateUI();
                characterTab.add(characterSearchScrollPane, gbc);

            }
        } else {
            userFieldInput = devWeaponsTabSearchbar.getText().toLowerCase();
            devWeaponTabScrollPanePanel.removeAll();
            for (String s : weaponsFlattened) {
                if (s.toLowerCase().contains(userFieldInput)) {
                    if (!userFieldInput.isEmpty()) {

                        generateWeaponCard(s, matchedCount);
                    }
                    matchedCount++;
                }
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
        if (characterName.equalsIgnoreCase(UNKNOWN_CHARACTER_PLACEHOLDER_NAME)) {
            JLabel unknownCharacterLabel = new JLabel(generateCharacterIconPath(UNKNOWN_CHARACTER_PLACEHOLDER_NAME));
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
    private void setFont(Component c) {
        Font font = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 16, characterTabPane.getFont());
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

            if (!isTabAlreadyOpen(characterTabPane, characterName)) {
                JPanel characterPage = generateCharacterPage(characterCard);
                characterTabPane.addTab(characterName, characterPage);
                characterTabPane.setSelectedComponent(characterPage);
            } else {
                characterTabPane.setSelectedIndex(characterTabPane.indexOfTab(characterName));
            }
        });

        return characterButton;
    }

    /**
     * Verifies if a tab for the specified character already exists in the tabbed pane.
     *
     * @param tp the tabbed pane
     * @param charName the specified character
     * @return true if the tab exists, otherwise false.
     */

    public boolean isTabAlreadyOpen(JTabbedPane tp, String charName) {
        for (int i = 0; i < tp.getTabCount(); i++) {
            if (tp.getTitleAt(i).equalsIgnoreCase(charName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a path to the character icon.
     *
     * @param charName the character name whose icon path is to be returned
     * @return character icon path
     */
    public static String generateCharacterIconPath(String charName) {
        return "./Files/Images/Characters/" + charName + ".png";
    }

    /**
     * Generates a path to the artifact icon.
     *
     * @param artifactName the artifact set name whose icon is to be returned
     * @return artifact set icon path
     */
    public static String generateArtifactIconPath(String artifactName) {
        return "./Files/Images/Artifacts/" + artifactName + ".png";
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

    public String lookUpWeaponCategoryForCharacter(String charName) {
        for (String key : characterWeaponsMap.keySet()) {
            List<String> weapons = characterWeaponsMap.get(key);
            if (weapons.contains(charName)) {
                return key;
            }

        }
        return null;
    }

    /**
     * Grabs all weapons based on their rarity and type.
     *
     * @param rarity rarity of the weapon
     * @param weaponType type of the weapon
     * @return list of the weapons with the specified rarity.
     */
    public static List<String> lookUpWeapons(RARITY rarity, String weaponType) {

        final String FOUR_STAR_WEAPON_KEY = "Four-Star " + weaponType;
        final String FIVE_STAR_WEAPON_KEY = "Five-Star " + weaponType;

        return switch (rarity) {
            case FOUR_STAR -> weaponsRaritiesMap.get(FOUR_STAR_WEAPON_KEY);
            case FIVE_STAR -> weaponsRaritiesMap.get(FIVE_STAR_WEAPON_KEY);
        };
    }

    /**
     * Looks up the rarity and type of specified weapon.
     *
     * @param weaponName the weapon name
     * @return WeaponInfo object with rarity and type of the weapon.
     */
    public static WeaponInfo lookUpWeaponRarityAndType(String weaponName) {
        for (String key : weaponsRaritiesMap.keySet()) {
            List<String> weapons = weaponsRaritiesMap.get(key);
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
        return artifactSetDescriptionsMap.get(setName);
    }

    /**
     * Adds allowed weapons (that is, wieldable by the specified character) to the list of options in the weapon combobox.
     *
     * @param dcmb weapon selector combo box model used by the combo box
     * @param charName character name
     */
    private void addAllowedWeapons(WeaponSelectorComboBoxModel dcmb, String charName) {
        dcmb.addElement(UNKNOWN_WEAPON_MESSAGE);
        String weaponType = lookUpWeaponCategoryForCharacter(charName);
        dcmb.addElement(FOUR_STAR_WEAPON_DELIMITER);
        dcmb.addAll(lookUpWeapons(RARITY.FOUR_STAR, weaponType));
        dcmb.addElement(FIVE_STAR_WEAPON_DELIMITER);
        dcmb.addAll(lookUpWeapons(RARITY.FIVE_STAR, weaponType));
    }

    /**
     * Generates a path to the weapon icon. Necessary parameter information can be acquired by first executing lookUpWeaponRarityAndType method.
     *
     * @param weaponName the name of the weapon
     * @param weaponType the type of the weapon
     * @param rarity the rarity of the weapon
     * @return the address of the icon for the weapon
     */
    public static String generateWeaponPath(String weaponName, String weaponType, RARITY rarity) {
        return switch (rarity) {
            case FOUR_STAR -> "./Files/Images/Weapons/" + weaponType + "_4star/" + weaponName + ".png";
            case FIVE_STAR -> "./Files/Images/Weapons/" + weaponType + "_5star/" + weaponName + ".png";
        };
    }

    public static String generateWeaponPath(String weaponName) {
        WeaponInfo wi = lookUpWeaponRarityAndType(weaponName);
        return switch (wi.getRarity()) {
            case FOUR_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_4star/" + weaponName + ".png";
            case FIVE_STAR -> "./Files/Images/Weapons/" + wi.getWeaponType() + "_5star/" + weaponName + ".png";
        };
    }

    private JPanel getMiddleSelectorPanel(JPanel jpanel) {
        JPanel middleSelectorPanel = new JPanel();
        middleSelectorPanel.setLayout(new GridLayoutManager(13, 1, new Insets(5, 5, 5, 5), -1, -1));
        middleSelectorPanel.setAlignmentY(0.5f);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(22, 0, 0, 0);
        middleSelectorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jpanel.add(middleSelectorPanel, gbc);
        return middleSelectorPanel;
    }

    private void getNotesTextField(CharacterCard selectedCharacterCard, JPanel jpanel) {
        JTextField notesTextField = new JTextField();
        Font emptyNotesFieldTextFieldFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, notesTextField.getFont());
        if (emptyNotesFieldTextFieldFont != null) {
            notesTextField.setFont(emptyNotesFieldTextFieldFont);
        }
        notesTextField.setHorizontalAlignment(10);
        notesTextField.setInheritsPopupMenu(false);
        notesTextField.setMargin(new Insets(2, 6, 2, 6));
        notesTextField.setOpaque(true);
        notesTextField.setDocument(new NotesTextModel(CHARACTER_LIMIT, selectedCharacterCard.getCharacterNotes()));
        notesTextField.getDocument().addDocumentListener(new NotesListener(selectedCharacterCard));
        jpanel.add(notesTextField,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(50, -1), null, 0, false));
    }

    private void getCharacterNameLabel(String charName, JPanel jpanel) {
        JLabel characterNameLabel = new JLabel();
        characterNameLabel.setAutoscrolls(true);
        Font characterNameLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, characterNameLabel.getFont());
        if (characterNameLabelFont != null) {
            characterNameLabel.setFont(characterNameLabelFont);
        }
        characterNameLabel.setText(charName);
        jpanel.add(characterNameLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));
    }

    private JComboBox<String> getWeaponSelectionBox(CharacterCard characterCard, JPanel jpanel) {
        JComboBox<String> weaponSelectionBox = new JComboBox<>();
        weaponSelectionBox.setAutoscrolls(false);
        weaponSelectionBox.setEditable(false);
        Font weaponSelectionBoxFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, weaponSelectionBox.getFont());
        if (weaponSelectionBoxFont != null) {
            weaponSelectionBox.setFont(weaponSelectionBoxFont);
        }
        weaponSelectionBox.setInheritsPopupMenu(false);
        final WeaponSelectorComboBoxModel weaponSelectorComboBoxModel = new WeaponSelectorComboBoxModel();
        addAllowedWeapons(weaponSelectorComboBoxModel, characterCard.getCharacterName());
        weaponSelectionBox.setModel(weaponSelectorComboBoxModel);
        weaponSelectionBox.setSelectedItem(
                characterCard.getWeapon().isEmpty() ? UNKNOWN_WEAPON_MESSAGE : characterCard.getWeapon());
        jpanel.add(weaponSelectionBox,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        return weaponSelectionBox;
    }

    private JLabel getWeaponNameLabel(CharacterCard characterCard, JPanel jpanel) {
        JLabel weaponNameLabel = new JLabel();
        weaponNameLabel.setAutoscrolls(true);
        Font weaponNameLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, weaponNameLabel.getFont());
        if (weaponNameLabelFont != null) {
            weaponNameLabel.setFont(weaponNameLabelFont);
        }
        weaponNameLabel.setText(characterCard.getWeapon());
        jpanel.add(weaponNameLabel,
                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));
        return weaponNameLabel;
    }

    private JLabel getSet1NameLabel(CharacterCard characterCard, JPanel jpanel) {
        JLabel set1NameLabel = new JLabel();
        set1NameLabel.setAlignmentY(0.5f);
        set1NameLabel.setAutoscrolls(true);
        set1NameLabel.setDoubleBuffered(false);
        Font set1NameLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, set1NameLabel.getFont());
        if (set1NameLabelFont != null) {
            set1NameLabel.setFont(set1NameLabelFont);
        }
        set1NameLabel.setHorizontalAlignment(10);
        set1NameLabel.setHorizontalTextPosition(11);
        set1NameLabel.setInheritsPopupMenu(true);
        set1NameLabel.setText(characterCard.getArtifactSet1().isEmpty() ? "" : characterCard.getArtifactSet1());
        set1NameLabel.setVerticalAlignment(0);
        set1NameLabel.setVerticalTextPosition(0);
        set1NameLabel.setToolTipText(TOOLTIP_FOR_LABELS_WITHOUT_ICON);
        jpanel.add(set1NameLabel,
                new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));
        return set1NameLabel;
    }

    private JComboBox<String> getSet1ComboBox(CharacterCard characterCard, JPanel jpanel) {
        JComboBox<String> set1ComboBox = new JComboBox<>();
        set1ComboBox.setAutoscrolls(false);
        set1ComboBox.setEditable(false);
        set1ComboBox.setFocusable(false);
        Font set1comboBoxFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, set1ComboBox.getFont());
        if (set1comboBoxFont != null) {
            set1ComboBox.setFont(set1comboBoxFont);
        }
        set1ComboBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<String> defaultComboBoxModelForSet1Selector = new DefaultComboBoxModel<>();
        defaultComboBoxModelForSet1Selector.addElement(UNKNOWN_SET_MESSAGE);
        defaultComboBoxModelForSet1Selector.addAll(artifactsFlattened);
        set1ComboBox.setModel(defaultComboBoxModelForSet1Selector);
        set1ComboBox.setSelectedItem(
                characterCard.getArtifactSet1().isEmpty() ? UNKNOWN_SET_MESSAGE : characterCard.getArtifactSet1());
        jpanel.add(set1ComboBox,
                new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        return set1ComboBox;
    }

    private JComboBox<String> getSet2ComboBox(CharacterCard characterCard, JPanel jpanel) {
        JComboBox<String> set2ComboBox = new JComboBox<>();
        set2ComboBox.setAutoscrolls(false);
        set2ComboBox.setEditable(false);
        set2ComboBox.setFocusable(false);
        Font set2ComboBoxFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, set2ComboBox.getFont());
        if (set2ComboBoxFont != null) {
            set2ComboBox.setFont(set2ComboBoxFont);
        }
        set2ComboBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<String> defaultComboBoxModelForSet2Selector = new DefaultComboBoxModel<>();
        defaultComboBoxModelForSet2Selector.addElement(UNKNOWN_SET_MESSAGE);
        defaultComboBoxModelForSet2Selector.addAll(artifactsFlattened);
        set2ComboBox.setModel(defaultComboBoxModelForSet2Selector);
        set2ComboBox.setSelectedItem(
                characterCard.getArtifactSet2().isEmpty() ? UNKNOWN_SET_MESSAGE : characterCard.getArtifactSet2());
        jpanel.add(set2ComboBox,
                new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        return set2ComboBox;
    }

    private JLabel getSet2NameLabel(CharacterCard characterCard, JPanel jpanel) {
        JLabel set2NameLabel = new JLabel();
        set2NameLabel.setAutoscrolls(true);
        Font set2NameLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, set2NameLabel.getFont());
        if (set2NameLabelFont != null) {
            set2NameLabel.setFont(set2NameLabelFont);
        }
        set2NameLabel.setToolTipText(TOOLTIP_FOR_LABELS_WITHOUT_ICON);
        set2NameLabel.setText(characterCard.getArtifactSet2());
        jpanel.add(set2NameLabel,
                new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));
        return set2NameLabel;
    }

    private void getCharacterWeaponSpacer(JPanel jpanel) {
        JPanel characterWeaponSpacer = new JPanel();
        characterWeaponSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 40, 0), -1, -1));
        jpanel.add(characterWeaponSpacer,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
    }

    private void getWeaponSet1Spacer(JPanel jpanel) {
        JPanel weaponSet1Spacer = new JPanel();
        weaponSet1Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(48, 0, 0, 0), -1, -1));
        jpanel.add(weaponSet1Spacer,
                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
    }

    private void getSet2Spacer(JPanel jpanel) {
        JPanel secondSetSpacer = new JPanel();
        secondSetSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(48, 0, 0, 0), -1, -1));
        jpanel.add(secondSetSpacer,
                new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
    }

    private void getYetAnotherSpacerOnTheBottom(JPanel jpanel) {
        JPanel bottomSpacer = new JPanel();
        bottomSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(40, 0, 20, 0), -1, -1));
        jpanel.add(bottomSpacer,
                new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
    }

    private JLabel getWeaponIcon(CharacterCard characterCard, JPanel jpanel) {
        JLabel weaponIcon = new JLabel();
        weaponIcon.setHorizontalAlignment(4);
        weaponIcon.setHorizontalTextPosition(4);
        String savedWeaponName = characterCard.getWeapon();
        if (savedWeaponName.isEmpty()) {
            weaponIcon.setIcon(new ImageIcon(UNKNOWN_WEAPON_PATH));
        } else {
            WeaponInfo weaponInfo = lookUpWeaponRarityAndType(savedWeaponName);
            weaponIcon.setIcon(new ImageIcon(generateWeaponPath(savedWeaponName, weaponInfo.getWeaponType(), weaponInfo.getRarity())));
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        jpanel.add(weaponIcon, gbc);
        weaponIcon.setToolTipText(TOOLTIP_FOR_WEAPON_ICON_LABEL);
        return weaponIcon;
    }

    private void getCharLabel(Icon charIcon, JPanel jpanel) {
        JLabel charLabel = new JLabel();
        charLabel.setIcon(charIcon);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 0, 5, 20);
        jpanel.add(charLabel, gbc);
    }

    private JLabel getSet1Icon(CharacterCard characterCard, JPanel jpanel) {
        JLabel set1Icon = new JLabel();
        set1Icon.setToolTipText(TOOLTIP_FOR_LABELS_WITH_ICON);
        set1Icon.setHorizontalAlignment(4);
        set1Icon.setHorizontalTextPosition(4);
        String savedArtifactSet1Name = characterCard.getArtifactSet1();
        if (savedArtifactSet1Name.isEmpty()) {
            set1Icon.setIcon(new ImageIcon(generateArtifactIconPath(UNKNOWN_ARTIFACT)));
        } else {
            set1Icon.setIcon(new ImageIcon(generateArtifactIconPath(savedArtifactSet1Name)));
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        jpanel.add(set1Icon, gbc);
        return set1Icon;
    }

    private JLabel getSet2Icon(CharacterCard characterCard, JPanel jpanel) {
        JLabel set2Icon = new JLabel();
        set2Icon.setToolTipText(TOOLTIP_FOR_LABELS_WITH_ICON);
        set2Icon.setHorizontalAlignment(4);
        set2Icon.setHorizontalTextPosition(4);
        String savedArtifactSet2Name = characterCard.getArtifactSet2();
        if (savedArtifactSet2Name.isEmpty()) {
            set2Icon.setIcon(new ImageIcon(generateArtifactIconPath(UNKNOWN_ARTIFACT)));
        } else {
            set2Icon.setIcon(new ImageIcon(generateArtifactIconPath(savedArtifactSet2Name)));
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        jpanel.add(set2Icon, gbc);
        return set2Icon;
    }

    private JPanel getCheckboxAndButtonPanel(JPanel jpanel) {
        JPanel checkboxAndButtonPanel = new JPanel();
        checkboxAndButtonPanel.setLayout(new GridLayoutManager(6, 2, new Insets(3, 3, 3, 3), -1, -1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(22, 0, 0, 100);
        jpanel.add(checkboxAndButtonPanel, gbc);
        checkboxAndButtonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        return checkboxAndButtonPanel;
    }

    private JCheckBox getArtifactListingCheckBox(CharacterCard characterCard, JPanel jpanel) {
        JCheckBox artifactListingCheckBox = new JCheckBox();
        Font artifactListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, artifactListingCheckBox.getFont());
        if (artifactListingCheckBoxFont != null) {
            artifactListingCheckBox.setFont(artifactListingCheckBoxFont);
        }
        artifactListingCheckBox.setText("Artifact Listing");
        if (characterCard.getArtifactSet1Status() || characterCard.getArtifactSet2Status()) {
            artifactListingCheckBox.doClick();
        }
        jpanel.add(artifactListingCheckBox,
                new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return artifactListingCheckBox;
    }

    private JCheckBox getTalentListingCheckBox(CharacterCard characterCard, JPanel jpanel) {
        JCheckBox talentListingCheckBox = new JCheckBox();
        Font talentListingCheckBoxFont = this.$$$getFont$$$("Source Code Pro", -1, 14, talentListingCheckBox.getFont());
        if (talentListingCheckBoxFont != null) {
            talentListingCheckBox.setFont(talentListingCheckBoxFont);
        }
        talentListingCheckBox.setText("Talent Listing");
        if (characterCard.getTalentStatus()) {
            talentListingCheckBox.doClick();
        }
        jpanel.add(talentListingCheckBox,
                new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return talentListingCheckBox;
    }

    private void getDomainListingsLabel(JPanel jpanel) {
        JLabel domainListingsLabel = new JLabel();
        Font domainListingsLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, domainListingsLabel.getFont());
        if (domainListingsLabelFont != null) {
            domainListingsLabel.setFont(domainListingsLabelFont);
        }
        domainListingsLabel.setText("Domain listings");
        jpanel.add(domainListingsLabel,
                new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
    }

    private JCheckBox getWeaponMaterialListingCheckbox(CharacterCard characterCard, JPanel jpanel) {
        JCheckBox weaponMaterialListingCheckbox = new JCheckBox();
        Font weaponMaterialListingCheckboxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, weaponMaterialListingCheckbox.getFont());
        if (weaponMaterialListingCheckboxFont != null) {
            weaponMaterialListingCheckbox.setFont(weaponMaterialListingCheckboxFont);
        }
        weaponMaterialListingCheckbox.setText("Weapon Material Listing");
        if (characterCard.getWeaponStatus()) {
            weaponMaterialListingCheckbox.doClick();
        }
        jpanel.add(weaponMaterialListingCheckbox,
                new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return weaponMaterialListingCheckbox;
    }

    private JButton getCloseButton(JPanel jpanel) {
        JButton closeButton = new JButton();
        closeButton.setBackground(new Color(-2725532));
        Font closeButtonFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, closeButton.getFont());
        if (closeButtonFont != null) {
            closeButton.setFont(closeButtonFont);
        }
        closeButton.setForeground(new Color(-6427));
        closeButton.setHideActionText(false);
        closeButton.setText(CLOSE_TEXT);
        jpanel.add(closeButton,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return closeButton;
    }

    private JButton getSaveButton(JPanel jpanel) {
        JButton saveButton = new JButton();
        saveButton.setBackground(new Color(-6039919));
        saveButton.setForeground(new Color(-394241));
        saveButton.setText("SAVE");
        jpanel.add(saveButton,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return saveButton;
    }

    private JTextArea getSetDetailsTextArea(JPanel jpanel) {
        JTextArea setDetailsTextArea = new JTextArea();
        setDetailsTextArea.setEditable(false);
        setDetailsTextArea.setFocusable(false);
        setDetailsTextArea.setLineWrap(true);
        setDetailsTextArea.setWrapStyleWord(true);
        Font setDetailsTextAreaFont = this.$$$getFont$$$("Source Code Pro", -1, 12, setDetailsTextArea.getFont());
        if (setDetailsTextAreaFont != null) {
            setDetailsTextArea.setFont(setDetailsTextAreaFont);
        }
        setDetailsTextArea.setText("[ Set Details ]");
        jpanel.add(setDetailsTextArea,
                new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW,
                        new Dimension(150, 280), new Dimension(150, 280), null, 0, false));
        return setDetailsTextArea;
    }

    /**
     * Generates a character page (in the tabbed pane).
     *
     * @param characterCard the character card
     * @return the JPanel that is the character page.
     */
    private JPanel generateCharacterPage(CharacterCard characterCard) {
        JPanel templateTab = new JPanel();
        assert characterCard != null;
        templateTab.setLayout(new GridBagLayout());
        JPanel middleSelectorPanel = getMiddleSelectorPanel(templateTab);
        getNotesTextField(characterCard, middleSelectorPanel);
        getCharacterNameLabel(characterCard.getCharacterName(), middleSelectorPanel);
        JComboBox<String> weaponSelectionBox = getWeaponSelectionBox(characterCard, middleSelectorPanel);
        JLabel weaponNameLabel = getWeaponNameLabel(characterCard, middleSelectorPanel);
        JLabel set1NameLabel = getSet1NameLabel(characterCard, middleSelectorPanel);
        JComboBox<String> set1ComboBox = getSet1ComboBox(characterCard, middleSelectorPanel);
        JComboBox<String> set2ComboBox = getSet2ComboBox(characterCard, middleSelectorPanel);
        JLabel set2NameLabel = getSet2NameLabel(characterCard, middleSelectorPanel);

        getCharacterWeaponSpacer(middleSelectorPanel);
        getWeaponSet1Spacer(middleSelectorPanel);
        getSet2Spacer(middleSelectorPanel);
        getYetAnotherSpacerOnTheBottom(middleSelectorPanel);

        JLabel weaponIconLabel = getWeaponIcon(characterCard, templateTab);
        weaponSelectionBox.addActionListener(new UpdateLabelListener(weaponNameLabel, weaponIconLabel, ToolData.SELECTION_BOX_TYPE.WEAPON));
        weaponSelectionBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON));
        weaponNameLabel.setToolTipText(TOOLTIP_FOR_WEAPON_NAME_LABEL);
        getCharLabel(characterCard.getCharacterIcon(), templateTab);
        JLabel set1Icon = getSet1Icon(characterCard, templateTab);
        JLabel set2Icon = getSet2Icon(characterCard, templateTab);

        set1ComboBox.addActionListener(new UpdateLabelListener(set1NameLabel, set1Icon, ToolData.SELECTION_BOX_TYPE.ARTIFACT));
        set2ComboBox.addActionListener(new UpdateLabelListener(set2NameLabel, set2Icon, ToolData.SELECTION_BOX_TYPE.ARTIFACT));
        set1ComboBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE));
        set2ComboBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO));

        JPanel checkboxAndButtonPanel = getCheckboxAndButtonPanel(templateTab);
        JCheckBox artifactListingCheckBox = getArtifactListingCheckBox(characterCard, checkboxAndButtonPanel);
        JCheckBox talentListingCheckBox = getTalentListingCheckBox(characterCard, checkboxAndButtonPanel);
        getDomainListingsLabel(checkboxAndButtonPanel);
        JCheckBox weaponMaterialListingCheckbox = getWeaponMaterialListingCheckbox(characterCard, checkboxAndButtonPanel);

        artifactListingCheckBox.addItemListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_ONE));
        artifactListingCheckBox.addItemListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_TWO));
        weaponMaterialListingCheckbox.addItemListener(new UpdateCharacterCardListener(characterCard,
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_WEAPON_MATERIALS));
        talentListingCheckBox.addItemListener(new UpdateCharacterCardListener(characterCard,
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_TALENT_MATERIALS));

        JButton closeButton = getCloseButton(checkboxAndButtonPanel);
        JButton saveButton = getSaveButton(checkboxAndButtonPanel);
        JTextArea setDetailsTextArea = getSetDetailsTextArea(checkboxAndButtonPanel);

        closeButton.addActionListener(new CloseButtonListener(characterTabPane));
        saveButton.addActionListener(new SaveButtonListener(characterCard));
        set1ComboBox.addActionListener(new UpdateTextAreaListener(setDetailsTextArea));
        set2ComboBox.addActionListener(new UpdateTextAreaListener(setDetailsTextArea));

        return templateTab;
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
        Font devWeaponIconFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWeaponIcon.getFont());
        if (devWeaponIconFont != null) {
            devWeaponIcon.setFont(devWeaponIconFont);
        }
        devWeaponIcon.setHorizontalAlignment(0);
        devWeaponIcon.setHorizontalTextPosition(0);
        devWeaponIcon.setIcon(new ImageIcon(generateWeaponPath(weaponName)));
        devWeaponIcon.setText(formatString(weaponName));
        devWeaponIcon.setVerticalAlignment(0);
        devWeaponIcon.setVerticalTextPosition(3);
        devWeaponCard.add(devWeaponIcon,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JCheckBox devWepMatListingCheckbox = new JCheckBox();
        devWepMatListingCheckbox.setBackground(new Color(-1));
        devWepMatListingCheckbox.setText("Weapon Listing");
        devWeaponCard.add(devWepMatListingCheckbox,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JLabel devWepMaterialPreview = new JLabel();
        Font devWepMaterialPreviewFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWepMaterialPreview.getFont());
        if (devWepMaterialPreviewFont != null) {
            devWepMaterialPreview.setFont(devWepMaterialPreviewFont);
        }
        devWepMaterialPreview.setHorizontalAlignment(0);
        devWepMaterialPreview.setHorizontalTextPosition(0);
        devWepMaterialPreview.setIcon(new ImageIcon(UNKNOWN_WEAPON_PATH));
        devWepMaterialPreview.setText("");
        devWepMaterialPreview.setVerticalAlignment(0);
        devWepMaterialPreview.setVerticalTextPosition(3);
        devWeaponCard.add(devWepMaterialPreview,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JLabel devWepTypeLabel = new JLabel();
        Font devWepTypeLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWepTypeLabel.getFont());
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
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        weaponsTab.add(devWeaponTabPanel, gbc);
        JScrollPane devWeaponTabScrollPane = new JScrollPane();
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
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 18, devWeaponsTabSearchbar.getFont());
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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        weaponsTab.add(devWeaponsTabSearchbar, gbc);

        devWeaponTabSearchButton.setMaximumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setMinimumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setPreferredSize(new Dimension(50, 30));
        devWeaponTabSearchButton.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        weaponsTab.add(devWeaponTabSearchButton, gbc);
        JButton devSaveAllWeapons = new JButton();
        devSaveAllWeapons.setBackground(new Color(-6039919));
        Font devSaveAllWeaponsFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devSaveAllWeapons.getFont());
        if (devSaveAllWeaponsFont != null) {
            devSaveAllWeapons.setFont(devSaveAllWeaponsFont);
        }
        devSaveAllWeapons.setForeground(new Color(-394241));
        devSaveAllWeapons.setText("SAVE all weapons");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 0, 0);
        weaponsTab.add(devSaveAllWeapons, gbc);
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
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-2702645));
        panel1.setEnabled(true);
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
                        GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        titleLabel = new JLabel();
        Font titleLabelFont = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, titleLabel.getFont());
        if (titleLabelFont != null) {
            titleLabel.setFont(titleLabelFont);
        }
        titleLabel.setForeground(new Color(-14940151));
        titleLabel.setText("Genshin Domain Application");
        panel1.add(titleLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainInformationPanel = new JPanel();
        mainInformationPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainInformationPanel.setBackground(new Color(-3689540));
        panel1.add(mainInformationPanel,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
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
        GridBagConstraints gbc;
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
        return panel1;
    }

}

