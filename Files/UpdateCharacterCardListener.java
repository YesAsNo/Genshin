package Files;

import static Files.ToolGUI.UNKNOWN_SET_MESSAGE;
import static Files.ToolGUI.UNKNOWN_WEAPON_MESSAGE;
import static javax.swing.DefaultButtonModel.SELECTED;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import Files.ToolData.CHARACTER_CARD_DATA_FIELD;

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
        switch(_changedData){
            case FARMING_SET_ONE: _characterCard.setArtifactSet1Status(convertStateChangeToBool(e.getStateChange()));return;
            case FARMING_SET_TWO: _characterCard.setArtifactSet2Status(convertStateChangeToBool(e.getStateChange()));return;
            case FARMING_TALENT_MATERIALS: _characterCard.setTalentStatus(convertStateChangeToBool(e.getStateChange()));return;
            case FARMING_WEAPON_MATERIALS: _characterCard.setWeaponStatus(convertStateChangeToBool(e.getStateChange()));return;
            default:
        }

    }
}
