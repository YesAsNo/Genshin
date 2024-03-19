package Files;

import static Files.ToolGUI.*;
import static Files.ToolData.*;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UpdateLabelListener implements ActionListener {
    private final JLabel _IconLabel;
    private final JLabel _NameLabel;
    private final ToolData.RESOURCE_TYPE SELECTION_BOX_TYPE;

    public UpdateLabelListener(JLabel nameLabel , JLabel iconLabel, ToolData.RESOURCE_TYPE RESOURCE_TYPE){
        _NameLabel = nameLabel;
        _IconLabel = iconLabel;
        SELECTION_BOX_TYPE = RESOURCE_TYPE;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String item = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();
        assert item != null;
        ImageIcon icon;
        if (item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE) || item.equalsIgnoreCase(UNKNOWN_WEAPON_MESSAGE))
        {

            _NameLabel.setText("");
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.ARTIFACT) {
                icon = new ImageIcon(generateResourceIconPath(UNKNOWN_ARTIFACT, ToolData.RESOURCE_TYPE.ARTIFACT));
            }
            else {
                icon = new ImageIcon(ToolGUI.UNKNOWN_WEAPON_PATH);
            }
        }
        else
        {
            _NameLabel.setText(item);
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.WEAPON) {
                icon = new ImageIcon(generateResourceIconPath(item, ToolData.RESOURCE_TYPE.WEAPON));
            }
            else {
                icon = new ImageIcon(generateResourceIconPath(item, RESOURCE_TYPE.ARTIFACT));
            }
        }
        _IconLabel.setIcon(icon);
    }
}
