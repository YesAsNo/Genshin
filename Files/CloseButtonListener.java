package Files;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseButtonListener implements ActionListener {
    private final CharacterCardGUI _cardGUI;
    public CloseButtonListener(CharacterCardGUI cardGUI){
        _cardGUI = cardGUI;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        _cardGUI.setVisible(false);

    }
}
