package Files;

import static Files.DomainTabGUI.getDomainFarmedMapping;
import static Files.DomainTabGUI.getDomainResourceTypeMapping;
import static Files.DomainTabGUI.getDomainTargetResourceType;
import static Files.ToolData.generateResourceIconPath;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DomainCardGUI extends JFrame {
    private final DomainTabGUI.DOMAIN_THEME domainTheme;
    private final String dName;
    private final Map<String, ImageIcon> matIcons;
    private final JLabel allCounterLabel;
    private final JLabel listedCounterLabel;
    private final JPanel mainPanel = new JPanel(new GridBagLayout());


    public DomainCardGUI(String domainName, DomainTabGUI.DOMAIN_THEME domainTheme, Map<String, ImageIcon> matIcons, JLabel domainAllCounterLabel, JLabel domainListedCounterLabel) {
        this.domainTheme = domainTheme;
        dName = domainName;
        this.matIcons = matIcons;
        allCounterLabel = domainAllCounterLabel;
        listedCounterLabel = domainListedCounterLabel;
        setTitle(domainName + " Overview");
        setContentPane(generateDomainCard());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JPanel generateDomainCard(){
        Map<String, List<String>> mapping = getDomainResourceTypeMapping(domainTheme);
        Map<String, Set<String>> farmedMapping = getDomainFarmedMapping(domainTheme);
        System.out.println(mapping);
        System.out.println(farmedMapping);
        JScrollPane mainPanelScrollPane = new JScrollPane();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(mainPanelScrollPane, gbc);
        JPanel mainScrollPaneViewport = new JPanel();
        mainScrollPaneViewport.setLayout(new GridBagLayout());
        mainScrollPaneViewport.setBackground(new Color(-465419));
        mainPanelScrollPane.setViewportView(mainScrollPaneViewport);
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBackground(new Color(-10301));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 50, 0, 50);
        mainScrollPaneViewport.add(titlePanel, gbc);
        titlePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        JLabel domainNameLabel = new JLabel();
        domainNameLabel.setForeground(new Color(-13494016));
        domainNameLabel.setText(dName);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        titlePanel.add(domainNameLabel, gbc);
        JPanel itemOverviewPanel = new JPanel();
        itemOverviewPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        itemOverviewPanel.setBackground(new Color(-10301));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        titlePanel.add(itemOverviewPanel, gbc);
        JTabbedPane itemOverviewTabbedPane = new JTabbedPane();
        itemOverviewTabbedPane.setBackground(new Color(-1));
        itemOverviewTabbedPane.setTabPlacement(2);
        itemOverviewPanel.add(itemOverviewTabbedPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
                        new Dimension(200, 200), null, 0, false));

        for (String domainMat : matIcons.keySet()){
            System.out.println(domainMat);
            JPanel innerUnlistedPanel = new JPanel(new GridBagLayout());
            JPanel innerListedPanel = new JPanel(new GridBagLayout());
            JPanel dayTab = new JPanel();
            dayTab.setLayout(new GridBagLayout());
            dayTab.setBackground(new Color(-1));
            itemOverviewTabbedPane.addTab("",matIcons.get(domainMat), dayTab);
            JLabel listedWeaponHeadline = new JLabel();
            listedWeaponHeadline.setForeground(new Color(-13494016));
            if (getDomainTargetResourceType(domainTheme) == ToolData.RESOURCE_TYPE.WEAPON){
                listedWeaponHeadline.setText("Listed weapons");
            }
            else{
                listedWeaponHeadline.setText("Listed characters");
            }

            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 0.01;
            gbc.anchor = GridBagConstraints.NORTH;
            dayTab.add(listedWeaponHeadline, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            dayTab.add(innerListedPanel,gbc);
            if(domainTheme != DomainTabGUI.DOMAIN_THEME.ARTIFACT_DOMAIN_THEME) {
                JLabel unlistedWeaponHeadline = new JLabel();
                unlistedWeaponHeadline.setBackground(new Color(-13494016));

                if (getDomainTargetResourceType(domainTheme) == ToolData.RESOURCE_TYPE.WEAPON) {
                    unlistedWeaponHeadline.setText("Other Weapons");
                } else {
                    unlistedWeaponHeadline.setText("Other Characters");
                }
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weightx = 1.0;
                gbc.weighty = 0.1;
                gbc.anchor = GridBagConstraints.NORTH;
                dayTab.add(unlistedWeaponHeadline, gbc);
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
                dayTab.add(innerUnlistedPanel, gbc);
            }
            int i = 0;
            if (domainTheme != DomainTabGUI.DOMAIN_THEME.ARTIFACT_DOMAIN_THEME){
                for(String farmedTargetItem : mapping.get(domainMat)) {
                    if (farmedMapping.containsKey(domainMat) && farmedMapping.get(domainMat).contains(farmedTargetItem)){
                        generateDomainItemLabel(farmedTargetItem,i,innerListedPanel).setIcon(
                                new ImageIcon(generateResourceIconPath(farmedTargetItem,getDomainTargetResourceType(domainTheme))));
                    }
                    else{
                        generateDomainItemLabel(farmedTargetItem,i,innerUnlistedPanel).setIcon(
                                new ImageIcon(generateResourceIconPath(farmedTargetItem,getDomainTargetResourceType(domainTheme))));
                    }
                    i++;
                }
            }
            else{
                if (farmedMapping.containsKey(domainMat)) {
                    for (String farmedTargetItem : farmedMapping.get(domainMat)) {
                        generateDomainItemLabel(farmedTargetItem, i, innerListedPanel).setIcon(new ImageIcon(
                                generateResourceIconPath(farmedTargetItem, getDomainTargetResourceType(domainTheme))));
                        i++;
                    }
                }
            }
        }

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        titlePanel.add(listedCounterLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        titlePanel.add(allCounterLabel, gbc);
        return mainPanel;
    }
    private JLabel generateDomainItemLabel(String item, int index,JPanel panel){
        JLabel domainItemLabel = new JLabel();
        domainItemLabel.setHorizontalAlignment(0);
        domainItemLabel.setHorizontalTextPosition(0);
        domainItemLabel.setText(item);
        domainItemLabel.setVerticalAlignment(0);
        domainItemLabel.setVerticalTextPosition(3);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = index % 4;
        gbc.gridy = index / 4;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.add(domainItemLabel, gbc);
        return domainItemLabel;
    }
}
