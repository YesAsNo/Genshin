package Files;

import static Files.ToolData.SAVE_LOCATION;
import static Files.ToolGUI.getFarmedMapping;
import static Files.ToolGUI.updateFarmedItemMap;

import com.google.gson.Gson;

import javax.swing.JButton;
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
        if (_characterCard.getWeapon().equalsIgnoreCase(ToolGUI.UNKNOWN_WEAPON_MESSAGE))
        {
            _characterCard.setWeapon("");
            _characterCard.setWeaponStatus(false);
        }
        else{
            updateFarmedItemMap(getFarmedMapping(ToolData.RESOURCE_TYPE.WEAPON),
                    _characterCard.getWeapon(),
                    _characterCard.getCharacterName(),
                    _characterCard.getWeaponStatus());
        }
        if (_characterCard.getArtifactSet1().equalsIgnoreCase(ToolGUI.UNKNOWN_SET_MESSAGE))
        {
            _characterCard.setArtifactSet1("");
            _characterCard.setArtifactSet1Status(false);
        }
        else{
            updateFarmedItemMap(getFarmedMapping(ToolData.RESOURCE_TYPE.ARTIFACT),
                    _characterCard.getArtifactSet1(),
                    _characterCard.getCharacterName(),
                    _characterCard.getArtifactSet1Status());
        }
        if (_characterCard.getArtifactSet2().equalsIgnoreCase(ToolGUI.UNKNOWN_SET_MESSAGE))
        {
            _characterCard.setArtifactSet2("");
            _characterCard.setArtifactSet2Status(false);
        }
        else{
            updateFarmedItemMap(getFarmedMapping(ToolData.RESOURCE_TYPE.ARTIFACT),
                    _characterCard.getArtifactSet2(),
                    _characterCard.getCharacterName(),
                    _characterCard.getArtifactSet2Status());
        }

        File f = new File(SAVE_LOCATION + _characterCard.getCharacterName());
        try{
            f.createNewFile();
            FileWriter fd = new FileWriter(f);
            gson.toJson(_characterCard,fd);
            fd.flush();
            fd.close();
            }
        catch (IOException ex)
            {
                System.out.println("Failed to save character details for character " +_characterCard.getCharacterName());
            }
        saveButton.setEnabled(true);
    }
}
