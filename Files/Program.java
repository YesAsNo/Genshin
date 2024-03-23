package Files;

import static Files.ToolData.flattenedDataCategory;
import static Files.ToolData.getFlattenedData;

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
import javax.swing.JTextPane;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Objects;

public class Program extends JFrame implements ActionListener {
    public Program() {
        $$$setupUI$$$();
        setContentPane(devMainPanel);
        setTitle("Genshin Domain App!");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel devMainPanel;
    public static final String NO_CHARACTERS_MATCH_MESSAGE = "No fighters >:(";
    public static final String UNKNOWN_CHARACTER_PLACEHOLDER_NAME = "unknown_character";
    public static final int MAX_CHARACTERS_PER_LINE = 12;
    private JTabbedPane devTabbedPane;
    private JPanel devPanelWithMainTabbedPane;
    private JPanel devCharacterTab;
    private JPanel devWeaponTab;
    private JTextField devSearchField;
    private JButton devSearchButton;
    private JPanel devSearchResultPanel;
    private JTextField devNotesTextField;
    private JComboBox<String> devSet1SelectionBox;
    private JCheckBox devArtifact1ListingCheckBox;
    private JCheckBox devTalentListingCheckBox;
    private JButton devCloseButton;
    private JButton devSaveButton;
    private JPanel devBottomSpacer;
    private JPanel devSet1Set2Spacer;
    private JPanel devWeaponSet1Spacer;
    private JPanel devTopSpacer;
    private JTextArea devSetDetailsTextArea;
    private JTextField devWeaponsTabSearchbar;
    private JButton devWeaponTabSearchButton;
    private JPanel devWeaponTabPanel;
    private JPanel devCharacterCardMainPanel;
    private JPanel devCharacterCardSelectorPanel;
    private JLabel devCharacterNameLabel;
    private JLabel devWeaponNameLabel;
    private JLabel devSet1NameLabel;
    private JComboBox<String> devSet2SelectionBox;
    private JLabel devSet2NameLabel;
    private JLabel devWeaponIconLabel;
    private JLabel devCharacterIconLabel;
    private JLabel devSet1IconLabel;
    private JLabel devSet2IconLabel;
    private JPanel devButtonAndCheckboxPanel;
    private JLabel devDomainListingsLabel;
    private JCheckBox devWepMatListingLabel;
    private JComboBox<String> devWeaponSelectorBox;
    private JPanel devWeaponCard;
    private JLabel devWeaponIcon;
    private JCheckBox devWepMatListingCheckbox;
    private JLabel devWepMaterialPreview;
    private JLabel devWepTypeLabel;
    private JButton devSaveAllWeapons;
    private JScrollPane devWeaponTabScrollPane;
    private JPanel devWeaponTabScrollPanePanel;
    private JComboBox<String> devFilterComboBox;
    private JPanel devWelcomeTab;
    private JPanel devDomainsTab;
    private JPanel devDomainsTabPanel;
    private JScrollPane devDomainsScrollPane;
    private JPanel devDomainsPanelForEverything;
    private JCheckBox devArtifact2ListingCheckBox;
    private JLabel Welcome_Barbara;
    private JTextPane devInfoTextPane;
    private JTextPane devUpdatesTextPane;
    private JPanel devBasicInfoPanel;
    private JPanel devBasicInfoLeftPanel;
    private JLabel devCreatorsLabel;
    private JLabel devLinakoLabel;
    private JLabel devPrecisi0nLabel;
    private JPanel devBasicInfoRightPanel;
    private JLabel devWelcomeLabel;
    private JPanel devBasicInfoSpacer;
    private JPanel devWeaponMatDomain;
    private JPanel weaponMatDomainPanel;
    private JPanel talentBookMatDomain;
    private JPanel artifactDomainPanel;
    private JPanel weeklyBossDomainPanel;
    private JPanel infoPanel;
    private JPanel infoMarginPanel;
    private JCheckBox devShowListedCheckBox;
    private JCheckBox devShowUnlistedCheckBox;
    private JLabel devShowMatchedAmountLabel;
    private JScrollPane scrollPane1;

    /**
     * Creates a few custom UI components by hand.
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        devSearchResultPanel = new JPanel(new GridBagLayout());
        scrollPane1 = new JScrollPane();
        devMainPanel = new JPanel();
        devSearchButton = new JButton("✓");
        devSearchButton.addActionListener(this);
        devSearchField = new JTextField();
        devSearchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                devSearchField.setText("");
            }
        });
    }

    /**
     * Listener for the checkButton.
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        boolean matched = false;
        int matchedCount = 0;
        devSearchResultPanel.removeAll();

        String userFieldInput = devSearchField.getText().toLowerCase();

        for (String s : getFlattenedData(flattenedDataCategory.CHARACTER)) {
            if (s.toLowerCase().contains(userFieldInput)) {
                matched = true;
                generateCharacterButton(s, matchedCount);
                matchedCount++;

            }
        }
        if (!matched) {
            generateCharacterButton(UNKNOWN_CHARACTER_PLACEHOLDER_NAME, matchedCount);
        } else {
            scrollPane1.setViewportView(devSearchResultPanel);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            scrollPane1.updateUI();
            devCharacterTab.add(scrollPane1, gbc);
        }
    }

    /**
     * Generates a character button for the character specified by name and the count of it.
     *
     * @param characterName the name of the character
     * @param number which character by count it is
     */
    private void generateCharacterButton(String characterName, int number) {
        String characterIconPath = generateCharacterIconPath(characterName);
        Icon characterIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(characterIconPath)));
        if (Objects.equals(characterName, UNKNOWN_CHARACTER_PLACEHOLDER_NAME)) {
            JLabel unknownCharacterLabel = new JLabel(characterIcon);
            unknownCharacterLabel.setText(NO_CHARACTERS_MATCH_MESSAGE);
            unknownCharacterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            unknownCharacterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            setFont(unknownCharacterLabel);
            devSearchResultPanel.add(unknownCharacterLabel);
            devSearchResultPanel.updateUI();
            return;
        }
        JButton characterButton = getjButton(characterName, characterIcon);

        addCharacterButtonToSelectedCharacterPanel(characterButton, number);

    }

    /**
     * Sets the hardcoded font for the specified component.
     *
     * @param c component whose font is to be changed
     */
    private void setFont(Component c) {
        Font font = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 16, devTabbedPane.getFont());
        if (font != null) {
            c.setFont(font);
        }
    }

    /**
     * Creates a JButton for the specified character.
     *
     * @param characterName the name of the character
     * @param characterIcon the icon of the character
     * @return JButton for the character.
     */
    private JButton getjButton(String characterName, Icon characterIcon) {
        JButton characterButton = new JButton();

        characterButton.setIcon(characterIcon);
        if (characterName.length() > MAX_CHARACTERS_PER_LINE) {
            characterButton.setText(removeWhitespace(characterName));
        } else {
            characterButton.setText(characterName);
        }
        characterButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        characterButton.setHorizontalTextPosition(SwingConstants.CENTER);
        setFont(characterButton);
        characterButton.setOpaque(false);
        characterButton.setBorderPainted(false);
        characterButton.setContentAreaFilled(false);
        /*characterButton.addActionListener(e -> {
            if (_openTabs.add(characterName)) {
                JPanel characterPage = generateCharacterPage(characterName, characterIcon);
                devTabbedPane.addTab(characterName, characterPage);
                devTabbedPane.setSelectedComponent(characterPage);
            } else {
                devTabbedPane.setSelectedIndex(devTabbedPane.indexOfTab(characterName));
            }
        });*/
        return characterButton;
    }

    /**
     * Generates a path to the character icon.
     *
     * @param charName the character name whose icon path is to be given back
     * @return character icon path
     */
    private String generateCharacterIconPath(String charName) {
        return "/Files/Images/Characters/" + charName + ".png";
    }

    /**
     * Adds a character button to the selected character panel (after triggering actionPerformed)
     *
     * @param charButton the button to add
     * @param number which character by count to be added
     */
    private void addCharacterButtonToSelectedCharacterPanel(JButton charButton, int number) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = number % 6;
        gbc.gridy = (number - gbc.gridx) / 6;
        devSearchResultPanel.add(charButton, gbc);
        devSearchResultPanel.updateUI();

    }

    /**
     * Removes whitespace from the name of the character, adds line break in its place and centers the name.
     *
     * @param name the character name to be changed
     * @return edited string
     */
    String removeWhitespace(String name) {
        return "<html><center>" + name.replace(" ", "<br>") + "</center></html>";

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
        devMainPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        devMainPanel.setBackground(new Color(-465419));
        devMainPanel.setEnabled(true);
        final Spacer spacer1 = new Spacer();
        devMainPanel.add(spacer1,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
                        GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        devPanelWithMainTabbedPane = new JPanel();
        devPanelWithMainTabbedPane.setLayout(new GridBagLayout());
        devPanelWithMainTabbedPane.setBackground(new Color(-468502));
        devMainPanel.add(devPanelWithMainTabbedPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devTabbedPane = new JTabbedPane();
        Font devTabbedPaneFont = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, devTabbedPane.getFont());
        if (devTabbedPaneFont != null) {
            devTabbedPane.setFont(devTabbedPaneFont);
        }
        devTabbedPane.setTabPlacement(1);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devPanelWithMainTabbedPane.add(devTabbedPane, gbc);
        devWelcomeTab = new JPanel();
        devWelcomeTab.setLayout(new GridBagLayout());
        devWelcomeTab.setBackground(new Color(-1));
        devWelcomeTab.setEnabled(true);
        devWelcomeTab.setFocusCycleRoot(false);
        Font devWelcomeTabFont = this.$$$getFont$$$(null, -1, -1, devWelcomeTab.getFont());
        if (devWelcomeTabFont != null) {
            devWelcomeTab.setFont(devWelcomeTabFont);
        }
        devWelcomeTab.setOpaque(true);
        devWelcomeTab.setRequestFocusEnabled(true);
        devTabbedPane.addTab("Basic Info", devWelcomeTab);
        devBasicInfoPanel = new JPanel();
        devBasicInfoPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devWelcomeTab.add(devBasicInfoPanel, gbc);
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
        devCharacterTab = new JPanel();
        devCharacterTab.setLayout(new GridBagLayout());
        devCharacterTab.setBackground(new Color(-1));
        devCharacterTab.setEnabled(true);
        devCharacterTab.setFocusCycleRoot(false);
        Font devCharacterTabFont = this.$$$getFont$$$(null, -1, -1, devCharacterTab.getFont());
        if (devCharacterTabFont != null) {
            devCharacterTab.setFont(devCharacterTabFont);
        }
        devCharacterTab.setOpaque(true);
        devCharacterTab.setRequestFocusEnabled(true);
        devTabbedPane.addTab("Characters", devCharacterTab);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devCharacterTab.add(devSearchResultPanel, gbc);
        devSearchButton.setMaximumSize(new Dimension(30, 30));
        devSearchButton.setMinimumSize(new Dimension(30, 30));
        devSearchButton.setPreferredSize(new Dimension(50, 30));
        devSearchButton.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        devCharacterTab.add(devSearchButton, gbc);
        devSearchField.setEnabled(true);
        Font devSearchFieldFont = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 18, devSearchField.getFont());
        if (devSearchFieldFont != null) {
            devSearchField.setFont(devSearchFieldFont);
        }
        devSearchField.setInheritsPopupMenu(false);
        devSearchField.setMaximumSize(new Dimension(240, 33));
        devSearchField.setMinimumSize(new Dimension(240, 33));
        devSearchField.setPreferredSize(new Dimension(240, 33));
        devSearchField.setText("Choose your fighter!");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 250, 0, 0);
        devCharacterTab.add(devSearchField, gbc);
        devWeaponTab = new JPanel();
        devWeaponTab.setLayout(new GridBagLayout());
        devWeaponTab.setBackground(new Color(-1));
        devTabbedPane.addTab("Weapons", devWeaponTab);
        devWeaponTabPanel = new JPanel();
        devWeaponTabPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devWeaponTab.add(devWeaponTabPanel, gbc);
        devWeaponTabScrollPane = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devWeaponTabPanel.add(devWeaponTabScrollPane, gbc);
        devWeaponTabScrollPanePanel = new JPanel();
        devWeaponTabScrollPanePanel.setLayout(new GridBagLayout());
        devWeaponTabScrollPane.setViewportView(devWeaponTabScrollPanePanel);
        devWeaponCard = new JPanel();
        devWeaponCard.setLayout(new GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        devWeaponCard.setBackground(new Color(-1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        devWeaponTabScrollPanePanel.add(devWeaponCard, gbc);
        devWeaponCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        devWeaponIcon = new JLabel();
        Font devWeaponIconFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWeaponIcon.getFont());
        if (devWeaponIconFont != null) {
            devWeaponIcon.setFont(devWeaponIconFont);
        }
        devWeaponIcon.setHorizontalAlignment(0);
        devWeaponIcon.setHorizontalTextPosition(0);
        devWeaponIcon.setIcon(
                new ImageIcon(getClass().getResource("/Files/Images/Weapons/Bow_5star/Elegy for the End.png")));
        devWeaponIcon.setText("[ Weapon Name ]");
        devWeaponIcon.setVerticalAlignment(0);
        devWeaponIcon.setVerticalTextPosition(3);
        devWeaponCard.add(devWeaponIcon,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devWepMatListingCheckbox = new JCheckBox();
        devWepMatListingCheckbox.setBackground(new Color(-1));
        devWepMatListingCheckbox.setText("Weapon Listing");
        devWeaponCard.add(devWepMatListingCheckbox,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devWepMaterialPreview = new JLabel();
        Font devWepMaterialPreviewFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWepMaterialPreview.getFont());
        if (devWepMaterialPreviewFont != null) {
            devWepMaterialPreview.setFont(devWepMaterialPreviewFont);
        }
        devWepMaterialPreview.setHorizontalAlignment(0);
        devWepMaterialPreview.setHorizontalTextPosition(0);
        devWepMaterialPreview.setIcon(
                new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/All Aerosiderite.png")));
        devWepMaterialPreview.setText("");
        devWepMaterialPreview.setVerticalAlignment(0);
        devWepMaterialPreview.setVerticalTextPosition(3);
        devWeaponCard.add(devWepMaterialPreview,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devWepTypeLabel = new JLabel();
        Font devWepTypeLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWepTypeLabel.getFont());
        if (devWepTypeLabelFont != null) {
            devWepTypeLabel.setFont(devWepTypeLabelFont);
        }
        devWepTypeLabel.setText("Type: Bow ");
        devWeaponCard.add(devWepTypeLabel,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devWeaponsTabSearchbar = new JTextField();
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
        devWeaponsTabSearchbar.setText("Search by name!");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 0, 0);
        devWeaponTab.add(devWeaponsTabSearchbar, gbc);
        devWeaponTabSearchButton = new JButton();
        devWeaponTabSearchButton.setMaximumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setMinimumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setPreferredSize(new Dimension(50, 30));
        devWeaponTabSearchButton.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 5);
        devWeaponTab.add(devWeaponTabSearchButton, gbc);
        devSaveAllWeapons = new JButton();
        devSaveAllWeapons.setBackground(new Color(-6039919));
        Font devSaveAllWeaponsFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devSaveAllWeapons.getFont());
        if (devSaveAllWeaponsFont != null) {
            devSaveAllWeapons.setFont(devSaveAllWeaponsFont);
        }
        devSaveAllWeapons.setForeground(new Color(-394241));
        devSaveAllWeapons.setText("SAVE all weapons");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 20, 0, 5);
        devWeaponTab.add(devSaveAllWeapons, gbc);
        devFilterComboBox = new JComboBox();
        devFilterComboBox.setBackground(new Color(-2702645));
        devFilterComboBox.setEnabled(true);
        Font devFilterComboBoxFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, -1, devFilterComboBox.getFont());
        if (devFilterComboBoxFont != null) {
            devFilterComboBox.setFont(devFilterComboBoxFont);
        }
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("[ Filter ]");
        defaultComboBoxModel1.addElement("Claymore");
        defaultComboBoxModel1.addElement("Bow");
        defaultComboBoxModel1.addElement("Polearm");
        defaultComboBoxModel1.addElement("Sword");
        defaultComboBoxModel1.addElement("Catalyst");
        devFilterComboBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 0, 5);
        devWeaponTab.add(devFilterComboBox, gbc);
        devShowListedCheckBox = new JCheckBox();
        devShowListedCheckBox.setBackground(new Color(-2702645));
        Font devShowListedCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, -1, devShowListedCheckBox.getFont());
        if (devShowListedCheckBoxFont != null) {
            devShowListedCheckBox.setFont(devShowListedCheckBoxFont);
        }
        devShowListedCheckBox.setForeground(new Color(-15072759));
        devShowListedCheckBox.setText("Show listed ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);
        devWeaponTab.add(devShowListedCheckBox, gbc);
        devShowUnlistedCheckBox = new JCheckBox();
        devShowUnlistedCheckBox.setBackground(new Color(-2702645));
        Font devShowUnlistedCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, -1, devShowUnlistedCheckBox.getFont());
        if (devShowUnlistedCheckBoxFont != null) {
            devShowUnlistedCheckBox.setFont(devShowUnlistedCheckBoxFont);
        }
        devShowUnlistedCheckBox.setForeground(new Color(-15072759));
        devShowUnlistedCheckBox.setText("Show unlisted ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);
        devWeaponTab.add(devShowUnlistedCheckBox, gbc);
        devShowMatchedAmountLabel = new JLabel();
        Font devShowMatchedAmountLabelFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, -1, devShowMatchedAmountLabel.getFont());
        if (devShowMatchedAmountLabelFont != null) {
            devShowMatchedAmountLabel.setFont(devShowMatchedAmountLabelFont);
        }
        devShowMatchedAmountLabel.setForeground(new Color(-15072759));
        devShowMatchedAmountLabel.setText("Matches: 35");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        devWeaponTab.add(devShowMatchedAmountLabel, gbc);
        devDomainsTab = new JPanel();
        devDomainsTab.setLayout(new GridBagLayout());
        devDomainsTab.setBackground(new Color(-1));
        devTabbedPane.addTab("Domains", devDomainsTab);
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
        devDomainsPanelForEverything = new JPanel();
        devDomainsPanelForEverything.setLayout(new GridBagLayout());
        devDomainsScrollPane.setViewportView(devDomainsPanelForEverything);
        weaponMatDomainPanel = new JPanel();
        weaponMatDomainPanel.setLayout(new GridBagLayout());
        weaponMatDomainPanel.setBackground(new Color(-10301));
        weaponMatDomainPanel.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 100, 5, 100);
        devDomainsPanelForEverything.add(weaponMatDomainPanel, gbc);
        weaponMatDomainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        label1.setIcon(
                new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/All Mist Veiled Elixir.png")));
        label1.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        weaponMatDomainPanel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/All Guyun.png")));
        label2.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        weaponMatDomainPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/All Aerosiderite.png")));
        label3.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        weaponMatDomainPanel.add(label3, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setAlignmentX(0.5f);
        panel1.setAlignmentY(0.5f);
        panel1.setBackground(new Color(-10301));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        weaponMatDomainPanel.add(panel1, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label4.getFont());
        if (label4Font != null) {
            label4.setFont(label4Font);
        }
        label4.setForeground(new Color(-13494016));
        label4.setText("Hidden Palace of Lianshan Formula");
        panel1.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label5.getFont());
        if (label5Font != null) {
            label5.setFont(label5Font);
        }
        label5.setForeground(new Color(-13494016));
        label5.setText("All weapons that need it: 53");
        panel1.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label6.getFont());
        if (label6Font != null) {
            label6.setFont(label6Font);
        }
        label6.setForeground(new Color(-13494016));
        label6.setText("Weapons listed for this domain: 34");
        panel1.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 5), -1, -1));
        panel2.setAlignmentX(0.5f);
        panel2.setAlignmentY(0.5f);
        panel2.setBackground(new Color(-26768));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 0);
        weaponMatDomainPanel.add(panel2, gbc);
        final JLabel label7 = new JLabel();
        label7.setAlignmentX(0.5f);
        label7.setFocusTraversalPolicyProvider(false);
        label7.setFocusable(false);
        Font label7Font = this.$$$getFont$$$(null, Font.BOLD, 16, label7.getFont());
        if (label7Font != null) {
            label7.setFont(label7Font);
        }
        label7.setForeground(new Color(-1));
        label7.setText("\uD83D\uDD2A");
        panel2.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        talentBookMatDomain = new JPanel();
        talentBookMatDomain.setLayout(new GridBagLayout());
        talentBookMatDomain.setBackground(new Color(-1068));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 100, 5, 100);
        devDomainsPanelForEverything.add(talentBookMatDomain, gbc);
        talentBookMatDomain.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label8 = new JLabel();
        label8.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Talent Materials/Resistance.png")));
        label8.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        talentBookMatDomain.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Talent Materials/Freedom.png")));
        label9.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        talentBookMatDomain.add(label9, gbc);
        final JLabel label10 = new JLabel();
        label10.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Talent Materials/Ballad.png")));
        label10.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        talentBookMatDomain.add(label10, gbc);
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        infoPanel.setAlignmentX(0.5f);
        infoPanel.setAlignmentY(0.5f);
        infoPanel.setBackground(new Color(-1068));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        talentBookMatDomain.add(infoPanel, gbc);
        final JLabel label11 = new JLabel();
        Font label11Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label11.getFont());
        if (label11Font != null) {
            label11.setFont(label11Font);
        }
        label11.setForeground(new Color(-14541824));
        label11.setText("Forsaken Rift");
        infoPanel.add(label11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        Font label12Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label12.getFont());
        if (label12Font != null) {
            label12.setFont(label12Font);
        }
        label12.setForeground(new Color(-14541824));
        label12.setText("All characters that need it: 53");
        infoPanel.add(label12, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        Font label13Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label13.getFont());
        if (label13Font != null) {
            label13.setFont(label13Font);
        }
        label13.setForeground(new Color(-14541824));
        label13.setText("Characters listed for this domain: 34");
        infoPanel.add(label13, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        infoMarginPanel = new JPanel();
        infoMarginPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 5), -1, -1));
        infoMarginPanel.setAlignmentX(0.5f);
        infoMarginPanel.setAlignmentY(0.5f);
        infoMarginPanel.setBackground(new Color(-10640));
        infoMarginPanel.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 0);
        talentBookMatDomain.add(infoMarginPanel, gbc);
        final JLabel label14 = new JLabel();
        label14.setAlignmentX(0.5f);
        label14.setFocusTraversalPolicyProvider(false);
        label14.setFocusable(false);
        Font label14Font = this.$$$getFont$$$(null, Font.BOLD, 16, label14.getFont());
        if (label14Font != null) {
            label14.setFont(label14Font);
        }
        label14.setForeground(new Color(-1));
        label14.setText("\uD83D\uDCD4");
        infoMarginPanel.add(label14,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        artifactDomainPanel = new JPanel();
        artifactDomainPanel.setLayout(new GridBagLayout());
        artifactDomainPanel.setBackground(new Color(-2756865));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 100, 5, 100);
        devDomainsPanelForEverything.add(artifactDomainPanel, gbc);
        artifactDomainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label15 = new JLabel();
        label15.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Artifacts/Heart of Depth.png")));
        label15.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        artifactDomainPanel.add(label15, gbc);
        final JLabel label16 = new JLabel();
        label16.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Artifacts/Blizzard Strayer.png")));
        label16.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        artifactDomainPanel.add(label16, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setAlignmentX(0.5f);
        panel3.setAlignmentY(0.5f);
        panel3.setBackground(new Color(-2756865));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        artifactDomainPanel.add(panel3, gbc);
        final JLabel label17 = new JLabel();
        Font label17Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label17.getFont());
        if (label17Font != null) {
            label17.setFont(label17Font);
        }
        label17.setForeground(new Color(-16575201));
        label17.setText("Peak of Vindagnyr");
        panel3.add(label17, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        Font label18Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label18.getFont());
        if (label18Font != null) {
            label18.setFont(label18Font);
        }
        label18.setForeground(new Color(-16575201));
        label18.setText("All characters holding it: 53");
        panel3.add(label18, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        Font label19Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label19.getFont());
        if (label19Font != null) {
            label19.setFont(label19Font);
        }
        label19.setForeground(new Color(-16575201));
        label19.setText("Characters listed for this domain: 34");
        panel3.add(label19, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 5), -1, -1));
        panel4.setAlignmentX(0.5f);
        panel4.setAlignmentY(0.5f);
        panel4.setBackground(new Color(-9382145));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 0);
        artifactDomainPanel.add(panel4, gbc);
        final JLabel label20 = new JLabel();
        label20.setAlignmentX(0.5f);
        label20.setFocusTraversalPolicyProvider(false);
        label20.setFocusable(false);
        Font label20Font = this.$$$getFont$$$(null, Font.BOLD, 16, label20.getFont());
        if (label20Font != null) {
            label20.setFont(label20Font);
        }
        label20.setForeground(new Color(-1));
        label20.setText("\uD83D\uDC51");
        panel4.add(label20, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weeklyBossDomainPanel = new JPanel();
        weeklyBossDomainPanel.setLayout(new GridBagLayout());
        weeklyBossDomainPanel.setBackground(new Color(-11811));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 100, 5, 100);
        devDomainsPanelForEverything.add(weeklyBossDomainPanel, gbc);
        weeklyBossDomainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label21 = new JLabel();
        label21.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Weekly Bosses/Dvalin's Sigh.png")));
        label21.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        weeklyBossDomainPanel.add(label21, gbc);
        final JLabel label22 = new JLabel();
        label22.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Weekly Bosses/Dvalin's Plume.png")));
        label22.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        weeklyBossDomainPanel.add(label22, gbc);
        final JLabel label23 = new JLabel();
        label23.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Weekly Bosses/Dvalin's Claw.png")));
        label23.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        weeklyBossDomainPanel.add(label23, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setAlignmentX(0.5f);
        panel5.setAlignmentY(0.5f);
        panel5.setBackground(new Color(-11811));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        weeklyBossDomainPanel.add(panel5, gbc);
        final JLabel label24 = new JLabel();
        Font label24Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label24.getFont());
        if (label24Font != null) {
            label24.setFont(label24Font);
        }
        label24.setForeground(new Color(-13236722));
        label24.setText("Stormterror Dvalin");
        panel5.add(label24, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        Font label25Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label25.getFont());
        if (label25Font != null) {
            label25.setFont(label25Font);
        }
        label25.setForeground(new Color(-13236722));
        label25.setText("All characters that need it: 53");
        panel5.add(label25, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label26 = new JLabel();
        Font label26Font = this.$$$getFont$$$("Source Code Pro", -1, 12, label26.getFont());
        if (label26Font != null) {
            label26.setFont(label26Font);
        }
        label26.setForeground(new Color(-13236722));
        label26.setText("Characters listed for this weekly boss: 34");
        panel5.add(label26, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 5), -1, -1));
        panel6.setAlignmentX(0.5f);
        panel6.setAlignmentY(0.5f);
        panel6.setBackground(new Color(-36698));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 0);
        weeklyBossDomainPanel.add(panel6, gbc);
        final JLabel label27 = new JLabel();
        label27.setAlignmentX(0.5f);
        label27.setFocusTraversalPolicyProvider(false);
        label27.setFocusable(false);
        Font label27Font = this.$$$getFont$$$(null, Font.BOLD, 16, label27.getFont());
        if (label27Font != null) {
            label27.setFont(label27Font);
        }
        label27.setForeground(new Color(-1));
        label27.setText("\uD83D\uDC09");
        panel6.add(label27, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JComboBox comboBox1 = new JComboBox();
        comboBox1.setBackground(new Color(-2702645));
        comboBox1.setEnabled(true);
        Font comboBox1Font = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, -1, comboBox1.getFont());
        if (comboBox1Font != null) {
            comboBox1.setFont(comboBox1Font);
        }
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("[ Filter ]");
        defaultComboBoxModel2.addElement("Artifacts");
        defaultComboBoxModel2.addElement("Talents");
        defaultComboBoxModel2.addElement("Weekly Talents");
        defaultComboBoxModel2.addElement("Weapons");
        comboBox1.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 10);
        devDomainsTab.add(comboBox1, gbc);
        final JButton button1 = new JButton();
        button1.setMaximumSize(new Dimension(30, 30));
        button1.setMinimumSize(new Dimension(30, 30));
        button1.setPreferredSize(new Dimension(50, 30));
        button1.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        devDomainsTab.add(button1, gbc);
        devCharacterCardMainPanel = new JPanel();
        devCharacterCardMainPanel.setLayout(new GridBagLayout());
        devTabbedPane.addTab("Untitled", devCharacterCardMainPanel);
        devCharacterCardSelectorPanel = new JPanel();
        devCharacterCardSelectorPanel.setLayout(new GridLayoutManager(16, 1, new Insets(5, 5, 5, 5), -1, -1));
        devCharacterCardSelectorPanel.setAlignmentY(0.5f);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(22, 0, 0, 0);
        devCharacterCardMainPanel.add(devCharacterCardSelectorPanel, gbc);
        devCharacterCardSelectorPanel.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        devNotesTextField = new JTextField();
        Font devNotesTextFieldFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, devNotesTextField.getFont());
        if (devNotesTextFieldFont != null) {
            devNotesTextField.setFont(devNotesTextFieldFont);
        }
        devNotesTextField.setHorizontalAlignment(10);
        devNotesTextField.setInheritsPopupMenu(false);
        devNotesTextField.setMargin(new Insets(2, 6, 2, 6));
        devNotesTextField.setOpaque(true);
        devNotesTextField.setRequestFocusEnabled(true);
        devNotesTextField.setText("[ Empty Notes Field ]");
        devCharacterCardSelectorPanel.add(devNotesTextField,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(50, -1), null, 0, false));
        devCharacterNameLabel = new JLabel();
        devCharacterNameLabel.setAutoscrolls(true);
        Font devCharacterNameLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, devCharacterNameLabel.getFont());
        if (devCharacterNameLabelFont != null) {
            devCharacterNameLabel.setFont(devCharacterNameLabelFont);
        }
        devCharacterNameLabel.setText("Albedo");
        devCharacterCardSelectorPanel.add(devCharacterNameLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));
        devWeaponSelectorBox = new JComboBox();
        devWeaponSelectorBox.setAutoscrolls(false);
        devWeaponSelectorBox.setEditable(false);
        Font devWeaponSelectorBoxFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, devWeaponSelectorBox.getFont());
        if (devWeaponSelectorBoxFont != null) {
            devWeaponSelectorBox.setFont(devWeaponSelectorBoxFont);
        }
        devWeaponSelectorBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("[ Amos' Bow Selected ]");
        devWeaponSelectorBox.setModel(defaultComboBoxModel3);
        devWeaponSelectorBox.setToolTipText("");
        devCharacterCardSelectorPanel.add(devWeaponSelectorBox,
                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devWeaponNameLabel = new JLabel();
        devWeaponNameLabel.setAutoscrolls(true);
        Font devWeaponNameLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, devWeaponNameLabel.getFont());
        if (devWeaponNameLabelFont != null) {
            devWeaponNameLabel.setFont(devWeaponNameLabelFont);
        }
        devWeaponNameLabel.setText("Amos's Bow");
        devCharacterCardSelectorPanel.add(devWeaponNameLabel,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));
        devSet1NameLabel = new JLabel();
        devSet1NameLabel.setAlignmentY(0.5f);
        devSet1NameLabel.setAutoscrolls(true);
        devSet1NameLabel.setDoubleBuffered(false);
        Font devSet1NameLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, devSet1NameLabel.getFont());
        if (devSet1NameLabelFont != null) {
            devSet1NameLabel.setFont(devSet1NameLabelFont);
        }
        devSet1NameLabel.setHorizontalAlignment(10);
        devSet1NameLabel.setHorizontalTextPosition(11);
        devSet1NameLabel.setInheritsPopupMenu(true);
        devSet1NameLabel.setText("Shimenava's Reminiscence");
        devSet1NameLabel.setVerticalAlignment(0);
        devSet1NameLabel.setVerticalTextPosition(0);
        devCharacterCardSelectorPanel.add(devSet1NameLabel,
                new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));
        devSet1SelectionBox = new JComboBox();
        devSet1SelectionBox.setAutoscrolls(false);
        devSet1SelectionBox.setEditable(true);
        Font devSet1SelectionBoxFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, devSet1SelectionBox.getFont());
        if (devSet1SelectionBoxFont != null) {
            devSet1SelectionBox.setFont(devSet1SelectionBoxFont);
        }
        devSet1SelectionBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("[ Empty Set 1 Selector ]");
        devSet1SelectionBox.setModel(defaultComboBoxModel4);
        devSet1SelectionBox.setToolTipText("");
        devCharacterCardSelectorPanel.add(devSet1SelectionBox,
                new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devSet2SelectionBox = new JComboBox();
        devSet2SelectionBox.setAutoscrolls(false);
        devSet2SelectionBox.setEditable(true);
        Font devSet2SelectionBoxFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, devSet2SelectionBox.getFont());
        if (devSet2SelectionBoxFont != null) {
            devSet2SelectionBox.setFont(devSet2SelectionBoxFont);
        }
        devSet2SelectionBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("[ Empty Set 2 Selector ]");
        devSet2SelectionBox.setModel(defaultComboBoxModel5);
        devSet2SelectionBox.setToolTipText("");
        devCharacterCardSelectorPanel.add(devSet2SelectionBox,
                new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devSet2NameLabel = new JLabel();
        devSet2NameLabel.setAutoscrolls(true);
        Font devSet2NameLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, devSet2NameLabel.getFont());
        if (devSet2NameLabelFont != null) {
            devSet2NameLabel.setFont(devSet2NameLabelFont);
        }
        devSet2NameLabel.setText("Noblesse Oblige");
        devCharacterCardSelectorPanel.add(devSet2NameLabel,
                new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));
        devTopSpacer = new JPanel();
        devTopSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 7, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devTopSpacer,
                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devWeaponSet1Spacer = new JPanel();
        devWeaponSet1Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devWeaponSet1Spacer,
                new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devSet1Set2Spacer = new JPanel();
        devSet1Set2Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devSet1Set2Spacer,
                new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devBottomSpacer = new JPanel();
        devBottomSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(20, 0, 0, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devBottomSpacer,
                new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devArtifact1ListingCheckBox = new JCheckBox();
        Font devArtifact1ListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, devArtifact1ListingCheckBox.getFont());
        if (devArtifact1ListingCheckBoxFont != null) {
            devArtifact1ListingCheckBox.setFont(devArtifact1ListingCheckBoxFont);
        }
        devArtifact1ListingCheckBox.setText("Set 1 Listing");
        devCharacterCardSelectorPanel.add(devArtifact1ListingCheckBox,
                new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devArtifact2ListingCheckBox = new JCheckBox();
        Font devArtifact2ListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, devArtifact2ListingCheckBox.getFont());
        if (devArtifact2ListingCheckBoxFont != null) {
            devArtifact2ListingCheckBox.setFont(devArtifact2ListingCheckBoxFont);
        }
        devArtifact2ListingCheckBox.setText("Set 2 Listing");
        devCharacterCardSelectorPanel.add(devArtifact2ListingCheckBox,
                new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devTalentListingCheckBox = new JCheckBox();
        Font devTalentListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, devTalentListingCheckBox.getFont());
        if (devTalentListingCheckBoxFont != null) {
            devTalentListingCheckBox.setFont(devTalentListingCheckBoxFont);
        }
        devTalentListingCheckBox.setText("Talent Listing");
        devCharacterCardSelectorPanel.add(devTalentListingCheckBox,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devWepMatListingLabel = new JCheckBox();
        Font devWepMatListingLabelFont = this.$$$getFont$$$("Source Code Pro", -1, 14, devWepMatListingLabel.getFont());
        if (devWepMatListingLabelFont != null) {
            devWepMatListingLabel.setFont(devWepMatListingLabelFont);
        }
        devWepMatListingLabel.setText("Weapon Material Listing");
        devCharacterCardSelectorPanel.add(devWepMatListingLabel,
                new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devWeaponIconLabel = new JLabel();
        devWeaponIconLabel.setHorizontalAlignment(4);
        devWeaponIconLabel.setHorizontalTextPosition(4);
        devWeaponIconLabel.setIcon(
                new ImageIcon(getClass().getResource("/Files/Images/Weapons/Bow_5star/Elegy for the End.png")));
        devWeaponIconLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        devCharacterCardMainPanel.add(devWeaponIconLabel, gbc);
        devCharacterIconLabel = new JLabel();
        devCharacterIconLabel.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Characters/Albedo.png")));
        devCharacterIconLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 0, 5, 20);
        devCharacterCardMainPanel.add(devCharacterIconLabel, gbc);
        devSet1IconLabel = new JLabel();
        devSet1IconLabel.setHorizontalAlignment(4);
        devSet1IconLabel.setHorizontalTextPosition(4);
        devSet1IconLabel.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Artifacts/Archaic Petra.png")));
        devSet1IconLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        devCharacterCardMainPanel.add(devSet1IconLabel, gbc);
        devSet2IconLabel = new JLabel();
        devSet2IconLabel.setHorizontalAlignment(4);
        devSet2IconLabel.setHorizontalTextPosition(4);
        devSet2IconLabel.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Artifacts/Noblesse Oblige.png")));
        devSet2IconLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        devCharacterCardMainPanel.add(devSet2IconLabel, gbc);
        devButtonAndCheckboxPanel = new JPanel();
        devButtonAndCheckboxPanel.setLayout(new GridLayoutManager(4, 2, new Insets(3, 3, 3, 3), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(22, 0, 0, 100);
        devCharacterCardMainPanel.add(devButtonAndCheckboxPanel, gbc);
        devButtonAndCheckboxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        devDomainListingsLabel = new JLabel();
        Font devDomainListingsLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, devDomainListingsLabel.getFont());
        if (devDomainListingsLabelFont != null) {
            devDomainListingsLabel.setFont(devDomainListingsLabelFont);
        }
        devDomainListingsLabel.setText("Set details          ");
        devButtonAndCheckboxPanel.add(devDomainListingsLabel,
                new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devCloseButton = new JButton();
        devCloseButton.setBackground(new Color(-2725532));
        Font devCloseButtonFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devCloseButton.getFont());
        if (devCloseButtonFont != null) {
            devCloseButton.setFont(devCloseButtonFont);
        }
        devCloseButton.setForeground(new Color(-6427));
        devCloseButton.setHideActionText(false);
        devCloseButton.setText("CLOSE");
        devButtonAndCheckboxPanel.add(devCloseButton,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devSaveButton = new JButton();
        devSaveButton.setBackground(new Color(-6039919));
        devSaveButton.setForeground(new Color(-394241));
        devSaveButton.setText("SAVE");
        devButtonAndCheckboxPanel.add(devSaveButton,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devSetDetailsTextArea = new JTextArea();
        devSetDetailsTextArea.setEditable(false);
        devSetDetailsTextArea.setFocusable(false);
        Font devSetDetailsTextAreaFont = this.$$$getFont$$$("Source Code Pro", -1, 12, devSetDetailsTextArea.getFont());
        if (devSetDetailsTextAreaFont != null) {
            devSetDetailsTextArea.setFont(devSetDetailsTextAreaFont);
        }
        devSetDetailsTextArea.setText("[ Set Details ]");
        devButtonAndCheckboxPanel.add(devSetDetailsTextArea,
                new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW,
                        new Dimension(150, 380), new Dimension(150, 380), null, 0, false));
        devWeaponMatDomain = new JPanel();
        devWeaponMatDomain.setLayout(new GridBagLayout());
        devWeaponMatDomain.setBackground(new Color(-1));
        devTabbedPane.addTab("WeaponMat", devWeaponMatDomain);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devWeaponMatDomain.add(panel7, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel7.add(scrollPane2, gbc);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridBagLayout());
        scrollPane2.setViewportView(panel8);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setBackground(new Color(-1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel8.add(panel9, gbc);
        final JLabel label28 = new JLabel();
        Font label28Font = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, label28.getFont());
        if (label28Font != null) {
            label28.setFont(label28Font);
        }
        label28.setForeground(new Color(-1767091));
        label28.setText("Hidden Palace of Lianshan Formula");
        panel9.add(label28, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return devMainPanel;
    }

}

