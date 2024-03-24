package Files;

import static Files.WeaponTabGUI.NOT_FARMING;
import static Files.WeaponTabGUI.STARTED_FARMING;
import static Files.WeaponTabGUI.getFarmingMap;

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
        if (source.isSelected()){
            getFarmingMap().put(weaponName,STARTED_FARMING);
        }
        else{
            getFarmingMap().put(weaponName,NOT_FARMING);
        }
    }
}
