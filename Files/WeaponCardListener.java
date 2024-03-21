package Files;

import static Files.ToolData.SAVE_LOCATION;
import static Files.ToolGUI.UNKNOWN_CHARACTER;
import static Files.ToolGUI.WEAPON_SAVE_FILE_NAME;
import static Files.UpdateCharacterCardListener.convertStateChangeToBool;

import com.google.gson.Gson;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaponCardListener implements ItemListener, ActionListener {

    private final String  _weaponName;
    private final Map<String,List<String>> _farmedWeapons;

    public WeaponCardListener(String weaponName, Map<String,List<String>> farmedWeapons){
        _weaponName = weaponName;
        _farmedWeapons = farmedWeapons;
    }
    public WeaponCardListener(Map<String,List<String>> farmedWeapons){
        _weaponName = "";
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
        ((JCheckBox)e.getSource()).setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton saveButton = (JButton) e.getSource();
        saveButton.setEnabled(false);
        Gson gson = new Gson();
        File f = new File(SAVE_LOCATION + WEAPON_SAVE_FILE_NAME);
        try{
            f.createNewFile();
            FileWriter fd = new FileWriter(f);
            gson.toJson(_farmedWeapons,fd);
            fd.flush();
            fd.close();

        }
        catch(IOException ex){
            System.out.println("Failed to save weapons");
        }
        saveButton.setEnabled(true);

    }
}
