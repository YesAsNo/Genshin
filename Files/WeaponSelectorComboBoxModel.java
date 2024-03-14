package Files;

import javax.swing.DefaultComboBoxModel;

public class WeaponSelectorComboBoxModel extends DefaultComboBoxModel<String> {
    public WeaponSelectorComboBoxModel() {}
    @Override
    public void setSelectedItem(Object item) {
        if (((String) item).equalsIgnoreCase(ToolGUI.FIVE_STAR_WEAPON_DELIMITER)
        || (((String) item).equalsIgnoreCase(ToolGUI.FOUR_STAR_WEAPON_DELIMITER)))
        {
            return;
        }
        super.setSelectedItem(item);
    }
}
