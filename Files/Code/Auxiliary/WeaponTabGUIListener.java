package Files.Code.Auxiliary;

import static Files.Code.GUIs.ToolGUI.serializeSave;
import static Files.Code.GUIs.WeaponTabGUI.getUnassignedFarmedWeapons;

import Files.Code.Data.Weapon;

import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class is the listener for all checkboxes in WeaponTabGUI.java
 */
public class WeaponTabGUIListener implements ItemListener {
    private final Weapon weapon;

    /**
     * Constructor of the class
     *
     * @param weapon weapon
     */
    public WeaponTabGUIListener(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        source.setEnabled(false);
        if (source.isSelected()) {
            getUnassignedFarmedWeapons().add(weapon);
        } else {
            getUnassignedFarmedWeapons().remove(weapon);
        }
        serializeSave();
        source.setEnabled(true);
    }
}
