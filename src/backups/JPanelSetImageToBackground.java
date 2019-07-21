/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import controlador.UtilitiesAnonymous;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Glenius
 */
public class JPanelSetImageToBackground extends JPanel{
    
    private Image background;
    
    public JPanelSetImageToBackground(String str_direction) {
        UtilitiesAnonymous anonymous = UtilitiesAnonymous.getInstance();
        this.setBackground(str_direction);
    }
    
    @Override
    public void paintComponent(Graphics g){
        
        int width = this.getSize().width;
	int height = this.getSize().height;
        
        if (this.background != null) {
            g.drawImage(this.background, 0, 0, width, height, null);
        }
        super.paintComponent(g);   
    }
    
    public void setBackground(String imagePath){
        this.setOpaque(false);
        this.background = new ImageIcon(imagePath).getImage();
        repaint();
    }
    
}
