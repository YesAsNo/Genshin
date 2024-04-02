package Files;

import static Files.ToolData.RESOURCE_TYPE;
import static Files.ToolData.getResourceIcon;
import static Files.ToolGUI.EMPTY_SET_SELECTOR;
import static Files.ToolGUI.EMPTY_WEAPON_SELECTOR;
import static Files.ToolGUI.UNKNOWN_ARTIFACT;
import static Files.ToolGUI.UNKNOWN_WEAPON;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UpdateLabelListener implements ActionListener {
    private final javax.swing.JLabel _JLabel;
    private final javax.swing.JLabel _NameLabel;
    private final JCheckBox _checkBox;
    private final ToolData.RESOURCE_TYPE SELECTION_BOX_TYPE;

    public UpdateLabelListener(javax.swing.JLabel nameLabel , javax.swing.JLabel jLabel, JCheckBox checkBox, ToolData.RESOURCE_TYPE RESOURCE_TYPE){
        _NameLabel = nameLabel;
        _JLabel = jLabel;
        _checkBox = checkBox;
        SELECTION_BOX_TYPE = RESOURCE_TYPE;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String item = ((JLabel) Objects.requireNonNull(((JComboBox<?>) e.getSource()).getSelectedItem())).getText();
        assert item != null;
        ImageIcon icon;
        if (item.equalsIgnoreCase(EMPTY_SET_SELECTOR) || item.equalsIgnoreCase(EMPTY_WEAPON_SELECTOR))
        {

            _NameLabel.setText("");
            _checkBox.setEnabled(false);
            if (_checkBox.isSelected()){
                _checkBox.setSelected(false);
            }
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.ARTIFACT_SET) {
                icon = getResourceIcon(UNKNOWN_ARTIFACT, ToolData.RESOURCE_TYPE.ARTIFACT_SET);
            }
            else {
                icon = getResourceIcon(UNKNOWN_WEAPON,RESOURCE_TYPE.WEAPON_NAME);
            }
        }
        else
        {
            _NameLabel.setText(item);
            _checkBox.setEnabled(true);
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.WEAPON_NAME) {
                icon = getResourceIcon(item, ToolData.RESOURCE_TYPE.WEAPON_NAME);
            }
            else {
                icon = getResourceIcon(item, RESOURCE_TYPE.ARTIFACT_SET);
            }
        }
        _JLabel.setIcon(icon);
    }
}
