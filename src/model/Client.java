
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
import model.ManageOfOneClient.StatusClient;
import model.NoteClient.Action;

/**
 * Class that contain information of a client
 * the class has an atribute colour: (this atribute take a colour if it`s call the function isClientStateFailed)
 * -> red is failed 3 consecutive times and is today
 * -> orange is failed 3 consecutive times and is not today
 * -> green if the status is not failed
 * -> black if is not a daily and he is in status failed today
 * -> pink if is not a daily backup and he is in status failed, but the fail is not today
 * 
 * the historic logs are the information of the schedule for every group.
 * @author Glenius
 */
public class Client implements Serializable{
    
    private String name;
    private String nameServer;
    
    private ArrayList<ReportErrors> itemsErrors;
    private ArrayList<Report> items;
    //private Color color;
    private BackupsInformationOfOneClient backupInformation;
    private static final int timeConsidererJobFailedNextDay = 10;  //hour time for to considerer a job failed to next day
    private ArrayList<Group> allGroups;
    private ArrayList<Group> groupsFaileds;
    private boolean isUpdateHistoricLogs;
    private boolean isTypeVSS;
    private boolean isUpdateVSS;
    private ArrayList<BackupsInformationOfOneSaveSet> ListSaveSetsFaileds;
    private ArrayList<NoteClient> NotesClient;
    private ManageOfOneClient manageClient;
    /**
     * constructor class
     * @param name 
     */
    
    public Client(String name) {
        this.nameServer = "";
        this.initComponents(name);    
    }
    
    public Client(String name,String nameServer) { 
        this.nameServer = nameServer;
        this.initComponents(name);    
    }
    
    private void initComponents(String name){
        this.manageClient = new ManageOfOneClient(name);
        this.ListSaveSetsFaileds = new ArrayList<BackupsInformationOfOneSaveSet>();
        this.NotesClient = new ArrayList<NoteClient>();
        this.name = name;
        this.itemsErrors = new ArrayList<ReportErrors>();
        this.items = new ArrayList<Report>();
        this.backupInformation = new BackupsInformationOfOneClient();
        this.allGroups = new ArrayList<Group>();
        this.groupsFaileds = new ArrayList<Group>();
        this.isUpdateHistoricLogs = false;
        this.isUpdateVSS = false;
    }
    
    public ManageOfOneClient getManageClient() {
        return manageClient;
    }
    
    public ArrayList<BackupsInformationOfOneSaveSet> getListSaveSetsFaileds() {
        return ListSaveSetsFaileds;
    }
    
    public ArrayList<BackupsInformationOfOneSaveSet> getListAllSaveSets() {
        return this.backupInformation.getListSaveSets();
    }
    
    public ArrayList<NoteClient> getNotesClient(){
        return this.NotesClient;
    }
    
    public void addNote(NoteClient noteClient){
        this.NotesClient.add(noteClient);
    }
    
    public void addNote(String description, String dateTime, String incidence, String nameSaveSet, String nameClient, String nameUser, Action action){
        NoteClient note = new NoteClient(description, dateTime, incidence, nameSaveSet, nameClient, nameUser, action);
        this.NotesClient.add(note);
    }
    
    public void addNote(String description, String dateTime, String incidence, String nameSaveSet, String nameClient, Action action){
        NoteClient note = new NoteClient(description, dateTime, incidence, nameSaveSet, nameClient, action);
        this.NotesClient.add(note);
    }
    
    public void deleteNote(int index){
        this.NotesClient.remove(index);
    }
    
    public String printfNotes(){
        String notes = "";
        for(NoteClient note: NotesClient){
            notes += note + "\n";
        }
        return notes;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    private void setName(String name) {
        this.name = name;
    }
    
    public void addReportError(ReportErrors report){
        itemsErrors.add(report);
    }
    
    public void addReport(Report report){
        items.add(report);
    }
    
    /**
     * return el error on the position of the errors list
     * @param number
     * @return
     * @throws Exception 
     */
    
    public ReportErrors getReportError(int number) throws Exception{
        if(number < 0 || number >= this.itemsErrors.size()){
            throw new Exception("Error: El número de reporte de error introducido es incorrecto.");
        }
        return this.itemsErrors.get(number);
    }
    
    public Report getReport(int number) throws Exception{
        if(number < 0 || number >= this.items.size()){
            throw new Exception("Error: El número de reporte de error introducido es incorrecto.");
        }
        return this.items.get(number);
    }
    
    /**
     * return the number of client errors
     * @return 
     */
    
    public int getSizeErrors(){
        return this.itemsErrors.size();
    }
    
    public int getSizeReports(){
        return this.items.size();
    }
    
    /**
     * it check if the client is in status failed and give a colour to Client.
     * A client is on failed state if the client job has failed 3 consecutives time in different days. 
     * Do not count as day if the job is not has executed.
     * The job status is not count as failed if the last day job executed is not fail
     * red is failed 3 consecutive times and is today
     * orange is failed 3 consecutive times and is not today
     * green if the status is not failed
     * black if is not a daily and he is in status failed today
     * pink if is not a daily and he is in status failed, but is not today
     * @return 
     */
    
    public boolean isClientStateFailed(){
        
        ArrayList<BackupsInformationOfOneSaveSet> listSaveSets = this.backupInformation.getListSaveSets();
        
        boolean isAnySaveSetInStateFailed = false;
        
        for(BackupsInformationOfOneSaveSet saveSetAnalizing:listSaveSets){
            if(saveSetAnalizing.isSaveSetStateFailed()){
                this.manageClient.setStatusClient(StatusClient.FAILED_TODAY);
                this.ListSaveSetsFaileds.add(saveSetAnalizing);
                isAnySaveSetInStateFailed = true;
            }
        }
        
        if(isAnySaveSetInStateFailed){
            return true;
        }
        
        int position = this.backupInformation.getFirstTimeJobIsExecuted();
        if(position == -1){
            return false;
        }
        
        if(this.backupInformation.isDailyBackup()){
            boolean isStateFailed = this.backupInformation.isFailed3ConsecutiveTimes();
            if(isStateFailed){
                if(position == 0){
                    this.manageClient.setIsDaily(true);
                    this.manageClient.setStatusClient(StatusClient.POSSIBLE_SUCCEDED_FALSE_TODAY);
                }else{
                    Job[] tableJobs = this.backupInformation.getTable();
                    int hour = tableJobs[position].getDate().getHour();
                    if(position == 1 && hour >= Client.timeConsidererJobFailedNextDay){  //hour time for to considerer a job failed to next day
                        this.manageClient.setIsDaily(true);
                        this.manageClient.setStatusClient(StatusClient.POSSIBLE_SUCCEDED_FALSE_TODAY);
                    }else{
                        this.manageClient.setIsDaily(true);
                        this.manageClient.setStatusClient(StatusClient.POSSIBLE_SUCCEDED_FALSE_NOT_TODAY);
                    }
                } 
            }else{
                this.manageClient.setStatusClient(StatusClient.SUCCESSFUL);
            }
            return isStateFailed;
        }else{
            Job[] tableJobs = this.backupInformation.getTable();
            if(tableJobs[position].getStatusJob() != Status.NOT_EXECUTED && tableJobs[position].getStatusJob() != Status.SUCCEEDED){
                if(position == 0 || position == 1){      
                    int hour = tableJobs[position].getDate().getHour();
                    if(position == 1 && hour <10){
                        this.manageClient.setIsDaily(false);
                        this.manageClient.setStatusClient(StatusClient.POSSIBLE_SUCCEDED_FALSE_NOT_TODAY);
                    }else{
                        this.manageClient.setIsDaily(false);
                      this.manageClient.setStatusClient(StatusClient.POSSIBLE_SUCCEDED_FALSE_TODAY);     
                    }
                }else{
                    this.manageClient.setIsDaily(false);
                    this.manageClient.setStatusClient(StatusClient.POSSIBLE_SUCCEDED_FALSE_NOT_TODAY);
                }
                return true;
            }else{
                this.manageClient.setStatusClient(StatusClient.SUCCESSFUL);
                return false;
            }
            
        }
    }
    
    /**
     * it checks if the job Client has failed 3 different days
     * @return 
     */
    
    public boolean isClientFault3Days(){
        int size=this.itemsErrors.size();
        String nameClient = this.name;
        if(size<3){
            return false;
        }
        Fecha date1=this.itemsErrors.get(0).getDate();
        ArrayList<Fecha> dates=new ArrayList();
        dates.add(date1);
        for(int i=1, jSize; i<size && dates.size()<3; i++){
            jSize=0;
            while(jSize<3){
                Fecha date = this.itemsErrors.get(i).getDate();
                if(dates.get(jSize).getDia() == date.getDia() 
                        &&  dates.get(jSize).getMes()== date.getMes()
                        &&  dates.get(jSize).getAny()== date.getAny()){
                    jSize = 3;
                }else{
                    ++jSize;
                    if(jSize >= dates.size()){
                        dates.add(this.itemsErrors.get(i).getDate());
                        jSize = 3;
                    }
                }
            }
        }
        if(dates.size()==3){
            return true;
        }
        return false;
    }
    
    /**
     *  this function es usefull for to detail the historic information
     *  the function is used for collect every job from log historic and save it on the correspondent group
     * For the correct use of the function, you need call it in every job recollected.
     * When you finish of to collect the jobs, then you should to call to function updateSchedule
     * @param job 
     */
    
    private void CollectInfoGroups(Job job){
        
        if(job == null){
            return;
        }
        
        if(job.getNameGroup() == " "){
            return;
        }
        
        Group group = new Group(job.getNameGroup());
        
        int i=0;
        
        while(i< this.allGroups.size()){
            
            if(this.allGroups.get(i).equals(group)){
                this.allGroups.get(i).increaseCounter();
                return;
            }else{
                ++i;
            } 
        }
        this.allGroups.add(group);
    }
    
    /**
     * update the Schedule of every group
     * @param mounthsToAnalize 
     */
    
    
    private void updateSchedule(int mounthsToAnalize){
        for(Group group:this.allGroups){
            group.calculatedAverageScheduleGroup(mounthsToAnalize);
        }
    }
    
    /**
     * update all historic information of the schedule for every group
     * @param jobs
     * @param numberMounths
     * @throws Exception 
     */
    
    protected void updateHistoricLogs(ArrayList<Job> jobs,int numberMounths) throws Exception{
        if(jobs == null){
            throw new Exception("the jobs are null");
        }
        if(jobs.size()  <= 0){
            throw new Exception("the jobs size to analyze is zero");
        }
        
        for(Job job: jobs){
            this.CollectInfoGroups(job);
        }
        
        this.updateSchedule(numberMounths);
        
        this.isUpdateHistoricLogs = true;
        
    }
    
    public String getGroupLaunched(){
        return this.manageClient.getGroupLaunched();
    }
    
    /**
     * the function analyze and return if the client is a failed client of type VSS.
     * @return
     * @throws Exception 
     */
    
    public void updateVSS() throws Exception{
        ArrayList<String> listVSS = new ArrayList<String>();
        
        String string = "VSS";
        listVSS.add(string);
        string = "SYSTEM STATE";
        listVSS.add(string);
        string = "DISASTER_RECOVERY";
        listVSS.add(string);
        string = "SYSTEM DB";
        listVSS.add(string);
        string = "WINDOWS ROLES AND FEATURES";
        listVSS.add(string);
        
        this.isUpdateVSS = true;
        String saveSet;
        int index;
        int size = this.ListSaveSetsFaileds.size();
        this.isTypeVSS = false;
        int value;
        
        if(size == 0){
            this.isTypeVSS = false;
            return;
        }
        
        for(int pos = 0; pos < size; ++pos){
            saveSet = this.ListSaveSetsFaileds.get(pos).getSaveSetName();
            index = 0;
            do{
                value = saveSet.indexOf(listVSS.get(index));
                ++index;
            }while(value == -1 && index < listVSS.size());
            
            if(value == -1){
                this.isTypeVSS = false;
                return;
            }
        }
        this.isTypeVSS = true;
    }
    
    /**
     * return if the client is a failed client of type VSS.
     * Attention: Is necessary to call to the function updateVSS() for to can use this function
     * @return
     * @throws Exception 
     */
    
    public boolean isTypeVSS() throws Exception{
        
        if(!this.isUpdateVSS){
            throw new Exception("Is necessary to call to the function updateVSS() for to can use this function");
        }
        
        return this.isTypeVSS;
    }
    
    /**
     * get to know if the logs has been collected
     * the historic logs are the information of the schedule for every group.
     * @return 
     */
    
    public boolean isUpdateHistoricLogs(){
        return this.isUpdateHistoricLogs;
    }
    
    protected void analyzeInfoBackup() throws ParseException, Exception{
        this.backupInformation.analyzeInfo(this.items);
        this.updateVSS();
    }
    
    public BackupsInformationOfOneClient getBackupsInformationOfOneClient(){
        return this.backupInformation;
    }
    
    public ArrayList<Group> getListAllGroups(){
        return this.allGroups;
    }
    
    public ArrayList<Group> getListGroupsFaileds(){
        return this.groupsFaileds;
    }
    
    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }
    
    public boolean isEnable(){
        return this.manageClient.isEnable();
    }
    
    public boolean isDisable(){
        return this.manageClient.isDisable();
    }
    
    public boolean isPending(){
        return this.manageClient.isPending();
    }
    
    public boolean isPendingToday(){
        return this.manageClient.isPendingToday();
    }
    
    public boolean isPendingNotToday(){
        return this.manageClient.isPendingNotToday();
    }
    
    public boolean isLaunched(){
        return this.manageClient.isLaunched();
    }
    
    public boolean isLaunchedToday(){
        return this.manageClient.isLaunchedToday();
    }
    
    public boolean isLaunchedNotToday(){
        return this.manageClient.isLaunchedNotToday();
    }
    
    public boolean isClientPossibleFalseSucceded(){
        return this.manageClient.isClientPossibleFalseSucceded();
    }
    
    public boolean isSolved(){
        return this.manageClient.isSolved();
    }
    
    protected void clear(Fecha dateTimeFile){
        this.backupInformation.clear();
        this.isUpdateVSS = false;
        this.itemsErrors = new ArrayList<ReportErrors>();
        this.items = new ArrayList<Report>();
        this.ListSaveSetsFaileds = new ArrayList<BackupsInformationOfOneSaveSet>();
        
        Fecha dateTimeToday = CalendarAnonymous.getInstance().getDateFechaToday();
        CompareDateTime compare = new CompareDateTime();
        if(compare.compare(dateTimeToday, dateTimeFile) == 1){
            this.manageClient.updateThisClass();
        } 
    }
    
    public void enableClient(String userName){
        this.manageClient.enableClient(userName);
        int size = this.NotesClient.size();
        for(int i=0;i<size;++i){
            if(this.NotesClient.get(i).getAction() == Action.DISABLE_CLIENT){
                this.NotesClient.remove(i);
                --i;
                size--;
            }
        }
    }
    
    @Override
    public String toString() {
        return name;
    }

}
