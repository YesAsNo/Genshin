package Files;

import static Files.DomainTabGUI.getDomainResourceTypeMapping;
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

public class DomainCardGUI extends JFrame {
    private final DomainTabGUI.domainTheme domainTheme;
    private final String dName;
    private final Map<String, ImageIcon> matIcons;
    private final JLabel allCounterLabel;
    private final JLabel listedCounterLabel;
    private final JPanel mainPanel = new JPanel(new GridBagLayout());

    public DomainCardGUI(String domainName, DomainTabGUI.domainTheme domainTheme, Map<String, ImageIcon> matIcons, JLabel domainAllCounterLabel, JLabel domainListedCounterLabel) {
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
            JPanel MonThu_Tab = new JPanel();
            MonThu_Tab.setLayout(new GridBagLayout());
            MonThu_Tab.setBackground(new Color(-1));
            itemOverviewTabbedPane.addTab("",matIcons.get(domainMat), MonThu_Tab);
            JLabel listedWeaponHeadline = new JLabel();
            listedWeaponHeadline.setForeground(new Color(-13494016));
            listedWeaponHeadline.setText("Weapons that still need this material");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 0.01;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.NORTH;
            MonThu_Tab.add(listedWeaponHeadline, gbc);
            JLabel unlistedWeaponHeadline = new JLabel();
            unlistedWeaponHeadline.setBackground(new Color(-13494016));
            unlistedWeaponHeadline.setText("Weapons that are unlisted");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.NORTH;
            MonThu_Tab.add(unlistedWeaponHeadline, gbc);
            int i = 0;
            for(String domainItem : mapping.get(domainMat)){
                JLabel domainItemLabel = new JLabel();
                domainItemLabel.setHorizontalAlignment(0);
                domainItemLabel.setHorizontalTextPosition(0);
                domainItemLabel.setIcon(
                        new ImageIcon(generateResourceIconPath(domainItem, ToolData.RESOURCE_TYPE.WEAPON)));//TODO:  Change this to appropriate resource.
                domainItemLabel.setText(domainItem);
                domainItemLabel.setVerticalAlignment(0);
                domainItemLabel.setVerticalTextPosition(3);
                gbc = new GridBagConstraints();
                gbc.gridx = i % 3;
                gbc.gridy = 2 + i/3;
                i++;
                MonThu_Tab.add(domainItemLabel, gbc);

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

}
