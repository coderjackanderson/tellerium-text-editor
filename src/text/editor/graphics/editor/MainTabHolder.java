package text.editor.graphics.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Holds each document in a new tab.
 *
 * Created on:  March 03, 2016
 * Edited on:   March 03, 2016
 *
 * @author Jackie Chan
 */
public class MainTabHolder extends JTabbedPane {
    
    
    public MainTabHolder(){createTabbedPane();}
    
    
    /**
     * Creates a new JTabbedPane.
     */
    private void createTabbedPane() {
        createNewDocument();
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    
    
    /**
     * Creates a new document with the MainTextPane class.
     */
    public void createNewDocument() {
        this.addTab("New File "+(this.getTabCount()+1), new JScrollPane(new MainTextPane()));
        this.setTabComponentAt(this.getTabCount()-1, new CustomTab(this));
    }
    
    
    /**
     * Returns the MainTextPane associated with the component at the specified 
     * index.
     * 
     * @param index     the index of the tab.
     * @return          the MainTextPane associated with the component at the 
     *                  index specified.
     */
    public MainTextPane getTextPane(int index) {
        return ((MainTextPane)((JScrollPane)this.getComponentAt(index)).getViewport().getView());
    }
    
    
    /**
     * A JPanel that contains a Button and a Label for the JTabbedPane.
     */
    private class CustomTab extends JPanel {
        
        
        /** The JTabbedPane that is associated with this tab. */
        private final JTabbedPane pane;
        
        
        /**
         * Default constructor for the CustomTab class.
         * 
         * @param pane      The JTabbedPane that this CustomTab is associated 
         *                  with.
         */
        public CustomTab(JTabbedPane pane) {
            
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            if(pane == null) {
                throw new NullPointerException("pane is null");
            }
            
            this.pane = pane;
            this.setOpaque(false);
            
            JLabel label = new JLabel() {
                
                @Override
                public String getText() {
                    int index = pane.indexOfTabComponent(CustomTab.this);
                    if(index != -1) {
                        return pane.getTitleAt(index);
                    }
                    return null;
                }
            };
            
            this.add(label);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            
            this.add(new TabButton());
        }
        
        
        /**
         * A JButton that will delete the tab it is associated with if clicked.
         */
        class TabButton extends JButton implements ActionListener {
            
            /** Creates a TabButton that will delete the tab if clicked. */
            public TabButton() {
                this.setPreferredSize(new Dimension(15, 15));
                this.setIcon(new ImageIcon("res/close_tab_action_10x10_white_icon.png"));
                this.setContentAreaFilled(false);
                this.setBorder(BorderFactory.createEtchedBorder());
                this.setBorderPainted(false);
                this.setFocusable(false);
                this.addActionListener(this);
                this.addMouseListener(new Listener());
                this.setRolloverEnabled(true);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Will remove the tab of whom's close button was clicked.
                int index = pane.indexOfTabComponent(CustomTab.this);
                if(index != -1) {
                    pane.remove(index);
                }
            }
        }
        
        
        /**
         * The listener for the TabButton. This will manage any rollover
         * actions that need to be implemented.
         */
        class Listener extends MouseAdapter {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if(component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton)component;
                    button.setBorderPainted(true);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if(component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton)component;
                    button.setBorderPainted(false);
                }
            }
        }
    }
}