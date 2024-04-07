package Files;

import static Files.ToolData.getArtifactSetDescription;
import static Files.ToolGUI.EMPTY_SET_SELECTOR;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class updates the set description panel on the right in CharacterCard.java
 */
public class UpdateTextAreaListener implements ActionListener {
    private final JTextArea _textArea;

    /**
     * Constructor of the class.
     * @param textArea the text area to update the contents of
     */
    public UpdateTextAreaListener(JTextArea textArea){
        _textArea = textArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        assert e.getSource() instanceof JComboBox<?>;
        JComboBox<?> src = (JComboBox<?>) e.getSource();
        assert src.getSelectedItem() instanceof JLabel;
        String item = ((JLabel) src.getSelectedItem()).getText();
        assert item != null;
        if (!item.equalsIgnoreCase(EMPTY_SET_SELECTOR)) {
            _textArea.setText(getArtifactSetDescription(item));
        }
        else{
            _textArea.setText("");
        }

    }
}
