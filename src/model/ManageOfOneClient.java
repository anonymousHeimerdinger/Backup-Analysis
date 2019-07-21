/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controlador.CalendarAnonymous;
import java.io.Serializable;

/**
 *
 * @author Glenius
 */
public class ManageOfOneClient implements Serializable{
    public enum StatusClient {SUCCESSFUL, POSSIBLE_SUCCEDED_FALSE_TODAY, POSSIBLE_SUCCEDED_FALSE_NOT_TODAY, REPORTED_SUCCEDED_FALSE, FAILED_TODAY,  FAILED_NOT_TODAY, UNKNOWN};
    public enum StatusMarked {  SUCCESSFUL, SOLVED, READED, INCIDENT, LAUNCH_TODAY, LAUNCH_NOT_TODAY,  PENDING_TODAY, PENDING_NOT_TODAY, UNMARKED, UNKNOWN, DISABLED}
    private StatusClient statusClient;
    private StatusMarked statusMarked;
    private String numberIncidence;
    private Fecha dateTimePending;
    private Fecha dateTimeLaunch;
    private Fecha dateTimeDisabled;
    private Fecha dateTimeFailed;
    private boolean isEnabled;
    private String nameUserDisabledClient;
    private String nameUserEnabledClient;
    private String nameGroupLaunched;
    private String nameClient;
    private String numberIncidentOld;
    private boolean isDaily;

    public ManageOfOneClient(String nameClient) {
        this.numberIncidence = " ";
        this.statusClient = StatusClient.UNKNOWN;
        this.statusMarked = StatusMarked.UNMARKED;
        this.dateTimeDisabled = new Fecha();
        this.dateTimeLaunch = new Fecha();
        this.dateTimePending = new Fecha();
        this.dateTimeFailed = new Fecha();
        this.isEnabled = true;
        this.nameGroupLaunched = " ";
        this.nameUserDisabledClient = " ";
        this.nameClient = nameClient;
        this.numberIncidentOld = " ";
        this.isDaily = true;
    }

    public boolean isDaily() {
        return isDaily;
    }
    
    public String getGroupLaunched(){
        return this.nameGroupLaunched;
    }

    public void setIsDaily(boolean isDaily) {
        this.isDaily = isDaily;
    }
    
    public StatusClient getStatusClient(){
        return this.statusClient;
    }
    
    public StatusMarked getStatusMarked(){
        return this.statusMarked;
    }
    
    public Fecha getDateTimePending() {
        return dateTimePending;
    }

    public Fecha getDateTimeLaunch() {
        return dateTimeLaunch;
    }

    public Fecha getDateTimeDisabled() {
        return dateTimeDisabled;
    }
    
    public void markedAsPending(){
        this.dateTimePending = CalendarAnonymous.getInstance().getDateFechaToday();
        this.setStatusMarked(StatusMarked.PENDING_TODAY);
    }
    
    public void launchGroup(String nameGroupLaunched){
        this.nameGroupLaunched = nameGroupLaunched;
        this.dateTimeLaunch = CalendarAnonymous.getInstance().getDateFechaToday();
        this.setStatusMarked(StatusMarked.LAUNCH_TODAY);
    }
    
    public String getIncidence(){
        return this.numberIncidence;
    }
    
    public void setIncidence(String incidence){
        this.numberIncidence = incidence;
        this.setStatusMarked(StatusMarked.INCIDENT);
    }
    
    public void disableClient(String nameUserDisabledClient){
        this.isEnabled = false;
        this.nameUserDisabledClient = nameUserDisabledClient;
        this.dateTimeDisabled = CalendarAnonymous.getInstance().getDateFechaToday();
        this.statusMarked = StatusMarked.DISABLED;
    }
    
    public void enableClient(String nameUserEnabledClient){
        this.isEnabled = true;
        this.nameUserEnabledClient = nameUserEnabledClient;  
        this.statusMarked = StatusMarked.UNMARKED;
    }
    
    public boolean isEnable(){
        return this.isEnabled;
    }
    
    public boolean isDisable(){
        return !this.isEnabled;
    }
    
    public boolean isPending(){
        if(this.statusMarked == StatusMarked.PENDING_TODAY || this.statusMarked == statusMarked.PENDING_NOT_TODAY){
            return true;
        }
        return false;
    }
    
    public boolean isPendingToday(){
        if(this.statusMarked == StatusMarked.PENDING_TODAY){
            return true;
        }
        return false;
    }
    
    public boolean isPendingNotToday(){
        if(this.statusMarked == statusMarked.PENDING_NOT_TODAY){
            return true;
        }
        return false;
    }
    
    public boolean isLaunched(){
        if(this.statusMarked == StatusMarked.LAUNCH_TODAY || this.statusMarked == statusMarked.LAUNCH_NOT_TODAY){
            return true;
        }
        return false;
    }
    
    public boolean isLaunchedToday(){
        if(this.statusMarked == StatusMarked.LAUNCH_TODAY){
            return true;
        }
        return false;
    }
    
    public boolean isLaunchedNotToday(){
        if(this.statusMarked == statusMarked.LAUNCH_NOT_TODAY){
            return true;
        }
        return false;
    }
    
    public boolean isSolved(){
        if(this.statusMarked == StatusMarked.SOLVED){
            return true;
        }
        return false;
    }
    
    public boolean isClientPossibleFalseSucceded(){
        if(this.statusClient == StatusClient.POSSIBLE_SUCCEDED_FALSE_NOT_TODAY || this.statusClient == StatusClient.POSSIBLE_SUCCEDED_FALSE_TODAY ||
                this.statusClient == StatusClient.REPORTED_SUCCEDED_FALSE){
            return true;
        }
        return false;
    }
    
    public String getNameUserDisabledClient(){
        return this.nameUserDisabledClient;
    }
    
    public String getNameUserEnabledClient(){
        return this.nameUserEnabledClient;
    }
    
    public String getIncidentSolved() throws Exception{
        if(this.statusMarked == statusMarked.SOLVED){
            return this.numberIncidentOld;
        }
        throw new Exception("An error ocurred. The job of the client " + this.nameClient + " is not solved");
    }
    
    public void setStatusClient(StatusClient statusClientReceived){
        switch(statusClientReceived){
            case SUCCESSFUL:
                if(this.statusClient == StatusClient.FAILED_NOT_TODAY || this.statusClient == StatusClient.FAILED_TODAY){
                    if(this.statusMarked == StatusMarked.LAUNCH_NOT_TODAY || this.statusMarked == StatusMarked.LAUNCH_TODAY){
                        this.nameGroupLaunched += " (" + CalendarAnonymous.getInstance().getDateFechaToday()
                            + " the job of the client " + this.nameClient +  " is correct)";
                    }
                    this.numberIncidentOld = this.numberIncidence.toString();
                    this.numberIncidence = " ";
                    this.statusMarked = statusMarked.SOLVED;
                }else{
                    if(this.statusMarked == statusMarked.READED){
                        this.statusMarked = StatusMarked.SUCCESSFUL;
                        this.nameGroupLaunched = " ";
                    }
                    if(this.statusMarked == statusMarked.SOLVED){
                        this.statusMarked = StatusMarked.SUCCESSFUL;
                        this.nameGroupLaunched = " ";
                        this.numberIncidentOld = " ";
                    }
                }
                this.changeStatusClient(statusClientReceived);
                break;
            case POSSIBLE_SUCCEDED_FALSE_TODAY:
                this.changeStatusClient(statusClientReceived);
                break;
            case POSSIBLE_SUCCEDED_FALSE_NOT_TODAY:
                this.changeStatusClient(statusClientReceived);
                break;
            case REPORTED_SUCCEDED_FALSE:
                if(this.statusClient == StatusClient.SUCCESSFUL){
                    this.changeStatusClient(statusClientReceived);
                }
                break;
            case FAILED_TODAY:
                if(this.statusClient != StatusClient.FAILED_NOT_TODAY){
                    this.dateTimeFailed = CalendarAnonymous.getInstance().getDateFechaToday();
                    this.changeStatusClient(statusClientReceived);
                }
                
            break;
            case FAILED_NOT_TODAY:
                this.changeStatusClient(statusClientReceived);
                break;
            case UNKNOWN:
                this.changeStatusClient(statusClientReceived);
                break;
            default:
                this.changeStatusClient(StatusClient.UNKNOWN);
                break;
        }
    }
    
    public void setStatusMarked(StatusMarked statusMarkedReceived){
        switch(statusMarkedReceived){
            case SUCCESSFUL:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case SOLVED:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case READED:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case INCIDENT:
                this.setStatusClient(StatusClient.REPORTED_SUCCEDED_FALSE);
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case LAUNCH_TODAY:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case LAUNCH_NOT_TODAY:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case PENDING_TODAY:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case PENDING_NOT_TODAY:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case UNMARKED:
                if(this.statusMarked == StatusMarked.DISABLED){
                    break;
                }
                if(this.statusMarked == StatusMarked.LAUNCH_TODAY){
                    this.nameGroupLaunched = " ";
                }else{
                    if(this.statusMarked == StatusMarked.INCIDENT){
                        this.setIncidence(" ");
                    }
                }
                this.changeStatusMarked(statusMarkedReceived);
                break;
            case DISABLED:
                this.changeStatusMarked(statusMarkedReceived);
                break;
            default:
                this.changeStatusMarked(StatusMarked.UNKNOWN);
                break;
        }
    }
    
    private void changeStatusClient(StatusClient statusClientReceived){
        this.statusClient = statusClientReceived;
    }
    
    private void changeStatusMarked(StatusMarked statusMarkedReceived){
        this.statusMarked = statusMarkedReceived;
    }
    
    protected void updateThisClass(){
        Fecha dateTimeToday = CalendarAnonymous.getInstance().getDateFechaToday();
        
        if(statusClient == StatusClient.FAILED_TODAY){
            if(!this.dateTimeFailed.equals(dateTimeToday)){
                this.statusClient = StatusClient.FAILED_NOT_TODAY;
            }
        }
        
        if(this.statusMarked == StatusMarked.LAUNCH_TODAY){
            if(!this.dateTimeLaunch.equals(dateTimeToday)){
                this.statusMarked = StatusMarked.LAUNCH_NOT_TODAY;
            }
            return;
        }
        if(statusMarked == StatusMarked.PENDING_TODAY){
            if(!this.dateTimePending.equals(dateTimeToday)){
                this.statusMarked = StatusMarked.PENDING_NOT_TODAY;
            }
            return;
        }
        if(statusMarked == StatusMarked.READED){
            this.statusMarked = StatusMarked.UNMARKED;
            return;
        }
    }
    
}
