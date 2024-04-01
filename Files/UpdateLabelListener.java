package Files;

import static Files.ToolData.RESOURCE_TYPE;
import static Files.ToolData.generateResourceIconPath;
import static Files.ToolGUI.UNKNOWN_ARTIFACT;
import static Files.ToolGUI.UNKNOWN_SET_MESSAGE;
import static Files.ToolGUI.UNKNOWN_WEAPON_MESSAGE;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UpdateLabelListener implements ActionListener {
    private final JLabel _IconLabel;
    private final JLabel _NameLabel;
    private final JCheckBox _checkBox;
    private final ToolData.RESOURCE_TYPE SELECTION_BOX_TYPE;

    public UpdateLabelListener(JLabel nameLabel , JLabel iconLabel, JCheckBox checkBox, ToolData.RESOURCE_TYPE RESOURCE_TYPE){
        _NameLabel = nameLabel;
        _IconLabel = iconLabel;
        _checkBox = checkBox;
        SELECTION_BOX_TYPE = RESOURCE_TYPE;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String item = ((iconLabel) ((JComboBox<?>) e.getSource()).getSelectedItem()).getText();
        assert item != null;
        ImageIcon icon;
        if (item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE) || item.equalsIgnoreCase(UNKNOWN_WEAPON_MESSAGE))
        {

            _NameLabel.setText("");
            _checkBox.setEnabled(false);
            if (_checkBox.isSelected()){
                _checkBox.setSelected(false);
            }
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.ARTIFACT_SET) {
                icon = new ImageIcon(generateResourceIconPath(UNKNOWN_ARTIFACT, ToolData.RESOURCE_TYPE.ARTIFACT_SET));
            }
            else {
                icon = new ImageIcon(ToolGUI.UNKNOWN_WEAPON_PATH);
            }
        }
        else
        {
            _NameLabel.setText(item);
            _checkBox.setEnabled(true);
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.WEAPON_NAME) {
                icon = new ImageIcon(generateResourceIconPath(item, ToolData.RESOURCE_TYPE.WEAPON_NAME));
            }
            else {
                icon = new ImageIcon(generateResourceIconPath(item, RESOURCE_TYPE.ARTIFACT_SET));
            }
        }
        _IconLabel.setIcon(icon);
    }
}
