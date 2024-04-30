package Files.Code.Auxiliary;

import static Files.Code.Data.ToolData.getArtifact;
import static Files.Code.GUIs.ToolGUI.EMPTY_SET_SELECTOR;

import Files.Code.Data.Artifact;

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
     *
     * @param textArea the text area to update the contents of
     */
    public UpdateTextAreaListener(JTextArea textArea) {
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
            Artifact artifact = getArtifact(item);
            _textArea.setText(artifact.description_2piece + "\n\n" + artifact.description_4piece);
        } else {
            _textArea.setText("");
        }

    }
}
