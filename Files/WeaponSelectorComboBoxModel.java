package Files;

import javax.swing.DefaultComboBoxModel;

public class WeaponSelectorComboBoxModel extends DefaultComboBoxModel<iconLabel> {
    public WeaponSelectorComboBoxModel() {}
    @Override
    public void setSelectedItem(Object item) {
        if (((iconLabel) item).getText().equalsIgnoreCase(ToolGUI.FIVE_STAR_WEAPON_DELIMITER)
        || (((iconLabel) item).getText().equalsIgnoreCase(ToolGUI.FOUR_STAR_WEAPON_DELIMITER)))
        {
            return;
        }
        super.setSelectedItem(item);
    }
}
