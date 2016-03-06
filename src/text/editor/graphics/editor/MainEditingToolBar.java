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
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import text.editor.graphics.MainWindow;
import text.editor.graphics.actions.Actions;


/**
 * Contains components to style text, and to create, open and save files.
 *
 * Created on:  March 01, 2016
 * Edited on:   March 05, 2016
 *
 * @author Jackie Chan
 */
public class MainEditingToolBar extends JToolBar {


    /** Buttons for the tool bar. */
    private static JButton newFileButton, openFileButton, saveFileButton,
                           boldButton, italicButton, underlineButton;


    /**
     * Combo box that allows the user to select their font size from a list or
     * type their own font.
     */
    private static JComboBox fontSizeComboBox;


    /**
     * A Combo box that allows the user to select a font family from a list of
     * installed font families or to type their own.
     */
    private static JComboBox fontNamesComboBox;


    /**
     * The previous font size that was selected. This is used when the user
     * does not type a number into the font size field.
     */
    private static int previousFontSize;


    /** The default constructor for the tool bar. */
    public MainEditingToolBar(){createToolBar();}


    /**
     * Creates and adds components to this tool bar. Components added will
     * include functions to create, open, and save files. The components will
     * also assist in styling text.
     */
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

        saveFileButton.addActionListener(new Actions.SaveFileAction());
        openFileButton.addActionListener(new Actions.OpenFileAction());
        newFileButton.addActionListener(new Actions.NewFileAction());

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
     * Creates the font family selection combo box. The Combo Box will include
     * all fonts installed on the system running the program.
     */
    private void createFontFamilyComboBox() {

        fontNamesComboBox = new JComboBox(GraphicsEnvironment.
                                            getLocalGraphicsEnvironment().
                                            getAvailableFontFamilyNames());

        fontNamesComboBox.setEditable(true);

        fontNamesComboBox.addActionListener(new AbstractAction() {

            /*
                Will set the font for the current selected JTextPane. Since
                StyledEditorKit is very poorly documented when dealing with
                JTextPane, you must use MutableAttributeSet to make a new
                SimpleAttributeSet. Then, you must set the font family of the
                attribute to the one the user selected. After that, set the
                character attributes of the JTextPane to the new AttributeSet
                and set the second parameter to false. Then reset focus to the
                document.
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
     * Creates the font size combo box. The font size combo box will allow the
     * user to select a font size from a list, or type in their own. Font sizes
     * in the list are multiples of two and go up to 98.
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

        fontSizeComboBox.setSelectedItem(12);

        this.add(fontSizeComboBox);

    }


    /**
     * Sets the font size displayed in the font size combo box to the size in
     * the parameter.
     *
     * @param size      The size to set.
     */
    public static void setCurrentFontSize(int size) {
        fontSizeComboBox.getEditor().setItem(size);
    }


    /**
     * Returns the current font size that appears in the font size combo box.
     *
     * @return          The size that appears in the font size combo box.
     */
    public static int getCurrentSelectedFontSize() {
        return (int)fontSizeComboBox.getEditor().getItem();
    }


    /**
     * Sets the font name displayed in the font names combo box to the name in
     * the parameter.
     *
     * @param fontName  The name to set.
     */
    public static void setCurrentFontName(String fontName) {
        fontNamesComboBox.getEditor().setItem(fontName);
    }


    /**
     * Returns the current font name that appears in the font names combo box.
     *
     * @return          The name that appears in the font names combo box.
     */
    public static String getCurrentSelectedFontName() {
        return fontNamesComboBox.getSelectedItem().toString();
    }

}