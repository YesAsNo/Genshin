package Files;

import static Files.ToolGUI.isSomeoneFarmingForTheWeapon;
import static Files.ToolGUI.isThisAWeapon;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;

/**
 * This class overwrites the default combobox renderer to support icons with text to be rendered inside comboboxes.
 */
public class ComboBoxRenderer extends JLabel implements ListCellRenderer<JLabel> {
    private final Color _selectedColor = new Color(0x848484);
    private JPanel panel;
    private JLabel jlabel;
    public ComboBoxRenderer(JComboBox<JLabel> comboBox) {
        panel = new JPanel();
        panel.add(this);
        jlabel = new JLabel();
        jlabel.setOpaque(false);
        jlabel.setFont(comboBox.getFont());
        panel.add(jlabel);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        if (value!= null){
            if (isSelected){
                jlabel.setBackground(new Color(-5275240));
                jlabel.setOpaque(true);
            }
            else{
                jlabel.setOpaque(false);
            }
            if (value.getIcon() != null){
                jlabel.setIcon(value.getIcon());
            }
            else{
                jlabel.setIcon(null);
            }
            jlabel.setText(value.getText());
            if (isThisAWeapon(value.getText()) && isSomeoneFarmingForTheWeapon(value.getText())){
                jlabel.setForeground(_selectedColor);
            }
            else{
                jlabel.setForeground(Color.BLACK);
            }
        }

        return jlabel;
    }
}
