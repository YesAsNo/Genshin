package Files;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class NotesListener implements DocumentListener {

    private final CharacterCard _characterCard;
    public NotesListener(CharacterCard card){
        _characterCard = card;
    }
    @Override
    public void insertUpdate(DocumentEvent e) {
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
        System.out.println("THERE WAS A CHANGE!!!!!!!ALARM!!!!!!!!!");
    }
}
