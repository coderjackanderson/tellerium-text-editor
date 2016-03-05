package text.editor;

import java.io.File;
import text.editor.graphics.MainWindow;


/**
 * This is the main class for the Java Text Editor application. It will contain 
 * tests for the startup of the application, and actually start up the
 * application.
 * 
 * Created On:  February 28, 2016
 * Edited On:   March 04, 2016
 * 
 * @author Jackie Chan
 */
public class Main {

    
    /**
     * The main method will perform checks for application settings and start 
     * the application (when fully implemented).
     * 
     * @param args      is irrelevant.
     */
    public static void main(String[] args) {
        
        // Check for the error reporting directory. If it does not exist, make it.
        if(!new File("ErrorReportingDirectory").exists()) {
            new File("ErrorReportingDirectory").mkdir();
        }
        new MainWindow().createAndShowGUI();
        MainWindow.setFocusToDocument();
    }
    
}
