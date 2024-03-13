package Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ActionListeners implements ActionListener {

    private final Object _trigger;
    private final Object _object;
    private final JComboBox<String> dummyComboBox = new JComboBox<>();
    private final JButton dummyButton = new JButton();

    public ActionListeners(Object trigger,Object objectToChange){
        _trigger = trigger;
        _object = objectToChange;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (_trigger.getClass() == dummyComboBox.getClass() && _object instanceof JLabel){
            String item = (String) ((JComboBox<?>) _trigger).getSelectedItem();
            if (Objects.equals(item, ToolGUI.UNKNOWN_SET_MESSAGE)){
                ImageIcon icon = new ImageIcon(
                        Objects.requireNonNull(getClass().getResource(ToolGUI.generateArtifactIconPath(ToolGUI.UNKNOWN_ARTIFACT))));
                ((JLabel) _object).setIcon(icon);
                return;
            }
            if (Objects.equals(((JLabel) _object).getToolTipText(), ToolGUI.TOOLTIP_FOR_LABELS_WITHOUT_ICON))
            {
                ((JLabel) _object).setText(item);
                return;
            }
            if (Objects.equals(((JLabel) _object).getToolTipText(), ToolGUI.TOOLTIP_FOR_LABELS_WITH_ICON)){
                ImageIcon icon = new ImageIcon(
                        Objects.requireNonNull(getClass().getResource(ToolGUI.generateArtifactIconPath(item))));
                ((JLabel) _object).setIcon(icon);

            }

        }
        if (_trigger.getClass() == dummyButton.getClass() && _object instanceof JTabbedPane){
            if (Objects.equals(((JButton) _trigger).getText(), ToolGUI.CLOSE_TEXT)){
                ((JTabbedPane) _object).removeTabAt(((JTabbedPane) _object).getSelectedIndex());
                ((JTabbedPane) _object).setSelectedIndex(0);
            }
        }
    }
}
