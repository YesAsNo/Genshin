package Files;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * This class implements a document listener.
 * Only used in character notes.
 */
public class NotesListener implements DocumentListener {

    private final CharacterCard _characterCard;
    private final JButton _button;

    /**
     * Constructor of the listener. Requires a character card.
     * @param card the character card
     * @param button save button
     */
    public NotesListener(CharacterCard card, JButton button){
        _characterCard = card;
        _button = button;
    }
    @Override
    public void insertUpdate(DocumentEvent e) {
        _button.setEnabled(true);
        try {
            _characterCard.setCharacterNotes(e.getDocument().getText(0,e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        insertUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("THERE WAS A CHANGE!!!!!!! ALARM!!!!!!!!!");
    }
}
