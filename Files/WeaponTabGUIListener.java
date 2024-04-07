package Files;

import static Files.ToolGUI.serializeSave;
import static Files.WeaponTabGUI.getUnassignedFarmedWeapons;

import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class is the listener for all checkboxes in WeaponTabGUI.java
 */
public class WeaponTabGUIListener implements ItemListener {
    private final String weaponName;

    /**
     * Constructor of the class
     * @param name the name of the weapon
     */
    public WeaponTabGUIListener(String name){
        weaponName = name;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        source.setEnabled(false);
        if (source.isSelected()){
            getUnassignedFarmedWeapons().add(weaponName);
        }
        else{
            getUnassignedFarmedWeapons().remove(weaponName);
        }
        serializeSave();
        source.setEnabled(true);
    }
}
