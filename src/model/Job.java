/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *The class job save the date and the status of the job 
 * @author Glenius
 */
public class Job implements Serializable{
    public enum Status {NOT_EXECUTED, SUCCEEDED, UNEXPECTEDLY_EXITED, INCOMPLETE, ABANDONED, FAILED};
    
    private Fecha date;
    private Status statusJob;
    private String nameGroup;
    private String nameClient;

    /**
     * Class constructor
     * @param date
     * @param statusJob 
     */
    
    public Job(Fecha date) {
        this.date = date;
        this.statusJob = Status.NOT_EXECUTED;
        this.nameClient = " ";
        this.nameGroup = " ";
    }
    
    
    public Job(Fecha date, Status statusJob,String nameGroup){
        this.date = date;
        this.statusJob = statusJob;
        this.nameClient = " ";
        this.nameGroup = nameGroup;
    }
    
    public Job(Fecha date, Status statusJob, String nameClient,String nameGroup){
        this.date = date;
        this.statusJob = statusJob;
        this.nameClient = nameClient;
        this.nameGroup = nameGroup;
    }
    
    
    
    
    
    /**
     * return the date of the job
     * @return 
     */

    public Fecha getDate() {
        return date;
    }

    /**
     * save the date collected by parameter
     * @param date 
     */
    
    public void setDate(Fecha date) {
        this.date = date;
    }
    
    /**
     * return the status of the job
     * @return 
     */

    public Status getStatusJob() {
        return statusJob;
    }
    
    /**
     * save the job status
     * @param statusJob 
     */

    public void setStatusJob(Status statusJob) {
        this.statusJob = statusJob;
    }
    
    /**
     * return the client name
     * @return 
     */
    
    public String getNameClient(){
        return this.nameClient;
    }
    
    /**
     * return the group name
     * @return 
     */
    
    public String getNameGroup(){
        return this.nameGroup;
    }
    
}
