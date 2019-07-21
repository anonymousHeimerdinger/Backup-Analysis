/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import controlador.UtilitiesAnonymous;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Glenius
 */
public class Help {

    public Help(java.awt.Frame jfrm) {
        JPanel panelLeft = new JPanel();
                
                JTextArea jTextArea = new JTextArea();
                jTextArea.setSize(500, 500);
                String string = "Cambios de la versión 3.4 a la versión 4\tFecha del cambio: 18/02/2018\n\n"
                + "-> Ahora se analizan los fallos en función del save set de cada cliente.\n\n"
                + "-> En detalles de clientes, se pueden observar los save sets fallados y todos los save sets.\n\n"
                + "-> Pendiente añadir funcionalidad a los campos de grupos.\n\n\n";
                jTextArea.setText(string);
                jTextArea.setEditable(false);
                panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
                panelLeft.add(jTextArea);
                panelLeft.setSize(500, 500);
                
                JPanel panelRight = new JPanel();
                panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
                
                JLabel labelImatge = UtilitiesAnonymous.getLabelImage("imatges/imageHelp.png", 400, 350);
                
                panelRight.add(labelImatge);
                
                String titlePanelUp = "Cambios que trae la nueva versión";
                String titlePanelDown = "Visita el Manual Backup EROSKI en la documentación";
                jTextArea.setOpaque(false);
                
                new JDDialogDoublePanel(jfrm, false, panelRight, panelLeft,"Help",1100, titlePanelDown, titlePanelUp);
    }
    
}
