package Files;

import static Files.ToolGUI.updateFarmedItemMap;

import javax.swing.JButton;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UpdateCharacterCardComboBoxListener implements ItemListener {
    private final CharacterCard _characterCard;
    private final ToolData.CHARACTER_CARD_DATA_FIELD _changedData;
    private final JButton _saveButton;
    public UpdateCharacterCardComboBoxListener(CharacterCard characterCard, ToolData.CHARACTER_CARD_DATA_FIELD changedData,
                                               JButton saveButton){
        _characterCard = characterCard;
        _changedData = changedData;
        _saveButton = saveButton;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        _saveButton.setEnabled(true);
        if (e.getStateChange() == ItemEvent.DESELECTED){
            String item = (String) e.getItem();
            if (_changedData == ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON || _changedData == ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE || _changedData  ==
                    ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO){
                updateFarmedItemMap(_changedData,_characterCard,false,item);
            }
        }
        if (e.getStateChange() == ItemEvent.SELECTED){
            String item = (String) e.getItem();
            switch(_changedData){
                case WEAPON: _characterCard.setWeapon(item);updateFarmedItemMap(_changedData,_characterCard,_characterCard.getWeaponStatus(),item);return;
                case NOTES:_characterCard.setCharacterNotes(item);return;
                case SET_ONE: _characterCard.setArtifactSet1(item);updateFarmedItemMap(_changedData,_characterCard,_characterCard.getArtifactSet1Status(),item);return;
                case SET_TWO: _characterCard.setArtifactSet2(item);updateFarmedItemMap(_changedData,_characterCard,_characterCard.getArtifactSet2Status(),item);return;
                default:
            }
        }
    }
}
