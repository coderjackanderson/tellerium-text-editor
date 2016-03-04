package text.editor.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import text.editor.errorreporting.ErrorReport;
import text.editor.graphics.MainWindow;

/**
 * Writes files to the file system.
 *
 * Created on:  March 02, 2016
 * Edited on:   March 03, 2016
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
     * Writes the contents of the Document into a RTF file. Plain text will
     * be added later.
     */
    public static void writeRTFDocument() {
        
        final JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showSaveDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            
            String fileName = fc.getSelectedFile().getAbsolutePath();
            
            /*
                Create regular expression to outlaw any characters that will
                cause problems in saving the file. 
                
                ([a-zA-Z0-9_-"',]+\\.*)  or something like that (no parenthesis).
            */
            
            if(!fileName.contains(".rtf")) {
                JOptionPane.showMessageDialog(null, 
                                                "File must end in \".rtf\".", 
                                                "Error", 
                                                JOptionPane.WARNING_MESSAGE);
                writeRTFDocument();
            }
            
            RTFEditorKit kit = new RTFEditorKit();
            
            BufferedOutputStream out;
            
            try {
                
                out = new BufferedOutputStream(new FileOutputStream(fileName));
                
                Document doc = MainWindow.getTextPaneDocument();
                
                kit.write(out,
                            doc,
                            doc.getStartPosition().getOffset(), 
                            doc.getLength());
                
                out.close();
                
                JOptionPane.showMessageDialog(null, 
                                                "File Saved...", 
                                                "File Saved...",
                                                JOptionPane.PLAIN_MESSAGE);
                
                // Set the title of the selected tab to the name of the file.
                MainWindow.setTabTitle(fileName.substring(
                                        fileName.lastIndexOf(File.separator)+1),
                                        MainWindow.getTabbedPane().getSelectedIndex());
                
            } catch (IOException | BadLocationException err) {
                new ErrorReport().createErrorReport(err);
            }
        }
    }
    
    
    /**
     * Reads a RTF document. Plain text will be added later.
     */
    public static void readFile() {
        
        final JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showOpenDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            
            if(fc.getSelectedFile() == null) {
                JOptionPane.showMessageDialog(null, 
                                                "Please select a file.", 
                                                "Error", 
                                                JOptionPane.WARNING_MESSAGE);
                readFile();
            }
            
            String fName = fc.getSelectedFile().getAbsolutePath();
            
            RTFEditorKit kit = new RTFEditorKit();
            
            try {
                
                MainWindow.getTabbedPane().createNewDocument();
                
                MainWindow.getTabbedPane().setSelectedIndex(MainWindow.getTabbedPane().getTabCount()-1);
                
                kit.read(new FileReader(fName), MainWindow.getTextPaneDocument(), 0);
                
                // Set the title of the selected tab to the name of the file.
                MainWindow.setTabTitle(fName.substring(
                                        fName.lastIndexOf(File.separator)+1), 
                                        MainWindow.getTabbedPane().getSelectedIndex());
                
            } catch (IOException | BadLocationException err) {
                new ErrorReport().createErrorReport(err);
            }    
        }
    }
}
