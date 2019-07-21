/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import model.Job.Status;

/**
 *
 * @author Glenius
 */
public class SaveSet implements Serializable{
    private String nameSaveSet;
    private Fecha date;
    private Status status;

    public SaveSet(String saveSet, Fecha date,Status status) {
        this.nameSaveSet = saveSet;
        this.date = date;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNameSaveSet() {
        return nameSaveSet;
    }

    public void setNameSaveSet(String saveSet) {
        this.nameSaveSet = nameSaveSet;
    }

    public Fecha getDate() {
        return date;
    }

    public void setDate(Fecha date) {
        this.date = date;
    }
    
    @Override
    public boolean equals(Object object){
        if(object instanceof SaveSet){
            SaveSet saveSet = (SaveSet) object;
            
            if(this.getNameSaveSet().equals(saveSet.getNameSaveSet())){
                return true;
            }else{
                return false;
            }  
        }else{
            return false;
        }
    }
}
