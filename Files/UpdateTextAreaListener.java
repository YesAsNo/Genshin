package Files;

import static Files.ToolData.lookUpSetDescription;
import static Files.ToolGUI.EMPTY_SET_SELECTOR;

import javax.swing.JComboBox;
import javax.swing.JLabel;
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
        assert e.getSource() instanceof JComboBox<?>;
        JComboBox<?> src = (JComboBox<?>) e.getSource();
        assert src.getSelectedItem() instanceof JLabel;
        String item = ((JLabel) src.getSelectedItem()).getText();
        assert item != null;
        if (!item.equalsIgnoreCase(EMPTY_SET_SELECTOR)) {
            _textArea.setText(lookUpSetDescription(item));
        }
        else{
            _textArea.setText("");
        }

    }
}
