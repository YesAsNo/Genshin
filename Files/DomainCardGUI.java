package Files;

import static Files.CharacterCardGUI.$$$getFont$$$;
import static Files.DomainCardGUI.DOMAIN_FILTER_OPTIONS.ALL_OPTIONS_BY_ENUM;
import static Files.DomainCardGUI.DOMAIN_FILTER_OPTIONS.ALL_OPTIONS_BY_STRING;
import static Files.ToolData.generateResourceIconPath;
import static Files.ToolData.getMapping;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DomainCardGUI implements ActionListener {
    private final JPanel domainTab = new JPanel(new GridBagLayout());
    private final JComboBox<String> filterBox = new JComboBox<>();
    private final JPanel domainsPanelOverview = new JPanel(new GridBagLayout());

    @Override
    public void actionPerformed(ActionEvent e) {
        DOMAIN_FILTER_OPTIONS option = ALL_OPTIONS_BY_STRING.get((String)filterBox.getSelectedItem());
        parseFilter(option);
    }

    public enum domainTheme {
        WEAPON_MATERIAL_THEME(-10301,-13494016,-26768,"\uD83D\uDD2A"),
        TALENT_BOOK_THEME(-1068,-14541824,-10640,"\uD83D\uDCD4"),
        WEEKLY_BOSS_DOMAIN(-11811,-13236722,-36698,"\uD83D\uDC09"),
        ARTIFACT_DOMAIN(-2756865,-16575201,-9382145,"\uD83D\uDC51");
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
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 10);
        domainTab.add(filterBox, gbc);
        domainTab.setBackground(new Color(-1));
        JPanel devDomainsTabPanel = new JPanel();
        devDomainsTabPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
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
    }
    private void parseFilter(DOMAIN_FILTER_OPTIONS filter){
        List<domainTheme> filteredThemes = new ArrayList<>();
        assert filter != null;
        switch(filter){
            case TALENT -> filteredThemes.add(domainTheme.TALENT_BOOK_THEME);
            case ARTIFACT -> filteredThemes.add(domainTheme.ARTIFACT_DOMAIN);
            case WEAPON_MAT -> filteredThemes.add(domainTheme.WEAPON_MATERIAL_THEME);
            case WEEKLY -> filteredThemes.add(domainTheme.WEEKLY_BOSS_DOMAIN);
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
                        domainMapping.get(domainName)), gbc);
            }
        }
    }
    private JPanel generateDomainCard(domainTheme dt,String domainName, List<String> domainMaterials){
        JPanel domainCard = new JPanel(new GridBagLayout());
        domainCard.setBackground(new Color(dt.panelBackgroundColor));
        domainCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        int i = 0;
        for (String materialName : domainMaterials){
            JLabel materialIcon = new JLabel();
            materialIcon.setIcon(new ImageIcon(generateResourceIconPath(materialName, getDomainResourceType(dt))));
            materialIcon.setText("");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2 + i++;
            gbc.gridy = 0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            domainCard.add(materialIcon, gbc);
        }
        JPanel weaponMaterialInfoPanel = new JPanel();
        weaponMaterialInfoPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        weaponMaterialInfoPanel.setAlignmentX(0.5f);
        weaponMaterialInfoPanel.setAlignmentY(0.5f);
        weaponMaterialInfoPanel.setBackground(new Color(dt.panelBackgroundColor));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        domainCard.add(weaponMaterialInfoPanel, gbc);
        JLabel domainNameLabel = new JLabel();
        Font domainNameLabelFont = $$$getFont$$$( Font.BOLD, 18, domainNameLabel.getFont());
        if (domainNameLabelFont != null) {
            domainNameLabel.setFont(domainNameLabelFont);
        }
        domainNameLabel.setForeground(new Color(dt.panelForegroundColor));
        domainNameLabel.setText(domainName);
        weaponMaterialInfoPanel.add(domainNameLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JLabel domainAllWeaponCounterLabel = new JLabel();
        Font domainAllWeaponCounterLabelFont =
               $$$getFont$$$(-1, 12, domainAllWeaponCounterLabel.getFont());
        if (domainAllWeaponCounterLabelFont != null) {
            domainAllWeaponCounterLabel.setFont(domainAllWeaponCounterLabelFont);
        }
        domainAllWeaponCounterLabel.setForeground(new Color(dt.panelForegroundColor));
        domainAllWeaponCounterLabel.setText("All weapons that need it: 53");
        weaponMaterialInfoPanel.add(domainAllWeaponCounterLabel,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JLabel domainListedWeaponCounterLabel = new JLabel();
        Font domainListedWeaponCounterLabelFont =
                $$$getFont$$$(-1, 12, domainListedWeaponCounterLabel.getFont());
        if (domainListedWeaponCounterLabelFont != null) {
            domainListedWeaponCounterLabel.setFont(domainListedWeaponCounterLabelFont);
        }
        domainListedWeaponCounterLabel.setForeground(new Color(dt.panelForegroundColor));
        domainListedWeaponCounterLabel.setText("Weapons listed for this domain: 34");
        weaponMaterialInfoPanel.add(domainListedWeaponCounterLabel,
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
        JLabel weaponMaterialDomainIconLabel = new JLabel();
        weaponMaterialDomainIconLabel.setAlignmentX(0.5f);
        weaponMaterialDomainIconLabel.setFocusTraversalPolicyProvider(false);
        weaponMaterialDomainIconLabel.setFocusable(false);
        Font weaponMaterialDomainIconLabelFont =
                $$$getFont$$$(Font.BOLD, 16, weaponMaterialDomainIconLabel.getFont());
        if (weaponMaterialDomainIconLabelFont != null) {
            weaponMaterialDomainIconLabel.setFont(weaponMaterialDomainIconLabelFont);
        }
        weaponMaterialDomainIconLabel.setForeground(new Color(-1));
        weaponMaterialDomainIconLabel.setText(dt.marginSymbol);
        marginPanel.add(weaponMaterialDomainIconLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        return domainCard;
    }
    public Map<String, List<String>> getDomainMapping(domainTheme dt){
        return switch(dt){
            case WEAPON_MATERIAL_THEME -> getMapping(ToolData.knownMappings.WEPDOMAIN_WEPMAT);
            case TALENT_BOOK_THEME -> getMapping(ToolData.knownMappings.TALENTDOMAIN_TALENTBOOK);
            case WEEKLY_BOSS_DOMAIN -> getMapping(ToolData.knownMappings.WEEKLYDOMAIN_WEEKLYBOSSMAT);
            case ARTIFACT_DOMAIN -> getMapping(ToolData.knownMappings.ARTIDOMAIN_ARTISET);
        };
    }
    public ToolData.RESOURCE_TYPE getDomainResourceType(domainTheme dt){
        return switch(dt){
            case WEAPON_MATERIAL_THEME -> ToolData.RESOURCE_TYPE.WEAPON_MATERIAL;
            case TALENT_BOOK_THEME -> ToolData.RESOURCE_TYPE.TALENT_BOOK;
            case WEEKLY_BOSS_DOMAIN -> ToolData.RESOURCE_TYPE.WEEKLY_BOSS_MATERIAL;
            case ARTIFACT_DOMAIN -> ToolData.RESOURCE_TYPE.ARTIFACT;
        };
    }

    public JPanel getMainPanel(){
        return domainTab;
    }
}
