package Files;

import static Files.ToolData.SAVE_LOCATION;
import static Files.ToolData.getTalentBookForCharacter;
import static Files.ToolGUI.getFarmedMapping;
import static Files.ToolGUI.updateFarmedItemMap;
import static Files.WeaponTabGUI.parseWeaponsMap;

import com.google.gson.Gson;

import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveButtonListener implements ActionListener {
    private final CharacterCard _characterCard;

    public SaveButtonListener(CharacterCard card){
        _characterCard = card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton saveButton = (JButton) e.getSource();
        saveButton.setEnabled(false);
        Gson gson = new Gson();
        if (_characterCard.getWeapon().equalsIgnoreCase(ToolGUI.EMPTY_WEAPON_SELECTOR))
        {
            _characterCard.setWeapon("");
            _characterCard.setWeaponStatus(false);
        }
        else{
            parseWeaponsMap();
        }
        if (_characterCard.getArtifactSet1().equalsIgnoreCase(ToolGUI.EMPTY_SET_SELECTOR))
        {
            _characterCard.setArtifactSet1("");
            _characterCard.setArtifactSet1Status(false);
        }
        else{
            updateFarmedItemMap(getFarmedMapping(ToolGUI.FARMED_DATATYPE.ARTIFACTS),
                    _characterCard.getArtifactSet1(),
                    _characterCard.getArtifactSet1Status());
        }
        if (_characterCard.getArtifactSet2().equalsIgnoreCase(ToolGUI.EMPTY_SET_SELECTOR))
        {
            _characterCard.setArtifactSet2("");
            _characterCard.setArtifactSet2Status(false);
        }
        else{
            updateFarmedItemMap(getFarmedMapping(ToolGUI.FARMED_DATATYPE.ARTIFACTS),
                    _characterCard.getArtifactSet2(),
                    _characterCard.getArtifactSet2Status());
        }
        if (_characterCard.getTalentStatus()){
            updateFarmedItemMap(getFarmedMapping(ToolGUI.FARMED_DATATYPE.TALENTS),
                    getTalentBookForCharacter(_characterCard.getCharacterName()),true);
        }

        File f = new File(SAVE_LOCATION + _characterCard.getCharacterName());
        JButton triggerButton = (JButton) e.getSource();
        try{
            f.createNewFile();
            FileWriter fd = new FileWriter(f);
            gson.toJson(_characterCard,fd);
            fd.flush();
            fd.close();
            Timer timer = new Timer(0, event->triggerButton.setText("SUCCESS"));
            timer.setRepeats(false);
            timer.start();
            }
        catch (IOException ex)
            {
                System.out.println("Failed to save character details for character " +_characterCard.getCharacterName());
                Timer timer = new Timer(0,event->triggerButton.setText("FAIL"));
                timer.setRepeats(false);
                timer.start();
            }
        Timer timer = new Timer(1000,event->triggerButton.setText("SAVE"));
        timer.setRepeats(false);
        timer.start();
        saveButton.setEnabled(true);
    }
}
