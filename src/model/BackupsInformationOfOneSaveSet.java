/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import model.Job.Status;
import model.ManageOfOneClient.StatusClient;

/**
 *
 * @author Glenius
 */
public class BackupsInformationOfOneSaveSet implements Serializable{
    private Status status;
    private boolean isAnalyzed;
    private String saveSetName;
    private Job[] table;
    private static final int MAX = 10;
    private static final int timeConsidererJobFailedNextDay = 10;  //hour time for to considerer a job failed to next day
    //private Color color;
    private ArrayList<SaveSet> itemsErrors;
    private ArrayList<SaveSet> items;
    private boolean isEnabled;
    private boolean isDaily;
    private StatusClient statusSaveSet;
    private Fecha lastCopySuccessful;
    
    public BackupsInformationOfOneSaveSet(String saveSetName){
        this.table = new Job[MAX];
        for(int i = 0; i<MAX; ++i){
            Job job = new Job(new Fecha());
            this.table[i] = job;
        }
        this.status = this.status.NOT_EXECUTED;
        this.isAnalyzed = false;
        this.saveSetName = saveSetName;
        //this.color = Color.white;
        this.items = new ArrayList<SaveSet>();
        this.itemsErrors = new ArrayList<SaveSet>();
        this.isEnabled = true;
        this.isDaily = true;
        this.statusSaveSet = StatusClient.UNKNOWN;
        this.lastCopySuccessful = new Fecha();
    }

    public Fecha getLastCopySuccessful() {
        return lastCopySuccessful;
    }

    public void updateCopySuccessful(Fecha copySuccessful){
        if(this.lastCopySuccessful.getAny() == 0){
            this.setLastCopySuccessful(copySuccessful);
        }
        CompareEverythingDateTime compare = new CompareEverythingDateTime();
        if(compare.compare(copySuccessful, this.lastCopySuccessful) == 1){
            this.setLastCopySuccessful(copySuccessful);
        }
    }
    
    private void setLastCopySuccessful(Fecha lastCopySuccessful) {
        this.lastCopySuccessful = lastCopySuccessful;
    }
    
    public boolean isDaily(){
        return this.isDaily;
    }
    
    public StatusClient getStatusSaveSetToClient(){
        return this.statusSaveSet;
    }
    
    public String getSaveSetName() {
        return saveSetName;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public boolean isEnabled(){
        return this.isEnabled;
    }
    
    public void setEnable(boolean isEnabled){
        this.isEnabled = isEnabled;
    }
    
    public Job[] getTable() {
        return table;
    }

    protected void setTable(Job[] table) {
        this.table = table;
    }
    
    public Job getJob(int position){
        return this.table[position];
    }
    
    public void setJob(int position, Job job){
        this.table[position] = job;
    }
    
    public ArrayList<SaveSet> getListItems(){
        return this.items;
    }
    
    protected void addToListItems(SaveSet saveSet){
        this.items.add(saveSet);
    }
    
    public ArrayList<SaveSet> getListItemsErrors(){
        return this.itemsErrors;
    }
    
    protected void addToListItemsErrors(SaveSet saveSet){
        this.itemsErrors.add(saveSet);
    }
    
    public SaveSet getItem(int position) throws Exception{
        if(position < 0 || position >= this.items.size()){
            throw new Exception("there is not this position on the list");
        }
        return this.items.get(position);
    }
    
    public SaveSet getItemError(int position) throws Exception{
        if(position < 0 || position >= this.itemsErrors.size()){
            throw new Exception("there is not this position on the list");
        }
        return this.itemsErrors.get(position);
    }
    
    public int getSizeItems(){
        return this.items.size();
    }
    
    public int getSizeItemsErrors(){
        return this.itemsErrors.size();
    }
    
    /**
     * it check if the saveset is in status failed and give a colour to saveset.
     * A client is on failed state if the saveset job has failed 3 consecutives time in different days. 
     * Do not count as day if the job is not has executed.
     * The job status is not count as failed if the last day job executed is not fail
     * red is failed 3 consecutive times and is today
     * orange is failed 3 consecutive times and is not today
     * green if the status is not failed
     * black if is not a daily and he is in status failed today
     * pink if is not a daily and he is in status failed, but is not today
     * @return 
     */
    
    public boolean isSaveSetStateFailed(){
        
        this.isAnalyzed = true;
        
        int position = this.getFirstTimeJobIsExecuted();
        if(position == -1){
            return false;
        }
        
        if(this.isDailyBackup()){
            boolean isStateFailed = this.isFailed3ConsecutiveTimes();
            if(isStateFailed){
                if(position == 0){
                    this.statusSaveSet = StatusClient.FAILED_TODAY;
                    this.isDaily = true;
                }else{
                    Job[] tableJobs = this.getTable();
                    int hour = tableJobs[position].getDate().getHour();
                    if(position == 1 && hour >= this.timeConsidererJobFailedNextDay){  //hour time for to considerer a job failed to next day
                        this.statusSaveSet = StatusClient.FAILED_TODAY;
                        this.isDaily = true;
                    }else{
                        this.statusSaveSet = StatusClient.FAILED_NOT_TODAY;
                        this.isDaily = true;
                    }
                } 
            }else{
                this.statusSaveSet = StatusClient.SUCCESSFUL;
            }
            return isStateFailed;
        }else{
            Job[] tableJobs = this.getTable();
            if(tableJobs[position].getStatusJob() != Status.NOT_EXECUTED && tableJobs[position].getStatusJob() != Status.SUCCEEDED){
                if(position == 0 || position == 1){      
                    int hour = tableJobs[position].getDate().getHour();
                    if(position == 1 && hour <10){
                        this.statusSaveSet = StatusClient.FAILED_NOT_TODAY;
                        this.isDaily = false;
                    }else{
                      this.statusSaveSet = StatusClient.FAILED_TODAY;
                        this.isDaily = false;;      
                    }
                }else{
                    this.statusSaveSet = StatusClient.FAILED_NOT_TODAY;
                    this.isDaily = false;;
                }
                return true;
            }else{
                this.statusSaveSet = StatusClient.SUCCESSFUL;
                return false;
            }
            
        }
    
    }
    
    
    
    
    /**
     * it check if the client has failed three consecutive times
     * @return 
     */
    
    public boolean isFailed3ConsecutiveTimes(){
        int position = 0;
        int countConsecutivesFaileds = 0;    
        /*
        we advance to the first time that the job is executed
        */     
        while(this.table[position].getStatusJob() == Status.NOT_EXECUTED && position < MAX){
            ++position;
        }
        /*
        we check if the job has failed 3 consecutive times
        */
        while(position < MAX){
            if(this.table[position].getStatusJob() != Status.NOT_EXECUTED){
                if(this.table[position].getStatusJob() != Status.SUCCEEDED){
                    ++countConsecutivesFaileds;
                }else{
                    return false;
                } 
                
                if(countConsecutivesFaileds >= 3){
                    return true;
                } 
            }   
            ++position;
        }
        return false;
    }
    
    public boolean isAnalyzedSaveSets(){
        return this.isAnalyzed;
    }
    
    /**
     * return the position correspondent to la first time that the job is executed.
     * If the job is not executed en last 10 days, then the function return -1
     * @return 
     */
    
    protected int getFirstTimeJobIsExecuted(){
        int position = 0;
        boolean isRunning = true;
        
        while(isRunning){
            
            if(position < MAX){
                if(this.table[position].getStatusJob() == Status.NOT_EXECUTED){
                    ++position;
                }else{
                    isRunning = false;
                }     
            }else{
                isRunning = false;
            }
            
        }
                
        
        if(position == MAX){
            return -1;
        }
        
        return position;
    }
    
    /**
     * check if is a daily backup
     * @return 
     */
    
    public boolean isDailyBackup(){
        
        for(int i=0;i<8;++i){
            if(this.table[i].getStatusJob() != Status.NOT_EXECUTED){
               if(this.table[i+1].getStatusJob() != Status.NOT_EXECUTED){
                   if(this.table[i+2].getStatusJob() != Status.NOT_EXECUTED){
                   return true;
               }
               }
            }
        }
        
        for(int i=0;i<9;++i){
            if(this.table[i].getStatusJob() == Status.NOT_EXECUTED){
               if(this.table[i+1].getStatusJob() == Status.NOT_EXECUTED){
                   return false;
               }
            }
        }
        return true;
    }
    
    protected void clear(){
        this.table = new Job[MAX];
        for(int i = 0; i<MAX; ++i){
            Job job = new Job(new Fecha());
            this.table[i] = job;
        }
        this.isAnalyzed = false;
        this.items = new ArrayList<SaveSet>();
        this.itemsErrors = new ArrayList<SaveSet>();
        
        updateStatus();
    }
    
    private void updateStatus(){
        this.status = this.status.NOT_EXECUTED;
    }
    
}
