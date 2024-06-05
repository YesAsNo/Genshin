package Files.Code.Auxiliary;

import static Files.Code.GUIs.ToolGUI.FIVE_STAR_WEAPON_DELIMITER;
import static Files.Code.GUIs.ToolGUI.FOUR_STAR_WEAPON_DELIMITER;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

/**
 * This class prevents selection of four and five-star weapon delimiters in the weapon selector comboboxes in CharacterCard.java
 */
public class WeaponSelectorComboBoxModel extends DefaultComboBoxModel<JLabel> {
    @Override
    public void setSelectedItem(Object item) {
        if (((JLabel) item).getText().equalsIgnoreCase(FIVE_STAR_WEAPON_DELIMITER) ||
                (((JLabel) item).getText().equalsIgnoreCase(FOUR_STAR_WEAPON_DELIMITER))) {
            return;
        }
        super.setSelectedItem(item);
    }
}
