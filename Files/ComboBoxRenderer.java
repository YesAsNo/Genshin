package Files;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

/**
 * This class overwrites the default combobox renderer to support icons with text to be rendered inside comboboxes.
 */
public class ComboBoxRenderer extends JLabel implements ListCellRenderer<JLabel> {

    @Override
    public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (value!= null){
            if (value.getIcon() != null){
                setIcon(value.getIcon());
            }
            else{
                setIcon(null);
            }
            setText(value.getText());
        }

        return this;
    }
}
