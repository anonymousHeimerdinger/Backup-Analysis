/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author Glenius
 */
public class ListenerItemsComboBox implements ItemListener {
    
    ApplicacioBackup app;
    
    @Override
    public void itemStateChanged(ItemEvent ie) {
        this.app.clearLists();
        
        switch(this.app.comboBox.getSelectedIndex()){
            default:
            case 0:
                this.app.changeServersToFailedServers();
                break;
            case 1:
                this.app.changeServersToClientsPending();
                break;
            case 2:
                this.app.changeServersToClientsPendingToday();
                break;
            case 3:
                this.app.changeServersToClientsPendingNotToday();
                break;
            case 4:
                this.app.changeServersToClientsLaunched();
                break;
            case 5:
                this.app.changeServersToClientsLaunchedToday();
                break; 
            case 6:
                this.app.changeServersToClientsLaunchedNotToday();
                break;
            case 7:
                this.app.changeServersToClientsSolved();
                break;
            case 8:
                this.app.changeServersToClientsPossibleFalseSucceded();
                break;
            case 9:
                this.app.changeServersToClientsDisabled();
                break;
            case 10:
                this.app.changeServersToAllServers();
                break;
        }
        
    }

    public ListenerItemsComboBox(ApplicacioBackup app) {
        this.app = app;
    }
    
    
    
}
