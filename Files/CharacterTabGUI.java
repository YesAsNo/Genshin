package Files;

import static Files.CharacterCardGUI.$$$getFont$$$;
import static Files.ToolData.generateResourceIconPath;
import static Files.ToolData.getFlattenedData;
import static Files.ToolGUI.NO_CHARACTERS_MATCH_MESSAGE;
import static Files.ToolGUI.UNKNOWN_CHARACTER;
import static Files.ToolGUI.checkIfCharacterCardHasBeenGenerated;
import static Files.ToolGUI.formatString;
import static Files.ToolGUI.getCharacterCard;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class CharacterTabGUI implements ActionListener {

    private final JPanel mainPanel = new JPanel(new GridBagLayout());
    private final JPanel searchResultPanel = new JPanel(new GridBagLayout());
    private final JButton searchConfirmButton = new JButton();
    private final JTextField searchField = new JTextField();
    private final JScrollPane searchScrollPane = new JScrollPane();

    public CharacterTabGUI(){
        mainPanel.setBackground(new Color(-1));
        mainPanel.setEnabled(true);
        mainPanel.setFocusCycleRoot(false);
        Font characterTabFont = $$$getFont$$$(-1, -1, mainPanel.getFont());
        if (characterTabFont != null) {
            mainPanel.setFont(characterTabFont);
        }
        mainPanel.setOpaque(true);
        mainPanel.setRequestFocusEnabled(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(searchResultPanel, gbc);
        searchConfirmButton.setMaximumSize(new Dimension(30, 30));
        searchConfirmButton.setMinimumSize(new Dimension(30, 30));
        searchConfirmButton.setPreferredSize(new Dimension(50, 30));
        searchConfirmButton.setText("✓");
        searchConfirmButton.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(searchConfirmButton, gbc);

        searchField.addMouseListener(new SearchBarListener());
        searchField.setEnabled(true);
        Font characterSelectorFieldFont =
                $$$getFont$$$(Font.BOLD, 18, searchField.getFont());
        if (characterSelectorFieldFont != null) {
            searchField.setFont(characterSelectorFieldFont);
        }
        searchField.setInheritsPopupMenu(false);
        searchField.setMaximumSize(new Dimension(240, 33));
        searchField.setMinimumSize(new Dimension(240, 33));
        searchField.setPreferredSize(new Dimension(240, 33));
        searchField.setText("Choose your fighter!");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 250, 0, 0);
        mainPanel.add(searchField, gbc);
    }
    public JPanel getMainPanel(){return mainPanel;}
    public JButton getSearchConfirmButton(){
        return searchConfirmButton;
    }
    /**
     * Generates a character button for the character specified by name and the index of the match.
     *
     * @param characterName the name of the character
     * @param index which character by count it is
     */
    private void generateCharacterButton(String characterName, int index) {
        if (characterName.equalsIgnoreCase(UNKNOWN_CHARACTER)) {
            JLabel unknownCharacterLabel = new JLabel(generateResourceIconPath(UNKNOWN_CHARACTER, ToolData.RESOURCE_TYPE.CHARACTER));
            unknownCharacterLabel.setText(NO_CHARACTERS_MATCH_MESSAGE);
            unknownCharacterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            unknownCharacterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            searchResultPanel.add(unknownCharacterLabel);
            searchResultPanel.updateUI();
            return;
        }
        JButton characterButton = getjButton(characterName);
        addCharacterButtonToSelectedCharacterPanel(characterButton, index);

    }
    /**
     * Creates a JButton for the specified character.
     *
     * @param characterName the name of the character
     * @return JButton for the character.
     */
    private JButton getjButton(String characterName) {
        JButton characterButton = new JButton();
        CharacterCard characterCard;
        if (!checkIfCharacterCardHasBeenGenerated(characterName)) {
            characterCard = new CharacterCard(characterName);
            ToolGUI.addCharacterCard(characterCard);
        } else {
            characterCard = getCharacterCard(characterName);
        }
        assert characterCard != null;
        characterButton.setIcon(characterCard.getCharacterIcon());
        characterButton.setText(formatString(characterName));
        characterButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        characterButton.setHorizontalTextPosition(SwingConstants.CENTER);
        characterButton.setOpaque(false);
        characterButton.setBorderPainted(false);
        characterButton.setContentAreaFilled(false);
        characterButton.addActionListener(e -> {
            if (isWindowAlreadyOpen(characterName)) {
                getOpenWindow(characterName).setVisible(true);
            } else {
                new CharacterCardGUI(characterCard);
            }
        });

        return characterButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        String userFieldInput;
        int matchedCount = 0;
        userFieldInput = searchField.getText().toLowerCase();
        searchResultPanel.removeAll();
        searchScrollPane.updateUI();
        for (String s : getFlattenedData(ToolData.flattenedDataCategory.CHARACTER)) {
            if (s.toLowerCase().contains(userFieldInput)) {
                {
                    generateCharacterButton(s, matchedCount);
                }
                matchedCount++;

            }
        }
        if (matchedCount == 0) {
            generateCharacterButton(UNKNOWN_CHARACTER, matchedCount);
        } else {
            searchScrollPane.setViewportView(searchResultPanel);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;

            searchResultPanel.add(searchScrollPane, gbc);

        }
    }
    /**
     * Verifies if a window for the specified character has already been opened.
     *
     * @param charName the specified character
     * @return true if the window is open, otherwise false.
     */

    public boolean isWindowAlreadyOpen(String charName) {
        Frame[] createdWindows = Frame.getFrames();
        for (Frame window : createdWindows) {
            if (window != null && window.getTitle().contains(charName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Gets the open window for the specified character.
     *
     * @param charName the name of the character
     * @return window with the loaded character card for the specified character
     */
    public Window getOpenWindow(String charName) {
        assert isWindowAlreadyOpen(charName);
        Frame[] createdWindows = Frame.getFrames();
        for (Frame frame : createdWindows) {
            if (frame != null && frame.getTitle().contains(charName)) {
                return frame;
            }
        }
        return null;
    }

    /**
     * Adds a character button to the selected character panel (after triggering actionPerformed)
     *
     * @param charButton the button to add
     * @param index the index of the match
     */
    private void addCharacterButtonToSelectedCharacterPanel(JButton charButton, int index) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = index % 6;
        gbc.gridy = (index - gbc.gridx) / 6;
        searchResultPanel.add(charButton, gbc);
        searchResultPanel.updateUI();

    }

}
