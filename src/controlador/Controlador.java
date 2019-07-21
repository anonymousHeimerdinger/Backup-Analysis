/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BackupsInformationOfOneSaveSet;
import model.Client;
import model.DataManager;
import model.Fecha;
import model.Server;

/**
 *
 * @author ggilavert
 */

public class Controlador {
    
    LectorExcel readerFiles;
    DataManager dades;
    Calendar fecha;
    ToAnalizeClientsVSS analyzeVSS;
    private boolean isAnalyzeDone;
    private String ruteDataBBDD;
    private String ruteData;
    
    
    public Controlador() {
        this.dades = new DataManager();
        this.readerFiles = LectorExcel.getInstance();
        this.fecha = new GregorianCalendar();
        this.analyzeVSS = ToAnalizeClientsVSS.getInstance();
        this.isAnalyzeDone = false;
        this.ruteData = "database/fileRute.bbdd";
        this.ruteDataBBDD = " ";
    }
    
    public void collectingDataAllJobs(File fichero) throws Exception{
        String nameServer = fichero.getName();
        int position = nameServer.indexOf(".");
        nameServer = nameServer.substring(0, position);
        
        ArrayList<Server> servers = this.dades.getServers();
        int size = servers.size();
        int pos= 0;
        boolean isThereIs = false;
        
        while(pos<size && !isThereIs){
            Server server = servers.get(pos);
            
            if(server.getNameServer().equals(nameServer)){
                isThereIs = true;
                this.readerFiles.toReadAllJobs(fichero, server.getClients());
            }
            ++pos;
        }
        
        if(!isThereIs){
            this.dades.addServer(nameServer);
            int sizeN = servers.size();
            this.readerFiles.toReadAllJobs(fichero,servers.get(sizeN-1).getClients());
        }
    }
    
    public void exportReport(int pos) throws Exception{ 
        Server server = this.dades.getServerWhereClientsFaileds3ConsecutiveTimes().get(pos);
        String nameServer = server.getNameServer();
        ControlDateJobs controlDateJobs = ControlDateJobs.getInstance();
        Fecha date = controlDateJobs.getDateLastDayJobsExecuted();
        String day = String.valueOf(date.getDia());
        String year = String.valueOf(date.getAny());
        int monthInt = date.getMes();
        String month;
        
        if(Integer.parseInt(day) < 10){
            day = "0" + day;
        }
        
        month = String.valueOf(monthInt);
        
        if(monthInt < 10){
            month = "0" + month;
        }
        
        nameServer += "_" + day + month + year; 
        nameServer +=  ".csv";
        this.readerFiles.toOverwrite(server.getClients(), nameServer);
    }
    
    
    /**
     * return all servers with all clients
     * @return 
     */
    
    public ArrayList<Server> getServers(){
        return this.dades.getServers();
    }
    
    /**
     * This function init, analyze and update if the clients has failed the last 3 jobs consecutive time and analyze if are of type VSS.
     * @return 
     * @throws java.lang.Exception 
     */
    
    public void initAnalyzeFaileds() throws Exception{
        this.dades.initAnalizeFaileds3ConsecutiveTimes();
        ArrayList<Server> servers = this.dades.getServerWhereClientsFaileds3ConsecutiveTimes();         
        this.analyzeVSS.getTextInformationFailedTypeVSS(servers);
        this.isAnalyzeDone = true;
    }
    
    
    /**
     * return clients in state failed ordered for each server
     * @throws Exception 
     */
    
    public  ArrayList<Server> getServerWhereClientsFaileds3ConsecutiveTimes() throws Exception {
        
        if(!this.isAnalyzeDone){
            throw new Exception("error: The analysis still has not made");
        }
        
        return this.dades.getServerWhereClientsFaileds3ConsecutiveTimes();
    }
    
    /**
     * this function return the clients in state failed and they are of type VSS. 
     * Attention: this function required to be used after of to call one time to the function 
     * initAnalyzeFaileds() 
     * @return
     * @throws Exception 
     */
    
    public ArrayList<String> getTextClientsVSS() throws Exception{
        
        if(!this.isAnalyzeDone){
            throw new Exception("error: The analysis still has not made");
        }
        
        return this.analyzeVSS.getTextInformationFailedTypeVSS();
    }
    
    public Fecha getDateFilesAnalyzeds(){
        ControlDateJobs controlDateJobs = ControlDateJobs.getInstance();
        Fecha dateTimeJobsExecuted = controlDateJobs.getDateLastDayJobsExecuted();
        this.dades.setDateLastDayJobsExecuted(dateTimeJobsExecuted);
        return dateTimeJobsExecuted;
    }

    public ArrayList<Server> getServerListVSS() throws Exception {
        return this.analyzeVSS.getServerListVSS();
    }

    public boolean isInitVSS() {
        return this.analyzeVSS.isInitVSS();
    }
    
    
    public ArrayList<Server> getServersWithClientsPending(){
        return this.dades.getServersWithClientsPending();
    }
    
    public ArrayList<Server> getServersWithClientsPendingToday(){
        return this.dades.getServersWithClientsPendingToday();
    }
    
    public ArrayList<Server> getServersWithClientsPendingNotToday(){
        return this.dades.getServersWithClientsPendingNotToday();
    }
    
    public ArrayList<Server> getServersWithClientsLaunched(){
        return this.dades.getServersWithClientsLaunched();
    }
    
    public ArrayList<Server> getServersWithClientsLaunchedToday(){
        return this.dades.getServersWithClientsLaunchedToday();
    }
    
    public ArrayList<Server> getServersWithClientsLaunchedNotToday(){
        return this.dades.getServersWithClientsLaunchedNotToday();
    }
    
    public ArrayList<Server> getServersWithClientsDisabled(){
        return this.dades.getServersWithClientsDisabled();
    }
    
    public ArrayList<Server> getServersWithClientsPossibleFalseSucceded(){
        return this.dades.getServersWithClientsPossibleFalseSucceded();
    }
    
    public ArrayList<Server> getServersWithClientsSolved(){
        return this.dades.getServersWithClientsSolved();
    }

    public String getRuteDataBBDD() {
        return ruteDataBBDD;
    }
    
    public void setRuteDataBBDD(String ruteDataBBDD) {
        this.ruteDataBBDD = ruteDataBBDD;
    }
    
    /**
     * return the clients that contain the name client pass by parameter
     * @param nameClient
     * @return 
     */
    
    public ArrayList<Client> getClients(String nameClient){
        return this.dades.getClients(nameClient);
    }
    
    public void addNameServerForEachClient(){
        this.dades.addNameServerForEachClient();
    }
    

    public Date ConversionToDate(int i, Fecha dateFilesAnalyzeds) throws ParseException {
        return CalendarAnonymous.conversionToDate(i, dateFilesAnalyzeds);
    }
    
    public void saveDates() throws Exception{
        SaveDates.getInstance().guardarDadesDisc(this.dades.getServers(), this.ruteDataBBDD);
    }
    
    public void loadDates() throws FileNotFoundException, IOException, ClassNotFoundException{
        ArrayList<Server> servers = (ArrayList<Server>) LoadDates.getInstance().carregarDadesDisc(this.ruteDataBBDD);
        this.dades = new DataManager(servers);
    }
    
    public void loadRuteData() throws FileNotFoundException, IOException, ClassNotFoundException{
        this.ruteDataBBDD = (String) LoadDates.getInstance().carregarDadesDisc(this.ruteData);
    }
    
    public void saveRuteData() throws Exception{
        SaveDates.getInstance().guardarDadesDisc(this.ruteDataBBDD, this.ruteData);
    }
    
    public void saveDates(String path) throws Exception{
        SaveDates.getInstance().guardarDadesDisc(this.dades.getServers(), path);
    }
    
}
