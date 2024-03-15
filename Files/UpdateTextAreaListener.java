package Files;

import static Files.ToolGUI.UNKNOWN_SET_MESSAGE;
import static Files.ToolGUI.lookUpSetDescription;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateTextAreaListener implements ActionListener {
    private final JTextArea _textArea;
    public UpdateTextAreaListener(JTextArea textArea){
        _textArea = textArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String item = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();
        assert item != null;
        if (!item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE)) {
            _textArea.setText(lookUpSetDescription(item));
        }
        else{
            _textArea.setText("");
        }

    }
}
