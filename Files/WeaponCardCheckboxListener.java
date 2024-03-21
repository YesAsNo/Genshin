package Files;

import static Files.ToolGUI.UNKNOWN_CHARACTER;
import static Files.UpdateCharacterCardListener.convertStateChangeToBool;

import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaponCardCheckboxListener implements ItemListener {

    private final String  _weaponName;
    private final Map<String,List<String>> _farmedWeapons;

    public WeaponCardCheckboxListener(String weaponName, Map<String,List<String>> farmedWeapons){
        _weaponName = weaponName;
        _farmedWeapons = farmedWeapons;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        ((JCheckBox)e.getSource()).setEnabled(false);
        if(!_farmedWeapons.containsKey(_weaponName)){
            _farmedWeapons.put(_weaponName, new ArrayList<>());
        }
        if (convertStateChangeToBool(e.getStateChange())){
            _farmedWeapons.get(_weaponName).add(UNKNOWN_CHARACTER);
        }
        else{
            _farmedWeapons.get(_weaponName).remove(UNKNOWN_CHARACTER);
        }
    }
}
