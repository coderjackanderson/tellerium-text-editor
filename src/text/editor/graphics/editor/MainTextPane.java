package text.editor.graphics.editor;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
import text.editor.graphics.actions.Actions;


/**
 * This is the main class for the main GUI of the application.
 *
 * Created on:  February 28, 2016
 * Edited on:   March 05, 2016
 *
 * @author Jackie Chan
 */
public class MainTextPane extends JTextPane {


    /** The full path to this documents file. */
    private String filePath;


    /**
     * Default constructor for the MainTextPane. Will add a DocumentListener,
     * set the preferred size of the document, and register key bindings
     * (keyboard shortcuts).
     */
    public MainTextPane() {

        this.getDocument().addDocumentListener(new DocumentManager());
        this.setPreferredSize(new Dimension(700,500));

        registerKeyBindings();
    }


    /**
     * Sets the full path to this documents file.
     *
     * @param filePath      The full path to this document's file.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    /**
     * Returns the full path to this document's file.
     *
     * @return              The full path to this document's file.
     */
    public String getFilePath() {
        return this.filePath;
    }


    /**
     * Adds key bindings (keyboard shortcuts) to the MainTextPane.
     *
     * CTRL+B   Applies bold styling.
     * CTRL+I   Applies italic styling.
     * CTRL+U   Applies underline styling.
     *
     * CTRL+N   Creates a new document.
     * CTRL+S   Saves the current document.
     * CTRL+O   Opens a document.
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

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK),
                                        "save_file");
        am.put("save_file", new Actions.SaveFileAction());

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK),
                                        "open_file");
        am.put("open_file", new Actions.OpenFileAction());

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK),
                                        "new_file");
        am.put("new_file", new Actions.NewFileAction());

    }


    /**
     * The DocumentManager class will keep track of the font, font size, and
     * amount of characters in a document.
     */
    class DocumentManager implements DocumentListener {

        // The amount of characters in the document.
        private int amountOfCharacters = 0;

        // Attributes for the text pane will be set to this.
        private MutableAttributeSet set;


        /** Default constructor for the DocumentManager. */
        public DocumentManager() {}


        /*
            Will increase the amount of characters in a document as the user
            enters them. Then will check if any displayed settings need to be
            changed.
        */
        @Override
        public void insertUpdate(DocumentEvent e) {

            amountOfCharacters += e.getLength();
            MainWindow.updateCharacterCount(amountOfCharacters);

            check();
        }


        /*
            Will decrease the amount of characters in a document as the user
            enters them. Then will check if any displayed settings need to be
            changed.
        */
        @Override
        public void removeUpdate(DocumentEvent e) {

            amountOfCharacters -= e.getLength();
            MainWindow.updateCharacterCount(amountOfCharacters);

            check();
        }


        /*
            This is not yet implemented (and probably wont be until a reason is
            found to implement it).
        */
        @Override
        public void changedUpdate(DocumentEvent e) {
            System.err.println("changeUpdate(DocumentEvent e) not implemented...");
        }


        /**
         * Updates the font family and size fields on the MainEditingToolBar
         * if they are not correct.
         */
        private void check() {

            set = MainWindow.getTextPane().getInputAttributes();

            // Get the current font family and size.
            String fontName = StyleConstants.getFontFamily(set);
            int fontSize    = StyleConstants.getFontSize(set);

            if(!fontName.equals(MainEditingToolBar.getCurrentSelectedFontName())) {
                MainEditingToolBar.setCurrentFontName(fontName);
            } else if(fontSize != MainEditingToolBar.getCurrentSelectedFontSize()) {
                MainEditingToolBar.setCurrentFontSize(fontSize);
            }

        }

    }

}