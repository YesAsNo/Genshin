package Files;

import static Files.DomainTabGUI.getAllCounterLabel;
import static Files.DomainTabGUI.getDomainMapping;
import static Files.DomainTabGUI.getDomainResourceType;
import static Files.DomainTabGUI.getDomainTargetResourceMapping;
import static Files.DomainTabGUI.getDomainTargetResourceType;
import static Files.DomainTabGUI.getListedCounterLabel;
import static Files.ToolData.changeFont;
import static Files.ToolData.getResourceIcon;
import static Files.ToolGUI.MAX_CHARACTERS_PER_LINE;
import static Files.ToolGUI.getAllFarmedWeapons;
import static Files.ToolGUI.getCharacterCard;
import static Files.ToolGUI.whoIsFarmingThis;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;

/**
 * This class constructs a domain card GUI (shown after clicking on a domain card).
 */
public class DomainCardGUI extends JFrame {
    private final DomainTabGUI.DOMAIN_THEME domainTheme;
    private final String domainName;
    private final JPanel mainPanel = new JPanel(new GridBagLayout());

    /**
     * Constructor of the class.
     * @param domainName domain name
     * @param domainTheme domain type. There are currently 4 types in the game
     */
    public DomainCardGUI(String domainName, DomainTabGUI.DOMAIN_THEME domainTheme) {
        this.domainTheme = domainTheme;
        this.domainName = domainName;
        setTitle(domainName + " Overview");
        setContentPane(generateDomainCard());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("./Files/Images/Icons/Emblem_Domains_pink.png").getImage());
        setVisible(true);
        setResizable(false);
    }
    /**
     * Puts the info elements into the main panel.
     */
    private JPanel generateDomainCard(){

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

        // TITLE PANEL
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBackground(new Color(domainTheme.panelBackgroundColor));
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

        // DOMAIN NAME
        JLabel domainNameLabel = new JLabel();
        domainNameLabel.setForeground(new Color(domainTheme.panelForegroundColor));
        domainNameLabel.setText(domainName);
        changeFont(domainNameLabel, ToolData.AVAILABLE_FONTS.HEADER_FONT, 20);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        titlePanel.add(domainNameLabel, gbc);

        // OVERVIEW PANEL
        JPanel itemOverviewPanel = new JPanel();
        itemOverviewPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        itemOverviewPanel.setBackground(new Color(domainTheme.panelBackgroundColor));
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
        for (String domainMat : getDomainMapping(domainTheme).get(domainName)){
            JPanel innerUnlistedPanel = new JPanel(new GridBagLayout());
            JPanel innerListedPanel = new JPanel(new GridBagLayout());
            JPanel dayTab = new JPanel();
            dayTab.setLayout(new GridBagLayout());
            dayTab.setBackground(new Color(-1));
            itemOverviewTabbedPane.addTab("",getResourceIcon(domainMat,getDomainResourceType(domainTheme)), dayTab, domainMat);
            JLabel listedWeaponHeadline = new JLabel();
            listedWeaponHeadline.setForeground(new Color(domainTheme.panelForegroundColor));
            if (getDomainTargetResourceType(domainTheme) == ToolData.RESOURCE_TYPE.WEAPON_NAME){
                listedWeaponHeadline.setText("Listed weapons");
            }
            else{
                listedWeaponHeadline.setText("Listed characters");
            }
            changeFont(listedWeaponHeadline, ToolData.AVAILABLE_FONTS.HEADER_FONT, 15);

            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.NORTH;
            dayTab.add(listedWeaponHeadline, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.5;
            gbc.fill = GridBagConstraints.BOTH;
            dayTab.add(innerListedPanel,gbc);
            if(domainTheme != DomainTabGUI.DOMAIN_THEME.ARTIFACT_DOMAIN_THEME) {
                JLabel unlistedWeaponHeadline = new JLabel();
                unlistedWeaponHeadline.setBackground(new Color(domainTheme.panelForegroundColor));

                if (getDomainTargetResourceType(domainTheme) == ToolData.RESOURCE_TYPE.WEAPON_NAME) {
                    unlistedWeaponHeadline.setText("Other Weapons");
                } else {
                    unlistedWeaponHeadline.setText("Other Characters");
                }
                changeFont(unlistedWeaponHeadline, ToolData.AVAILABLE_FONTS.HEADER_FONT, 15);
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.NORTH;
                dayTab.add(unlistedWeaponHeadline, gbc);
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.weightx = 1.0;
                gbc.weighty = 0.5;
                gbc.fill = GridBagConstraints.BOTH;
                dayTab.add(innerUnlistedPanel, gbc);
            }

            int i = 0;
            int k = 0;
            switch (getDomainResourceType(domainTheme)){
                case ARTIFACT_SET -> {
                    for (String charName : whoIsFarmingThis(domainMat,getDomainResourceType(domainTheme))){
                        generateDomainItemLabel(charName,i,innerListedPanel);
                        i++;
                    }
                }
                case WEAPON_MATERIAL -> {
                    for (String eligibleWeapon : getEligibleItems(domainMat)){
                        if (getAllFarmedWeapons().contains(eligibleWeapon)){
                            generateDomainItemLabel(eligibleWeapon,i,innerListedPanel);
                            i++;
                        }
                        else{
                            generateDomainItemLabel(eligibleWeapon,k,innerUnlistedPanel);
                            k++;
                        }
                    }
                }
                case WEEKLY_BOSS_MATERIAL,TALENT_BOOK -> {
                    for (String eligibleCharacter : getEligibleItems(domainMat)){
                        if (whoIsFarmingThis(domainMat,getDomainResourceType(domainTheme)).contains(eligibleCharacter)){
                            generateDomainItemLabel(eligibleCharacter,i,innerListedPanel);
                            i++;
                            }
                        else{
                            generateDomainItemLabel(eligibleCharacter,k,innerUnlistedPanel);
                            k++;
                            }
                    }
                }
            }

        }

        // DOMAIN LISTINGS INFO
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        JLabel label = new JLabel(getListedCounterLabel(domainName,getDomainResourceType(domainTheme)));
        changeFont(label, ToolData.AVAILABLE_FONTS.REGULAR_FONT, 12);
        titlePanel.add(label, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        JLabel label2 = new JLabel(getAllCounterLabel(domainName,getDomainResourceType(domainTheme)));
        changeFont(label2, ToolData.AVAILABLE_FONTS.REGULAR_FONT, 12);
        titlePanel.add(label2, gbc);
        return mainPanel;
    }
    private List<String> getEligibleItems(String material){
        Map<String, List<String>> targetResourceMapping = getDomainTargetResourceMapping(domainTheme);
        return targetResourceMapping.get(material);



    }
    private String formatLabel(String characterName, String characterNotes){
        final String HTML_BEGINNING = "<html><center>";
        final String HTML_END = "</center></html>";
        final String HTML_BREAK = "<br>";
        StringBuilder formattedNotes = new StringBuilder(characterName + HTML_BREAK);
        for (int i = 0; i < characterNotes.length(); i++){
            if (i % MAX_CHARACTERS_PER_LINE == 0){
                formattedNotes.append(HTML_BREAK);
            }
            formattedNotes.append(characterNotes.charAt(i));
            }
        return HTML_BEGINNING + formattedNotes + HTML_END;
        }
    // GENERATED WEAPONS/CHARACTERS

    private void generateDomainItemLabel(String item, int index,JPanel panel){
        JLabel domainItemLabel = new JLabel();

        if (getDomainResourceType(domainTheme) == ToolData.RESOURCE_TYPE.ARTIFACT_SET) {
            CharacterCard card = getCharacterCard(item);
            if (card != null && !card.getCharacterNotes().isEmpty()) {
                domainItemLabel.setText(formatLabel(item, card.getCharacterNotes()));
            } else {
                domainItemLabel.setText(item);
            }
        }
        domainItemLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        domainItemLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        domainItemLabel.setVerticalTextPosition(SwingConstants.CENTER);
        domainItemLabel.setVerticalAlignment(SwingConstants.TOP);
        changeFont(domainItemLabel, ToolData.AVAILABLE_FONTS.TEXT_FONT, 12.0F);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = index % 3;
        gbc.gridy = index / 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 10);
        domainItemLabel.setIcon(getResourceIcon(item,getDomainTargetResourceType(domainTheme)));
        panel.add(domainItemLabel, gbc);
    }
}
