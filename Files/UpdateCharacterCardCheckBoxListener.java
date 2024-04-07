package Files;

import static Files.ToolData.getTalentBookForCharacter;
import static Files.ToolData.getWeeklyBossMatForCharacter;
import static Files.ToolGUI.updateFarmedItemMap;

import Files.ToolData.CHARACTER_CARD_DATA_FIELD;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class serves as the listener for checkboxes generated in CharacterCard.java
 */
public class UpdateCharacterCardCheckBoxListener implements ItemListener {
    private final CharacterCard _characterCard;
    private final CHARACTER_CARD_DATA_FIELD _changedData;
    private final JButton _saveButton;

    /**
     * Constructor of the class
     * @param characterCard the character card where the checkbox is located
     * @param changedData the changed data type in the card
     * @param saveButton implementation side effect. Will set the button to active once a change has been made.
     */
    public UpdateCharacterCardCheckBoxListener(CharacterCard characterCard, CHARACTER_CARD_DATA_FIELD changedData,
                                       JButton saveButton){
        _characterCard = characterCard;
        _changedData = changedData;
        _saveButton = saveButton;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        boolean currentStatus = ((JCheckBox)e.getSource()).isSelected();
        _saveButton.setEnabled(true);
        switch(_changedData){
            case FARMING_SET_ONE:
                _characterCard.setArtifactSet1Status(currentStatus);
                updateFarmedItemMap(CHARACTER_CARD_DATA_FIELD.SET_ONE,_characterCard,currentStatus,_characterCard.getArtifactSet1());
                return;
            case FARMING_SET_TWO:
                _characterCard.setArtifactSet2Status(currentStatus);
                updateFarmedItemMap(CHARACTER_CARD_DATA_FIELD.SET_TWO,_characterCard,currentStatus,_characterCard.getArtifactSet2());
                return;
            case FARMING_TALENT_MATERIALS:
                _characterCard.setTalentStatus(currentStatus);
                updateFarmedItemMap(_changedData,_characterCard,currentStatus,getTalentBookForCharacter(_characterCard.getCharacterName()));
                updateFarmedItemMap(_changedData,_characterCard,currentStatus,getWeeklyBossMatForCharacter(_characterCard.getCharacterName()));
                return;
            case FARMING_WEAPON_MATERIALS:
                _characterCard.setWeaponStatus(currentStatus);
                updateFarmedItemMap(_changedData,_characterCard,currentStatus,_characterCard.getWeapon());
                return;
            default:
        }
    }
}
