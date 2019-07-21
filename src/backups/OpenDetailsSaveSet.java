/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.BackupsInformationOfOneSaveSet;
import model.Job;
import model.SaveSet;

/**
 *
 * @author Glenius
 */
public class OpenDetailsSaveSet extends javax.swing.JDialog{
    
    JTextArea txtAreaFaileds;
    JTextArea txtAreaAll;
    JScrollPane scrollFaileds;
    JScrollPane scrollAll;
    BackupsInformationOfOneSaveSet saveSet;
    JPanelImagesInfoBackup panelImages;
    Color color;
            
    /**
     * 
     * @param parent
     * @param modal
     * @param client
     * @param nameServer
     * @param color 
     */
    public OpenDetailsSaveSet(java.awt.Frame parent, boolean modal,BackupsInformationOfOneSaveSet saveSet,String nameSaveSet, Color color) {
        super(parent, modal);
        String title = "Job details of the Save set: "+ nameSaveSet;
        this.saveSet = saveSet;
        this.color = color;
        
        int height = 1000;
        int width = 450;
        this.setDefaultLookAndFeelDecorated(true);
        this.setTitle(title);
        Rectangle rectangle = new Rectangle(width, height);
        this.setBounds(rectangle);
        this.setLocationRelativeTo(null);
        
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        initComponents();
        
    }
    
    private void initComponents(){
        initTxtAreaFaileds();
        initTxtAreaAll();
        initPanelImages();
        this.setVisible(true);
    }
    
    private void initPanelImages() {
        
        this.panelImages = new JPanelImagesInfoBackup(this.color);
        Rectangle rectangle = new Rectangle(400, 150);
        this.panelImages.setBounds(rectangle);
        
        Job[] table = this.saveSet.getTable();
        
        this.panelImages.fillLabelsImage(table, saveSet.getLastCopySuccessful().getDate());
        this.add(this.panelImages);
    }
    
    private void initTxtAreaFaileds(){
        this.txtAreaFaileds = new JTextArea();
        
        
        String description = "";
        
        for(int i = 0;i<saveSet.getSizeItemsErrors();++i){
            try {
                SaveSet item = saveSet.getItemError(i);
                description += item.getNameSaveSet() + " ,date time: " + item.getDate() + " ,status: " + item.getStatus() + "\n";
            } catch (Exception ex) {
                description += "incorrect reading\n";
            }
        }
        
        this.txtAreaFaileds.setText(description);
        this.txtAreaFaileds.setEditable(false);
        this.scrollFaileds = new JScrollPane(this.txtAreaFaileds);
        this.scrollFaileds.setBorder(BorderFactory.createTitledBorder("Jobs faileds:"));
        this.add(this.scrollFaileds);
    }
    
    private void initTxtAreaAll(){
        this.txtAreaAll = new JTextArea();
        
        String description = "";
        
        for(int i = 0;i<saveSet.getSizeItems();++i){
            try {
                SaveSet item = saveSet.getItem(i);
                description += item.getNameSaveSet() + " ,date time: " + item.getDate() + " ,status: " + item.getStatus() + "\n";
            } catch (Exception ex) {
                description += "incorrect reading\n";
            }
        }
        
        this.txtAreaAll.setText(description);
        this.txtAreaAll.setEditable(false);
        this.scrollAll = new JScrollPane(this.txtAreaAll);
        this.scrollAll.setBorder(BorderFactory.createTitledBorder("all jobs:"));
        this.add(this.scrollAll);
    }
    
}
