package Files;

import static Files.WeaponTabGUI.getUnassignedFarmedWeapons;
import static Files.WeaponTabGUI.saveAllWeapons;

import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class WeaponTabGUIListener implements ItemListener {
    private final String weaponName;
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
        saveAllWeapons();
        source.setEnabled(true);
    }
}
