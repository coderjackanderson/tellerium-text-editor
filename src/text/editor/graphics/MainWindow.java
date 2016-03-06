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
 * The MainWindow will contain a JTextPane (allows user to edit documents), a
 * JToolBar (allows user to style text, and to open, save, and create new files)
 * , and another JToolBar (the status bar).
 *
 * Created on:  February 28, 2016
 * Edited on:   March 05, 2016
 *
 * @author Jackie Chan
 */
public class MainWindow extends JFrame {


    /** JPanel with BorderLayout that will hold most of the GUI components. */
    private static JPanel               panel;


    /** Holds the documents that the user will edit. */
    private static MainTabHolder        tabHolder;


    /**
     * Contains buttons and other components that allow the user to style text,
     * and to open, save, and create new documents.
     */
    private static MainEditingToolBar   editingToolBar;


    /** The status bar. */
    private static StatusBar            statusBar;


    /**
     * Default constructor for the MainWindow. The MainWindow will contain a
     * JTextPane (for editing .rtf documents), JToolBar (includes functions for
     * opening, saving, and creating new files and styling text), and another
     * JToolBar (the status bar at the bottom that keeps track of the character
     * count). It will also set the default look and feel to GTK+.
     */
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
     * Creates and shows all components of the MainWindow GUI.
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

        setFocusToDocument();

    }


    /**
     * Returns the Document associated with the main text editing area.
     *
     * @return      The document associated with the main text editing area.
     */
    public static Document getTextPaneDocument() {
        return tabHolder.getTextPane(tabHolder.getSelectedIndex()).getDocument();
    }


    /**
     * Returns the JTextPane that is associated with the active document.
     *
     * @return      The JTextPane associated with the active document.
     */
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
     * Sets the title of the tab at a specified index.
     *
     * @param title     the title.
     * @param index     the index of the tab.
     */
    public static void setTabTitle(String title, int index) {
        tabHolder.setTitleAt(index, title);
    }


    /**
     * Updates the character count that appears on the status bar.
     *
     * @param value     the value to set the character count to.
     */
    public static void updateCharacterCount(int value) {
        StatusBar.updateCharacterCount(value);
    }


    /**
     * Sets the focus to the document that is selected or lost focus.
     */
    public static void setFocusToDocument() {
        getTextPane().requestFocus();
    }


    /**
     * Returns the title of the active document.
     *
     * @return          The title at the specified index.
     */
    public static String getActiveDocumentsName() {
        return tabHolder.getTitleAt(tabHolder.getSelectedIndex());
    }

}
