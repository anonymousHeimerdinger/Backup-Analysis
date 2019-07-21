/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import model.Client;
import model.Fecha;
import model.Group;
import model.Job;
import model.Job.Status;
import model.ReportErrors;
import model.Report;

/**
 * Utility Class Singleton to read and to write a file
 * @author Glenius
 */
public class LectorExcel {
    private static LectorExcel instance = new LectorExcel();
 
    public static LectorExcel getInstance() {
        if(instance == null){
            instance = new LectorExcel();
        }
        return instance;
    }
    private LectorExcel() {  
    }
    
    /**
     * extracts the information of an excel file and returns the information of the clients
     * @param fichero
     * @return
     * @throws Exception 
     */
    
    public ArrayList<Client> toReadJobsFaileds(File fichero) throws Exception{
        
        ControlDateJobs controlDateJobs = ControlDateJobs.getInstance();
        
        ArrayList<Client> clients = new ArrayList();
        int i;
        boolean isNewClient;
        String clientName = null;
        String saveSetName = null;
        String groupStartTime = null;
        String saveType = null;
        String level = null;
        
        try{
            Scanner sc=new Scanner(fichero);
            String line=sc.nextLine();
            while(line.indexOf("Client Name,")==-1){
                line=sc.nextLine();
            }
            while(sc.hasNextLine()){
                line=sc.nextLine();
                
                i=line.indexOf(",");
                clientName=line.substring(0, i);
                line=line.substring(i+1);    

                i=line.indexOf(",");
                saveSetName=line.substring(0, i);
                line=line.substring(i+2);

                i=line.indexOf(",");
                groupStartTime =line.substring(0, i);
                line=line.substring(i+1);

                i=line.indexOf(",");
                saveType=line.substring(0, i);
                line=line.substring(i+1);

                i=line.indexOf(",");
                level =line.substring(0, i);

                isNewClient = true;
                
                i=0;
                
                while(i < clients.size() && isNewClient){
                    if(clients.get(i).getName().equals(clientName)){
                        ReportErrors reportError = new ReportErrors(saveSetName, groupStartTime, saveType, level, "failed");
                        clients.get(i).addReportError(reportError);
                        controlDateJobs.eventUpdateDateLastDayJobsExecuted(groupStartTime);
                        isNewClient = false;
                    }else{
                        ++i;
                    }
                }
                if(isNewClient == true){
                    Client client = new Client(clientName);
                    clients.add(client);
                    ReportErrors reportError = new ReportErrors(saveSetName, groupStartTime, saveType, level, "failed");
                    clients.get(i).addReportError(reportError);
                    controlDateJobs.eventUpdateDateLastDayJobsExecuted(groupStartTime);
                }
            } 
        }catch(FileNotFoundException e){
            throw new Exception("Error de lectura");
        } 
        return clients;
    }

    /**
     * Add text to file
     * @param clients
     * @param nameFileWriter
     * @throws Exception 
     */
    
    public void toAddWrite(ArrayList<Client> clients, String nameFileWriter) throws Exception{
       
        FileWriter fout = null;
        PrintWriter pw = null;
        
        try{
            fout = new FileWriter(nameFileWriter, true);
            pw = new PrintWriter(fout);
            
            String line = "Client Name,Save Set Name,Group Start Time,Save Type,Level,Status";
            pw.println(line);
            
            int size = clients.size();
            
            for(int i=0;i<size;++i){
                for(int j=0;j<clients.get(i).getSizeErrors();++j){
                   pw.println(clients.get(i).getName() + "," + clients.get(i).getReport(j).toString()); 
                }
            } 
        }catch(Exception e){
            throw new Exception("Error de escritura");
        }finally{
            if(null != fout){
                fout.close();
            }
        }
        
    }
    
    /**
     * Overwrite the file.
     * @param clients
     * @param nameFileWriter
     * @throws Exception 
     */
    
    public void toOverwrite(ArrayList<Client> clients, String nameFileWriter) throws Exception{
        
        FileWriter fout = null;
        PrintWriter pw = null;
        
        try{
            fout = new FileWriter(nameFileWriter);
            pw = new PrintWriter(fout);
            
            String line = "Client Name,Save Set Name,Group Start Time,Save Type,Level,Status";
            pw.println(line);
            
            int size = clients.size();
            
            for(int i=0;i<size;++i){
                pw.println(clients.get(i).getName() + "," + clients.get(i).getReport(0).toString());
                for(int j=1;j<clients.get(i).getSizeErrors();++j){
                   pw.println("," + clients.get(i).getReport(j).toString()); 
                }
            } 
        }catch(Exception e){
            throw new Exception("Error de escritura");
        }finally{
            if(null != fout){
                fout.close();
            }
        }
    }
    
    public ArrayList<Client> toReadAllJobs(File fichero, ArrayList<Client> clients) throws Exception{
        
        ControlDateJobs controlDateJobs = ControlDateJobs.getInstance();
        
        int i;
        boolean isNewClient;
        String clientName = null;
        String saveSetName = null;
        String groupStartTime = null;
        String saveType = null;
        String level = null;
        String str_status = null;
        Status status;
        
        try{
            Scanner sc=new Scanner(fichero);
            String line=sc.nextLine();
            while(line.indexOf("Client Name,")==-1){
                line=sc.nextLine();
            }
            while(sc.hasNextLine()){
                line=sc.nextLine();
                
                i=line.indexOf(",");
                clientName=line.substring(0, i);
                line=line.substring(i+1);  

                i=line.indexOf(",");
                saveSetName=line.substring(0, i);
                line=line.substring(i+1);
                
                i=line.indexOf(",");
                String number = line.substring(0, 1);
                if((!isNumber(number)) && (!number.equals(","))){
                    line = line.substring(i+1);
                    i=line.indexOf(",");
                    saveSetName += "," + line.substring(0, i);
                    number = line.substring(0, 1);
                    if((!isNumber(number)) && (!number.equals(","))){
                        line = line.substring(i+1);
                        i=line.indexOf(",");
                        saveSetName += "," + line.substring(0, i);
                    }
                }
                line=line.substring(i+1);
                
                i=line.indexOf(",");
                groupStartTime =line.substring(0, i);
                line=line.substring(i+1);

                i=line.indexOf(",");
                saveType=line.substring(0, i);
                line=line.substring(i+1);

                i=line.indexOf(",");
                level =line.substring(0, i);
                line=line.substring(i+1);

                str_status =line.substring(0, line.length());
                i=line.indexOf(",");
                if(i != -1){
                    str_status = str_status.substring(0, i);
                }
                
                switch(str_status){
                    case "succeeded":
                        status = Status.SUCCEEDED;
                        break;
                    case "failed":
                        status = Status.FAILED;
                        break;
                    case "incomplete":
                        status = Status.INCOMPLETE;
                        break;
                    case "abandoned":
                        status = Status.ABANDONED;
                        break;
                    case "unexpectedly exited":
                        status = Status.UNEXPECTEDLY_EXITED;
                        break;
                    default:
                        status = Status.FAILED;
                        break;
                }
                
                isNewClient = true;
                
                i=0;
                
                while(i < clients.size() && isNewClient){
                    if(clients.get(i).getName().equals(clientName)){
                        if(status !=Status.SUCCEEDED){
                            ReportErrors reportError = new ReportErrors(saveSetName, groupStartTime, saveType, level, str_status);
                            clients.get(i).addReportError(reportError);
                        }
                        Report report = new Report(saveSetName, groupStartTime, saveType, level, status);
                        clients.get(i).addReport(report);
                        controlDateJobs.eventUpdateDateLastDayJobsExecuted(groupStartTime);
                        isNewClient = false;
                    }else{
                        ++i;
                    }
                }
                if(isNewClient == true){
                    Client client = new Client(clientName);
                    clients.add(client);
                    if(status !=Status.SUCCEEDED){
                        ReportErrors reportError = new ReportErrors(saveSetName, groupStartTime, saveType, level, str_status);
                        clients.get(i).addReportError(reportError);
                    }
                    Report report = new Report(saveSetName, groupStartTime, saveType, level, status);
                    clients.get(i).addReport(report);
                    controlDateJobs.eventUpdateDateLastDayJobsExecuted(groupStartTime);
                }
            } 
        }catch(FileNotFoundException e){
            throw new Exception("Error de lectura");
        } 
        return clients;
    }

    private boolean isNumber(String number) {
        return number.equals("0") || number.equals("1") || number.equals("2") || number.equals("3") || number.equals("4")
                || number.equals("5") || number.equals("6") || number.equals("7") || number.equals("8") || number.equals("9");
    }
}
