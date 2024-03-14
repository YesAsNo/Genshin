package Files;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonListener implements ActionListener {
    private final JTabbedPane _tabbedPane;
    public SaveButtonListener(JTabbedPane tabbedPane){
        _tabbedPane = tabbedPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton saveButton = (JButton) e.getSource();



    }
}
