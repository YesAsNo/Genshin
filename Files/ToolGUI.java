package Files;

import static Files.ToolData.SAVE_LOCATION;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.Component;
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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ToolGUI extends JFrame {

    public static final String NO_CHARACTERS_MATCH_MESSAGE = "No fighters >:(";
    public static final String UNKNOWN_CHARACTER = "unknown_character";
    public static final int MAX_CHARACTERS_PER_LINE = 15;
    public static final String UNKNOWN_SET_MESSAGE = "[ Empty Set Selector ]";
    public static final String FOUR_STAR_WEAPON_DELIMITER = "[ 4-Star Weapons ]";
    public static final String FIVE_STAR_WEAPON_DELIMITER = "[ 5-Star Weapons ]";
    public static final String UNKNOWN_ARTIFACT = "unknown_artifact";
    public static final String UNKNOWN_WEAPON_MESSAGE = "[ Empty Weapon Selector ]";
    public static final String UNKNOWN_WEAPON_PATH = "./Files/Images/Weapons/unknown_weapon.png";
    public static final String CLOSE_TEXT = "CLOSE";
    public static final int CHARACTER_LIMIT = 150;
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
    private JPanel devDomainsTab;
    private JPanel devDomainsTabPanel;
    private JScrollPane devDomainsScrollPane;
    private JPanel devDomainCardViewport;
    private JButton domainSearchButton;
    private JComboBox<String> domainFilterBox;
    private static Map<String, Set<String>> farmedWeapons = new TreeMap<>();
    public static Map<String, Set<String>> farmedArtifacts = new TreeMap<>();
    private static final List<CharacterCard> generatedCharacterCards = new ArrayList<>();
    private static final CharacterTabGUI _characterTabGUI = new CharacterTabGUI();
    private static final WeaponTabGUI _weaponsTabGUI = new WeaponTabGUI();

    /**
     * Constructor of the GUI class.
     */

    public ToolGUI() {
        $$$setupUI$$$();
        addTab("Characters", _characterTabGUI.getMainPanel());
        addTab("Weapons", _weaponsTabGUI.getMainPanel());
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
        mainPanel = new JPanel();
        readGeneratedCharacterCards();
        parseCharacterWeapons();
        parseFarmedWeapons();
        parseCharacterArtifacts();
    }
    private void parseFarmedWeapons(){
        Gson gson = new Gson();
        File f = new File(SAVE_LOCATION + WEAPON_SAVE_FILE_NAME);
        if (!f.exists()){
            return;
        }
        try {
            JsonReader reader = new JsonReader(new FileReader(f));
            farmedWeapons = gson.fromJson(reader, new TypeToken<TreeMap<String, Set<String>>>() {}.getClass());
            if (farmedWeapons == null){
                farmedWeapons = new TreeMap<>();
            }
        }
        catch(IOException ex){
            System.out.println("The weapon save file failed to parse.");
        }
    }

    public void addDomainCardToViewport(JPanel jPanel, GridBagConstraints gbc) {
        devDomainCardViewport.add(jPanel, gbc);
    }

    public void addTab(String title, JPanel jpanel) {
        mainTabbedPane.addTab(title, jpanel);
    }

    public static void addCharacterCard(CharacterCard characterCard) {
        generatedCharacterCards.add(characterCard);
    }

    public static Map<String, Set<String>> getFarmedMapping(ToolData.RESOURCE_TYPE rt) {
        assert rt != null;
        if (rt == ToolData.RESOURCE_TYPE.ARTIFACT) {
            return farmedArtifacts;
        } else {
            return farmedWeapons;
        }
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
        if (farmedWeapons.isEmpty()) {
            System.out.println("1234");
        }
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
    public static void serializeSave(){
        Gson gson = new Gson();
        File f = new File(SAVE_LOCATION + WEAPON_SAVE_FILE_NAME);
        try{
            f.createNewFile();
            FileWriter fd = new FileWriter(f);
            gson.toJson(farmedWeapons,fd);
            fd.flush();
            fd.close();
        }
        catch(IOException ex){
            System.out.println("Failed to save weapons");
        }
    }
    /**
     * Sets the hardcoded font for the specified component.
     *
     * @param c component whose font is to be changed
     */
    public void setFont(Component c) {
        Font font = $$$getFont$$$("Source Code Pro Black", Font.BOLD, 16, mainTabbedPane.getFont());
        if (font != null) {
            c.setFont(font);
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
        mainTabbedPane = new JTabbedPane();
        Font mainTabbedPaneFont = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, mainTabbedPane.getFont());
        if (mainTabbedPaneFont != null) {
            mainTabbedPane.setFont(mainTabbedPaneFont);
        }
        mainTabbedPane.setTabPlacement(1);
        mainInformationPanel.add(mainTabbedPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
                        new Dimension(200, 200), null, 0, false));
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
        devDomainsTab = new JPanel();
        devDomainsTab.setLayout(new GridBagLayout());
        devDomainsTab.setBackground(new Color(-1));
        mainTabbedPane.addTab("Domains", devDomainsTab);
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

