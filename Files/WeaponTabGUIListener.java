package Files;

import static Files.WeaponTabGUI.getFarmingMap;
import static Files.WeaponTabGUI.saveAllWeapons;
import static Files.WeaponTabGUI.setAllCheckboxesEnabled;

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
        setAllCheckboxesEnabled(false);
        if (source.isSelected()){
            getFarmingMap().put(weaponName, WeaponTabGUI.LISTED_STATUS.LISTED_GENERALLY);
        }
        else{
            getFarmingMap().put(weaponName, WeaponTabGUI.LISTED_STATUS.UNLISTED);
        }
        saveAllWeapons();
        setAllCheckboxesEnabled(true);
    }
}
