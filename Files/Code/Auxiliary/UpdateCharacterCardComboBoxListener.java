package Files;

import static Files.ToolGUI.EMPTY_SET_SELECTOR;
import static Files.ToolGUI.updateFarmedItemMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class serves as the listener for comboboxes (weapon and artifact selectors) generated in CharacterCard.java
 */
public class UpdateCharacterCardComboBoxListener implements ItemListener {
    private final CharacterListing _characterListing;
    private final ToolData.CHARACTER_CARD_DATA_FIELD _changedData;
    private final JButton _saveButton;
    /**
     * Constructor of the class
     * @param characterListing the character card where the checkbox is located
     * @param changedData the changed data type in the card
     * @param saveButton implementation side effect. Will set the button to active once a change has been made.
     */
    public UpdateCharacterCardComboBoxListener(CharacterListing characterListing, ToolData.CHARACTER_CARD_DATA_FIELD changedData,
                                               JButton saveButton){
        _characterListing = characterListing;
        _changedData = changedData;
        _saveButton = saveButton;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        _saveButton.setEnabled(true);
        String item = ((JLabel)e.getItem()).getText();
        if (e.getStateChange() == ItemEvent.DESELECTED && !item.equalsIgnoreCase(EMPTY_SET_SELECTOR)){
            if (_changedData == ToolData.CHARACTER_CARD_DATA_FIELD.WEAPON || _changedData == ToolData.CHARACTER_CARD_DATA_FIELD.SET_ONE || _changedData  ==
                    ToolData.CHARACTER_CARD_DATA_FIELD.SET_TWO){
                updateFarmedItemMap(_changedData, _characterListing,false,item);
            }
        }
        if (e.getStateChange() == ItemEvent.SELECTED){
            switch(_changedData){
                case WEAPON: _characterListing.setWeapon(item);updateFarmedItemMap(_changedData, _characterListing,
                        _characterListing.getWeaponStatus(),item);return;
                case NOTES:
                    _characterListing.setCharacterNotes(item);return;
                case SET_ONE: _characterListing.setArtifactSet1(item);updateFarmedItemMap(_changedData,
                        _characterListing, _characterListing.getArtifactSet1Status(),item);return;
                case SET_TWO: _characterListing.setArtifactSet2(item);updateFarmedItemMap(_changedData,
                        _characterListing, _characterListing.getArtifactSet2Status(),item);return;
                default:
            }
        }
    }
}
