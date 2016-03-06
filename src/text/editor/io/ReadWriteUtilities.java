package text.editor.io;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import text.editor.errorreporting.ErrorReport;
import text.editor.graphics.MainWindow;
import text.editor.graphics.actions.FileType;
import text.editor.graphics.editor.MainTabHolder;
import text.editor.graphics.editor.MainTextPane;
import text.editor.graphics.editor.StatusBar;


/**
 * Writes files to the file system.
 *
 * Created on:  March 02, 2016
 * Edited on:   March 05, 2016
 * 
 * @author Jackie Chan
 */
public class ReadWriteUtilities {
    
    
    /** 
     * Private constructor so the ReadWriteUtilities class cannot be 
     * instantiated. 
     */
    private ReadWriteUtilities(){}
    
    
    /**
     * Saves the document that the user has typed in. This is only called when
     * the document is not already saved.
     * 
     * @param doc   The document to save.
     */
    public static void saveFile(Document doc) {
        
        JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

            String filePath = fc.getSelectedFile().getAbsolutePath();           
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1);
            
            if(fileName.contains(".rtf")) {
                
                /*
                    If the file name contains .rtf, then use the RTFEditorKit to
                    save the file to the disk.
                */
                RTFEditorKit kit = new RTFEditorKit();
                BufferedOutputStream out;
                
                try {
                    
                    out = new BufferedOutputStream(new FileOutputStream(filePath));
                    kit.write(out, doc, doc.getStartPosition().getOffset(), doc.getLength());
                    out.close();
                    
                    MainWindow.getTextPane().setFilePath(filePath);
                    
                    showSaveConfirmation(fileName);
                    
                } catch (BadLocationException | IOException err) {
                    new ErrorReport().createErrorReport(err);
                    JOptionPane.showMessageDialog(fc, 
                                                    "Error saving file...", 
                                                    "Error", 
                                                    JOptionPane.ERROR_MESSAGE);
                }
                
            } else {
                
                /*
                    Check if the user is okay with the loss of formatting.
                */
                int isUserOkay = JOptionPane.showConfirmDialog(fc, 
                                                                "You are saving the file in a "
                                                              + "format where styles may be lost. "
                                                              + "Do you accept?", 
                                                                "File Format Warning", 
                                                                JOptionPane.YES_NO_OPTION, 
                                                                JOptionPane.QUESTION_MESSAGE);
                
                /*
                    If the user is okay with the loss of some formatting,
                    then go ahead and save the file.
                */
                if(isUserOkay == JOptionPane.YES_OPTION) {
                    
                    try {
                        
                        String text = doc.getText(doc.getStartPosition().getOffset(), doc.getLength());
                        System.out.println("File Content: "+text);
                        
                        try (PrintWriter pw = new PrintWriter(
                                              new BufferedWriter(
                                              new FileWriter(filePath, true)))) {
                            
                            pw.println(text);
                            pw.close();
                        }
                        
                        MainWindow.getTextPane().setFilePath(filePath);
                        
                        showSaveConfirmation(fileName);
                        
                    } catch (BadLocationException | IOException err) {
                        new ErrorReport().createErrorReport(err);
                        JOptionPane.showMessageDialog(fc, 
                                                        "An error occured while saving the file.", 
                                                        "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
                                
            }
            
        }
        
    }
    

    /**
     * Reads a document from the file system.
     */
    public static void readFile() {
        
        final JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            
            String filePath = fc.getSelectedFile().getAbsolutePath();
            String fileName = getFileName(filePath);
            
            RTFEditorKit kit = new RTFEditorKit();

            // Get some temporary variables for better and shorter references.
            MainTabHolder tempTH    = MainWindow.getTabbedPane();
            tempTH.createNewDocument();
            Document tempDoc        = MainWindow.getTextPaneDocument();
            MainTextPane tempTP     = MainWindow.getTextPane();
            
            if(fileName.contains(".rtf")) {
                
                try {

                    tempTH.setSelectedIndex(tempTH.getTabCount()-1);

                    // Read the text and put it in the document.
                    kit.read(new FileReader(filePath), tempDoc, 0);

                    // Set some titles and stuff.
                    tempTH.setTitleAt(tempTH.getSelectedIndex(), fileName);
                    tempTP.setFilePath(filePath);

                } catch (IOException | BadLocationException err) {
                    new ErrorReport().createErrorReport(err);
                    JOptionPane.showMessageDialog(fc, 
                                                    "Error opening file.", 
                                                    "Error", 
                                                    JOptionPane.ERROR_MESSAGE);
                }
                
            } else {
                
                try {
                    
                    BufferedReader br = new BufferedReader(
                                        new FileReader(filePath));
                    
                    String line;
                    String data = "";
                    
                    while((line = br.readLine()) != null) 
                        data += line+"\n";
                    
                    tempDoc.insertString(0, data, tempTP.getCharacterAttributes());
                    
                    tempTH.setTitleAt(tempTH.getSelectedIndex(), fileName);
                    tempTP.setFilePath(filePath);
                    
                } catch (BadLocationException | IOException err) {
                    new ErrorReport().createErrorReport(err);
                    JOptionPane.showMessageDialog(fc, 
                                                    "Error opening file.", 
                                                    "Error", 
                                                    JOptionPane.ERROR_MESSAGE);                    
                }
                
            }
            
        }
        
    }
    
    
    /**
     * Overwrites a file that is already saved to the disk.
     * 
     * @param filePath  The path to the file on the disk.
     * @param format    The format of the document. 
     * @param doc       The document that contains the text.
     */
    public static void overwriteFile(String filePath, 
                                     FileType format, 
                                     Document doc) {
        
        System.out.println("File format: "+format);
        
        switch(format) {
            
            case RTF:
                
                RTFEditorKit kit = new RTFEditorKit();
                BufferedOutputStream out;
                
                try {
                    
                    out = new BufferedOutputStream(new FileOutputStream(filePath));
                    kit.write(out, 
                                doc, 
                                doc.getStartPosition().getOffset(), 
                                doc.getLength());
                    out.close();
                    
                    showSaveConfirmation(getFileName(filePath));

                } catch (BadLocationException | IOException err) {                    
                    new ErrorReport().createErrorReport(err);
                    JOptionPane.showMessageDialog(null, 
                                                    "An error occured while saving the file.", 
                                                    "Error", 
                                                    JOptionPane.ERROR_MESSAGE);
                }
                
                break;
                
                
            case TXT_OTHER:
                
                try {
                    String text = doc.getText(doc.getStartPosition().getOffset(), doc.getLength());
                    System.out.println("File Content: "+text);

                    try (PrintWriter pw = new PrintWriter(
                                          new BufferedWriter(
                                          new FileWriter(filePath, true)))) {
                        pw.println(text);
                        pw.close();
                    }

                    MainWindow.getTextPane().setFilePath(filePath);

                    showSaveConfirmation(getFileName(filePath));
                    
                } catch (BadLocationException | IOException err) {
                    new ErrorReport().createErrorReport(err);
                    JOptionPane.showMessageDialog(null, 
                                                    "An error occured while saving the file.", 
                                                    "Error", 
                                                    JOptionPane.ERROR_MESSAGE);
                }
                
                break;
                
                
            default:
                JOptionPane.showMessageDialog(null,
                                                "An unexpected error occured...", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
                break;
        }
        
    }    
    
    
    /**
     * Shows confirmation that the file has been saved to the user. Will set the
     * title of the tab the file was in to the file name, display on the status
     * bar that the file was saved, and then reset the status bar message to
     * Active.
     * 
     * @param fileName      The name of the file saved.
     */
    private static void showSaveConfirmation(String fileName) {
        MainTabHolder tempTH = MainWindow.getTabbedPane();
        tempTH.setTitleAt(tempTH.getSelectedIndex(), fileName);
        StatusBar.updateStatusMessage(fileName+" saved successfully...");
        resetStatusMessage();
    }
    
    
    /**
     * Creates a new thread that will wait for five seconds and then set the 
     * status bar message back to "Status: Active".
     */
    private static void resetStatusMessage() {
                        
        new Thread(() -> {
            
            try {
                Thread.sleep(5000);
                StatusBar.updateStatusMessage("Active");
            } catch (InterruptedException err) {
                new ErrorReport().createErrorReport(err);
            }
            
        }).start();
    }
    
    
    /**
     * Returns a String containing the file name of the parameter passed to it.
     * 
     * @param filePath  The file path.
     * @return          The file name.
     */
    private static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf(File.separator)+1);
    }
    
}
