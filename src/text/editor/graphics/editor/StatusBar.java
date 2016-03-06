package text.editor.graphics.editor;

import javax.swing.JLabel;
import javax.swing.JToolBar;

/**
 * The status bar for the main editing window.
 *
 * Created on:  March 03, 2016
 * Edited on:   March 05, 2016
 *
 * @author Jackie Chan
 */
public class StatusBar extends JToolBar {


    /** The JLabel that will contain the character count. */
    private static JLabel       characterCount;


    /** The JLabel that will display messages to the user. */
    private static JLabel       messageArea;


    /** Default constructor for the StatusBar. */
    public StatusBar() {createContent();}


    /**
     * Creates the components of the status bar and adds them.
     */
    private void createContent() {
        characterCount  = new JLabel("Character Count: 0");
        messageArea     = new JLabel("Status: Active");

        this.add(characterCount);
        this.addSeparator();
        this.add(messageArea);
    }


    /**
     * Sets the character count to the one specified.
     *
     * @param value     The value to set.
     */
    public static void updateCharacterCount(int value) {
        characterCount.setText("Character Count: "+value);
    }


    /**
     * Sets the status message.
     *
     * @param status    The status message to set.
     */
    public static void updateStatusMessage(String status) {
        messageArea.setText("Status: "+status);
    }

}
