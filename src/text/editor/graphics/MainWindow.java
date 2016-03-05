package text.editor.graphics;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.Document;
import text.editor.errorreporting.ErrorReport;
import text.editor.graphics.editor.MainEditingToolBar;
import text.editor.graphics.editor.MainTabHolder;
import text.editor.graphics.editor.MainTextPane;
import text.editor.graphics.editor.StatusBar;

/**
 * This is the main window for the GUI.
 *
 * Created on:  February 28, 2016
 * Edited on:   March 04, 2016
 * 
 * @author Jackie Chan
 */
public class MainWindow extends JFrame {
    
    
    /** JPanel with BorderLayout that will hold most of the GUI components. */
    private static JPanel               panel;
    
    
    /** Area where the user will enter and format text for their document. */
    private static MainTabHolder        tabHolder;
    
    
    /** Tool bar that will contain buttons for user interaction with the application. */
    private static MainEditingToolBar   editingToolBar;
    
    
    /** The status bar. */
    private static StatusBar            statusBar;
    
    
    /** Default constructor for the MainWindow. */
    public MainWindow() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | 
                    InstantiationException | 
                    IllegalAccessException | 
                    UnsupportedLookAndFeelException err) {
            new ErrorReport().createErrorReport(err);
        }
    }
    
    
    /**
     * Creates and shows the MainWindow GUI.
     */
    public void createAndShowGUI() {
        this.setTitle("Text Editor");
                
        panel           = new JPanel(new BorderLayout());
        tabHolder       = new MainTabHolder();
        editingToolBar  = new MainEditingToolBar();
        statusBar       = new StatusBar();
        
        panel.add(tabHolder, BorderLayout.CENTER);
        panel.add(editingToolBar, BorderLayout.PAGE_START);
        panel.add(statusBar, BorderLayout.PAGE_END);
        
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);         
    }
    
    
    /**
     * Returns the Document associated with the main text editing area.
     * 
     * @return      The document associated with the main text editing area.
     */
    public static Document getTextPaneDocument() {
        return tabHolder.getTextPane(tabHolder.getSelectedIndex()).getDocument();
    }
    
    
    public static MainTextPane getTextPane() {
        return (MainTextPane)tabHolder.getTextPane(tabHolder.getSelectedIndex());
    }
    
    
    /**
     * Returns the tabbed pane that holds the documents.
     * 
     * @return      the JTabbedPane that holds the documents.
     */
    public static MainTabHolder getTabbedPane() {
        return tabHolder;
    }
    
    
    /**
     * Sets the title of the tab at the specified index.
     * 
     * @param title     the title.
     * @param index     the index of the tab whose title should be set.
     */
    public static void setTabTitle(String title, int index) {
        tabHolder.setTitleAt(index, title);
    }
    
    
    /**
     * Updates the amount of characters the user has typed in displayed on the
     * status bar.
     * 
     * @param value     the value to set the amount of characters to.
     */
    public static void updateCharacterCount(int value) {
        statusBar.updateCharacterCount(value);
    }
    
    
    /**
     * Sets the focus to the document that is selected or lost focus.
     */
    public static void setFocusToDocument() {
        getTextPane().requestFocus();
    }
    
}
