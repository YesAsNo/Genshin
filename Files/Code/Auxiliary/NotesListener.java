package Files.Code.Auxiliary;

import Files.Code.Data.CharacterListing;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * This class implements a document listener.
 * Only used in character notes.
 */
public class NotesListener implements DocumentListener {

    private final CharacterListing _characterListing;
    private final JButton _button;

    /**
     * Constructor of the listener. Requires a character card.
     *
     * @param card the character card
     * @param button save button
     */
    public NotesListener(CharacterListing card, JButton button) {
        _characterListing = card;
        _button = button;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        _button.setEnabled(true);
        try {
            _characterListing.setCharacterNotes(e.getDocument().getText(0, e.getDocument().getLength()));
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
