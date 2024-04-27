package Files.Code.Auxiliary;

import static Files.Code.Data.ToolData.SAVE_LOCATION;

import Files.Code.GUIs.CharacterListing;
import com.google.gson.Gson;

import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class implements the save button listener, found in CharacterCardGUI.
 * @link CharacterCardGUI
 */
public class SaveButtonListener implements ActionListener {
    private final CharacterListing _characterListing;

    /**
     * Constructor of the class.
     * @param card character card
     */
    public SaveButtonListener(CharacterListing card){
        _characterListing = card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton saveButton = (JButton) e.getSource();
        saveButton.setEnabled(false);
        Gson gson = new Gson();
        File f = new File(SAVE_LOCATION + _characterListing.getCharacterName());
        JButton triggerButton = (JButton) e.getSource();
        try{
            f.createNewFile();
            FileWriter fd = new FileWriter(f);
            gson.toJson(_characterListing,fd);
            fd.flush();
            fd.close();
            Timer timer = new Timer(0, event->triggerButton.setText("SUCCESS"));
            timer.setRepeats(false);
            timer.start();
            }
        catch (IOException ex)
            {
                System.out.println("Failed to save character details for character " + _characterListing.getCharacterName());
                Timer timer = new Timer(0,event->triggerButton.setText("FAIL"));
                timer.setRepeats(false);
                timer.start();
            }
        Timer timer = new Timer(1000,event->triggerButton.setText("SAVE"));
        timer.setRepeats(false);
        timer.start();
    }
}
