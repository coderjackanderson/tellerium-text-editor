package text.editor.graphics.editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledEditorKit;
import text.editor.graphics.MainWindow;
import text.editor.io.ReadWriteUtilities;

/**
 * This is the main class for the text editor area of the application.
 * 
 * Created on:  February 28, 2016
 * Edited on:   March 03, 2016
 *
 * @author Jackie Chan
 */
public class MainTextPane extends JTextPane {

    
    /** 
     * Default constructor for the MainTextPane.
     */
    public MainTextPane() {
        this.getDocument().addDocumentListener(new DocumentManager());
        this.setPreferredSize(new Dimension(700,500));
        registerKeyBindings();
    }
    
    
    /**
     * Registers key bindings for the CTRL+B, CTRL+I, and CTRL+U key combinations.
     * 
     * CTRL-B       this will activate or deactivate the bold format.
     * CTRL-I       this will activate or deactivate the italic format.
     * CTRL-U       this will activate or deactivate the underline format.
     */
    private void registerKeyBindings() {
        
        InputMap im     = this.getInputMap();
        ActionMap am    = this.getActionMap();
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK),
                                        "toggle_bold");
        am.put("toggle_bold", new StyledEditorKit.BoldAction());
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK), 
                                        "toggle_italic");
        am.put("toggle_italic", new StyledEditorKit.ItalicAction());
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK), 
                                        "toggle_underline");
        am.put("toggle_underline", new StyledEditorKit.UnderlineAction());
        
        Action saveAction = new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadWriteUtilities.writeRTFDocument();
            }
        };        
        
        Action openAction = new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadWriteUtilities.readFile();
            }
        };
        
        Action newFileAction = new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.getTabbedPane().createNewDocument();
            }
        };
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK),
                                        "save_file");
        am.put("save_file", saveAction);
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), 
                                        "open_file");
        am.put("open_file", openAction);
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), 
                                        "new_file");
        am.put("new_file", newFileAction);
        
    }
    
    
    /**
     * For right now, this class will just keep track of how many characters
     * have been entered and removed from the document.
     */
    class DocumentManager implements DocumentListener {
        
        
        // The amount of characters in the document.
        private int amountOfCharacters = 0;
        
        public DocumentManager() {}

        @Override
        public void insertUpdate(DocumentEvent e) {
            amountOfCharacters += e.getLength();
            MainWindow.updateCharacterCount(amountOfCharacters);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            amountOfCharacters -= e.getLength();
            MainWindow.updateCharacterCount(amountOfCharacters);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.err.println("changeUpdate(DocumentEvent e) not implemented...");
        }
    
    }

}
