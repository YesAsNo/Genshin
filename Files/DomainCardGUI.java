package Files;

import static Files.CharacterCardGUI.$$$getFont$$$;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


public class DomainCardGUI {
    private final JPanel _card;
    private final ToolGUI _toolGUI;
    public enum decorationTheme{
        WEAPON_MATERIAL_THEME(-10301,-13494016,-26768,"\uD83D\uDD2A"),
        TALENT_BOOK_THEME(-1068,-14541824,-10640,"\uD83D\uDCD4");
        public final int panelBackgroundColor;
        public final int panelForegroundColor;
        public final int marginBackgroundColor;
        public final String marginSymbol;
        decorationTheme(int bgColor, int fgColor, int marginBgColor, String symbol){
            this.panelBackgroundColor = bgColor;
            this.panelForegroundColor = fgColor;
            this.marginBackgroundColor = marginBgColor;
            this.marginSymbol = symbol;
        }
    }
    public DomainCardGUI(ToolGUI toolGUI) {
        _toolGUI = toolGUI;
        _card = new JPanel();
        _card.setLayout(new GridBagLayout());
        _card.setBackground(new Color(-10301));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 100, 5, 100);
        _toolGUI.addDomainCardToViewport(_card,gbc);
        _card.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

        JLabel materialIconR = new JLabel();
        materialIconR.setIcon(
                new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/All Mist Veiled Elixir.png")));
        materialIconR.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        _card.add(materialIconR, gbc);

        JLabel materialIconMiddle = new JLabel();
        materialIconMiddle.setIcon(
                new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/All Guyun.png")));
        materialIconMiddle.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        _card.add(materialIconMiddle, gbc);
        JLabel materialIconL = new JLabel();
        materialIconL.setIcon(
                new ImageIcon(getClass().getResource("/Files/Images/Weapon Materials/All Aerosiderite.png")));
        materialIconL.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        _card.add(materialIconL, gbc);
        JPanel weaponMaterialInfoPanel = new JPanel();
        weaponMaterialInfoPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        weaponMaterialInfoPanel.setAlignmentX(0.5f);
        weaponMaterialInfoPanel.setAlignmentY(0.5f);
        weaponMaterialInfoPanel.setBackground(new Color(-10301));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        _card.add(weaponMaterialInfoPanel, gbc);
        JLabel domainNameLabel = new JLabel();
        Font domainNameLabelFont = $$$getFont$$$( Font.BOLD, 18, domainNameLabel.getFont());
        if (domainNameLabelFont != null) {
            domainNameLabel.setFont(domainNameLabelFont);
        }
        domainNameLabel.setForeground(new Color(-13494016));
        domainNameLabel.setText("Hidden Palace of Lianshan Formula");
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
        domainAllWeaponCounterLabel.setForeground(new Color(-13494016));
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
        domainListedWeaponCounterLabel.setForeground(new Color(-13494016));
        domainListedWeaponCounterLabel.setText("Weapons listed for this domain: 34");
        weaponMaterialInfoPanel.add(domainListedWeaponCounterLabel,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        JPanel marginPanel = new JPanel();
        marginPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 5), -1, -1));
        marginPanel.setAlignmentX(0.5f);
        marginPanel.setAlignmentY(0.5f);
        marginPanel.setBackground(new Color(-26768));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 0);
        _card.add(marginPanel, gbc);
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
        weaponMaterialDomainIconLabel.setText("\uD83D\uDD2A");
        marginPanel.add(weaponMaterialDomainIconLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));

    }
}
