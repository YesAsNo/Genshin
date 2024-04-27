package Files;

import static Files.DomainTabGUI.DOMAIN_FILTER_OPTIONS.ALL_OPTIONS_BY_ENUM;
import static Files.DomainTabGUI.DOMAIN_FILTER_OPTIONS.ALL_OPTIONS_BY_STRING;
import static Files.ToolData.AVAILABLE_FONTS;
import static Files.ToolData.RESOURCE_TYPE.TALENT_BOOK;
import static Files.ToolData.RESOURCE_TYPE.WEEKLY_BOSS_MATERIAL;
import static Files.ToolData.changeFont;
import static Files.ToolGUI.getFarmedMapping;
import static Files.ToolGUI.howManyAreFarmingThis;
import static Files.WeaponTabGUI.getUnassignedFarmedWeapons;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

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
import javax.swing.event.MouseInputAdapter;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class constructs the domain tab GUI, available from the Domains tab in the main application.
 */
public class DomainTabGUI implements ActionListener {
    private final JPanel domainTab = new JPanel(new GridBagLayout());
    private final JComboBox<JLabel> filterBox = new JComboBox<>();
    private final JPanel domainsPanelOverview = new JPanel(new GridBagLayout());
    private static final JRadioButton wedSatButton = new JRadioButton();
    private static final JRadioButton tueFriButton = new JRadioButton();
    private static final JRadioButton monThuButton = new JRadioButton();
    private static final JRadioButton allButton = new JRadioButton();
    private static final ButtonGroup bg_dayFilter = new ButtonGroup();
    static {
        bg_dayFilter.add(wedSatButton);
        bg_dayFilter.add(tueFriButton);
        bg_dayFilter.add(monThuButton);
        bg_dayFilter.add(allButton);
    }
    private static final JRadioButton showAllButton = new JRadioButton();
    private static final JRadioButton showListedButton = new JRadioButton();
    private static final ButtonGroup bg_listedFilter = new ButtonGroup();
    static{
        bg_listedFilter.add(showAllButton);
        bg_listedFilter.add(showListedButton);
        showAllButton.setSelected(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JLabel label = (JLabel) filterBox.getSelectedItem();
        DOMAIN_FILTER_OPTIONS option = ALL_OPTIONS_BY_STRING.get(label.getText());
        parseFilter(option,getDayFilter(),showAllButton.isSelected());
    }

    /**
     * Enum for Day filter.
     */
    public enum DAY_FILTER{
        /**
         * Monday/Thursday only
         */
        MONDAY_THURSDAY("Mon/Thu"),
        /**
         * Tuesday/Friday only
         */
        TUESDAY_FRIDAY("Tues/Fri"),
        /**
         * Wednesday/Saturday only
         */
        WEDNESDAY_SATURDAY("Wed/Sat"),
        /**
         * Sunday only. (On Sunday, every domain and every material within can be farmed)
         */
        SUNDAY_ALL("All");
        /**
         * The string token for the respective enum value.
         */
        public final String stringToken;
        DAY_FILTER(String token){
            stringToken = token;
        }

        /**
         * Map containing all enum values, mapped to the respective string values.
         */
        public static final TreeMap<DAY_FILTER, String> ALL_OPTIONS_BY_ENUM = new TreeMap<>();
        static {
            for (DAY_FILTER v : DAY_FILTER.values()){
                ALL_OPTIONS_BY_ENUM.put(v,v.stringToken);
            }
        }


    }

    /**
     * Enum containing different domain themes. There are four domain types available ingame, represented by the enums.
     */
    public enum DOMAIN_THEME {
        /**
         * Weapon material domain theme.
         */
        WEAPON_MATERIAL_THEME(-10301,-13494016,-26768,"\uD83D\uDD2A"),
        /**
         * Talent book domain theme.
         */
        TALENT_BOOK_THEME(-1068,-14541824,-10640,"\uD83D\uDCD4"),
        /**
         * Weekly boss domain theme.
         */
        WEEKLY_BOSS_DOMAIN_THEME(-11811,-13236722,-36698,"\uD83D\uDC09"),
        /**
         * Artifact domain theme.
         */
        ARTIFACT_DOMAIN_THEME(-2756865,-16575201,-9382145,"\uD83D\uDC51");
        /**
         * Panel background color for a theme.
         */
        public final int panelBackgroundColor;
        /**
         * Panel foreground color for a theme.
         */
        public final int panelForegroundColor;
        /**
         * Margin panel background color.
         */
        public final int marginBackgroundColor;
        /**
         * Margin panel symbol.
         */
        public final String marginSymbol;
        DOMAIN_THEME(int bgColor, int fgColor, int marginBgColor, String symbol){
            this.panelBackgroundColor = bgColor;
            this.panelForegroundColor = fgColor;
            this.marginBackgroundColor = marginBgColor;
            this.marginSymbol = symbol;
        }
    }

    /**
     * Enum representing domain filter options.
     */
    public enum DOMAIN_FILTER_OPTIONS{
        /**
         * All domains
         */
        NO_FILTER("All Domains"),
        /**
         * Artifact domains only
         */
        ARTIFACT("Artifact"),
        /**
         * Talent book domains only
         */
        TALENT("Talent Book"),
        /**
         * Weekly boss domains only
         */
        WEEKLY("Weekly Boss Material"),
        /**
         * Weapon material domains only
         */
        WEAPON_MAT("Weapon Material");
        /**
         * Mapping containing all filter options, mapped to their string tokens.
         */
        public static final Map<DOMAIN_FILTER_OPTIONS, String> ALL_OPTIONS_BY_ENUM = new TreeMap<>();
        /**
         * Mapping containing all filter options, mapped to their enums
         */
        public static final Map<String, DOMAIN_FILTER_OPTIONS> ALL_OPTIONS_BY_STRING = new TreeMap<>();

        static {
            for (DOMAIN_FILTER_OPTIONS e: values()) {
                ALL_OPTIONS_BY_ENUM.put(e, e.stringToken);
                ALL_OPTIONS_BY_STRING.put(e.stringToken,e);
            }
        }

        /**
         * The string token for every enum
         */
        public final String stringToken;
        DOMAIN_FILTER_OPTIONS(String stringToken) {
            this.stringToken = stringToken;
        }
    }

    /**
     * Constructor of the class.
     */
    public DomainTabGUI() {
        // SHOW UNLISTED (ALL) BUTTON
        showAllButton.setBackground(new Color(-2702645));
        showAllButton.setForeground(new Color(-13236722));
        showAllButton.setText("Show all ");
        changeFont(showAllButton, AVAILABLE_FONTS.BLACK_FONT, 12);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 130, 0, 0);
        domainTab.add(showAllButton, gbc);

        // SHOW LISTED BUTTON
        showListedButton.setBackground(new Color(-2702645));
        showListedButton.setForeground(new Color(-13236722));
        showListedButton.setText("Show listed ");
        changeFont(showListedButton, AVAILABLE_FONTS.BLACK_FONT, 12);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);
        domainTab.add(showListedButton, gbc);

        // FILTER COMBO BOX
        filterBox.setBackground(new Color(-2702645));
        filterBox.setEnabled(true);
        final DefaultComboBoxModel<JLabel> filterBoxModel = new DefaultComboBoxModel<>();
        for (DOMAIN_FILTER_OPTIONS option : ALL_OPTIONS_BY_ENUM.keySet()){
            JLabel label = new JLabel();
            label.setText(option.stringToken);
            filterBoxModel.addElement(label);
        }
        filterBox.setModel(filterBoxModel);
        filterBox.setSelectedIndex(0);
        filterBox.addActionListener(this);
        filterBox.setRenderer(new ComboBoxRenderer(filterBox));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 5, 0, 5);
        changeFont(filterBox, AVAILABLE_FONTS.BLACK_FONT, 12);
        domainTab.add(filterBox, gbc);
        domainTab.setBackground(new Color(-1));

        // DAY FILTER BUTTONS
        NavigableSet<DAY_FILTER> dayFilters = DAY_FILTER.ALL_OPTIONS_BY_ENUM.navigableKeySet();
        Iterator<DAY_FILTER> i = dayFilters.iterator();
        int c = 0;
        for (Enumeration<AbstractButton> it = bg_dayFilter.getElements(); it.hasMoreElements();){
            AbstractButton button = it.nextElement();
            DAY_FILTER f = i.next();
            button.setText(f.stringToken);
            if (f.stringToken.equalsIgnoreCase(getDayFilterToken())){
                button.setSelected(true);
                button.setBackground(new Color(-5275240));
                button.setForeground(new Color(-1));
                button.setToolTipText("(It is today)");
                changeFont(button, AVAILABLE_FONTS.BLACK_FONT, 12);
            }
            else{
                button.setBackground(new Color(-2702645));
                button.setForeground(new Color(-13236722));
                changeFont(button, AVAILABLE_FONTS.BLACK_FONT, 12);
            }
            gbc = new GridBagConstraints();
            gbc.gridx = 3 + c;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 5);
            gbc.anchor = GridBagConstraints.WEST;
            c++;
            domainTab.add(button,gbc);
        }

        // DOMAINS TAB PANEL
        JPanel devDomainsTabPanel = new JPanel();
        devDomainsTabPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
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
        showAllButton.addActionListener(this);
        showListedButton.addActionListener(this);
    }

    private String getDayFilter(){
        for (Enumeration<AbstractButton> it = bg_dayFilter.getElements(); it.hasMoreElements();){
            AbstractButton button = it.nextElement();
            if (button.getModel() == bg_dayFilter.getSelection()){
                return button.getText();
            }
        }
        return allButton.getText();
    }

    private void parseFilter(DOMAIN_FILTER_OPTIONS filter,String dayFilter, boolean status){
        List<DOMAIN_THEME> filteredThemes = new ArrayList<>();
        assert filter != null;
        switch(filter){
            case TALENT:
                filteredThemes.add(DOMAIN_THEME.TALENT_BOOK_THEME);
                break;
            case ARTIFACT:
                filteredThemes.add(DOMAIN_THEME.ARTIFACT_DOMAIN_THEME);
                break;
            case WEAPON_MAT:
                filteredThemes.add(DOMAIN_THEME.WEAPON_MATERIAL_THEME);
                break;
            case WEEKLY:
                filteredThemes.add(DOMAIN_THEME.WEEKLY_BOSS_DOMAIN_THEME);
                break;
            case NO_FILTER:
                Collections.addAll(filteredThemes, DOMAIN_THEME.values());
                break;
        }
        domainsPanelOverview.removeAll();
        domainsPanelOverview.updateUI();
        int i = 0;
        for (DOMAIN_THEME dt : filteredThemes) {
            Map<String, List<String>> domainMapping = getDomainMapping(dt);
            for (Domain domain : domainMapping.keySet()) {
                if (status || isSomethingFarmedInThisDomain(dt, domainName)) {
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = i++;
                    gbc.weightx = 1.0;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.insets = new Insets(5, 100, 5, 100);
                    domainsPanelOverview.add(
                            generateDomainCard(dt, domainName, dayFilter), gbc);
                }
            }
        }
    }

    /**
     * Returns whether something is farmed in this domain.
     * @param dt domain type identified by the theme.
     * @param domainName domain name
     * @return true if something is farmed, false if not.
     */
    public static boolean isSomethingFarmedInThisDomain(DOMAIN_THEME dt,Domain domain){
        Set<Item> farmedMapping = getDomainFarmedList(dt);
        List<String> domainItems = Objects.requireNonNull(getDomainMapping(dt)).get(domainName);
        for (Item item : farmedMapping){
            if (dt == DOMAIN_THEME.WEAPON_MATERIAL_THEME && domainItems.contains(getAscensionMaterialForWeapon(item))){
                return true;
            }
            else if (domainItems.contains(item)){
                return true;
            }
        }
        return false;
    }

    private JPanel generateDomainCard(DOMAIN_THEME dt, Domain domain, String dayFilter){
        JPanel domainCard = new JPanel(new GridBagLayout());
        domainCard.setBackground(new Color(dt.panelBackgroundColor));
        domainCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        int i = 0;
        for (String materialName : getDomainMapping(dt).get(domainName)){

            JLabel materialIconLabel = new JLabel();
            ImageIcon materialIcon = getResourceIcon(materialName, getDomainResourceType(dt));

            if (dayFilter.equalsIgnoreCase(DAY_FILTER.SUNDAY_ALL.stringToken) ||getDomainResourceType(dt) == WEEKLY_BOSS_MATERIAL
                    || getDomainResourceType(dt) == ToolData.RESOURCE_TYPE.ARTIFACT || dayToAvailableMaterialsMapping.get(dayFilter).contains(materialName)) {
                materialIconLabel.setIcon(materialIcon);
            }
            else{
                materialIcon = new ImageIcon(GrayFilter.createDisabledImage(materialIcon.getImage()));
                materialIconLabel.setIcon(materialIcon);
            }
            materialIconLabel.setToolTipText(materialName);
            materialIconLabel.setText("");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2 + i;
            gbc.gridy = 0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            domainCard.add(materialIconLabel, gbc);
            i++;
        }

        // DOMAIN INFO PANEL
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
        changeFont(domainNameLabel, AVAILABLE_FONTS.HEADER_FONT, 18);
        domainNameLabel.setForeground(new Color(dt.panelForegroundColor));
        domainNameLabel.setText(domainName);
        domainInfoPanel.add(domainNameLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));

        // CHARACTERS LISTED FOR THIS DOMAIN LABEL
        JLabel domainListedCounterLabel = new JLabel();
        changeFont(domainListedCounterLabel, AVAILABLE_FONTS.TEXT_FONT, 16);
        domainListedCounterLabel.setForeground(new Color(dt.panelForegroundColor));
        domainListedCounterLabel.setText(getListedCounterLabel(domainName,getDomainResourceType(dt)));
        domainInfoPanel.add(domainListedCounterLabel,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));

        // PANEL WITH ICON
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
        changeFont(domainIconLabel,AVAILABLE_FONTS.BLACK_FONT,16);
        domainIconLabel.setForeground(new Color(-1));
        domainIconLabel.setText(dt.marginSymbol);
        marginPanel.add(domainIconLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        domainCard.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new DomainCardGUI(domainName,dt);
            }
        });
        return domainCard;
    }

    /**
     * Returns the general mapping of a domain (All domains of this type -> All materials farmed in every domain of this type)
     * @param dt domain theme
     * @return the mapping
     */
    public static Map<String, List<String>> getDomainMapping(DOMAIN_THEME dt){
        switch(dt){
            case WEAPON_MATERIAL_THEME: return getMapping(WEPDOMAIN_WEPMAT);
            case TALENT_BOOK_THEME: return getMapping(TALENTDOMAIN_TALENTBOOK);
            case WEEKLY_BOSS_DOMAIN_THEME: return getMapping(WEEKLYDOMAIN_WEEKLYBOSSMAT);
            case ARTIFACT_DOMAIN_THEME: return getMapping(ARTIDOMAIN_ARTISET);
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns the TYPE of the resources farmed in this domain.
     * @param dt domain theme
     * @return respective resource type
     */
    public static ToolData.RESOURCE_TYPE getDomainResourceType(DOMAIN_THEME dt){
        switch(dt){
            case WEAPON_MATERIAL_THEME: return ToolData.RESOURCE_TYPE.WEAPON_MATERIAL;
            case TALENT_BOOK_THEME: return ToolData.RESOURCE_TYPE.TALENT_BOOK;
            case WEEKLY_BOSS_DOMAIN_THEME: return WEEKLY_BOSS_MATERIAL;
            case ARTIFACT_DOMAIN_THEME: return ToolData.RESOURCE_TYPE.ARTIFACT_SET;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns the mapping for the "beneficiary" of the farmed resources. E.g. if it's a weapon material domain, returns the weapon material mapping.
     * @param dt domain theme
     * @return the target resource mapping
     */
    public static Map<String, List<String>> getDomainTargetResourceMapping(DOMAIN_THEME dt){
        switch (dt){
            case WEAPON_MATERIAL_THEME: return getMapping(WEPMAT_WEPNAME);
            case TALENT_BOOK_THEME: return getMapping(TALENTBOOK_CHAR);
            case WEEKLY_BOSS_DOMAIN_THEME: return getMapping(WEEKLYBOSSMAT_CHAR);
            case ARTIFACT_DOMAIN_THEME: return getMapping(ARTIDOMAIN_ARTISET);
        };
        throw new IllegalArgumentException();
    }

    /**
     * Returns the type of the "benecifiary" of the farmed resoruces. For weapon materials, these are the weapons, and for everything else, these are characters.
     * @param dt domain theme
     * @return the target resource type.
     */
    public static ToolData.RESOURCE_TYPE getDomainTargetResourceType(DOMAIN_THEME dt){
        switch (dt){
            case WEAPON_MATERIAL_THEME: return ToolData.RESOURCE_TYPE.WEAPON_NAME;
            case TALENT_BOOK_THEME: case WEEKLY_BOSS_DOMAIN_THEME: case ARTIFACT_DOMAIN_THEME: return ToolData.RESOURCE_TYPE.CHARACTER;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a set of items farmed in all domains of this type.
     * @param dt domain theme
     * @return set of items
     */
    public static Set<Item> getDomainFarmedList(DOMAIN_THEME dt){
        switch(dt){
            case WEAPON_MATERIAL_THEME: {
                TreeSet<Item> allFarmedWeapons = new TreeSet<>(getUnassignedFarmedWeapons());
                for (String weapon: getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS).keySet()){
                    assert getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS) != null;
                    if (!getFarmedMapping(ToolGUI.FARMED_DATATYPE.WEAPONS).get(weapon).isEmpty()){
                        allFarmedWeapons.add(weapon);
                    }
                }
                return allFarmedWeapons;
            }
            case TALENT_BOOK_THEME: case WEEKLY_BOSS_DOMAIN_THEME: {
                TreeSet<String> allFarmedTalents = new TreeSet<>();
                for (String talentResourceName : getFarmedMapping(ToolGUI.FARMED_DATATYPE.TALENTS).keySet()){
                    if (!getFarmedMapping(ToolGUI.FARMED_DATATYPE.TALENTS).get(talentResourceName).isEmpty()){
                        allFarmedTalents.add(talentResourceName);
                    }
                }
                return allFarmedTalents;
            }
            case ARTIFACT_DOMAIN_THEME: {
                TreeSet<String> allFarmedArtifacts = new TreeSet<>();
                for (String artifactName : getFarmedMapping(ToolGUI.FARMED_DATATYPE.ARTIFACTS).keySet()){
                    if (!getFarmedMapping(ToolGUI.FARMED_DATATYPE.ARTIFACTS).get(artifactName).isEmpty()){
                        allFarmedArtifacts.add(artifactName);
                    }
                }
                return allFarmedArtifacts;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a counter label text that tells how many "benefactors" are there per domain.
     * E.g. for a talent domain: how many characters exist in the game that require talent books from the given domain
     * @param domainName domain name
     * @param rt resource type
     * @return the text
     */
    public static String getAllCounterLabel(Domain domain,ToolData.RESOURCE_TYPE rt){
        String labelText;
        String domainMaterialCategory = "";
        Set<String> matchedCharacters = new TreeSet<>();
        int counter = 0;
        switch(rt){
            case WEAPON_MATERIAL: {
                for (String matName: getMapping(WEPDOMAIN_WEPMAT).get(domainName)){
                    matchedCharacters.addAll(getMapping(WEPMAT_WEPNAME).get(matName));
                }
                domainMaterialCategory = "weapons";
                break;
            }
            case ARTIFACT_SET: {
                counter = getFlattenedData(ToolData.RESOURCE_TYPE.CHARACTER).size();
                matchedCharacters.add("All characters");
                domainMaterialCategory = "characters";
                break;
            }
            case WEEKLY_BOSS_MATERIAL: {
                for (String matName: getMapping(WEEKLYDOMAIN_WEEKLYBOSSMAT).get(domainName)){
                    matchedCharacters.addAll(getMapping(WEEKLYBOSSMAT_CHAR).get(matName));
                }
                domainMaterialCategory = "characters";
                break;
            }
            case TALENT_BOOK: {
                for (String matName: getMapping(TALENTDOMAIN_TALENTBOOK).get(domainName)){
                    matchedCharacters.addAll(getMapping(TALENTBOOK_CHAR).get(matName));
                }
                domainMaterialCategory = "characters";
                break;
            }
        }
        labelText = "<html>" + "All" + " " + domainMaterialCategory + " " + "that need it: " + "<u>"
                + (counter == 0 ? matchedCharacters.size() : counter ) + "</u>" + "</html>";
        return labelText;

    }
    /**
     * Returns a counter label text that tells how many listed "benefactors" are there per domain.
     * E.g. for a talent domain: how many characters are currently farming talents from that domain.
     * @param domainName domain name
     * @param rt resource type
     * @return the text
     */
    public static String getListedCounterLabel(Domain domain, ToolData.RESOURCE_TYPE rt){
        String labelText;
        String domainMaterialCategory = "";
        int counter = 0;
        switch(rt){
            case WEAPON_MATERIAL: {
                for (String weaponName: getDomainFarmedList(DOMAIN_THEME.WEAPON_MATERIAL_THEME)){
                    if (getMapping(WEPDOMAIN_WEPMAT).get(domainName).contains(getAscensionMaterialForWeapon(weaponName))){
                        counter++;
                    }
                }
                domainMaterialCategory = "weapons";
                break;
            }
            case ARTIFACT_SET: {
                Set<String> mapping = getDomainFarmedList(DOMAIN_THEME.ARTIFACT_DOMAIN_THEME);
                for (String setName : mapping){
                    if (getMapping(ARTIDOMAIN_ARTISET).get(domainName).contains(setName)){
                        counter += howManyAreFarmingThis(setName,ARTIFACT_SET);
                    }
                }
                domainMaterialCategory = "characters";
                break;
            }
            case WEEKLY_BOSS_MATERIAL: {
                Set<String> mapping = getDomainFarmedList(DOMAIN_THEME.WEEKLY_BOSS_DOMAIN_THEME);
                for (String materialName : mapping){
                    if (getMapping(WEEKLYDOMAIN_WEEKLYBOSSMAT).get(domainName).contains(materialName)){
                        counter += howManyAreFarmingThis(materialName,WEEKLY_BOSS_MATERIAL);
                    }
                }
                domainMaterialCategory = "characters";
                break;
            }
            case TALENT_BOOK: {
                Set<String> mapping = getDomainFarmedList(DOMAIN_THEME.TALENT_BOOK_THEME);
                for (String bookName : mapping){
                    if (getMapping(TALENTDOMAIN_TALENTBOOK).get(domainName).contains(bookName)){
                        counter += howManyAreFarmingThis(bookName,TALENT_BOOK);
                    }
                }
                domainMaterialCategory = "characters";
                break;
            }
            default: {}
        }
        labelText = "<html>" + "Listed" + " " + domainMaterialCategory + " " + "that need it: " + "<u>"
                + counter + "</u>" + "</html>";
        return labelText;
    }

    /**
     * Retrieves the main panel. Only used in ToolGUI.
     * @return main panel
     */
    public JPanel getMainPanel(){
        return domainTab;
    }

    /**
     * Returns a day filter string token based on the current system day.
     * @return day filter string.
     */
    public static String getDayFilterToken() {
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentDay = currentTime.get(Calendar.DAY_OF_WEEK);
        if (currentHour < 4){
            currentDay = (currentDay - 1) % 7;
        }
        switch (currentDay) {
            case MONDAY: case THURSDAY: return DAY_FILTER.MONDAY_THURSDAY.stringToken;
            case TUESDAY: case FRIDAY: return DAY_FILTER.TUESDAY_FRIDAY.stringToken;
            case WEDNESDAY: case SATURDAY: return DAY_FILTER.WEDNESDAY_SATURDAY.stringToken;
            default: return DAY_FILTER.SUNDAY_ALL.stringToken;
        }
    }
}
