package Files;

import static Files.ToolData.artifactsFlattened;
import static Files.ToolData.charactersFlattened;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Objects;

public class ToolGUI extends JFrame implements ActionListener {

    public static final String NO_CHARACTERS_MATCH_MESSAGE = "No fighters >:(";
    public static final String UNKNOWN_CHARACTER_PLACEHOLDER_NAME = "unknown_character";
    public static final int MAX_CHARACTERS_PER_LINE = 12;

    public static final String TOOLTIP_FOR_LABELS_WITHOUT_ICON = "Here will be the chosen set name shown";
    public static final String TOOLTIP_FOR_LABELS_WITH_ICON = "Here will be the chosen set icon shown";
    public static final String UNKNOWN_SET_MESSAGE = "[ Empty Set Selector ]";

    public static final String UNKNOWN_ARTIFACT = "unknown_artifact";
    public static final String CLOSE_TEXT = "CLOSE";

    private JPanel panel1;
    private JLabel titleLabel;
    private JTabbedPane characterTabPane;
    private JPanel mainInformationPanel;
    private JPanel characterTab;
    private JPanel artifactsTab;
    private JTextField characterSelectorField;
    private JButton searchConfirmButton;
    private JPanel selectedCharacterPanel;
    private JScrollPane scrollPane1;

    public ToolGUI() {
        $$$setupUI$$$();
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
        // TODO: place custom component creation code here
        selectedCharacterPanel = new JPanel(new GridBagLayout());
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();
        searchConfirmButton = new JButton("✓");
        searchConfirmButton.addActionListener(this);
        characterSelectorField = new JTextField();
        characterSelectorField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                characterSelectorField.setText("");
            }
        });
    }

    /**
     * Listener for the searchConfirmButton.
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {

        String userFieldInput = characterSelectorField.getText().toLowerCase();
        boolean matched = false;
        int matchedCount = 0;
        selectedCharacterPanel.removeAll();

        for (String s : charactersFlattened) {
            if (s.toLowerCase().contains(userFieldInput)) {
                matched = true;
                if (!userFieldInput.isEmpty()) {
                    generateCharacterButton(s, matchedCount);
                }
                matchedCount++;

            }
        }
        if (!matched || userFieldInput.isEmpty()) {
            generateCharacterButton(UNKNOWN_CHARACTER_PLACEHOLDER_NAME, matchedCount);
        } else {
            scrollPane1.setViewportView(selectedCharacterPanel);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            scrollPane1.updateUI();
            characterTab.add(scrollPane1, gbc);

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
        if (characterName.equalsIgnoreCase(UNKNOWN_CHARACTER_PLACEHOLDER_NAME)) {
            JLabel unknownCharacterLabel = new JLabel(characterIcon);
            unknownCharacterLabel.setText(NO_CHARACTERS_MATCH_MESSAGE);
            unknownCharacterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            unknownCharacterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            setFont(unknownCharacterLabel);
            selectedCharacterPanel.add(unknownCharacterLabel);
            selectedCharacterPanel.updateUI();
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
        Font font = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 16, characterTabPane.getFont());
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
        characterButton.addActionListener(e -> {
            if (!isTabAlreadyOpen(characterTabPane, characterName)) {
                JPanel characterPage = generateCharacterPage(characterName, characterIcon);
                characterTabPane.addTab(characterName, characterPage);
                characterTabPane.setSelectedComponent(characterPage);
            } else {
                characterTabPane.setSelectedIndex(characterTabPane.indexOfTab(characterName));
            }
        });
        return characterButton;
    }

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
     * @param charName the character name whose icon path is to be given back
     * @return character icon path
     */
    public static String generateCharacterIconPath(String charName) {
        return "/Files/Images/Characters/" + charName + ".png";
    }

    public static String generateArtifactIconPath(String artifactName) {
        return "/Files/Images/Artifacts/" + artifactName + ".png";
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
        selectedCharacterPanel.add(charButton, gbc);
        selectedCharacterPanel.updateUI();

    }

    /**
     * Removes whitespace from the name of the character, adds line break in its place and centers the name.
     *
     * @param name the character name to be changed
     * @return edited string
     */
    public String removeWhitespace(String name) {
        return "<html><center>" + name.replace(" ", "<br>") + "</center></html>";

    }

    /**
     * Generates a character page (in the tabbed pane).
     *
     * @param charName the character name
     * @param charIcon the character icon
     * @return the JPanel that is the character page.
     */
    private JPanel generateCharacterPage(String charName, Icon charIcon) {
        JPanel templateTab = new JPanel();
        templateTab.setLayout(new GridBagLayout());
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
        templateTab.add(middleSelectorPanel, gbc);
        middleSelectorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

        JTextField emptyNotesFieldTextField = new JTextField();
        Font emptyNotesFieldTextFieldFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, emptyNotesFieldTextField.getFont());
        if (emptyNotesFieldTextFieldFont != null) {
            emptyNotesFieldTextField.setFont(emptyNotesFieldTextFieldFont);
        }
        emptyNotesFieldTextField.setHorizontalAlignment(10);
        emptyNotesFieldTextField.setInheritsPopupMenu(false);
        emptyNotesFieldTextField.setMargin(new Insets(2, 6, 2, 6));
        emptyNotesFieldTextField.setOpaque(true);
        emptyNotesFieldTextField.setRequestFocusEnabled(true);
        emptyNotesFieldTextField.setText("[ Empty Notes Field ]");
        middleSelectorPanel.add(emptyNotesFieldTextField,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(50, -1), null, 0, false));

        JLabel characterNameLabel = new JLabel();
        characterNameLabel.setAutoscrolls(true);
        Font characterNameLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, characterNameLabel.getFont());
        if (characterNameLabelFont != null) {
            characterNameLabel.setFont(characterNameLabelFont);
        }
        characterNameLabel.setText(charName);
        middleSelectorPanel.add(characterNameLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));

        JComboBox<String> weaponSelectionBox = new JComboBox<>();
        weaponSelectionBox.setAutoscrolls(false);
        weaponSelectionBox.setEditable(false);
        Font weaponSelectionBoxFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, weaponSelectionBox.getFont());
        if (weaponSelectionBoxFont != null) {
            weaponSelectionBox.setFont(weaponSelectionBoxFont);
        }
        weaponSelectionBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<String> defaultComboBoxModelForWeaponSelector = new DefaultComboBoxModel<>();
        defaultComboBoxModelForWeaponSelector.addElement("[ Amos' Bow Selected ]");
        weaponSelectionBox.setModel(defaultComboBoxModelForWeaponSelector);
        weaponSelectionBox.setToolTipText("");
        middleSelectorPanel.add(weaponSelectionBox,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        JLabel weaponNameLabel = new JLabel();
        weaponNameLabel.setAutoscrolls(true);
        Font weaponNameLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, weaponNameLabel.getFont());
        if (weaponNameLabelFont != null) {
            weaponNameLabel.setFont(weaponNameLabelFont);
        }
        weaponNameLabel.setText("Amos's Bow");
        middleSelectorPanel.add(weaponNameLabel,
                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));

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
        set1NameLabel.setText("");
        set1NameLabel.setVerticalAlignment(0);
        set1NameLabel.setVerticalTextPosition(0);
        set1NameLabel.setToolTipText(TOOLTIP_FOR_LABELS_WITHOUT_ICON);
        middleSelectorPanel.add(set1NameLabel,
                new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));

        JComboBox<String> set1comboBox = new JComboBox<>();
        set1comboBox.setAutoscrolls(false);
        set1comboBox.setEditable(true);
        Font set1comboBoxFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, set1comboBox.getFont());
        if (set1comboBoxFont != null) {
            set1comboBox.setFont(set1comboBoxFont);
        }
        set1comboBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<String> defaultComboBoxModelForSet1Selector = new DefaultComboBoxModel<>();
        defaultComboBoxModelForSet1Selector.addElement(UNKNOWN_SET_MESSAGE);
        defaultComboBoxModelForSet1Selector.addAll(artifactsFlattened);
        set1comboBox.setModel(defaultComboBoxModelForSet1Selector);
        set1comboBox.setToolTipText("");
        middleSelectorPanel.add(set1comboBox,
                new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        JComboBox<String> set2ComboBox = new JComboBox<>();
        set2ComboBox.setAutoscrolls(false);
        set2ComboBox.setEditable(true);
        Font set2ComboBoxFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, set2ComboBox.getFont());
        if (set2ComboBoxFont != null) {
            set2ComboBox.setFont(set2ComboBoxFont);
        }
        set2ComboBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<String> defaultComboBoxModelForSet2Selector = new DefaultComboBoxModel<>();
        defaultComboBoxModelForSet2Selector.addElement(UNKNOWN_SET_MESSAGE);
        defaultComboBoxModelForSet2Selector.addAll(artifactsFlattened);
        set2ComboBox.setModel(defaultComboBoxModelForSet2Selector);
        set2ComboBox.setToolTipText("");
        middleSelectorPanel.add(set2ComboBox,
                new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        JLabel set2NameLabel = new JLabel();
        set2NameLabel.setAutoscrolls(true);
        Font set2NameLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, set2NameLabel.getFont());
        if (set2NameLabelFont != null) {
            set2NameLabel.setFont(set2NameLabelFont);
        }
        set2NameLabel.setToolTipText(TOOLTIP_FOR_LABELS_WITHOUT_ICON);
        set2NameLabel.setText("");
        middleSelectorPanel.add(set2NameLabel,
                new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));

        JButton addAnotherSetButton = new JButton();
        addAnotherSetButton.setText("Add another set?");
        middleSelectorPanel.add(addAnotherSetButton,
                new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JPanel characterWeaponSpacer = new JPanel();
        characterWeaponSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 40, 0), -1, -1));
        middleSelectorPanel.add(characterWeaponSpacer,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));

        JPanel weaponSet1Spacer = new JPanel();
        weaponSet1Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(48, 0, 0, 0), -1, -1));
        middleSelectorPanel.add(weaponSet1Spacer,
                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));

        JPanel secondSetSpacer = new JPanel();
        secondSetSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1));
        middleSelectorPanel.add(secondSetSpacer,
                new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));

        JPanel bottomSpacer = new JPanel();
        bottomSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(40, 0, 0, 0), -1, -1));
        middleSelectorPanel.add(bottomSpacer,
                new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));

        JLabel weaponIcon = new JLabel();
        weaponIcon.setHorizontalAlignment(4);
        weaponIcon.setHorizontalTextPosition(4);
        weaponIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Files/Images/Weapons/Bow_5star/Elegy for the End.png"))));
        weaponIcon.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        templateTab.add(weaponIcon, gbc);

        JLabel charLabel = new JLabel();
        charLabel.setIcon(charIcon);
        charLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 0, 5, 20);
        templateTab.add(charLabel, gbc);

        JLabel set1Icon = new JLabel();
        set1Icon.setToolTipText(TOOLTIP_FOR_LABELS_WITH_ICON);
        set1Icon.setHorizontalAlignment(4);
        set1Icon.setHorizontalTextPosition(4);
        set1Icon.setIcon(new ImageIcon(
                Objects.requireNonNull(getClass().getResource(ToolGUI.generateArtifactIconPath(UNKNOWN_ARTIFACT)))));
        set1Icon.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        templateTab.add(set1Icon, gbc);

        JLabel set2Icon = new JLabel();
        set2Icon.setToolTipText(TOOLTIP_FOR_LABELS_WITH_ICON);
        set2Icon.setHorizontalAlignment(4);
        set2Icon.setHorizontalTextPosition(4);
        set2Icon.setIcon(new ImageIcon(
                Objects.requireNonNull(getClass().getResource(ToolGUI.generateArtifactIconPath(UNKNOWN_ARTIFACT)))));
        set2Icon.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        templateTab.add(set2Icon, gbc);

        set1comboBox.addActionListener(new ActionListeners(set1comboBox, set1NameLabel));
        set1comboBox.addActionListener(new ActionListeners(set1comboBox, set1Icon));
        set2ComboBox.addActionListener(new ActionListeners(set2ComboBox, set2NameLabel));
        set2ComboBox.addActionListener(new ActionListeners(set2ComboBox, set2Icon));

        JPanel checkboxAndButtonPanel = new JPanel();
        checkboxAndButtonPanel.setLayout(new GridLayoutManager(6, 2, new Insets(3, 3, 3, 3), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(22, 0, 0, 100);
        templateTab.add(checkboxAndButtonPanel, gbc);
        checkboxAndButtonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

        JCheckBox artifactListingCheckBox = new JCheckBox();
        Font artifactListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, artifactListingCheckBox.getFont());
        if (artifactListingCheckBoxFont != null) {
            artifactListingCheckBox.setFont(artifactListingCheckBoxFont);
        }
        artifactListingCheckBox.setText("Artifact Listing");
        checkboxAndButtonPanel.add(artifactListingCheckBox,
                new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JCheckBox talentListingCheckBox = new JCheckBox();
        Font talentListingCheckBoxFont = this.$$$getFont$$$("Source Code Pro", -1, 14, talentListingCheckBox.getFont());
        if (talentListingCheckBoxFont != null) {
            talentListingCheckBox.setFont(talentListingCheckBoxFont);
        }
        talentListingCheckBox.setText("Talent Listing");
        checkboxAndButtonPanel.add(talentListingCheckBox,
                new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel domainListingsLabel = new JLabel();
        Font domainListingsLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, domainListingsLabel.getFont());
        if (domainListingsLabelFont != null) {
            domainListingsLabel.setFont(domainListingsLabelFont);
        }
        domainListingsLabel.setText("Domain listings");
        checkboxAndButtonPanel.add(domainListingsLabel,
                new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));

        JCheckBox weaponMaterialListingCheckbox = new JCheckBox();
        Font weaponMaterialListingCheckboxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, weaponMaterialListingCheckbox.getFont());
        if (weaponMaterialListingCheckboxFont != null) {
            weaponMaterialListingCheckbox.setFont(weaponMaterialListingCheckboxFont);
        }
        weaponMaterialListingCheckbox.setText("Weapon Material Listing");
        checkboxAndButtonPanel.add(weaponMaterialListingCheckbox,
                new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JButton closeButton = new JButton();
        closeButton.setBackground(new Color(-2725532));
        Font closeButtonFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, closeButton.getFont());
        if (closeButtonFont != null) {
            closeButton.setFont(closeButtonFont);
        }
        closeButton.setForeground(new Color(-6427));
        closeButton.setHideActionText(false);
        closeButton.setText(CLOSE_TEXT);
        closeButton.addActionListener(new ActionListeners(closeButton, characterTabPane));
        checkboxAndButtonPanel.add(closeButton,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JButton saveButton = new JButton();
        saveButton.setBackground(new Color(-6039919));
        saveButton.setForeground(new Color(-394241));
        saveButton.setText("SAVE");
        checkboxAndButtonPanel.add(saveButton,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JTextArea setDetailsTextArea = new JTextArea();
        setDetailsTextArea.setEditable(false);
        setDetailsTextArea.setFocusable(false);
        Font setDetailsTextAreaFont = this.$$$getFont$$$("Source Code Pro", -1, 12, setDetailsTextArea.getFont());
        if (setDetailsTextAreaFont != null) {
            setDetailsTextArea.setFont(setDetailsTextAreaFont);
        }
        setDetailsTextArea.setText("[ Set Details ]");
        checkboxAndButtonPanel.add(setDetailsTextArea,
                new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW,
                        new Dimension(150, 280), new Dimension(150, 280), null, 0, false));

        return templateTab;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void addArtifactSets(DefaultComboBoxModel<String> dcmb) {
        for (String artifactName : artifactsFlattened) {
            dcmb.addElement(artifactName);
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
        artifactsTab = new JPanel();
        artifactsTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        artifactsTab.setBackground(new Color(-1));
        characterTabPane.addTab("Artifacts", artifactsTab);
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

