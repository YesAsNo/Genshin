package Files;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer<iconLabel> {

    @Override
    public Component getListCellRendererComponent(JList<? extends iconLabel> list, iconLabel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (value!= null){
            if (value.getIcon() != null){
                setIcon(value.getIcon());
            }
            setText(value.getText());
        }

        return this;
    }
}
