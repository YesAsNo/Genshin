package Files;

import static Files.ToolData.*;
import static Files.ToolData.WEAPON_FILTER_OPTIONS.ALL_OPTIONS_BY_ENUM;
import static Files.ToolData.WEAPON_FILTER_OPTIONS.ALL_OPTIONS_BY_STRING;
import static Files.ToolData.WEAPON_FILTER_OPTIONS.NO_FILTER;
import static Files.ToolGUI.UNKNOWN_CHARACTER;
import static Files.ToolGUI.formatString;
import static Files.ToolGUI.getFarmedMapping;
import static Files.ToolGUI.isSomeoneFarmingForTheWeapon;
import static Files.ToolGUI.serializeSave;
import static Files.ToolGUI.updateFarmedItemMap;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WeaponTabGUI implements ItemListener, ActionListener {

    private final JPanel mainPanel = new JPanel(new GridBagLayout());
    private final JScrollPane devWeaponTabScrollPane = new JScrollPane();
    private final JTextField devWeaponsTabSearchbar = new JTextField();
    private final JButton devWeaponTabSearchButton = new JButton();
    private final JPanel devWeaponTabScrollPanePanel = new JPanel();
    private final JButton devSaveAllWeapons = new JButton();
    private final JCheckBox showListedCheckBox = new JCheckBox();
    private final JCheckBox showUnlistedCheckBox = new JCheckBox();
    private final JLabel showMatchedAmountLabel = new JLabel();
    private static final JComboBox<String> devFilterComboBox = new JComboBox<>();
    private static final Map<String,Integer> weaponsMap = new TreeMap<>();
    public static final int FARMED_FOR_A_SPECIFIED_CHARACTER = -1;
    public static final int NOT_FARMING = 0;
    public static final int FARMED_GENERALLY = 1;
    public enum SearchFlag{
        ALL,
        LISTED_ONLY,
        UNLISTED_ONLY
    }

    public WeaponTabGUI(){
        setUpWeaponsPanel();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    public static void parseWeaponsMap(){
        for (String weapon: getFlattenedData(ToolData.RESOURCE_TYPE.WEAPON_NAME)){
            Map<String, Set<String>> mapping = getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS);
            if (mapping.containsKey(weapon)&& !mapping.get(weapon).isEmpty())
            {
                Set<String> chars = mapping.get(weapon);
                if (chars.contains(UNKNOWN_CHARACTER)){
                    weaponsMap.put(weapon, FARMED_GENERALLY);
                }
                else{
                    weaponsMap.put(weapon,FARMED_FOR_A_SPECIFIED_CHARACTER);
                }
            }
            else{
                weaponsMap.put(weapon,NOT_FARMING);
            }

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        showListedCheckBox.setEnabled(false);
        showUnlistedCheckBox.setEnabled(false);
        parseSearch(getSearchFlag());
        showListedCheckBox.setEnabled(true);
        showUnlistedCheckBox.setEnabled(true);
    }
    public SearchFlag getSearchFlag(){
        if (showListedCheckBox.isSelected() && !showUnlistedCheckBox.isSelected()){
            return SearchFlag.LISTED_ONLY;
        }
        else if (showUnlistedCheckBox.isSelected() && !showListedCheckBox.isSelected()){
            return SearchFlag.UNLISTED_ONLY;
        }
        else{
            return SearchFlag.ALL;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton triggerButton = (JButton) e.getSource();
        triggerButton.setEnabled(false);
        if (triggerButton == devSaveAllWeapons) {
            try {
                saveAllWeapons();
                Timer timer = new Timer(0,event->triggerButton.setText("SUCCESS"));
                timer.setRepeats(false);
                timer.start();
            }
            catch (IOException ex){
                Timer timer = new Timer(0,event->triggerButton.setText("FAIL"));
                timer.setRepeats(false);
                timer.start();
            }
            Timer timer = new Timer(1000,event->triggerButton.setText("SAVE"));
            timer.setRepeats(false);
            timer.start();
        } else {
            parseSearch(getSearchFlag());
        }
        triggerButton.setEnabled(true);
    }
    private void saveAllWeapons() throws IOException {

        for(String weapon : weaponsMap.keySet()){
            if (weaponsMap.get(weapon) == FARMED_GENERALLY){
                updateFarmedItemMap(getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS),weapon,UNKNOWN_CHARACTER,true);
            }
            if (weaponsMap.get(weapon) == NOT_FARMING) {
                updateFarmedItemMap(getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS),weapon,UNKNOWN_CHARACTER,false);
            }
            //The third case was handled in the SaveButtonListener.
        }
        serializeSave();
    }

    private void parseSearch(SearchFlag flag) {
        String userFieldInput = devWeaponsTabSearchbar.getText().toLowerCase();
        devWeaponTabScrollPanePanel.removeAll();
        devWeaponTabScrollPane.updateUI();
        parseWeaponsMap();
        int matchedCount = 0;
        for (String s : getFlattenedData(ToolData.RESOURCE_TYPE.WEAPON_NAME)) {
            ToolData.WEAPON_FILTER_OPTIONS filter = ALL_OPTIONS_BY_STRING.get((String) devFilterComboBox.getSelectedItem());
            assert filter != null;

            if (inputMatchesFilters(userFieldInput,s,filter,flag)) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = matchedCount % 3;
                gbc.gridy = (matchedCount - gbc.gridx) / 3;
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.insets = new Insets(10, 10, 10, 10);
                devWeaponTabScrollPanePanel.add(generateWeaponCard(s), gbc);

                matchedCount++;
            }

        }
        showMatchedAmountLabel.setText("Matches: "+ matchedCount);
        changeFont(showMatchedAmountLabel, AVAILABLE_FONTS.BLACK_FONT, 12);
    }
    private boolean inputMatchesFilters(String input, String weapon, ToolData.WEAPON_FILTER_OPTIONS filter,SearchFlag flag){
        if(weapon.toLowerCase().contains(input.toLowerCase()) &&
                (lookUpWeaponRarityAndType(weapon).getWeaponType().equalsIgnoreCase(filter.stringToken)||
                        filter == NO_FILTER)){
            if(weaponsMap.get(weapon) != NOT_FARMING && flag == SearchFlag.LISTED_ONLY){
                return true;
            }
            if(weaponsMap.get(weapon) == NOT_FARMING && flag == SearchFlag.UNLISTED_ONLY){
                return true;
            }
            return flag == SearchFlag.ALL;
        }
        return false;
    }

    public static Map<String,Integer>getFarmingMap(){
        return weaponsMap;
    }
    public JPanel generateWeaponCard(String weaponName) {

        // WEAPON CARD PANEL
        JPanel devWeaponCard = new JPanel();
        devWeaponCard.setLayout(new GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        devWeaponCard.setBackground(new Color(-1));
        devWeaponCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

        // WEAPON ICON AND NAME
        JLabel devWeaponIcon = new JLabel();
        devWeaponIcon.setHorizontalAlignment(0);
        devWeaponIcon.setHorizontalTextPosition(0);
        devWeaponIcon.setIcon(getResourceIcon(weaponName, ToolData.RESOURCE_TYPE.WEAPON_NAME));
        devWeaponIcon.setText(formatString(weaponName));
        changeFont(devWeaponIcon, AVAILABLE_FONTS.BLACK_FONT, 12);
        devWeaponIcon.setVerticalAlignment(0);
        devWeaponIcon.setVerticalTextPosition(3);
        devWeaponCard.add(devWeaponIcon,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));

        // WEAPON LISTING CHECK BOX
        JCheckBox devWepMatListingCheckbox = new JCheckBox();
        devWepMatListingCheckbox.setBackground(new Color(-1));
        Map<String,Set<String>> mapping = getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS);
        assert mapping != null;
        if (isSomeoneFarmingForTheWeapon(weaponName)) {
            devWepMatListingCheckbox.setSelected(true);
            devWepMatListingCheckbox.setEnabled(false);
            devWepMatListingCheckbox.setText("Already Farmed");
        } else {
            devWepMatListingCheckbox.setText("List Weapon?");
        }
        changeFont(devWepMatListingCheckbox, AVAILABLE_FONTS.TEXT_FONT, 12);
        devWepMatListingCheckbox.addItemListener(new WeaponTabGUIListener(weaponName));
        devWeaponCard.add(devWepMatListingCheckbox,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        // WEAPON MATERIAL PREVIEW ICON
        JLabel devWepMaterialPreview = new JLabel();
        devWepMaterialPreview.setHorizontalAlignment(0);
        devWepMaterialPreview.setHorizontalTextPosition(0);
        devWepMaterialPreview.setIcon(getResourceIcon(getWeaponMaterialForWeapon(weaponName), ToolData.RESOURCE_TYPE.WEAPON_MATERIAL));
        devWepMaterialPreview.setText("");
        devWepMaterialPreview.setVerticalAlignment(0);
        devWepMaterialPreview.setVerticalTextPosition(3);
        devWeaponCard.add(devWepMaterialPreview,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));

        // WEAPON TYPE LABEL
        JLabel devWepTypeLabel = new JLabel();
        devWepTypeLabel.setText("Type: " + lookUpWeaponRarityAndType(weaponName).getWeaponType());
        changeFont(devWepTypeLabel, AVAILABLE_FONTS.TEXT_FONT, 12);
        devWeaponCard.add(devWepTypeLabel,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        return devWeaponCard;
    }

    private void setUpWeaponsPanel() {

        // WEAPON PANEL
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel overviewPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(overviewPanel, gbc);

        // SCROLL PANE
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        overviewPanel.add(devWeaponTabScrollPane, gbc);
        devWeaponTabScrollPanePanel.setLayout(new GridBagLayout());
        devWeaponTabScrollPane.setViewportView(devWeaponTabScrollPanePanel);
        devWeaponTabScrollPane.updateUI();

        // SEARCH BAR
        devWeaponsTabSearchbar.setEnabled(true);
        devWeaponsTabSearchbar.setInheritsPopupMenu(false);
        devWeaponsTabSearchbar.setMaximumSize(new Dimension(240, 33));
        devWeaponsTabSearchbar.setMinimumSize(new Dimension(240, 33));
        devWeaponsTabSearchbar.setPreferredSize(new Dimension(240, 33));
        devWeaponsTabSearchbar.setText("Search by name!");
        changeFont(devWeaponsTabSearchbar, AVAILABLE_FONTS.BLACK_FONT, 18);
        devWeaponsTabSearchbar.addMouseListener(new SearchBarListener());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(devWeaponsTabSearchbar, gbc);

        // SEARCH BUTTON
        devWeaponTabSearchButton.setMinimumSize(new Dimension(50, 30));
        devWeaponTabSearchButton.setPreferredSize(new Dimension(50, 30));
        devWeaponTabSearchButton.setText("✓");
        devWeaponTabSearchButton.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(devWeaponTabSearchButton, gbc);

        // SAVE BUTTON
        devSaveAllWeapons.setBackground(new Color(-6039919));
        devSaveAllWeapons.setForeground(new Color(-394241));
        devSaveAllWeapons.setPreferredSize(new Dimension(100,30));
        devSaveAllWeapons.setText("SAVE");
        changeFont(devSaveAllWeapons, AVAILABLE_FONTS.BLACK_FONT, 12);
        devSaveAllWeapons.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 20, 0, 5);
        mainPanel.add(devSaveAllWeapons, gbc);

        // FILTER COMBO BOX
        devFilterComboBox.setBackground(new Color(-2702645));
        devFilterComboBox.setEnabled(true);
        final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();

        for (ToolData.WEAPON_FILTER_OPTIONS options : ALL_OPTIONS_BY_ENUM.keySet()) {
            defaultComboBoxModel1.addElement(options.stringToken);
        }
        changeFont(devFilterComboBox, AVAILABLE_FONTS.BLACK_FONT, 12);
        devFilterComboBox.setModel(defaultComboBoxModel1);
        devFilterComboBox.addItemListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 0, 5);
        mainPanel.add(devFilterComboBox, gbc);

        // SHOW UNLISTED/LISTED COMBO BOX
        showListedCheckBox.setBackground(new Color(-2702645));
        showListedCheckBox.setForeground(new Color(-15072759));
        showListedCheckBox.setText("Show listed ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);
        mainPanel.add(showListedCheckBox, gbc);
        showUnlistedCheckBox.setBackground(new Color(-2702645));
        showUnlistedCheckBox.setForeground(new Color(-15072759));
        showUnlistedCheckBox.setText("Show unlisted ");
        changeFont(showListedCheckBox, AVAILABLE_FONTS.BLACK_FONT, 12);
        changeFont(showUnlistedCheckBox, AVAILABLE_FONTS.BLACK_FONT, 12);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);
        mainPanel.add(showUnlistedCheckBox, gbc);

        showListedCheckBox.addItemListener(this);
        showUnlistedCheckBox.addItemListener(this);
        showMatchedAmountLabel.setForeground(new Color(-15072759));

        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        mainPanel.add(showMatchedAmountLabel, gbc);
    }

}
