/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import javax.swing.SwingUtilities;
import model.Usuari;

/**
 *
 * @author Glenius
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                Usuari usuari = new Usuari();
                new ApplicacioBackup();
            }
        });
    }
    
}
