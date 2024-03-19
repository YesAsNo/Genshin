package Files;

import static Files.ToolData.artifactsFlattened;
import static Files.ToolGUI.CHARACTER_LIMIT;
import static Files.ToolGUI.CLOSE_TEXT;
import static Files.ToolGUI.FIVE_STAR_WEAPON_DELIMITER;
import static Files.ToolGUI.FOUR_STAR_WEAPON_DELIMITER;
import static Files.ToolGUI.TOOLTIP_FOR_LABELS_WITHOUT_ICON;
import static Files.ToolGUI.TOOLTIP_FOR_LABELS_WITH_ICON;
import static Files.ToolGUI.TOOLTIP_FOR_WEAPON_ICON_LABEL;
import static Files.ToolGUI.TOOLTIP_FOR_WEAPON_NAME_LABEL;
import static Files.ToolGUI.UNKNOWN_ARTIFACT;
import static Files.ToolGUI.UNKNOWN_SET_MESSAGE;
import static Files.ToolGUI.UNKNOWN_WEAPON_MESSAGE;
import static Files.ToolGUI.UNKNOWN_WEAPON_PATH;
import static Files.ToolGUI.generateArtifactIconPath;
import static Files.ToolGUI.generateWeaponPath;
import static Files.ToolGUI.lookUpWeaponCategoryForCharacter;
import static Files.ToolGUI.lookUpWeaponRarityAndType;
import static Files.ToolGUI.lookUpWeapons;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;

public class CharacterCardGUI extends JFrame {
    public CharacterCardGUI(CharacterCard characterCard){
        setTitle(characterCard.getCharacterName() + " Character Overview");
        setContentPane(generateCharacterPage(characterCard));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

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
        dcmb.addAll(lookUpWeapons(ToolData.RARITY.FOUR_STAR, weaponType));
        dcmb.addElement(FIVE_STAR_WEAPON_DELIMITER);
        dcmb.addAll(lookUpWeapons(ToolData.RARITY.FIVE_STAR, weaponType));
    }
    private JPanel getMiddleSelectorPanel(JPanel jpanel) {
        JPanel middleSelectorPanel = new JPanel();
        middleSelectorPanel.setLayout(new GridLayoutManager(16, 1, new Insets(5, 5, 5, 5), -1, -1));
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
                $$$getFont$$$(Font.BOLD, 14, notesTextField.getFont());
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
                $$$getFont$$$(Font.BOLD, 18, characterNameLabel.getFont());
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
                $$$getFont$$$(Font.BOLD, 14, weaponSelectionBox.getFont());
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
                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        return weaponSelectionBox;
    }

    private JLabel getWeaponNameLabel(CharacterCard characterCard, JPanel jpanel) {
        JLabel weaponNameLabel = new JLabel();
        weaponNameLabel.setAutoscrolls(true);
        Font weaponNameLabelFont = $$$getFont$$$(Font.BOLD, 18, weaponNameLabel.getFont());
        if (weaponNameLabelFont != null) {
            weaponNameLabel.setFont(weaponNameLabelFont);
        }
        weaponNameLabel.setText(characterCard.getWeapon());
        jpanel.add(weaponNameLabel,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));
        return weaponNameLabel;
    }

    private JLabel getSet1NameLabel(CharacterCard characterCard, JPanel jpanel) {
        JLabel set1NameLabel = new JLabel();
        set1NameLabel.setAlignmentY(0.5f);
        set1NameLabel.setAutoscrolls(true);
        set1NameLabel.setDoubleBuffered(false);
        Font set1NameLabelFont = $$$getFont$$$(Font.BOLD, 18, set1NameLabel.getFont());
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
                new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));
        return set1NameLabel;
    }

    private JComboBox<String> getSet1ComboBox(CharacterCard characterCard, JPanel jpanel) {
        JComboBox<String> set1ComboBox = new JComboBox<>();
        set1ComboBox.setAutoscrolls(false);
        set1ComboBox.setEditable(false);
        set1ComboBox.setFocusable(false);
        Font set1comboBoxFont = $$$getFont$$$(Font.BOLD, 14, set1ComboBox.getFont());
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
                new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        return set1ComboBox;
    }

    private JComboBox<String> getSet2ComboBox(CharacterCard characterCard, JPanel jpanel) {
        JComboBox<String> set2ComboBox = new JComboBox<>();
        set2ComboBox.setAutoscrolls(false);
        set2ComboBox.setEditable(false);
        set2ComboBox.setFocusable(false);
        Font set2ComboBoxFont = $$$getFont$$$(Font.BOLD, 14, set2ComboBox.getFont());
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
                new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        return set2ComboBox;
    }

    private JLabel getSet2NameLabel(CharacterCard characterCard, JPanel jpanel) {
        JLabel set2NameLabel = new JLabel();
        set2NameLabel.setAutoscrolls(true);
        Font set2NameLabelFont = $$$getFont$$$(Font.BOLD, 18, set2NameLabel.getFont());
        if (set2NameLabelFont != null) {
            set2NameLabel.setFont(set2NameLabelFont);
        }
        set2NameLabel.setToolTipText(TOOLTIP_FOR_LABELS_WITHOUT_ICON);
        set2NameLabel.setText(characterCard.getArtifactSet2());
        jpanel.add(set2NameLabel,
                new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false));
        return set2NameLabel;
    }

    private void getCharacterWeaponSpacer(JPanel jpanel) {
        JPanel characterWeaponSpacer = new JPanel();
        characterWeaponSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 7, 0), -1, -1));
        jpanel.add(characterWeaponSpacer,
                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
    }

    private void getWeaponSet1Spacer(JPanel jpanel) {
        JPanel weaponSet1Spacer = new JPanel();
        weaponSet1Spacer.setLayout(new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1));
        jpanel.add(weaponSet1Spacer,
                new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
    }

    private void getSet2Spacer(JPanel jpanel) {
        JPanel secondSetSpacer = new JPanel();
        secondSetSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1));
        jpanel.add(secondSetSpacer,
                new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
    }

    private void getYetAnotherSpacerOnTheBottom(JPanel jpanel) {
        JPanel bottomSpacer = new JPanel();
        bottomSpacer.setLayout(new GridLayoutManager(1, 1, new Insets(20, 0, 0, 0), -1, -1));
        jpanel.add(bottomSpacer,
                new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
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

    private JCheckBox getArtifactSet1ListingCheckBox(CharacterCard characterCard, JPanel jpanel) {
        JCheckBox artifactListingCheckBox = new JCheckBox();
        Font artifactListingCheckBoxFont =
                $$$getFont$$$(-1, 14, artifactListingCheckBox.getFont());
        if (artifactListingCheckBoxFont != null) {
            artifactListingCheckBox.setFont(artifactListingCheckBoxFont);
        }
        artifactListingCheckBox.setText("Artifact Listing");
        if (characterCard.getArtifactSet1Status() || characterCard.getArtifactSet2Status()) {
            artifactListingCheckBox.doClick();
        }
        jpanel.add(artifactListingCheckBox,
                new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return artifactListingCheckBox;
    }
    private JCheckBox getArtifactSet2ListingCheckBox(CharacterCard characterCard, JPanel jpanel) {
        JCheckBox artifactListingCheckBox = new JCheckBox();
        Font artifactListingCheckBoxFont =
                $$$getFont$$$(-1, 14, artifactListingCheckBox.getFont());
        if (artifactListingCheckBoxFont != null) {
            artifactListingCheckBox.setFont(artifactListingCheckBoxFont);
        }
        artifactListingCheckBox.setText("Artifact Listing");
        if (characterCard.getArtifactSet1Status() || characterCard.getArtifactSet2Status()) {
            artifactListingCheckBox.doClick();
        }
        jpanel.add(artifactListingCheckBox,
                new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return artifactListingCheckBox;
    }

    private JCheckBox getTalentListingCheckBox(CharacterCard characterCard, JPanel jpanel) {
        JCheckBox talentListingCheckBox = new JCheckBox();
        Font talentListingCheckBoxFont = $$$getFont$$$(-1, 14, talentListingCheckBox.getFont());
        if (talentListingCheckBoxFont != null) {
            talentListingCheckBox.setFont(talentListingCheckBoxFont);
        }
        talentListingCheckBox.setText("Talent Listing");
        if (characterCard.getTalentStatus()) {
            talentListingCheckBox.doClick();
        }
        jpanel.add(talentListingCheckBox,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return talentListingCheckBox;
    }

    private void getDomainListingsLabel(JPanel jpanel) {
        JLabel domainListingsLabel = new JLabel();
        Font domainListingsLabelFont =
                $$$getFont$$$(Font.BOLD, 18, domainListingsLabel.getFont());
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
                $$$getFont$$$(-1, 14, weaponMaterialListingCheckbox.getFont());
        if (weaponMaterialListingCheckboxFont != null) {
            weaponMaterialListingCheckbox.setFont(weaponMaterialListingCheckboxFont);
        }
        weaponMaterialListingCheckbox.setText("Weapon Material Listing");
        if (characterCard.getWeaponStatus()) {
            weaponMaterialListingCheckbox.doClick();
        }
        jpanel.add(weaponMaterialListingCheckbox,
                new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return weaponMaterialListingCheckbox;
    }

    private JButton getCloseButton(JPanel jpanel) {
        JButton closeButton = new JButton();
        closeButton.setBackground(new Color(-2725532));
        Font closeButtonFont = $$$getFont$$$(Font.BOLD, -1, closeButton.getFont());
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
        Font setDetailsTextAreaFont = $$$getFont$$$(-1, 12, setDetailsTextArea.getFont());
        if (setDetailsTextAreaFont != null) {
            setDetailsTextArea.setFont(setDetailsTextAreaFont);
        }
        setDetailsTextArea.setText("[ Set Details ]");
        jpanel.add(setDetailsTextArea,
                new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW,
                        new Dimension(150, 380), new Dimension(150, 380), null, 0, false));
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
        JCheckBox artifactSet1ListingCheckBox = getArtifactSet1ListingCheckBox(characterCard, middleSelectorPanel);
        JCheckBox artifactSet2ListingCheckBox = getArtifactSet2ListingCheckBox(characterCard, middleSelectorPanel);
        JCheckBox talentListingCheckBox = getTalentListingCheckBox(characterCard, middleSelectorPanel);
        getDomainListingsLabel(checkboxAndButtonPanel);
        JCheckBox weaponMaterialListingCheckbox = getWeaponMaterialListingCheckbox(characterCard, middleSelectorPanel);

        artifactSet1ListingCheckBox.addItemListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_ONE));
        artifactSet2ListingCheckBox.addItemListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_TWO));
        weaponMaterialListingCheckbox.addItemListener(new UpdateCharacterCardListener(characterCard,
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_WEAPON_MATERIALS));
        talentListingCheckBox.addItemListener(new UpdateCharacterCardListener(characterCard,
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_TALENT_MATERIALS));

        JButton closeButton = getCloseButton(checkboxAndButtonPanel);
        JButton saveButton = getSaveButton(checkboxAndButtonPanel);
        JTextArea setDetailsTextArea = getSetDetailsTextArea(checkboxAndButtonPanel);

        closeButton.addActionListener(new CloseButtonListener(this));
        saveButton.addActionListener(new SaveButtonListener(characterCard));
        set1ComboBox.addActionListener(new UpdateTextAreaListener(setDetailsTextArea));
        set2ComboBox.addActionListener(new UpdateTextAreaListener(setDetailsTextArea));

        return templateTab;
    }
    private Font $$$getFont$$$(int style, int size, Font currentFont) {
        if (currentFont == null) {
            return null;
        }
        String resultName;
        Font testFont = new Font("Source Code Pro", Font.PLAIN, 10);
        if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
            resultName = "Source Code Pro";
        } else {
            resultName = currentFont.getName();
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
                size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) :
                new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }
}
