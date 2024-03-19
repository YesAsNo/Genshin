package Files;

import static Files.ToolData.RESOURCE_TYPE;
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
import static Files.ToolGUI.generateResourceIconPath;
import static Files.ToolGUI.lookUpWeaponCategoryForCharacter;
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


    private JLabel getNameLabel(CharacterCard characterCard, JPanel jpanel, GridConstraints gc, ToolData.CHARACTER_CARD_DATA_FIELD dataField) {
        JLabel setNameLabel = new JLabel();
        setNameLabel.setAutoscrolls(true);
        Font nameLabelFont = $$$getFont$$$(Font.BOLD, 18, setNameLabel.getFont());
        if (nameLabelFont != null) {
            setNameLabel.setFont(nameLabelFont);
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE){
            setNameLabel.setText(characterCard.getArtifactSet1());
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO){
            setNameLabel.setText(characterCard.getArtifactSet2());
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON){
            setNameLabel.setText(characterCard.getWeapon());
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.NAME){
            setNameLabel.setText(characterCard.getCharacterName());
        }

        setNameLabel.setToolTipText(TOOLTIP_FOR_LABELS_WITHOUT_ICON);
        jpanel.add(setNameLabel, gc);
        return setNameLabel;
    }

    private JComboBox<String> getSetComboBox(CharacterCard characterCard, JPanel jpanel, GridConstraints gc, ToolData.CHARACTER_CARD_DATA_FIELD dataField) {
        JComboBox<String> setComboBox = new JComboBox<>();
        setComboBox.setAutoscrolls(false);
        setComboBox.setEditable(false);
        setComboBox.setFocusable(false);
        Font set1comboBoxFont = $$$getFont$$$(Font.BOLD, 14, setComboBox.getFont());
        if (set1comboBoxFont != null) {
            setComboBox.setFont(set1comboBoxFont);
        }
        setComboBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<String> defaultComboBoxModelForSet1Selector = new DefaultComboBoxModel<>();
        defaultComboBoxModelForSet1Selector.addElement(UNKNOWN_SET_MESSAGE);
        defaultComboBoxModelForSet1Selector.addAll(artifactsFlattened);
        setComboBox.setModel(defaultComboBoxModelForSet1Selector);
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE){
            setComboBox.setSelectedItem(
                    characterCard.getArtifactSet1().isEmpty() ? UNKNOWN_SET_MESSAGE : characterCard.getArtifactSet1());
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO){
            setComboBox.setSelectedItem(
                    characterCard.getArtifactSet2().isEmpty() ? UNKNOWN_SET_MESSAGE : characterCard.getArtifactSet2());
        }

        jpanel.add(setComboBox, gc);
        return setComboBox;
    }

    private void generateSpacer(JPanel jpanel, GridLayoutManager gridLayoutManager, GridConstraints gridConstraints) {
        JPanel characterWeaponSpacer = new JPanel();
        characterWeaponSpacer.setLayout(gridLayoutManager);
        jpanel.add(characterWeaponSpacer, gridConstraints);
    }

    private JLabel getWeaponIcon(CharacterCard characterCard, JPanel jpanel) {
        JLabel weaponIcon = new JLabel();
        weaponIcon.setHorizontalAlignment(4);
        weaponIcon.setHorizontalTextPosition(4);
        String savedWeaponName = characterCard.getWeapon();
        if (savedWeaponName.isEmpty()) {
            weaponIcon.setIcon(new ImageIcon(UNKNOWN_WEAPON_PATH));
        } else {
            weaponIcon.setIcon(new ImageIcon(generateResourceIconPath(savedWeaponName, RESOURCE_TYPE.WEAPON)));
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
            set1Icon.setIcon(new ImageIcon(generateResourceIconPath(UNKNOWN_ARTIFACT,RESOURCE_TYPE.ARTIFACT)));
        } else {
            set1Icon.setIcon(new ImageIcon(generateResourceIconPath(savedArtifactSet1Name,RESOURCE_TYPE.ARTIFACT)));
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
            set2Icon.setIcon(new ImageIcon(generateResourceIconPath(UNKNOWN_ARTIFACT, RESOURCE_TYPE.ARTIFACT)));
        } else {
            set2Icon.setIcon(new ImageIcon(generateResourceIconPath(savedArtifactSet2Name,RESOURCE_TYPE.ARTIFACT)));
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

    private JCheckBox getListingCheckBox(CharacterCard characterCard, JPanel jpanel, String title,
                                         GridConstraints gridConstraints,
                                         ToolData.CHARACTER_CARD_DATA_FIELD dataField) {
        JCheckBox listingCheckBox = new JCheckBox();
        Font listingCheckBoxFont =
                $$$getFont$$$(-1, 14, listingCheckBox.getFont());
        if (listingCheckBoxFont != null) {
            listingCheckBox.setFont(listingCheckBoxFont);
        }
        listingCheckBox.setText(title);
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_ONE){
            if (characterCard.getArtifactSet1Status()) {
                listingCheckBox.doClick();
            }
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_TWO){
            if (characterCard.getArtifactSet2Status()) {
                listingCheckBox.doClick();
            }
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_TALENT_MATERIALS){
            if (characterCard.getTalentStatus()) {
                listingCheckBox.doClick();
            }
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_WEAPON_MATERIALS){
            if (characterCard.getWeaponStatus()) {
                listingCheckBox.doClick();
            }
        }
        jpanel.add(listingCheckBox,gridConstraints);
        return listingCheckBox;
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
        getNameLabel(characterCard, middleSelectorPanel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                false),
                ToolData.CHARACTER_CARD_DATA_FIELD.NAME);
        JComboBox<String> weaponSelectionBox = getWeaponSelectionBox(characterCard, middleSelectorPanel);
        JLabel weaponNameLabel = getNameLabel(characterCard, middleSelectorPanel,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                false),
                ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON);
        JLabel set1NameLabel = getNameLabel(characterCard, middleSelectorPanel,
                new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                new Dimension(177, 23), null, 1, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE);
        JLabel set2NameLabel = getNameLabel(characterCard, middleSelectorPanel,
                new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                new Dimension(177, 23), null, 1, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO);
        JComboBox<String> set1ComboBox = getSetComboBox(characterCard, middleSelectorPanel,
                new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE);
        JComboBox<String> set2ComboBox = getSetComboBox(characterCard, middleSelectorPanel,
                new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO);

        generateSpacer(middleSelectorPanel,
                new GridLayoutManager(1, 1, new Insets(0, 0, 7, 0), -1, -1),
                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                0, false));
        generateSpacer(middleSelectorPanel,
                new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1),
                new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        generateSpacer(middleSelectorPanel,
                new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1),
                new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                0, false));
        generateSpacer(middleSelectorPanel,
                new GridLayoutManager(1, 1, new Insets(20, 0, 0, 0), -1, -1),
                new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));

        JLabel weaponIconLabel = getWeaponIcon(characterCard, templateTab);
        weaponSelectionBox.addActionListener(new UpdateLabelListener(weaponNameLabel, weaponIconLabel, ToolData.RESOURCE_TYPE.WEAPON));
        weaponSelectionBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON));
        weaponNameLabel.setToolTipText(TOOLTIP_FOR_WEAPON_NAME_LABEL);
        getCharLabel(characterCard.getCharacterIcon(), templateTab);
        JLabel set1Icon = getSet1Icon(characterCard, templateTab);
        JLabel set2Icon = getSet2Icon(characterCard, templateTab);

        set1ComboBox.addActionListener(new UpdateLabelListener(set1NameLabel, set1Icon, ToolData.RESOURCE_TYPE.ARTIFACT));
        set2ComboBox.addActionListener(new UpdateLabelListener(set2NameLabel, set2Icon, ToolData.RESOURCE_TYPE.ARTIFACT));
        set1ComboBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE));
        set2ComboBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO));

        JPanel checkboxAndButtonPanel = getCheckboxAndButtonPanel(templateTab);
        JCheckBox artifactSet1ListingCheckBox = getListingCheckBox(characterCard, middleSelectorPanel,"Artifact Listing",
                new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_ONE);
        JCheckBox artifactSet2ListingCheckBox = getListingCheckBox(characterCard, middleSelectorPanel,"Artifact Listing",
                new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_TWO);
        JCheckBox talentListingCheckBox = getListingCheckBox(characterCard, middleSelectorPanel,"Talent Listing",
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_TALENT_MATERIALS);
        JCheckBox weaponMaterialListingCheckbox = getListingCheckBox(characterCard, middleSelectorPanel,"Weapon Material Listing",
                new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_WEAPON_MATERIALS);
        getDomainListingsLabel(checkboxAndButtonPanel);
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
