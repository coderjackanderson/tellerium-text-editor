package text.editor.graphics.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractAction;
import text.editor.graphics.MainWindow;
import text.editor.io.ReadWriteUtilities;

/**
 * Contains classes for the Actions that multiple components of the GUI may need
 * to perform.
 *
 * Created on:  March 05, 2016
 * Edited on:   March 05, 2016
 *
 * @author Jackie Chan
 */
public class Actions {


    /**
     * Saves a file when implemented.
     */
    public static class SaveFileAction extends AbstractAction
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String filePath = MainWindow.getTextPane().getFilePath();

            // The file is not saved on the disk, so save it.
            if(filePath == null) {
                ReadWriteUtilities.saveFile(MainWindow.getTextPaneDocument());
                return;
            }

            /*
                The file may be on the disk, so check if it is or isn't. The user
                may have deleted the file while it was opened in the program.
            */
            File temp = new File(filePath);
            if(temp.exists()) {
                ReadWriteUtilities.overwriteFile(filePath,
                                                    getFileType(filePath),
                                                    MainWindow.getTextPaneDocument());
            } else {
                ReadWriteUtilities.saveFile(MainWindow.getTextPaneDocument());
            }

            MainWindow.setFocusToDocument();
        }


        /**
         * Returns the file type as an enumeration.
         *
         * @param s     The string with the file path.
         * @return      The file extension as an enumeration.
         */
        private FileType getFileType(String s) {

            String ext = s.substring(s.indexOf(".", s.lastIndexOf(File.separator)));

            if(ext.equalsIgnoreCase(".rtf")) {
                return FileType.RTF;
            } else {
                return FileType.TXT_OTHER;
            }

        }

    }


    /**
     * Opens a file.
     */
    public static class OpenFileAction extends AbstractAction
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ReadWriteUtilities.readFile();
            MainWindow.setFocusToDocument();
        }

    }


    /**
     * Creates a new file.
     */
    public static class NewFileAction extends AbstractAction
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MainWindow.getTabbedPane().createNewDocument();
        }

    }

    public static class CloseFileAction extends AbstractAction
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public static class FontFamilyChangeAction extends AbstractAction
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public static class FontSizeChangeAction extends AbstractAction
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}
