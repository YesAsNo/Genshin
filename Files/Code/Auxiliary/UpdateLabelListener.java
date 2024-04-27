package Files;

import static Files.ToolData.RESOURCE_TYPE;
import static Files.ToolData.getArtifact;
import static Files.ToolData.getPlaceholderIcon;
import static Files.ToolData.getWeapon;
import static Files.ToolGUI.EMPTY_SET_SELECTOR;
import static Files.ToolGUI.EMPTY_WEAPON_SELECTOR;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * This class updates the labels that show either the icon or the name based on the selection made in a combobox.
 * Used in CharacterCard.java
 */
public class UpdateLabelListener implements ActionListener {
    private final javax.swing.JLabel _JLabel;
    private final javax.swing.JLabel _NameLabel;
    private final JCheckBox _checkBox;
    private final ToolData.RESOURCE_TYPE SELECTION_BOX_TYPE;

    /**
     * Constructor of the class.
     * @param nameLabel the label that shows the selected item name
     * @param iconLabel the label that shows the selected item icon
     * @param checkBox the checkbox that lists the item
     * @param RESOURCE_TYPE the resource type of the item
     */
    public UpdateLabelListener(javax.swing.JLabel nameLabel , javax.swing.JLabel iconLabel, JCheckBox checkBox, ToolData.RESOURCE_TYPE RESOURCE_TYPE){
        _NameLabel = nameLabel;
        _JLabel = iconLabel;
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
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.ARTIFACT) {
                icon = getPlaceholderIcon("artifact");
            }
            else {
                icon = getPlaceholderIcon("weapon");
            }
        }
        else
        {
            _NameLabel.setText(item);
            _checkBox.setEnabled(true);
            if (SELECTION_BOX_TYPE == RESOURCE_TYPE.WEAPON_NAME) {
                icon = getWeapon(item).icon;
            }
            else {
                icon = getArtifact(item).icon;
            }
        }
        _JLabel.setIcon(icon);
    }
}
