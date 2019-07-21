/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Glenius
 */
public class NoteClient implements Serializable{
    private String description;
    private String dateTime;
    private String incidence;
    private String nameSaveSet;
    private String nameClient;
    private String nameUser;
    public enum Action { COMMENT, ENABLE_CLIENT, DISABLE_CLIENT, ENABLE_SAVE_SET, DISABLE_SAVE_SET, OPEN_INCIDENT, CLOSE_INCIDENT, UNKNOWN }
    private Action action;

    public NoteClient(String description, String dateTime, String incidence, String nameSaveSet, String nameClient, String nameUser, Action action) {
        this.description = description;
        this.dateTime = dateTime;
        this.incidence = incidence;
        this.nameSaveSet = nameSaveSet;
        this.nameClient = nameClient;
        this.nameUser = nameUser;
        this.action = action;
    }

    public NoteClient(String description, String dateTime, String incidence, String nameSaveSet, String nameClient, Action action) {
        this.description = description;
        this.dateTime = dateTime;
        this.incidence = incidence;
        this.nameSaveSet = nameSaveSet;
        this.nameClient = nameClient;
        this.nameUser = "unknown";
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    
    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public String getNameSaveSet() {
        return nameSaveSet;
    }

    public void setNameSaveSet(String nameSaveSet) {
        this.nameSaveSet = nameSaveSet;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    @Override
    public String toString() {
        String string;
        String stringPrimary = "User: " + nameUser + "\t" + 
                "\nIncidence: " + incidence + "\t date time: " + dateTime +
                "\ndescription:\n" + description
                +"\n----------------------------------------------------------------------------------";
        String stringSecond = "User: " + nameUser + "\n Save set: " + nameSaveSet + 
                "\nIncidence: " + incidence + "\t date time: " + dateTime +
                "\ndescription:\n" + description
                +"\n----------------------------------------------------------------------------------";
        switch(this.action){
            case CLOSE_INCIDENT:
            case COMMENT:
            case OPEN_INCIDENT:
            case DISABLE_SAVE_SET:
            case ENABLE_SAVE_SET:
                string =  this.action.toString() + "--> " + stringSecond;
                break;
            case DISABLE_CLIENT:
            case ENABLE_CLIENT:
                string = this.action.toString() + "--> " + stringPrimary;
                break;
            case UNKNOWN:
                default:
                    string = "comment error";
                    break;
        }
        return string;
    }
    
}
