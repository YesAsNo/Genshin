package Files;

import static Files.ToolData.AVAILABLE_FONTS;
import static Files.ToolData.RESOURCE_TYPE;
import static Files.ToolData.changeFont;
import static Files.ToolData.getFlattenedData;
import static Files.ToolData.getResizedResourceIcon;
import static Files.ToolData.getResourceIcon;
import static Files.ToolData.lookUpWeaponCategoryForCharacter;
import static Files.ToolData.lookUpWeapons;
import static Files.ToolGUI.CHARACTER_LIMIT;
import static Files.ToolGUI.EMPTY_SET_SELECTOR;
import static Files.ToolGUI.EMPTY_WEAPON_SELECTOR;
import static Files.ToolGUI.FIVE_STAR_WEAPON_DELIMITER;
import static Files.ToolGUI.FOUR_STAR_WEAPON_DELIMITER;
import static Files.ToolGUI.UNKNOWN_ARTIFACT;
import static Files.ToolGUI.UNKNOWN_WEAPON;

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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CharacterCardGUI extends JFrame {
    private final Map<String, JLabel> weaponLabels = new LinkedHashMap<>();
    private final Map<String, JLabel> artifactLabels = new LinkedHashMap<>();
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
        JLabel label = new JLabel();
        label.setText(EMPTY_WEAPON_SELECTOR);
        ImageIcon default_img = getResizedResourceIcon(UNKNOWN_WEAPON,RESOURCE_TYPE.WEAPON_NAME,20);
        label.setIcon(default_img);
        weaponLabels.put(EMPTY_WEAPON_SELECTOR,label);
        for (ToolData.WEAPON_RARITY rarity: ToolData.WEAPON_RARITY.values()){
            String weaponType = lookUpWeaponCategoryForCharacter(charName);
            label = new JLabel();
            switch(rarity){
                case FIVE_STAR -> label.setText(FIVE_STAR_WEAPON_DELIMITER);
                case FOUR_STAR -> label.setText(FOUR_STAR_WEAPON_DELIMITER);
            }
            label.setIcon(default_img);
            weaponLabels.put(label.getText(),label);
            List<String> weapons = lookUpWeapons(rarity, weaponType);
            Collections.sort(weapons);
            for (String weapon: weapons){
                label = new JLabel();
                label.setIcon(getResizedResourceIcon(weapon,RESOURCE_TYPE.WEAPON_NAME,20));
                label.setText(weapon);
                weaponLabels.put(weapon,label);
            }
        }
        dcmb.addAll(weaponLabels.values());
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
        changeFont(notesTextField, AVAILABLE_FONTS.TEXT_FONT, 14.0F);
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


    private JComboBox<JLabel> getWeaponSelectionBox(CharacterCard characterCard, JPanel jpanel) {
        JComboBox<JLabel> weaponSelectionBox = new JComboBox<>();
        weaponSelectionBox.setAutoscrolls(false);
        weaponSelectionBox.setEditable(false);
        changeFont(weaponSelectionBox, AVAILABLE_FONTS.HEADER_FONT, 14.0F);
        weaponSelectionBox.setInheritsPopupMenu(false);
        final WeaponSelectorComboBoxModel weaponSelectorComboBoxModel = new WeaponSelectorComboBoxModel();
        addAllowedWeapons(weaponSelectorComboBoxModel, characterCard.getCharacterName());
        weaponSelectionBox.setModel(weaponSelectorComboBoxModel);
        weaponSelectionBox.setRenderer(new ComboBoxRenderer());
        weaponSelectionBox.setSelectedItem(characterCard.getWeapon().isEmpty() ? weaponLabels.get(EMPTY_WEAPON_SELECTOR):
                weaponLabels.get(characterCard.getWeapon()));
        jpanel.add(weaponSelectionBox,
                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        return weaponSelectionBox;
    }


    private javax.swing.JLabel getNameLabel(CharacterCard characterCard, JPanel jpanel, GridConstraints gc, ToolData.CHARACTER_CARD_DATA_FIELD dataField) {
        javax.swing.JLabel setNameLabel = new javax.swing.JLabel();
        setNameLabel.setAutoscrolls(true);
        changeFont(setNameLabel, AVAILABLE_FONTS.HEADER_FONT, 18.0F);

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

        jpanel.add(setNameLabel, gc);
        return setNameLabel;
    }

    private JComboBox<JLabel> getSetComboBox(CharacterCard characterCard, JPanel jpanel, GridConstraints gc, ToolData.CHARACTER_CARD_DATA_FIELD dataField) {
        JComboBox<JLabel> setComboBox = new JComboBox<>();
        setComboBox.setAutoscrolls(false);
        setComboBox.setEditable(false);
        setComboBox.setFocusable(false);
        changeFont(setComboBox, AVAILABLE_FONTS.HEADER_FONT, 14.0F);
        setComboBox.setInheritsPopupMenu(false);
        final DefaultComboBoxModel<JLabel> setComboBoxModel = new DefaultComboBoxModel<>();
        JLabel label = new JLabel();
        label.setText(EMPTY_SET_SELECTOR);
        label.setIcon(getResizedResourceIcon(UNKNOWN_ARTIFACT,RESOURCE_TYPE.ARTIFACT_SET,20));
        artifactLabels.put(UNKNOWN_ARTIFACT,label);
        setComboBoxModel.addElement(label);
        for (String artifactName: getFlattenedData(RESOURCE_TYPE.ARTIFACT_SET)){
            label = new JLabel();
            label.setText(artifactName);
            label.setIcon(getResizedResourceIcon(artifactName,RESOURCE_TYPE.ARTIFACT_SET,20));
            artifactLabels.put(artifactName,label);
            setComboBoxModel.addElement(label);
        }
        setComboBox.setModel(setComboBoxModel);
        setComboBox.setRenderer(new ComboBoxRenderer());
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE){
            setComboBox.setSelectedItem(
                    characterCard.getArtifactSet1().isEmpty() ? artifactLabels.get(UNKNOWN_ARTIFACT) : artifactLabels.get(characterCard.getArtifactSet1()));
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO){
            setComboBox.setSelectedItem(
                    characterCard.getArtifactSet2().isEmpty() ? artifactLabels.get(UNKNOWN_ARTIFACT) : artifactLabels.get(characterCard.getArtifactSet2()));
        }

        jpanel.add(setComboBox, gc);
        return setComboBox;
    }
    private void generateSpacer(JPanel jpanel, GridLayoutManager gridLayoutManager, GridConstraints gridConstraints) {
        JPanel characterWeaponSpacer = new JPanel();
        characterWeaponSpacer.setLayout(gridLayoutManager);
        jpanel.add(characterWeaponSpacer, gridConstraints);
    }

    private javax.swing.JLabel getWeaponIcon(CharacterCard characterCard, JPanel jpanel) {
        javax.swing.JLabel weaponIcon = new javax.swing.JLabel();
        weaponIcon.setHorizontalAlignment(4);
        weaponIcon.setHorizontalTextPosition(4);
        String savedWeaponName = characterCard.getWeapon();
        if (savedWeaponName.isEmpty()) {
            weaponIcon.setIcon(getResourceIcon(UNKNOWN_WEAPON,RESOURCE_TYPE.WEAPON_NAME));
        } else {
            weaponIcon.setIcon(getResourceIcon(savedWeaponName,RESOURCE_TYPE.WEAPON_NAME));
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        jpanel.add(weaponIcon, gbc);
        return weaponIcon;
    }

    private void getCharLabel(Icon charIcon, JPanel jpanel) {
        javax.swing.JLabel charLabel = new javax.swing.JLabel();
        charLabel.setIcon(charIcon);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 0, 5, 20);
        jpanel.add(charLabel, gbc);
    }

    private javax.swing.JLabel getSetIcon(CharacterCard characterCard, JPanel jpanel, GridBagConstraints gbc) {
        javax.swing.JLabel setIcon = new javax.swing.JLabel();
        setIcon.setHorizontalAlignment(4);
        setIcon.setHorizontalTextPosition(4);
        String savedArtifactSetName = characterCard.getArtifactSet1();
        if (savedArtifactSetName.isEmpty()) {
            setIcon.setIcon(getResourceIcon(UNKNOWN_ARTIFACT,RESOURCE_TYPE.ARTIFACT_SET));
        } else {
            setIcon.setIcon(getResourceIcon(savedArtifactSetName,RESOURCE_TYPE.ARTIFACT_SET));
        }
        jpanel.add(setIcon, gbc);
        return setIcon;
    }


    private JPanel getRightPanel(JPanel jpanel) {
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
        changeFont(listingCheckBox, AVAILABLE_FONTS.TEXT_FONT, 14.0F);
        listingCheckBox.setText(title);
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_ONE){
            if (characterCard.getArtifactSet1Status()) {
                listingCheckBox.doClick();
            }
            if (characterCard.getArtifactSet1().isEmpty()){
                listingCheckBox.setSelected(false);
                listingCheckBox.setEnabled(false);
            }
        }
        if (dataField == ToolData.CHARACTER_CARD_DATA_FIELD.FARMING_SET_TWO){
            if (characterCard.getArtifactSet2Status()) {
                listingCheckBox.doClick();
            }
            if (characterCard.getArtifactSet2().isEmpty()){
                listingCheckBox.setSelected(false);
                listingCheckBox.setEnabled(false);
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
            if (characterCard.getWeapon().isEmpty()){
                listingCheckBox.setSelected(false);
                listingCheckBox.setEnabled(false);
            }
        }
        jpanel.add(listingCheckBox,gridConstraints);
        return listingCheckBox;
    }
    private void getDomainListingsLabel(JPanel jpanel) {
        javax.swing.JLabel domainListingsLabel = new javax.swing.JLabel();
        changeFont(domainListingsLabel, AVAILABLE_FONTS.HEADER_FONT, 18.0F);
        domainListingsLabel.setText("Set Information");
        jpanel.add(domainListingsLabel,
                new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
    }
    private JButton getSaveButton(JPanel jpanel) {
        JButton saveButton = new JButton();
        saveButton.setBackground(new Color(-6039919));
        saveButton.setForeground(new Color(-394241));
        saveButton.setText("SAVE");
        changeFont(saveButton, AVAILABLE_FONTS.HEADER_FONT, 12.0F);
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
        changeFont(setDetailsTextArea, AVAILABLE_FONTS.TEXT_FONT, 12.0F);
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
        JComboBox<JLabel> weaponSelectionBox = getWeaponSelectionBox(characterCard, middleSelectorPanel);
        javax.swing.JLabel weaponNameLabel = getNameLabel(characterCard, middleSelectorPanel,
                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(177, 23), null, 1, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON);
        javax.swing.JLabel set1NameLabel = getNameLabel(characterCard, middleSelectorPanel,
                new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                new Dimension(177, 23), null, 1, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE);
        javax.swing.JLabel set2NameLabel = getNameLabel(characterCard, middleSelectorPanel,
                new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null,
                new Dimension(177, 23), null, 1, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO);
        JComboBox<JLabel> set1ComboBox = getSetComboBox(characterCard, middleSelectorPanel,
                new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false),
                ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE);
        JComboBox<JLabel> set2ComboBox = getSetComboBox(characterCard, middleSelectorPanel,
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

        javax.swing.JLabel weaponJLabel = getWeaponIcon(characterCard, templateTab);

        getCharLabel(characterCard.getCharacterIcon(), templateTab);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(5, 0, 0, 20);
        javax.swing.JLabel set1Icon = getSetIcon(characterCard, templateTab, gbc);
        gbc.gridy ++;
        javax.swing.JLabel set2Icon = getSetIcon(characterCard, templateTab, gbc);

        JPanel checkboxAndButtonPanel = getRightPanel(templateTab);
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
        set1ComboBox.addActionListener(new UpdateLabelListener(set1NameLabel, set1Icon,artifactSet1ListingCheckBox, ToolData.RESOURCE_TYPE.ARTIFACT_SET));
        set2ComboBox.addActionListener(new UpdateLabelListener(set2NameLabel, set2Icon,artifactSet2ListingCheckBox, ToolData.RESOURCE_TYPE.ARTIFACT_SET));
        set1ComboBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE));
        set2ComboBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO));
        weaponSelectionBox.addActionListener(new UpdateLabelListener(weaponNameLabel, weaponJLabel,weaponMaterialListingCheckbox, ToolData.RESOURCE_TYPE.WEAPON_NAME));
        weaponSelectionBox.addActionListener(new UpdateCharacterCardListener(characterCard, ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON));

        JButton saveButton = getSaveButton(checkboxAndButtonPanel);
        JTextArea setDetailsTextArea = getSetDetailsTextArea(checkboxAndButtonPanel);

        saveButton.addActionListener(new SaveButtonListener(characterCard));
        set1ComboBox.addActionListener(new UpdateTextAreaListener(setDetailsTextArea));
        set2ComboBox.addActionListener(new UpdateTextAreaListener(setDetailsTextArea));

        return templateTab;
    }
    public static Font $$$getFont$$$(int style, int size, Font currentFont) {
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
