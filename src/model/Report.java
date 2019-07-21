/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controlador.ControlDateJobs;
import java.io.Serializable;
import model.Job.Status;

/**
 *
 * @author Glenius
 */
public class Report implements Serializable{
    private String saveSetName;
    private String groupStartTime;
    private String saveType;
    private String level;
    private Fecha date;
    private Status status;

    public Report(String saveSetName, String groupStartTime, String saveType, String level, Status status) throws Exception{
        this.saveSetName = saveSetName;
        this.groupStartTime = groupStartTime;
        this.saveType = saveType;
        
        this.date = ControlDateJobs.collectDateAndHourFromGroupStartTime(groupStartTime);
        this.level = level;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSaveSetName() {
        return this.saveSetName;
    }

    private void setSaveSetName(String saveSetName) {
        this.saveSetName = saveSetName;
    }

    public String getGroupStartTime() {
        return groupStartTime;
    }

    private void setGroupStartTime(String groupStartTime) {
        this.groupStartTime = groupStartTime;
    }

    public String getSaveType() {
        return saveType;
    }

    private void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getLevel() {
        return level;
    }

    private void setLevel(String level) {
        this.level = level;
    }
    
    public Fecha getDate() {
        return date;
    }

    public void setDate(Fecha date) {
        this.date = date;
    }
    
    
    @Override
    public String toString() {
        return saveSetName + "," + groupStartTime + "," + saveType + "," + level + "," + status;
    }
}
