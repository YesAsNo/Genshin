package Files;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

/**
 * This class prevents selection of four and five-star weapon delimiters in the weapon selector comboboxes in CharacterCard.java
 */
public class WeaponSelectorComboBoxModel extends DefaultComboBoxModel<JLabel> {
    @Override
    public void setSelectedItem(Object item) {
        if (((JLabel) item).getText().equalsIgnoreCase(ToolGUI.FIVE_STAR_WEAPON_DELIMITER)
        || (((JLabel) item).getText().equalsIgnoreCase(ToolGUI.FOUR_STAR_WEAPON_DELIMITER)))
        {
            return;
        }
        super.setSelectedItem(item);
    }
}
