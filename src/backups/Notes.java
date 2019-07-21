/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Glenius
 */
public class Notes{
    
    private JTextArea txtAnotations;
    private JScrollPane scroll;
    public Notes(String name, String txtInit, boolean isEditabletxtArea, Color color) {
        this.txtAnotations = new JTextArea(txtInit);
        this.txtAnotations.setEditable(isEditabletxtArea);
        this.scroll = new JScrollPane(this.txtAnotations);
        this.scroll.setBorder(BorderFactory.createTitledBorder(name + ":"));
        this.scroll.setBackground(color);
        Dimension dim = new Dimension(100, 950);
        this.scroll.setPreferredSize(dim);
        this.txtAnotations.setVisible(true);
    }

    public String getAnotations() {
        return this.txtAnotations.getText();
    }

    public void setAnotations(String anotations) {
        this.txtAnotations.setText(anotations);
    }
    
    public void changeStateEditableText(boolean isEditabletxtArea){
        this.txtAnotations.setEditable(isEditabletxtArea);
    }
    
    public void setTitleBorder(String name){
        this.scroll.setBorder(BorderFactory.createTitledBorder(name + ":"));
    }
    
    public JScrollPane getJScrollPane(){
        return this.scroll;
    }
    
}
