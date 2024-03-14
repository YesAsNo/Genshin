package Files;

import static Files.ToolData.SAVE_LOCATION;

import com.google.gson.Gson;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveButtonListener implements ActionListener {
    private final CharacterCard _characterCard;

    public SaveButtonListener(CharacterCard card){
        _characterCard = card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton saveButton = (JButton) e.getSource();
        saveButton.setEnabled(false);
        Gson gson = new Gson();

        File f_dir = new File(SAVE_LOCATION);
        File f = new File(SAVE_LOCATION + _characterCard.getCharacterName());
        f_dir.mkdir();
        try{
            f.createNewFile();
            FileWriter fd = new FileWriter(f);
            gson.toJson(_characterCard,fd);
            fd.flush();
            fd.close();
            }
        catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        saveButton.setEnabled(true);
    }
}
