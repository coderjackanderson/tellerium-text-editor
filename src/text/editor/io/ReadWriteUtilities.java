package text.editor.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import text.editor.graphics.MainWindow;

/**
 * Writes files to the file system.
 *
 * Created on:  02 March 2016
 * Edited on:   02 March 2016
 * 
 * @author Jackie Chan
 */
public class ReadWriteUtilities {

    
    /**
     * Writes the contents of the Document into a RTF file. Plain text will
     * be added later.
     */
    public static void writeRTFDocument() {
        
        final JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showSaveDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            
            String fileName = fc.getSelectedFile().getAbsolutePath();
            
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
                
            } catch (IOException | BadLocationException err) {
                err.printStackTrace();
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
            
            String fileName = fc.getSelectedFile().getAbsolutePath();
            
            RTFEditorKit kit = new RTFEditorKit();
            
            try {
                
                kit.read(new FileReader(fileName), MainWindow.getTextPaneDocument(), 0);
                
            } catch (IOException | BadLocationException err) {
                err.printStackTrace();
            }    
        }
    }
}
