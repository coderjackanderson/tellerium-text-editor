package text.editor.errorreporting;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Writes an error report to the error reporting directory.
 * 
 * Created on:  March 03, 2016
 * Edited on:   March 04, 2016
 *
 * @author Jackie Chan
 */
public class ErrorReport {
    
    
    /** Default constructor for the ErrorReport class. */
    public ErrorReport() {}
    
    
    /**
     * Creates an error report and writes it to a file. 
     * 
     * @param e     the error that was reported.
     */
    public void createErrorReport(Exception e) {
        
        // Get the stack trace and put it in a String.
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String errorMessage = sw.toString();
        
        String fileName = new SimpleDateFormat("yyyy_MM_dd").
                                format(new Date())+
                                "_"+System.nanoTime()+".err";
        
        try {
            
            if(!new File("ErrorReportingDirectory").exists()) {
                new File("ErrorReportingDirectory").mkdir();
            }
            
            PrintWriter pw = new PrintWriter("ErrorReportingDirectory"+
                                              File.separator+
                                              fileName);    
            
            pw.println(errorMessage);
            
            pw.close();
            
        } catch (IOException err) {
            JOptionPane.showMessageDialog(null,
                                            "An error occured while writing the error file.", 
                                            "Error", 
                                            JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
