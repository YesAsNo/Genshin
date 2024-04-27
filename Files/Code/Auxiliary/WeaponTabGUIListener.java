package Files.Code.Auxiliary;

import Files.Code.Data.Weapon;

import static Files.Code.GUIs.WeaponTabGUI.getUnassignedFarmedWeapons;
import static Files.ToolGUI.serializeSave;

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
     * @param name the name of the weapon
     */
    public WeaponTabGUIListener(Weapon weapon){
        this.weapon = weapon;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        source.setEnabled(false);
        if (source.isSelected()){
            getUnassignedFarmedWeapons().add(weapon);
        }
        else{
            getUnassignedFarmedWeapons().remove(weapon);
        }
        serializeSave();
        source.setEnabled(true);
    }
}
