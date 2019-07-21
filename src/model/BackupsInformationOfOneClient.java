/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controlador.CalendarAnonymous;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import model.Job.Status;
import model.CompareTimeHour;

/**
 * Class that it contain of information of the status backups of the last ten days of the client
 * 
 */

public class BackupsInformationOfOneClient implements Serializable{
    private Job[] table;
    private static final int MAX = 10;
    private ArrayList<BackupsInformationOfOneSaveSet> ListSaveSets;
    
    /**
     * Constructor of the class
     */
    
    public BackupsInformationOfOneClient(){
        this.table = new Job[MAX];
        for(int i = 0; i<MAX; ++i){
            Job job = new Job(new Fecha());
            this.table[i] = job;
        }
        this.ListSaveSets = new ArrayList<BackupsInformationOfOneSaveSet>();
    }

    protected ArrayList<BackupsInformationOfOneSaveSet> getListSaveSets() {
        return ListSaveSets;
    }
    
    /**
     * it saves and it collects by parameter a status and the position that corresponds to a date of the last ten days.
     * The status is the status of a job of a client.
     * @param position
     * @param status 
     * @param date 
     */
    
    private void setInfoBackup(int position, SaveSet saveSet) throws Exception{
        
        if(position < 0 || position >= MAX){
            return;
        }
        
        Job job = this.table[position];
        
        if(saveSet.getStatus().ordinal()>this.table[position].getStatusJob().ordinal()){
            job.setDate(saveSet.getDate());
            job.setStatusJob(saveSet.getStatus());
        }
        
        int i=0;
        boolean notFind = true;
        while(i<this.ListSaveSets.size() && notFind){
            BackupsInformationOfOneSaveSet oldSaveSet = this.ListSaveSets.get(i);
            if(oldSaveSet.getSaveSetName().equals(saveSet.getNameSaveSet())){
                Job jobSaveSet = oldSaveSet.getTable()[position];
                //Fecha dateOldJob = jobSaveSet.getDate();
                //Fecha dateNewJob = saveSet.getDate();
                if(saveSet.getStatus() == Status.SUCCEEDED){
                    oldSaveSet.addToListItems(saveSet);
                    oldSaveSet.updateCopySuccessful(saveSet.getDate());
                }else{
                    oldSaveSet.addToListItems(saveSet);
                    oldSaveSet.addToListItemsErrors(saveSet);
                }
                
                if(saveSet.getStatus().ordinal()>jobSaveSet.getStatusJob().ordinal()){
                    jobSaveSet.setDate(saveSet.getDate());
                    jobSaveSet.setStatusJob(saveSet.getStatus());
                }
                /**
                 * 
                 * // to save the status job more severity of the day
                if(saveSet.getStatus().ordinal()>jobSaveSet.getStatusJob().ordinal()){
                    jobSaveSet.setDate(saveSet.getDate());
                    jobSaveSet.setStatusJob(saveSet.getStatus());
                }
                * 
                *  // to save the last status job of the day
                * CompareTimeHour compareTimeHour = new CompareTimeHour();
                * if(compareTimeHour.compare(dateNewJob, dateOldJob) == 1){
                    jobSaveSet.setDate(saveSet.getDate());
                    jobSaveSet.setStatusJob(saveSet.getStatus());
                }
                **/
                notFind = false;
            }
            ++i;
        }
        
        if(notFind == true){
            BackupsInformationOfOneSaveSet newSaveSet = new BackupsInformationOfOneSaveSet(saveSet.getNameSaveSet());
            if(saveSet.getStatus() == Status.SUCCEEDED){
                newSaveSet.updateCopySuccessful(saveSet.getDate());
                newSaveSet.addToListItems(saveSet);
            }else{
                newSaveSet.addToListItems(saveSet);
                newSaveSet.addToListItemsErrors(saveSet);
            }
            this.ListSaveSets.add(newSaveSet);
            Job jobSaveSet = newSaveSet.getTable()[position];
            jobSaveSet.setDate(saveSet.getDate());
            jobSaveSet.setStatusJob(saveSet.getStatus());
        }
        
    }
    
    
    /**
     * return the jobs of last ten days of the backup of the client
     * @return 
     */
    
    public Job[] getTable(){
        return this.table;
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
    
    
    /**
     * it save and it collect by parameter a status and the date of this status.
     * The status is the status of a job of a client. 
     * @param saveSet
     * @throws java.text.ParseException
     */
    
    public void setInfoBackup(SaveSet saveSet) throws ParseException, Exception{
        CalendarAnonymous calendar = CalendarAnonymous.getInstance();
        int position = calendar.conversionToInt(saveSet.getDate());
        this.setInfoBackup(position, saveSet);
    }
    
    /**
     * save it and analyze the dates that the client has failed.
     * @param itemsErrors 
     */

    void analyzeInfo(ArrayList<Report> items) throws ParseException, Exception {
        for(Report report: items){
            Fecha date = report.getDate();
            
            SaveSet saveSet = new SaveSet(report.getSaveSetName(), date, report.getStatus());
            this.setInfoBackup(saveSet);
        }
    }
    
    protected void clear(){
        this.table = new Job[MAX];
        for(int i = 0; i<MAX; ++i){
            Job job = new Job(new Fecha());
            this.table[i] = job;
        }
        for(BackupsInformationOfOneSaveSet saveSet:this.ListSaveSets){
            saveSet.clear();
        }
    }
    
}
