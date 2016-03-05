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
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import text.editor.graphics.MainWindow;
import text.editor.io.ReadWriteUtilities;

/**
 * This is the main class for the text editor area of the application.
 * 
 * Created on:  February 28, 2016
 * Edited on:   March 04, 2016
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
     * CTRL+B       this will activate or deactivate the bold format.
     * CTRL+I       this will activate or deactivate the italic format.
     * CTRL+U       this will activate or deactivate the underline format.
     * CTRL+N       this will create a new document.
     * CTRL+S       this will save the current document.
     * CTRL+O       this will open a document.
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
                MainWindow.setFocusToDocument();
            }
        };        
        
        Action openAction = new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadWriteUtilities.readFile();
                MainWindow.setFocusToDocument();
            }
        };
        
        Action newFileAction = new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.getTabbedPane().createNewDocument();
                MainWindow.setFocusToDocument();
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
     * Will keep track of what fonts attributes are being used and how many
     * characters in the document.
     */
    class DocumentManager implements DocumentListener {
        
        
        // The amount of characters in the document.
        private int amountOfCharacters = 0;
        
        
        // Attributes for the text pane will be set to this.
        private MutableAttributeSet set;
        
        
        /** Default constructor for the DocumentManager. */
        public DocumentManager() {}

        @Override
        public void insertUpdate(DocumentEvent e) {
            amountOfCharacters += e.getLength();
            MainWindow.updateCharacterCount(amountOfCharacters);
            
            check();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            amountOfCharacters -= e.getLength();
            MainWindow.updateCharacterCount(amountOfCharacters);
            
            check();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.err.println("changeUpdate(DocumentEvent e) not implemented...");
        }
        
        
        /**
         * Checks if any field in the tool bar needs to be updated.
         */
        private void check() {
            
            set = MainWindow.getTextPane().getInputAttributes();
            
            String fontName = StyleConstants.getFontFamily(set);
            int fontSize    = StyleConstants.getFontSize(set);
            
            
            /*
                Check if the font or font size in the tool bar needs to be
                updated.
            */
            if(!fontName.equals(MainEditingToolBar.getCurrentSelectedFontName())) {
                MainEditingToolBar.setCurrentFontName(fontName);
            } else if(fontSize != MainEditingToolBar.getCurrentSelectedFontSize()) {
                MainEditingToolBar.setCurrentFontSize(fontSize);
            }
        }
    
    }

}
