package text.editor.graphics;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import text.editor.graphics.editor.MainTextPane;

/**
 * This is the main window for the GUI.
 *
 * Created on:  February 28, 2016
 * Edited on:   February 29, 2016
 * 
 * @author Jackie Chan
 */
public class MainWindow extends JFrame {
    
    
    /** JPanel with BorderLayout that will hold most of the GUI components. */
    private static JPanel               panel;
    
    
    /** Area where the user will enter and format text for their document. */
    private static MainTextPane       textPane;
    
    
    /** Default constructor for the MainWindow. */
    public MainWindow() {}
    
    
    public void createAndShowGUI() {
        this.setTitle("Main Window");
        
        panel       = new JPanel(new BorderLayout());
        textPane  = new MainTextPane();
        
        panel.add(new JScrollPane(textPane), BorderLayout.CENTER);
        
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 450);
        this.setVisible(true);         
    }
}
