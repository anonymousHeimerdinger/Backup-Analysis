/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Client;

/**
 *
 * @author Glenius
 */
public class OpenDetailsClient{
    
    String name;
    String nameServer;
    JTextArea txtAreaSaveSetsFaileds;
    JTextArea txtAreaSaveSetsAll;
    
    Client client;
    Color color;
    JScrollPane scrollSaveSetsFaileds;
    JScrollPane scrollSaveSetsAll;
    
    /**
     * 
     * @param parent
     * @param modal
     * @param client
     * @param nameServer
     * @param color 
     */
    public OpenDetailsClient(java.awt.Frame parent, boolean modal,Client client,String nameServer,Color color) {
        
        String title = "Details Client "+ client.getName() + "\t  from server: " + nameServer;
        this.nameServer = nameServer;
        this.client = client;
        this.color = color;
        
        initComponents();
        
        openJDDialogDoublePanel(parent, modal, title);
    }

    private void initComponents() {
        this.initPanelSaveSetsAll();
        this.initPanelSaveSetsFaileds();
    }

    private void openJDDialogDoublePanel(java.awt.Frame parent, boolean modal,String title) {
        JPanel panelLeft = new JPanel();
        panelLeft.add(this.scrollSaveSetsFaileds);
        panelLeft.add(this.scrollSaveSetsAll);
        PanelDetails panelRight = new PanelDetails(this.color, this.client, this.nameServer);
        panelLeft.setVisible(true);
        panelRight.setVisible(true);
        new JDDialogDoublePanel(parent, modal,panelLeft,panelRight,title,1000, 1200);
    }

    private void initPanelSaveSetsFaileds() {
        
        this.txtAreaSaveSetsFaileds = new JTextArea(300,100);
        String description = "";
        
        for(int i = 0;i<client.getSizeErrors();++i){
            try {
                description += client.getReportError(i) + "\n";
            } catch (Exception ex) {
                description += "\n";
            }
        }
        
        this.txtAreaSaveSetsFaileds.setText(description);
        //this.txtAreaSaveSetsFaileds.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.txtAreaSaveSetsFaileds.setEditable(false);
        this.txtAreaSaveSetsFaileds.setVisible(true);
        this.scrollSaveSetsFaileds = new JScrollPane(txtAreaSaveSetsFaileds);
        this.scrollSaveSetsFaileds.setBorder(BorderFactory.createTitledBorder("Faileds save sets:"));
        this.scrollSaveSetsFaileds.setVisible(true);
    }

    private void initPanelSaveSetsAll() {
        
        this.txtAreaSaveSetsAll = new JTextArea(300,100);
        String description = "";
        
        for(int i = 0;i<client.getSizeReports();++i){
            try {
                description += client.getReport(i) + "\n";
            } catch (Exception ex) {
                description += "\n";
            }
        }
        
        //this.scrollSaveSetsAll.setLayout(new BoxLayout(this.scrollSaveSetsAll, BoxLayout.Y_AXIS));
        
        this.txtAreaSaveSetsAll.setText(description);
        //this.txtAreaSaveSetsFaileds.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.txtAreaSaveSetsAll.setEditable(false);
        this.scrollSaveSetsAll = new JScrollPane(txtAreaSaveSetsAll);
        this.txtAreaSaveSetsAll.setVisible(true);
        this.scrollSaveSetsAll.setBorder(BorderFactory.createTitledBorder("All save sets:"));
        this.scrollSaveSetsAll.setVisible(true);
    }
}
