package text.editor;

import java.io.File;
import text.editor.graphics.MainWindow;


/**
 * The Main class will perform tests on the applications running environment and
 * then display the GUI of the application.
 *
 * Created On:  February 28, 2016
 * Edited On:   March 05, 2016
 *
 * @author Jackie Chan
 */
public class Main {


    /**
     * The main method will perform checks on the environment the program is
     * running in and then display the GUI.
     *
     * @param args      The command line arguments are not used.
     */
    public static void main(String[] args) {

        /*
            Check if the error reporting directory exists. If it doesn't make it.
        */
        if(!new File("ErrorReportingDirectory").exists()) {
            new File("ErrorReportingDirectory").mkdir();
        }

        // Show the GUI.
        new MainWindow().createAndShowGUI();
    }

}
