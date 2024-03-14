package Files;

import javax.swing.JTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseButtonListener implements ActionListener {
    private final JTabbedPane _tabbedPane;
    public CloseButtonListener(JTabbedPane tabbedPane){
        _tabbedPane = tabbedPane;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        _tabbedPane.removeTabAt(_tabbedPane.getSelectedIndex());
        _tabbedPane.setSelectedIndex(0);

    }
}
