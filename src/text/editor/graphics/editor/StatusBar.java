package text.editor.graphics.editor;

import javax.swing.JLabel;
import javax.swing.JToolBar;

/**
 * The status bar for the main editing window.
 *
 * Created on:  March 03, 2016
 * Edited on:   March 03, 2016
 *
 * @author Jackie Chan
 */
public class StatusBar extends JToolBar {
    
    
    private static JLabel       characterCount;
    
    
    /** Default constructor for the StatusBar. */
    public StatusBar() {createContent();}
    
    
    private void createContent() {
        characterCount = new JLabel("Character Count: 0");
        
        this.add(characterCount);
    }
    
    
    public void updateCharacterCount(int value) {
        characterCount.setText("Character Count: "+value);
    }
    

}
