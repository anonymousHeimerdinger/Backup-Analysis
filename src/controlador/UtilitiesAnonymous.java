/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Image;
import java.net.URL;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author Glenius
 */
public class UtilitiesAnonymous {
    
    private static UtilitiesAnonymous instance = new UtilitiesAnonymous();

    public static UtilitiesAnonymous getInstance(){
        if(instance == null){
            instance = new UtilitiesAnonymous();
        }
        return instance;
    }
    
    
    
    public static Icon getIcon(String address, int scale_X,int scale_Y){
        ImageIcon imageIcon = new ImageIcon(address);
        Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(scale_X,scale_Y,Image.SCALE_DEFAULT));
        return icon;
    }
    
    public static JLabel getLabelImage(String address, int scale_X,int scale_Y){
        Icon icon = UtilitiesAnonymous.getIcon(address, scale_X, scale_Y);
        return new JLabel(icon);
    }
    
    public static int[] addElement(int[] a, int e) {
        a  = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
    return a;
    }
    
    public static void setSelectedAllIndicesOnJlist(JList jList, int sizeList){
        int[] all = new int[]{};
        for(int i=0;i<sizeList;++i){
            all = UtilitiesAnonymous.addElement(all, i);
        }
        
        jList.setSelectedIndices(all);
    }
    
}
