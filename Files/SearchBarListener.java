package Files;

import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchBarListener extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e){
        JTextField src = (JTextField) e.getSource();
        src.setText("");
    }
}
