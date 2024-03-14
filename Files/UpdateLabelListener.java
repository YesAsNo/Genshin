package Files;

import static Files.ToolGUI.UNKNOWN_SET_MESSAGE;
import static Files.ToolGUI.UNKNOWN_WEAPON_MESSAGE;
import static Files.ToolGUI.generateArtifactIconPath;
import static Files.ToolGUI.lookUpWeaponRarity;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UpdateLabelListener implements ActionListener {
    private final JLabel _IconLabel;
    private final JLabel _NameLabel;
    private final ToolData.SELECTION_BOX_TYPE _SELECTIONBOXTYPE;

    public UpdateLabelListener(JLabel nameLabel , JLabel iconLabel, ToolData.SELECTION_BOX_TYPE SELECTION_BOX_TYPE){
        _NameLabel = nameLabel;
        _IconLabel = iconLabel;
        _SELECTIONBOXTYPE = SELECTION_BOX_TYPE;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String item = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();
        assert item != null;
        ImageIcon icon;
        if (item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE) || item.equalsIgnoreCase(UNKNOWN_WEAPON_MESSAGE))
        {

            _NameLabel.setText("");
            if (_SELECTIONBOXTYPE == ToolData.SELECTION_BOX_TYPE.ARTIFACT) {
                icon = new ImageIcon(generateArtifactIconPath(ToolGUI.UNKNOWN_ARTIFACT));
            }
            else {
                icon = new ImageIcon(ToolGUI.generateWeaponPath());
            }
        }
        else
        {
            _NameLabel.setText(item);
            if (_SELECTIONBOXTYPE == ToolData.SELECTION_BOX_TYPE.WEAPON) {
                WeaponInfo weaponInfo = lookUpWeaponRarity(item);
                icon = new ImageIcon(ToolGUI.generateWeaponPath(item, weaponInfo.getWeaponType(), weaponInfo.getRarity()));
            }
            else {
                icon = new ImageIcon(generateArtifactIconPath(item));
            }
        }
        _IconLabel.setIcon(icon);
    }
}
