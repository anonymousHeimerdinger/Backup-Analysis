/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Glenius
 */
public class BorderGlenius{
    private static BorderGlenius instance = new BorderGlenius();
    
    public static BorderGlenius getInstance() {
        if(instance == null){
            instance = new BorderGlenius();
        }
        return instance;
    }
    private BorderGlenius() {
        
    }
    
    public Border getBorder(Color colorMargin, int margin){
        Border emptyBorder = BorderFactory.createLineBorder(colorMargin, margin);
        Border border = BorderFactory.createCompoundBorder(emptyBorder, new EtchedBorder());
        return border;
    }
    
    public Border getBorder(Color colorMargin, int margin, Color borderColor, Color borderPaddingColor){
        Border marginBorder = BorderFactory.createLineBorder(colorMargin, margin);
        Border border = BorderFactory.createCompoundBorder(marginBorder, new EtchedBorder(borderColor, borderPaddingColor));
        return border;
    }
    
}
