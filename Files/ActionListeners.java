package Files;

import static Files.ToolGUI.UNKNOWN_SET_MESSAGE;
import static Files.ToolGUI.lookUpSetDescription;
import static Files.ToolGUI.lookUpWeaponRarity;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
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
        if (_trigger.getClass() == dummyComboBox.getClass() && _object instanceof JLabel label){
            String item = (String) ((JComboBox<?>) _trigger).getSelectedItem();
            assert item != null;
            if (item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE)){

                if (label.getToolTipText().equalsIgnoreCase(ToolGUI.TOOLTIP_FOR_LABELS_WITHOUT_ICON)){
                    label.setText("");
                    return;
                }
                if (label.getToolTipText().equalsIgnoreCase(ToolGUI.TOOLTIP_FOR_LABELS_WITH_ICON)){
                    ImageIcon icon = new ImageIcon(
                            Objects.requireNonNull(getClass().getResource(ToolGUI.generateArtifactIconPath(ToolGUI.UNKNOWN_ARTIFACT))));
                    label.setIcon(icon);
                    return;
                }

            }
            if (label.getToolTipText().equalsIgnoreCase(ToolGUI.TOOLTIP_FOR_LABELS_WITHOUT_ICON))
            {
                label.setText(item);
                return;
            }
            if (label.getToolTipText().equalsIgnoreCase(ToolGUI.TOOLTIP_FOR_WEAPON_NAME_LABEL)){
                label.setText(item);
                return;
            }
            if (label.getToolTipText().equalsIgnoreCase(ToolGUI.TOOLTIP_FOR_WEAPON_ICON_LABEL)){
                WeaponInfo weaponInfo = lookUpWeaponRarity(item);
                ImageIcon icon = new ImageIcon(
                        Objects.requireNonNull(getClass().getResource(ToolGUI.generateWeaponPath(item,weaponInfo.getWeaponType(),weaponInfo.getRarity()))));
                label.setIcon(icon);
                return;
            }
            if (label.getToolTipText().equalsIgnoreCase(ToolGUI.TOOLTIP_FOR_LABELS_WITH_ICON)){
                ImageIcon icon = new ImageIcon(
                        Objects.requireNonNull(getClass().getResource(ToolGUI.generateArtifactIconPath(item))));
                label.setIcon(icon);

            }

        }
        if (_trigger.getClass() == dummyButton.getClass() && _object instanceof JTabbedPane){
            if (Objects.equals(((JButton) _trigger).getText(), ToolGUI.CLOSE_TEXT)){
                ((JTabbedPane) _object).removeTabAt(((JTabbedPane) _object).getSelectedIndex());
                ((JTabbedPane) _object).setSelectedIndex(0);
            }
        }
        if (_trigger.getClass() == dummyComboBox.getClass() && _object instanceof JTextArea){
            String item = (String) ((JComboBox<?>) _trigger).getSelectedItem();
            assert item != null;
            if (!item.equalsIgnoreCase(UNKNOWN_SET_MESSAGE)) {
                ((JTextArea) _object).setText(lookUpSetDescription(item));
            }
            else{
                ((JTextArea) _object).setText("");
            }

        }
    }
}
