package Files;

import static Files.ToolData.charactersFlattened;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.*;
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

public class Program extends JFrame implements ActionListener {
    public Program() {
        $$$setupUI$$$();
        setContentPane(devMainPanel);
        setTitle("Genshin Domain App!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel devMainPanel;
    public static final String NO_CHARACTERS_MATCH_MESSAGE = "No fighters >:(";
    public static final String UNKNOWN_CHARACTER_PLACEHOLDER_NAME = "unknown_character";
    public static final int MAX_CHARACTERS_PER_LINE = 12;
    private JLabel devMainTabLabel;
    private JTabbedPane devTabbedPane;
    private JPanel devPanelWithMainTabbedPane;
    private JPanel devCharacterTab;
    private JPanel devWeaponTab;
    private JTextField devSearchField;
    private JButton devSearchButton;
    private JPanel devSearchResultPanel;
    private JTextField devNotesTextField;
    private JComboBox<String> devSet1SelectionBox;
    private JCheckBox devArtifactListingCheckBox;
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
    private JCheckBox devWepMatListingCheckbox;
    private JPanel weapon_card;
    private JLabel devWeaponIcon;
    private JLabel devWepMaterialPreview;
    private JLabel devWepTypeLabel;
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

        for (String s : charactersFlattened) {
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
     * Generates a character page (in the tabbed pane).
     * @param charName the character name
     * @param charIcon the character icon
     * @return the JPanel that is the character page.
     */
    /*private JPanel generateCharacterPage(String charName, Icon charIcon) {
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        final JLabel label2 = new JLabel();
        label2.setIcon(charIcon);
        label2.setText("");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 0, 5, 20);
        panel4.add(label2, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(12, 1, new Insets(5, 5, 5, 5), -1, -1));
        panel5.setAlignmentY(0.5f);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(22, 0, 0, 0);
        panel4.add(panel5, gbc);
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        JTextField devNotesTextField = new JTextField();
        Font emptyNotesFieldTextFieldFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, devNotesTextField.getFont());
        if (emptyNotesFieldTextFieldFont != null) {
            devNotesTextField.setFont(emptyNotesFieldTextFieldFont);
        }
        devNotesTextField.setHorizontalAlignment(10);
        devNotesTextField.setInheritsPopupMenu(false);
        devNotesTextField.setMargin(new Insets(2, 6, 2, 6));
        devNotesTextField.setOpaque(true);
        devNotesTextField.setRequestFocusEnabled(true);
        devNotesTextField.setText("[ Empty Notes Field ]");
        devNotesTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                devNotesTextField.setText("");
            }
        });
        panel5.add(devNotesTextField,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(50, -1), null, 0, false));
        JComboBox<String> devSet1SelectionBox = new JComboBox<>();
        devSet1SelectionBox.setAutoscrolls(false);
        devSet1SelectionBox.setEditable(false);
        Font comboBox1Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, devSet1SelectionBox.getFont());
        if (comboBox1Font != null) {
            devSet1SelectionBox.setFont(comboBox1Font);
        }
        devSet1SelectionBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        defaultComboBoxModel1.addElement("[ Empty Artifact Selector ]");
        addArtifactSets(defaultComboBoxModel1);
        devSet1SelectionBox.setModel(defaultComboBoxModel1);
        devSet1SelectionBox.setToolTipText("");
        panel5.add(devSet1SelectionBox,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setAutoscrolls(true);
        Font label3Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label3.getFont());
        if (label3Font != null) {
            label3.setFont(label3Font);
        }
        label3.setText(charName);
        panel5.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JComboBox<String> comboBox2 = new JComboBox<>();
        comboBox2.setAutoscrolls(false);
        comboBox2.setEditable(false);
        Font comboBox2Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, comboBox2.getFont());
        if (comboBox2Font != null) {
            comboBox2.setFont(comboBox2Font);
        }
        comboBox2.setInheritsPopupMenu(false);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("[ Amos' Bow Selected ]");
        comboBox2.setModel(defaultComboBoxModel1);
        comboBox2.setToolTipText("");
        panel5.add(comboBox2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setAutoscrolls(true);
        Font label3Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Amos's Bow");
        panel5.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final DefaultComboBoxModel<String> defaultComboBoxModel2 = new DefaultComboBoxModel<>();
        defaultComboBoxModel2.addElement("[ Amos' Bow Selected ]");
        comboBox2.setModel(defaultComboBoxModel2);
        comboBox2.setToolTipText("");
        panel5.add(comboBox2,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setAlignmentY(0.5f);
        label4.setAutoscrolls(true);
        label4.setDoubleBuffered(false);
        Font label4Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setHorizontalAlignment(10);
        label4.setHorizontalTextPosition(11);
        label4.setInheritsPopupMenu(true);
        label4.setText("Shimenava's Reminiscence");
        label4.setVerticalAlignment(0);
        label4.setVerticalTextPosition(0);
        panel5.add(label4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(177, 23), null, 1, false));
        devSet1SelectionBox = new JComboBox();
        devSet1SelectionBox.setAutoscrolls(false);
        devSet1SelectionBox.setEditable(true);
        Font comboBox1Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, devSet1SelectionBox.getFont());
        if (comboBox1Font != null) devSet1SelectionBox.setFont(comboBox1Font);
        devSet1SelectionBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("[ Empty Set 1 Selector ]");
        devSet1SelectionBox.setModel(defaultComboBoxModel2);
        devSet1SelectionBox.setToolTipText("");
        panel5.add(devSet1SelectionBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JComboBox comboBox3 = new JComboBox();
        comboBox3.setAutoscrolls(false);
        comboBox3.setEditable(true);
        Font comboBox3Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 14, comboBox3.getFont());
        if (comboBox3Font != null) comboBox3.setFont(comboBox3Font);
        comboBox3.setInheritsPopupMenu(false);
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("[ Empty Set 2 Selector ]");
        comboBox3.setModel(defaultComboBoxModel3);
        comboBox3.setToolTipText("");
        panel5.add(comboBox3, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setAutoscrolls(true);
        Font label5Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Noblesse Oblige");
        panel5.add(label5, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(177, 23), null, 1, false));
        devTopSpacer = new JPanel();
        devTopSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 40, 0), -1, -1));
        panel5.add(devTopSpacer, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devWeaponSet1Spacer = new JPanel();
        devWeaponSet1Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(48, 0, 0, 0), -1, -1));
        panel5.add(devWeaponSet1Spacer, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devSet1Set2Spacer = new JPanel();
        devSet1Set2Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(43, 0, 0, 0), -1, -1));
        panel5.add(devSet1Set2Spacer, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devBottomSpacer = new JPanel();
        devBottomSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(43, 0, 0, 0), -1, -1));
        panel5.add(devBottomSpacer, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setHorizontalAlignment(4);
        label6.setHorizontalTextPosition(4);
        label6.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Weapons/Bow_5star/Elegy for the End.png")));
        label6.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel4.add(label6, gbc);
        final JLabel label7 = new JLabel();
        label7.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Characters/Albedo.png")));
        label7.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 0, 5, 20);
        panel4.add(label7, gbc);
        final JLabel label8 = new JLabel();
        label8.setHorizontalAlignment(4);
        label8.setHorizontalTextPosition(4);
        label8.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Artifacts/Archaic Petra.png")));
        label8.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        panel4.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setHorizontalAlignment(4);
        label9.setHorizontalTextPosition(4);
        label9.setIcon(new ImageIcon(getClass().getResource("/Files/Images/Artifacts/Noblesse Oblige.png")));
        label9.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        panel4.add(label9, gbc);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(6, 2, new Insets(3, 3, 3, 3), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(22, 0, 0, 100);
        panel4.add(panel6, gbc);
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        JCheckBox devArtifactListingCheckBox = new JCheckBox();
        Font artifactListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, devArtifactListingCheckBox.getFont());
        if (artifactListingCheckBoxFont != null) {
            devArtifactListingCheckBox.setFont(artifactListingCheckBoxFont);
        }
        devArtifactListingCheckBox.setText("Artifact Listing");
        panel6.add(devArtifactListingCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devTalentListingCheckBox = new JCheckBox();
        Font talentListingCheckBoxFont = this.$$$getFont$$$("Source Code Pro", -1, 14, devTalentListingCheckBox.getFont());
        if (talentListingCheckBoxFont != null) {
            devTalentListingCheckBox.setFont(talentListingCheckBoxFont);
        }
        devTalentListingCheckBox.setText("Talent Listing");
        panel6.add(devTalentListingCheckBox, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setText("Domain listings");
        panel6.add(label10, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox devWepMatListingCheckbox = new JCheckBox();
        Font checkBox1Font = this.$$$getFont$$$("Source Code Pro", -1, 14, devWepMatListingCheckbox.getFont());
        if (checkBox1Font != null) {
            devWepMatListingCheckbox.setFont(checkBox1Font);
        }
        devWepMatListingCheckbox.setText("Weapon Material Listing");
        panel6.add(devWepMatListingCheckbox, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devCloseButton = new JButton();
        devCloseButton.setBackground(new Color(-2725532));
        Font CLOSEButtonFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devCloseButton.getFont());
        if (CLOSEButtonFont != null) devCloseButton.setFont(CLOSEButtonFont);
        devCloseButton.setForeground(new Color(-6427));
        devCloseButton.setHideActionText(false);
        devCloseButton.setText("CLOSE");
        panel6.add(devCloseButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devSaveButton = new JButton();
        devSaveButton.setBackground(new Color(-6039919));
        devSaveButton.setForeground(new Color(-394241));
        devSaveButton.setText("SAVE");
        panel6.add(devSaveButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devSetDetailsTextArea = new JTextArea();
        devSetDetailsTextArea.setEditable(false);
        devSetDetailsTextArea.setFocusable(false);
        Font setDetailsTextAreaFont = this.$$$getFont$$$("Source Code Pro", -1, 12, devSetDetailsTextArea.getFont());
        if (setDetailsTextAreaFont != null) devSetDetailsTextArea.setFont(setDetailsTextAreaFont);
        devSetDetailsTextArea.setText("[ Set Details ]");
        panel6.add(devSetDetailsTextArea, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(150, 280), new Dimension(150, 280), null, 0, false));
    }*/

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        devMainPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        devMainPanel.setBackground(new Color(-2702645));
        devMainPanel.setEnabled(true);
        final Spacer spacer1 = new Spacer();
        devMainPanel.add(spacer1,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
                        GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        devMainTabLabel = new JLabel();
        Font devMainTabLabelFont =
                this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, devMainTabLabel.getFont());
        if (devMainTabLabelFont != null) {
            devMainTabLabel.setFont(devMainTabLabelFont);
        }
        devMainTabLabel.setForeground(new Color(-14940151));
        devMainTabLabel.setText("Genshin Domain Application");
        devMainPanel.add(devMainTabLabel,
                new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devPanelWithMainTabbedPane = new JPanel();
        devPanelWithMainTabbedPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        devPanelWithMainTabbedPane.setBackground(new Color(-3689540));
        devMainPanel.add(devPanelWithMainTabbedPane,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devTabbedPane = new JTabbedPane();
        Font devTabbedPaneFont = this.$$$getFont$$$("Source Code Pro Black", Font.BOLD, 20, devTabbedPane.getFont());
        if (devTabbedPaneFont != null) {
            devTabbedPane.setFont(devTabbedPaneFont);
        }
        devTabbedPane.setTabPlacement(1);
        devPanelWithMainTabbedPane.add(devTabbedPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
                        new Dimension(200, 200), null, 0, false));
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
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devCharacterTab.add(devSearchResultPanel, gbc);
        devSearchButton = new JButton();
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
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devWeaponTab.add(devWeaponTabPanel, gbc);
        weapon_card = new JPanel();
        weapon_card.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        devWeaponTabPanel.add(weapon_card, gbc);
        weapon_card.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
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
        weapon_card.add(devWeaponIcon,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devWepMatListingCheckbox = new JCheckBox();
        devWepMatListingCheckbox.setText("Weapon Listing");
        weapon_card.add(devWepMatListingCheckbox,
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
                new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/Aerosiderite.png")));
        devWepMaterialPreview.setText("");
        devWepMaterialPreview.setVerticalAlignment(0);
        devWepMaterialPreview.setVerticalTextPosition(3);
        weapon_card.add(devWepMaterialPreview,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devWepTypeLabel = new JLabel();
        Font devWepTypeLabelFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, -1, devWepTypeLabel.getFont());
        if (devWepTypeLabelFont != null) {
            devWepTypeLabel.setFont(devWepTypeLabelFont);
        }
        devWepTypeLabel.setText("Type: Bow ");
        weapon_card.add(devWepTypeLabel,
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
        devWeaponsTabSearchbar.setText("Search by name or type!");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        devWeaponTab.add(devWeaponsTabSearchbar, gbc);
        devWeaponTabSearchButton = new JButton();
        devWeaponTabSearchButton.setMaximumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setMinimumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setPreferredSize(new Dimension(50, 30));
        devWeaponTabSearchButton.setText("✓");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        devWeaponTab.add(devWeaponTabSearchButton, gbc);
        devCharacterCardMainPanel = new JPanel();
        devCharacterCardMainPanel.setLayout(new GridBagLayout());
        devTabbedPane.addTab("Untitled", devCharacterCardMainPanel);
        devCharacterCardSelectorPanel = new JPanel();
        devCharacterCardSelectorPanel.setLayout(new GridLayoutManager(12, 1, new Insets(5, 5, 5, 5), -1, -1));
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
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("[ Amos' Bow Selected ]");
        devWeaponSelectorBox.setModel(defaultComboBoxModel1);
        devWeaponSelectorBox.setToolTipText("");
        devCharacterCardSelectorPanel.add(devWeaponSelectorBox,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
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
                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
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
                new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
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
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("[ Empty Set 1 Selector ]");
        devSet1SelectionBox.setModel(defaultComboBoxModel2);
        devSet1SelectionBox.setToolTipText("");
        devCharacterCardSelectorPanel.add(devSet1SelectionBox,
                new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
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
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("[ Empty Set 2 Selector ]");
        devSet2SelectionBox.setModel(defaultComboBoxModel3);
        devSet2SelectionBox.setToolTipText("");
        devCharacterCardSelectorPanel.add(devSet2SelectionBox,
                new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
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
                new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));
        devTopSpacer = new JPanel();
        devTopSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 40, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devTopSpacer,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devWeaponSet1Spacer = new JPanel();
        devWeaponSet1Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(48, 0, 0, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devWeaponSet1Spacer,
                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devSet1Set2Spacer = new JPanel();
        devSet1Set2Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(43, 0, 0, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devSet1Set2Spacer,
                new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        devBottomSpacer = new JPanel();
        devBottomSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(43, 0, 0, 0), -1, -1));
        devCharacterCardSelectorPanel.add(devBottomSpacer,
                new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
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
        devButtonAndCheckboxPanel.setLayout(new GridLayoutManager(6, 2, new Insets(3, 3, 3, 3), -1, -1));
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
        devArtifactListingCheckBox = new JCheckBox();
        Font devArtifactListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, devArtifactListingCheckBox.getFont());
        if (devArtifactListingCheckBoxFont != null) {
            devArtifactListingCheckBox.setFont(devArtifactListingCheckBoxFont);
        }
        devArtifactListingCheckBox.setText("Artifact Listing");
        devButtonAndCheckboxPanel.add(devArtifactListingCheckBox,
                new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devTalentListingCheckBox = new JCheckBox();
        Font devTalentListingCheckBoxFont =
                this.$$$getFont$$$("Source Code Pro", -1, 14, devTalentListingCheckBox.getFont());
        if (devTalentListingCheckBoxFont != null) {
            devTalentListingCheckBox.setFont(devTalentListingCheckBoxFont);
        }
        devTalentListingCheckBox.setText("Talent Listing");
        devButtonAndCheckboxPanel.add(devTalentListingCheckBox,
                new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devDomainListingsLabel = new JLabel();
        Font devDomainListingsLabelFont =
                this.$$$getFont$$$("Source Code Pro", Font.BOLD, 18, devDomainListingsLabel.getFont());
        if (devDomainListingsLabelFont != null) {
            devDomainListingsLabel.setFont(devDomainListingsLabelFont);
        }
        devDomainListingsLabel.setText("Domain listings");
        devButtonAndCheckboxPanel.add(devDomainListingsLabel,
                new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        devWepMatListingLabel = new JCheckBox();
        Font devWepMatListingLabelFont = this.$$$getFont$$$("Source Code Pro", -1, 14, devWepMatListingLabel.getFont());
        if (devWepMatListingLabelFont != null) {
            devWepMatListingLabel.setFont(devWepMatListingLabelFont);
        }
        devWepMatListingLabel.setText("Weapon Material Listing");
        devButtonAndCheckboxPanel.add(devWepMatListingLabel,
                new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
                new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW,
                        new Dimension(150, 280), new Dimension(150, 280), null, 0, false));
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

