/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 *
 * @author Glenius
 */
public class JDDialogDoublePanel extends javax.swing.JDialog{
    
    JPanel panelUp, panelDown, panelLeft, panelRight;
    JButton btn_OK;
    
    public JDDialogDoublePanel(Frame frame, boolean bln,JPanel panelUp, JPanel panelDown,String title,int height,String titlePanelUp,String titlePanelDown) {
        super(frame, bln);
        this.setDefaultLookAndFeelDecorated(true);
        this.setTitle(title);
        this.setBounds(height,height,height,height);
        this.setLocationRelativeTo(null);
        
        String str_direction = "imatges/mantenimentInfo01.jpg";
        JPanelSetImageToBackground panelBackground = new JPanelSetImageToBackground(str_direction);
        
        this.panelUp = panelUp;
        this.panelDown = panelDown;
        this.panelUp.setOpaque(false);
        this.panelDown.setOpaque(false);
        
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        
        this.panelUp.setSize(500,500);
        this.panelDown.setSize(500, 500);
        
        panelBackground.add(this.panelUp);
        panelBackground.add(this.panelDown);
        
        this.panelUp.setBorder(BorderFactory.createTitledBorder(titlePanelUp));
        this.panelDown.setBorder(BorderFactory.createTitledBorder(titlePanelDown));
        
        this.panelUp.setAlignmentX(LEFT_ALIGNMENT);
        this.panelDown.setAlignmentX(CENTER_ALIGNMENT);     
        
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        this.setContentPane(panelBackground);
        
        this.setVisible(true);
    }
    
    public JDDialogDoublePanel(Frame frame, boolean bln,JPanel panelLeft, JPanel panelRight,String title,int height, int width) {
        super(frame, bln);
        
        //this.setBounds(height,width,height,width);
        
        this.setDefaultLookAndFeelDecorated(true);
        
        this.setTitle(title);
        this.setBounds(height,height,height,height);
        //this.setSize(height, width);
        this.setLocationRelativeTo(null);
        this.panelLeft = panelLeft;
        this.panelRight = panelRight;
        
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        
        //this.getContentPane().setLayout(new GridLayout(1, 2));
        
        this.panelRight.setBorder(BorderFactory.createTitledBorder("Last ten days"));
        
        BoxLayout layoutLeft = new BoxLayout(panelLeft, BoxLayout.Y_AXIS);
        BoxLayout layoutRight = new BoxLayout(panelRight, BoxLayout.Y_AXIS);
        
        this.panelLeft.setLayout(layoutLeft);
        this.panelRight.setLayout(layoutRight);
        
        this.add(this.panelLeft);
        this.add(this.panelRight);
        this.panelLeft.setVisible(true);
        this.panelRight.setVisible(true);
        this.setVisible(true);
    }
    
}
