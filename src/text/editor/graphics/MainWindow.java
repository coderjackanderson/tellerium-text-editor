package text.editor.graphics;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.Document;
import text.editor.graphics.editor.MainEditingToolBar;
import text.editor.graphics.editor.MainTextPane;

/**
 * This is the main window for the GUI.
 *
 * Created on:  February 28, 2016
 * Edited on:   March 02, 2016
 * 
 * @author Jackie Chan
 */
public class MainWindow extends JFrame {
    
    
    /** JPanel with BorderLayout that will hold most of the GUI components. */
    private static JPanel               panel;
    
    
    /** Area where the user will enter and format text for their document. */
    private static MainTextPane       textPane;
    
    
    /** Tool bar that will contain buttons for user interaction with the application. */
    private static MainEditingToolBar editingToolBar;
    
    
    /** Default constructor for the MainWindow. */
    public MainWindow() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | 
                    InstantiationException | 
                    IllegalAccessException | 
                    UnsupportedLookAndFeelException err) {
            err.printStackTrace(); 
        }
    }
    
    
    /**
     * Creates and shows the MainWindow GUI.
     */
    public void createAndShowGUI() {
        this.setTitle("Tellurium Text Editor");
                
        panel           = new JPanel(new BorderLayout());
        textPane        = new MainTextPane();
        editingToolBar  = new MainEditingToolBar();
        
        panel.add(new JScrollPane(textPane), BorderLayout.CENTER);
        panel.add(editingToolBar, BorderLayout.PAGE_START);
        
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);         
        
        textPane.requestFocus();
    }
    
    
    /**
     * Returns the Document associated with the main text editing area.
     * 
     * @return      The document associated with the main text editing area.
     */
    public static Document getTextPaneDocument() {
        return textPane.getDocument();
    }
    
}
