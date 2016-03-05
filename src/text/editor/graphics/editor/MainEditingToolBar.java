package text.editor.graphics.editor;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import text.editor.graphics.MainWindow;
import text.editor.io.ReadWriteUtilities;


/**
 * The tool bar for the main editing screen. Will contain items to edit text
 * styles and to also open, create new, and save files.
 * 
 * Created on:  March 01, 2016
 * Edited on:   March 04, 2016
 *
 * @author Jackie Chan
 */
public class MainEditingToolBar extends JToolBar {
    
    
    /** Buttons for the tool bar. */
    private static JButton newFileButton, openFileButton, saveFileButton, 
                           boldButton, italicButton, underlineButton;
    
    
    /** The combo box that contains the font sizes. */
    private static JComboBox fontSizeComboBox;
    
    
    /** The combo box that contains the available fonts. */
    private static JComboBox fontNamesComboBox;
    
    
    /** The previous font that was selected. */
    private static int previousFontSize;
    
    
    /** The default constructor for the tool bar. */
    public MainEditingToolBar(){createToolBar();}
    
    
    /** Creates and adds controls to the tool bar. */
    private void createToolBar() {
        newFileButton   = new JButton("New");
        openFileButton  = new JButton("Open");
        saveFileButton  = new JButton("Save");
        
        // These buttons will contain images. 
        boldButton      = new JButton(new ImageIcon("res/bold_action_32x32_white_icon.png"));
        italicButton    = new JButton(new ImageIcon("res/italic_action_32x32_white_icon.png"));
        underlineButton = new JButton(new ImageIcon("res/underline_action_32x32_white_icon.png"));
        
        boldButton.addActionListener(new StyledEditorKit.BoldAction());
        italicButton.addActionListener(new StyledEditorKit.ItalicAction());
        underlineButton.addActionListener(new StyledEditorKit.UnderlineAction());
        
        saveFileButton.addActionListener(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadWriteUtilities.writeRTFDocument();
                MainWindow.setFocusToDocument();
            }
        });

        
        openFileButton.addActionListener(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadWriteUtilities.readFile();
                MainWindow.setFocusToDocument();
            }
        });
        
        
        newFileButton.addActionListener(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.getTabbedPane().createNewDocument();
                MainWindow.setFocusToDocument();
            }
        });        
        
        this.add(newFileButton);
        this.add(openFileButton);
        this.add(saveFileButton);
        
        this.addSeparator();
        
        createFontFamilyComboBox();
        
        this.addSeparator();
        
        createFontSizeComboBox();
        
        this.addSeparator();
        
        this.add(boldButton);
        this.add(italicButton);
        this.add(underlineButton);
    }
    
    
    /**
     * Creates the font family selection combo box.
     */
    private void createFontFamilyComboBox() {
        
        fontNamesComboBox = new JComboBox(GraphicsEnvironment.
                                            getLocalGraphicsEnvironment().
                                            getAvailableFontFamilyNames());
        
        fontNamesComboBox.setEditable(true);        
        
        fontNamesComboBox.addActionListener(new AbstractAction() {
            
            /* 
                Will set the font name for the text pane.
            */
            @Override
            public void actionPerformed(ActionEvent e) {
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, fontNamesComboBox.getSelectedItem().toString());                
                MainWindow.getTextPane().setCharacterAttributes(attr, false);
                MainWindow.setFocusToDocument();
            }
        });
        
        fontNamesComboBox.setSelectedItem("Times New Roman");
        
        this.add(fontNamesComboBox);
    }
    
    
    /**
     * Creates the font size combo box.
     */
    private void createFontSizeComboBox() {
        
        // Get some font sizes (multiples of 2).
        List<Integer> array = new ArrayList();
        for(int i = 0; i < 100; i++) {if(i % 2 == 0) array.add(i);}
        
        fontSizeComboBox = new JComboBox(array.toArray());
        fontSizeComboBox.setEditable(true);
        
        fontSizeComboBox.addActionListener(new AbstractAction() {
            
            /*
                Sets the font size for the text pane.
            */
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Make sure the user only typed in a number.
                Matcher matcher = Pattern.compile("^[\\d]+$").matcher(
                            fontSizeComboBox.getEditor().getItem().toString());
                
                if(matcher.find()) {
                    
                    int fontSize = (int)fontSizeComboBox.getEditor().getItem();
                    
                    // Change the font size.
                    MutableAttributeSet attr = new SimpleAttributeSet();
                    StyleConstants.setFontSize(attr, fontSize);                        
                    MainWindow.getTextPane().setCharacterAttributes(attr, false);
                    
                    // Set the previous font size to the new font size.
                    previousFontSize = fontSize;
                    
                    // Request focus on the document.
                    MainWindow.setFocusToDocument();
                    
                } else {                    
                    JOptionPane.showMessageDialog(null, 
                                                    "You must type a number", 
                                                    "Error", 
                                                    JOptionPane.ERROR_MESSAGE);
                    fontSizeComboBox.getEditor().setItem(previousFontSize);
                    MainWindow.setFocusToDocument();                   
                }   
            }    
        });
        
        // Set the current font.
        fontSizeComboBox.setSelectedItem(12);
        
        this.add(fontSizeComboBox);
    }
    
    
    /**
     * Sets the current selected font size to the one in the parameter.
     * 
     * @param size      the size to set the current font size to.
     */
    public static void setCurrentFontSize(int size) {
        fontSizeComboBox.getEditor().setItem(size);
    }
    
    
    /**
     * Returns the current font size in the fontSizeComboBox.
     * 
     * @return          the current font size in the fontSizeComboBox.
     */
    public static int getCurrentSelectedFontSize() {
        return (int)fontSizeComboBox.getEditor().getItem();
    }
    
    
    /**
     * Sets the current selected font name to the one in the parameter.
     * 
     * @param fontName  the font name to set the current font to.
     */
    public static void setCurrentFontName(String fontName) {
        fontNamesComboBox.getEditor().setItem(fontName);
    }
   
    
    /**
     * Returns the current font name in the fontNamesComboBox.
     * 
     * @return          the current font name in the fontNamesComboBox.
     */
    public static String getCurrentSelectedFontName() {
        return fontNamesComboBox.getSelectedItem().toString();
    }
    
}