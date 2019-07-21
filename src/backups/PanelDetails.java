/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Client;
import model.Job;

/**
 *
 * @author Glenius
 */
public class PanelDetails extends JPanel{

    JPanel panelImageGraph;
    JPanelImagesInfoBackup panelImages;
    
    Color colorPanel;
    Client client;
    String nameServer;
    
    public PanelDetails(Color colorPanel, Client client, String nameServer) {
        
        this.colorPanel = colorPanel;
        this.client = client;
        this.nameServer = nameServer;
        
        initListPanelImages();
    }
    
    private void initListPanelImages() {
        this.panelImageGraph = new JPanel();
        
        this.panelImageGraph.setBackground(this.colorPanel);
        
        this.panelImages = new JPanelImagesInfoBackup(this.colorPanel);
        
        Job[] table = this.client.getBackupsInformationOfOneClient().getTable();
        
        this.panelImages.fillLabelsImage(table);
        this.panelImageGraph.add(this.panelImages);
        this.add(this.panelImageGraph);
        this.panelImageGraph.setVisible(true);
    }
    
}
