package Files;

import static Files.ToolGUI.UNKNOWN_SET_MESSAGE;
import static Files.ToolGUI.UNKNOWN_WEAPON_MESSAGE;
import static Files.ToolGUI.updateFarmedArtifacts;
import static javax.swing.DefaultButtonModel.SELECTED;

import Files.ToolData.CHARACTER_CARD_DATA_FIELD;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UpdateCharacterCardListener implements ActionListener, ItemListener {
    private final CharacterCard _characterCard;
    private final CHARACTER_CARD_DATA_FIELD _changedData;
    public UpdateCharacterCardListener(CharacterCard characterCard, CHARACTER_CARD_DATA_FIELD changedData){
        _characterCard = characterCard;
        _changedData = changedData;
    }
    public void actionPerformed(ActionEvent e) {
        String item = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();
        assert item != null;
        switch(_changedData){
            case WEAPON:
                if (item.equalsIgnoreCase(UNKNOWN_WEAPON_MESSAGE)) {
                    _characterCard.setWeapon("");
                } else {
                    _characterCard.setWeapon(item);
                }
                return;
            case NOTES:_characterCard.setCharacterNotes(item);return;
            case SET_ONE:
                if (item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE)) {
                _characterCard.setArtifactSet1("");
                } else {
                _characterCard.setArtifactSet1(item);
                }
                return;
            case SET_TWO:
                if (item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE)) {
                _characterCard.setArtifactSet2("");
                } else {
                _characterCard.setArtifactSet2(item);
                }
                return;
            default:
        }

    }
    private boolean convertStateChangeToBool(int state){
        return !(state == SELECTED);
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        //TODO: Add methods to update FarmedTalents and WeaponMats
        boolean currentStatus;
        String farmedItemName = "";
        switch(_changedData){
            case FARMING_SET_ONE:
                currentStatus = convertStateChangeToBool(e.getStateChange());
                _characterCard.setArtifactSet1Status(currentStatus);
                farmedItemName= _characterCard.getArtifactSet1();
                if (!farmedItemName.isEmpty())
                {
                    updateFarmedArtifacts(farmedItemName,_characterCard.getCharacterName(),currentStatus);
                }
                return;
            case FARMING_SET_TWO:
                currentStatus = convertStateChangeToBool(e.getStateChange());
                _characterCard.setArtifactSet2Status(currentStatus);
                farmedItemName = _characterCard.getArtifactSet2();
                if (!farmedItemName.isEmpty())
                {
                    updateFarmedArtifacts(farmedItemName,_characterCard.getCharacterName(),currentStatus);
                }
                return;
            case FARMING_TALENT_MATERIALS:
                currentStatus = convertStateChangeToBool(e.getStateChange());
                _characterCard.setTalentStatus(currentStatus);
                return;
            case FARMING_WEAPON_MATERIALS:
                currentStatus = convertStateChangeToBool(e.getStateChange());
                _characterCard.setWeaponStatus(currentStatus);
                return;
            default:
        }

    }
}
