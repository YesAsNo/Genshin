package Files;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

public class WeaponSelectorComboBoxModel extends DefaultComboBoxModel<JLabel> {
    public WeaponSelectorComboBoxModel() {}
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
