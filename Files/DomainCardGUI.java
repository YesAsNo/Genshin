package Files;

import static Files.CharacterCardGUI.$$$getFont$$$;
import static Files.DomainCardGUI.DOMAIN_FILTER_OPTIONS.ALL_OPTIONS_BY_ENUM;
import static Files.DomainCardGUI.DOMAIN_FILTER_OPTIONS.ALL_OPTIONS_BY_STRING;
import static Files.ToolData.generateResourceIconPath;
import static Files.ToolData.getFlattenedData;
import static Files.ToolData.getMapping;
import static Files.ToolData.getWeaponMaterialForWeapon;
import static Files.ToolData.knownMappings.DAY_AVAILABLEMATS;
import static Files.ToolData.knownMappings.TALENTBOOK_CHAR;
import static Files.ToolData.knownMappings.TALENTDOMAIN_TALENTBOOK;
import static Files.ToolData.knownMappings.WEEKLYBOSSMAT_CHAR;
import static Files.ToolData.knownMappings.WEEKLYDOMAIN_WEEKLYBOSSMAT;
import static Files.ToolData.knownMappings.WEPDOMAIN_WEPMAT;
import static Files.ToolData.knownMappings.WEPMAT_WEPNAME;
import static Files.ToolGUI.getFarmedMapping;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DomainCardGUI implements ActionListener {
    private final JPanel domainTab = new JPanel(new GridBagLayout());
    private final JComboBox<String> filterBox = new JComboBox<>();
    private final JPanel domainsPanelOverview = new JPanel(new GridBagLayout());
    private final Map<String, List<String>> dayToAvailableMaterialsMapping = getMapping(DAY_AVAILABLEMATS);
    private static final JRadioButton wedSatButton = new JRadioButton();
    private static final JRadioButton tueFriButton = new JRadioButton();
    private static final JRadioButton monThuButton = new JRadioButton();
    private static final JRadioButton allButton = new JRadioButton();
    private static final ButtonGroup bg = new ButtonGroup();
    static {
        bg.add(wedSatButton);
        bg.add(tueFriButton);
        bg.add(monThuButton);
        bg.add(allButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        DOMAIN_FILTER_OPTIONS option = ALL_OPTIONS_BY_STRING.get((String)filterBox.getSelectedItem());
        parseFilter(option,getDayFilter());
    }

    public enum domainTheme {
        WEAPON_MATERIAL_THEME(-10301,-13494016,-26768,"\uD83D\uDD2A"),
        TALENT_BOOK_THEME(-1068,-14541824,-10640,"\uD83D\uDCD4"),
        WEEKLY_BOSS_DOMAIN_THEME(-11811,-13236722,-36698,"\uD83D\uDC09"),
        ARTIFACT_DOMAIN_THEME(-2756865,-16575201,-9382145,"\uD83D\uDC51");
        public final int panelBackgroundColor;
        public final int panelForegroundColor;
        public final int marginBackgroundColor;
        public final String marginSymbol;
        domainTheme(int bgColor, int fgColor, int marginBgColor, String symbol){
            this.panelBackgroundColor = bgColor;
            this.panelForegroundColor = fgColor;
            this.marginBackgroundColor = marginBgColor;
            this.marginSymbol = symbol;
        }
    }
    public enum DOMAIN_FILTER_OPTIONS{
        NO_FILTER("[ Filter ]"),
        ARTIFACT("Artifact"),
        TALENT("Talent Book"),
        WEEKLY("Weekly Boss Material"),
        WEAPON_MAT("Weapon Material");

        public static final Map<DOMAIN_FILTER_OPTIONS, String> ALL_OPTIONS_BY_ENUM = new TreeMap<>();
        public static final Map<String, DOMAIN_FILTER_OPTIONS> ALL_OPTIONS_BY_STRING = new TreeMap<>();

        static {
            for (DOMAIN_FILTER_OPTIONS e: values()) {
                ALL_OPTIONS_BY_ENUM.put(e, e.stringToken);
                ALL_OPTIONS_BY_STRING.put(e.stringToken,e);
            }
        }

        public final String stringToken;
        DOMAIN_FILTER_OPTIONS(String stringToken) {
            this.stringToken = stringToken;
        }
    }
    public DomainCardGUI() {
        filterBox.setBackground(new Color(-2702645));
        filterBox.setEnabled(true);
        final DefaultComboBoxModel<String> filterBoxModel = new DefaultComboBoxModel<>();
        filterBoxModel.addAll(ALL_OPTIONS_BY_ENUM.values());
        filterBoxModel.setSelectedItem(DOMAIN_FILTER_OPTIONS.NO_FILTER.stringToken);
        filterBox.setModel(filterBoxModel);
        filterBox.addActionListener(this);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 300, 0, 5);
        domainTab.add(filterBox, gbc);
        domainTab.setBackground(new Color(-1));


        wedSatButton.setBackground(new Color(-2702645));
        wedSatButton.setForeground(new Color(-13236722));
        wedSatButton.setText("Wed/Sat");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 5);
        domainTab.add(wedSatButton, gbc);

        tueFriButton.setBackground(new Color(-5275240));
        tueFriButton.setForeground(new Color(-1));
        tueFriButton.setText("Tues/Fri");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 5);
        domainTab.add(tueFriButton, gbc);

        monThuButton.setBackground(new Color(-2702645));
        monThuButton.setForeground(new Color(-13236722));
        monThuButton.setText("Mon/Thu");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 5);
        domainTab.add(monThuButton, gbc);


        allButton.setBackground(new Color(-2702645));
        allButton.setForeground(new Color(-13236722));
        allButton.setText("All");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        domainTab.add(allButton, gbc);
        JPanel devDomainsTabPanel = new JPanel();
        devDomainsTabPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        domainTab.add(devDomainsTabPanel, gbc);
        JScrollPane devDomainsScrollPane = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        devDomainsTabPanel.add(devDomainsScrollPane, gbc);
        devDomainsScrollPane.setViewportView(domainsPanelOverview);

        wedSatButton.addActionListener(this);
        monThuButton.addActionListener(this);
        tueFriButton.addActionListener(this);
        allButton.addActionListener(this);
        allButton.setSelected(true);
    }
    private String getDayFilter(){
        for (Enumeration<AbstractButton> it = bg.getElements();it.hasMoreElements();){
            AbstractButton button = it.nextElement();
            if (button.getModel() == bg.getSelection()){
                return button.getText();
            }
        }
        return allButton.getText();
    }
    private void parseFilter(DOMAIN_FILTER_OPTIONS filter,String dayFilter){
        List<domainTheme> filteredThemes = new ArrayList<>();
        assert filter != null;
        switch(filter){
            case TALENT -> filteredThemes.add(domainTheme.TALENT_BOOK_THEME);
            case ARTIFACT -> filteredThemes.add(domainTheme.ARTIFACT_DOMAIN_THEME);
            case WEAPON_MAT -> filteredThemes.add(domainTheme.WEAPON_MATERIAL_THEME);
            case WEEKLY -> filteredThemes.add(domainTheme.WEEKLY_BOSS_DOMAIN_THEME);
            case NO_FILTER -> filteredThemes.addAll(List.of(domainTheme.values()));
        }
        domainsPanelOverview.removeAll();
        domainsPanelOverview.updateUI();
        int i = 0;
        for (domainTheme dt : filteredThemes) {
            Map<String, List<String>> domainMapping = getDomainMapping(dt);
            for (String domainName : domainMapping.keySet()) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = i++;
                gbc.weightx = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = new Insets(5, 100, 5, 100);
                domainsPanelOverview.add(generateDomainCard(dt, domainName,
                        domainMapping.get(domainName),dayFilter), gbc);
            }
        }
    }
    private JPanel generateDomainCard(domainTheme dt,String domainName, List<String> domainMaterials,String dayFilter){
        JPanel domainCard = new JPanel(new GridBagLayout());
        domainCard.setBackground(new Color(dt.panelBackgroundColor));
        domainCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        int i = 0;
        for (String materialName : domainMaterials){

            JLabel materialIconLabel = new JLabel();
            ImageIcon materialIcon = new ImageIcon(generateResourceIconPath(materialName, getDomainResourceType(dt)));

            if (dayFilter.equalsIgnoreCase("All") ||getDomainResourceType(dt) == ToolData.RESOURCE_TYPE.WEEKLY_BOSS_MATERIAL
                    || getDomainResourceType(dt) == ToolData.RESOURCE_TYPE.ARTIFACT|| dayToAvailableMaterialsMapping.get(dayFilter).contains(materialName)) {
                materialIconLabel.setIcon(materialIcon);
            }
            else{
                materialIconLabel.setIcon(new ImageIcon(GrayFilter.createDisabledImage(materialIcon.getImage())));
            }
            materialIconLabel.setText("");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2 + i++;
            gbc.gridy = 0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            domainCard.add(materialIconLabel, gbc);
        }
        JPanel domainInfoPanel = new JPanel();
        domainInfoPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        domainInfoPanel.setAlignmentX(0.5f);
        domainInfoPanel.setAlignmentY(0.5f);
        domainInfoPanel.setBackground(new Color(dt.panelBackgroundColor));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        domainCard.add(domainInfoPanel, gbc);
        JLabel domainNameLabel = new JLabel();
        Font domainNameLabelFont = $$$getFont$$$( Font.BOLD, 18, domainNameLabel.getFont());
        if (domainNameLabelFont != null) {
            domainNameLabel.setFont(domainNameLabelFont);
        }
        domainNameLabel.setForeground(new Color(dt.panelForegroundColor));
        domainNameLabel.setText(domainName);
        domainInfoPanel.add(domainNameLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JLabel domainAllCounterLabel = new JLabel();
        Font domainAllWeaponCounterLabelFont =
               $$$getFont$$$(-1, 12, domainAllCounterLabel.getFont());
        if (domainAllWeaponCounterLabelFont != null) {
            domainAllCounterLabel.setFont(domainAllWeaponCounterLabelFont);
        }
        domainAllCounterLabel.setForeground(new Color(dt.panelForegroundColor));
        String[] labelText = getAllCounterLabel(domainName,getDomainResourceType(dt));
        domainAllCounterLabel.setText(labelText[0]);
        domainAllCounterLabel.setToolTipText(labelText[1]);
        domainInfoPanel.add(domainAllCounterLabel,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JLabel domainListedCounterLabel = new JLabel();
        Font domainListedCounterLabelFont =
                $$$getFont$$$(-1, 12, domainListedCounterLabel.getFont());
        if (domainListedCounterLabelFont != null) {
            domainListedCounterLabel.setFont(domainListedCounterLabelFont);
        }
        domainListedCounterLabel.setForeground(new Color(dt.panelForegroundColor));
        labelText = getListedCounterLabel(domainName,getDomainResourceType(dt));
        domainListedCounterLabel.setText(labelText[0]);
        domainListedCounterLabel.setToolTipText(labelText[1]);
        domainInfoPanel.add(domainListedCounterLabel,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JPanel marginPanel = new JPanel();
        marginPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 5), -1, -1));
        marginPanel.setAlignmentX(0.5f);
        marginPanel.setAlignmentY(0.5f);
        marginPanel.setBackground(new Color(dt.marginBackgroundColor));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 0);
        domainCard.add(marginPanel, gbc);
        JLabel domainIconLabel = new JLabel();
        domainIconLabel.setAlignmentX(0.5f);
        domainIconLabel.setFocusTraversalPolicyProvider(false);
        domainIconLabel.setFocusable(false);
        Font weaponMaterialDomainIconLabelFont =
                $$$getFont$$$(Font.BOLD, 16, domainIconLabel.getFont());
        if (weaponMaterialDomainIconLabelFont != null) {
            domainIconLabel.setFont(weaponMaterialDomainIconLabelFont);
        }
        domainIconLabel.setForeground(new Color(-1));
        domainIconLabel.setText(dt.marginSymbol);
        marginPanel.add(domainIconLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        return domainCard;
    }
    public Map<String, List<String>> getDomainMapping(domainTheme dt){
        return switch(dt){
            case WEAPON_MATERIAL_THEME -> getMapping(WEPDOMAIN_WEPMAT);
            case TALENT_BOOK_THEME -> getMapping(TALENTDOMAIN_TALENTBOOK);
            case WEEKLY_BOSS_DOMAIN_THEME -> getMapping(WEEKLYDOMAIN_WEEKLYBOSSMAT);
            case ARTIFACT_DOMAIN_THEME -> getMapping(ToolData.knownMappings.ARTIDOMAIN_ARTISET);
        };
    }
    public ToolData.RESOURCE_TYPE getDomainResourceType(domainTheme dt){
        return switch(dt){
            case WEAPON_MATERIAL_THEME -> ToolData.RESOURCE_TYPE.WEAPON_MATERIAL;
            case TALENT_BOOK_THEME -> ToolData.RESOURCE_TYPE.TALENT_BOOK;
            case WEEKLY_BOSS_DOMAIN_THEME -> ToolData.RESOURCE_TYPE.WEEKLY_BOSS_MATERIAL;
            case ARTIFACT_DOMAIN_THEME -> ToolData.RESOURCE_TYPE.ARTIFACT;
        };
    }
    public String[] getAllCounterLabel(String domainName,ToolData.RESOURCE_TYPE rt){
        String[] labelText = new String[2];
        String domainMaterialCategory = "";
        Set<String> matchedCharacters = new TreeSet<>();
        int counter = 0;
        switch(rt){
            case WEAPON_MATERIAL -> {
                for (String matName: getMapping(WEPDOMAIN_WEPMAT).get(domainName)){
                    matchedCharacters.addAll(getMapping(WEPMAT_WEPNAME).get(matName));
                }
                domainMaterialCategory = "weapons";
            }
            case ARTIFACT -> {
                counter = getFlattenedData(ToolData.flattenedDataCategory.CHARACTER).size();
                matchedCharacters.add("All characters");
                domainMaterialCategory = "characters";
            }
            case WEEKLY_BOSS_MATERIAL -> {
                for (String matName: getMapping(WEEKLYDOMAIN_WEEKLYBOSSMAT).get(domainName)){
                    matchedCharacters.addAll(getMapping(WEEKLYBOSSMAT_CHAR).get(matName));
                }
                domainMaterialCategory = "characters";
            }
            case TALENT_BOOK -> {
                for (String matName: getMapping(TALENTDOMAIN_TALENTBOOK).get(domainName)){
                    matchedCharacters.addAll(getMapping(TALENTBOOK_CHAR).get(matName));
                }
                domainMaterialCategory = "characters";
            }
            default -> {}
        }
        labelText[0] = "<html>" + "All" + " " + domainMaterialCategory + " " + "that need it: " + "<u>"
                + (counter == 0 ? matchedCharacters.size() : counter ) + "</u>" + "</html>";
        labelText[1] = matchedCharacters.toString();
        return labelText;

    }
    public String[] getListedCounterLabel(String domainName, ToolData.RESOURCE_TYPE rt){
        String[] labelText = new String[2];
        String domainMaterialCategory = "";
        Set<String> matchedItems = new TreeSet<>();
        int counter = 0;
        switch(rt){
            case WEAPON_MATERIAL -> {
                for (String weaponName: getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS).keySet()){
                    if (!getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS).get(weaponName).isEmpty() &&
                            getMapping(WEPDOMAIN_WEPMAT).get(domainName).contains(getWeaponMaterialForWeapon(weaponName))){
                        counter++;
                        matchedItems.add(weaponName);
                    }
                }
            }
            //TODO: Finish the remaining cases.
            case ARTIFACT -> {
                counter = getFlattenedData(ToolData.flattenedDataCategory.CHARACTER).size();
                matchedItems.add("All characters");
                domainMaterialCategory = "characters";
            }
            case WEEKLY_BOSS_MATERIAL -> {
                for (String matName: getMapping(WEEKLYDOMAIN_WEEKLYBOSSMAT).get(domainName)){
                    matchedItems.addAll(getMapping(WEEKLYBOSSMAT_CHAR).get(matName));
                }
                domainMaterialCategory = "characters";
            }
            case TALENT_BOOK -> {
                for (String matName: getMapping(TALENTDOMAIN_TALENTBOOK).get(domainName)){
                    matchedItems.addAll(getMapping(TALENTBOOK_CHAR).get(matName));
                }
                domainMaterialCategory = "characters";
            }
            default -> {}
        }
        labelText[0] = String.valueOf(counter);
        labelText[1] = matchedItems.isEmpty() ? "None" : matchedItems.toString();
        return labelText;
    }
    public JPanel getMainPanel(){
        return domainTab;
    }
    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal);
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
