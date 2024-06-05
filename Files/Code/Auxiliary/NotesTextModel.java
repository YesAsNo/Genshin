package Files.Code.Auxiliary;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This class represents the text model used in character notes. Capped length of the notes is 150 characters.
 */
public class NotesTextModel extends PlainDocument {
    private final int limit;

    public NotesTextModel(int limit, String initialText) {
        super();
        this.limit = limit;
        try {
            super.insertString(0, initialText, null);
        } catch (BadLocationException e) {
            System.out.println("Could not process Notes for the character.");
        }

    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }

}
