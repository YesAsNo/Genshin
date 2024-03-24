package Files;

import static Files.ToolData.WEAPON_FILTER_OPTIONS.ALL_OPTIONS;
import static Files.ToolData.generateResourceIconPath;
import static Files.ToolData.getFlattenedData;
import static Files.ToolData.lookUpWeaponMaterial;
import static Files.ToolData.lookUpWeaponRarityAndType;
import static Files.ToolGUI.UNKNOWN_CHARACTER;
import static Files.ToolGUI.formatString;
import static Files.ToolGUI.getFarmedMapping;
import static Files.ToolGUI.serializeSave;
import static Files.ToolGUI.updateFarmedItemMap;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
    private static final JComboBox<String> devFilterComboBox = new JComboBox<>();
    private static final Map<String,Integer> weaponsToUpdate = new TreeMap<>();
    public static final int FARMED_FOR_A_SPECIFIED_CHARACTER = -1;
    public static final int NOT_FARMING = 0;
    public static final int STARTED_FARMING = 1;

    public WeaponTabGUI(){
        setUpWeaponsPanel();
        parseWeaponsToUpdate();

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    private void parseWeaponsToUpdate(){
        for (String weapon: getFlattenedData(ToolData.flattenedDataCategory.WEAPON_NAME)){
            Map<String, Set<String>> mapping = getFarmedMapping(ToolData.RESOURCE_TYPE.WEAPON);
            if (mapping.containsKey(weapon))
            {
                Set<String> chars = mapping.get(weapon);
                if (chars.contains(UNKNOWN_CHARACTER)){
                    weaponsToUpdate.put(weapon,STARTED_FARMING);
                }
                else{
                    weaponsToUpdate.put(weapon,FARMED_FOR_A_SPECIFIED_CHARACTER);
                }
            }
            else{
                weaponsToUpdate.put(weapon,NOT_FARMING);
            }

        }
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        ((JCheckBox)e.getSource()).setEnabled(false);
        //TODO: updateFarmedItemMap(_farmedWeapons,_weaponName,UNKNOWN_CHARACTER,convertStateChangeToBool(e.getStateChange()));
        ((JCheckBox)e.getSource()).setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton triggerButton = (JButton) e.getSource();
        triggerButton.setEnabled(false);
        if (triggerButton == devSaveAllWeapons) {
            saveAllWeapons();
        } else {
            parseSearch();
        }
        triggerButton.setEnabled(true);
    }
    private void saveAllWeapons(){

        for(String weapon : weaponsToUpdate.keySet()){
            if (weaponsToUpdate.get(weapon) == STARTED_FARMING){
                updateFarmedItemMap(getFarmedMapping(ToolData.RESOURCE_TYPE.WEAPON),weapon,UNKNOWN_CHARACTER,true);
            }
            if (weaponsToUpdate.get(weapon) == NOT_FARMING) {
                updateFarmedItemMap(getFarmedMapping(ToolData.RESOURCE_TYPE.WEAPON),weapon,UNKNOWN_CHARACTER,false);
            }
            //The third case was handled in the SaveButtonListener.
        }
        serializeSave();
    }

    private void parseSearch() {
        String userFieldInput = devWeaponsTabSearchbar.getText().toLowerCase();
        devWeaponTabScrollPanePanel.removeAll();
        devWeaponTabScrollPane.updateUI();
        int matchedCount = 0;
        for (String s : getFlattenedData(ToolData.flattenedDataCategory.WEAPON_NAME)) {
            String weaponCategory = lookUpWeaponRarityAndType(s).getWeaponType();
            String filterOption = (String) devFilterComboBox.getSelectedItem();
            assert weaponCategory != null;
            assert filterOption != null;

            if (s.toLowerCase().contains(userFieldInput) && (filterOption.equalsIgnoreCase(weaponCategory) ||
                    filterOption.equalsIgnoreCase(ToolData.WEAPON_FILTER_OPTIONS.NO_FILTER.filterOption))) {
                generateWeaponCard(s, matchedCount);
                matchedCount++;
            }

        }
    }
    public static Map<String,Integer>getFarmingMap(){
        return weaponsToUpdate;
    }
    public void generateWeaponCard(String weaponName, int matchedCount) {
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
        devWeaponIcon.setHorizontalAlignment(0);
        devWeaponIcon.setHorizontalTextPosition(0);
        devWeaponIcon.setIcon(new ImageIcon(generateResourceIconPath(weaponName, ToolData.RESOURCE_TYPE.WEAPON)));
        devWeaponIcon.setText(formatString(weaponName));
        devWeaponIcon.setVerticalAlignment(0);
        devWeaponIcon.setVerticalTextPosition(3);
        devWeaponCard.add(devWeaponIcon,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JCheckBox devWepMatListingCheckbox = new JCheckBox();
        devWepMatListingCheckbox.setBackground(new Color(-1));
        assert getFarmedMapping(ToolData.RESOURCE_TYPE.WEAPON) != null;
        if (getFarmedMapping(ToolData.RESOURCE_TYPE.WEAPON).containsKey(weaponName)) {
            devWepMatListingCheckbox.setSelected(true);
            devWepMatListingCheckbox.setEnabled(false);
            devWepMatListingCheckbox.setText("Already Farmed");
        } else {
            devWepMatListingCheckbox.setText("List Weapon?");
        }
        devWepMatListingCheckbox.addItemListener(new WeaponTabGUIListener(weaponName));

        devWeaponCard.add(devWepMatListingCheckbox,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JLabel devWepMaterialPreview = new JLabel();
        devWepMaterialPreview.setHorizontalAlignment(0);
        devWepMaterialPreview.setHorizontalTextPosition(0);
        devWepMaterialPreview.setIcon(new ImageIcon(
                generateResourceIconPath(lookUpWeaponMaterial(weaponName), ToolData.RESOURCE_TYPE.WEAPON_MATERIAL)));
        devWepMaterialPreview.setText("");
        devWepMaterialPreview.setVerticalAlignment(0);
        devWepMaterialPreview.setVerticalTextPosition(3);
        devWeaponCard.add(devWepMaterialPreview,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JLabel devWepTypeLabel = new JLabel();
        devWepTypeLabel.setText("Type: " + lookUpWeaponRarityAndType(weaponName).getWeaponType());
        devWeaponCard.add(devWepTypeLabel,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
    }

    private void setUpWeaponsPanel() {

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel overviewPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(overviewPanel, gbc);

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

        devWeaponsTabSearchbar.setEnabled(true);

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
        mainPanel.add(devWeaponsTabSearchbar, gbc);

        devWeaponTabSearchButton.setMaximumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setMinimumSize(new Dimension(30, 30));
        devWeaponTabSearchButton.setPreferredSize(new Dimension(50, 30));
        devWeaponTabSearchButton.setText("✓");
        devWeaponTabSearchButton.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(devWeaponTabSearchButton, gbc);

        devSaveAllWeapons.setBackground(new Color(-6039919));
        devSaveAllWeapons.setForeground(new Color(-394241));
        devSaveAllWeapons.setText("SAVE all weapons");
        devSaveAllWeapons.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 0, 0);
        mainPanel.add(devSaveAllWeapons, gbc);

        devFilterComboBox.setBackground(new Color(-2702645));
        devFilterComboBox.setEnabled(true);
        final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();

        for (ToolData.WEAPON_FILTER_OPTIONS options : ALL_OPTIONS.keySet()) {
            defaultComboBoxModel1.addElement(options.filterOption);
        }

        devFilterComboBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        mainPanel.add(devFilterComboBox, gbc);
    }

}
