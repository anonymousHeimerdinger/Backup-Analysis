/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import model.Fecha;

/**
 * Class singleton that controll the date of the last day on someone job was executed.
 * Attention: the function eventUpdateDateLastDayJobsExecuted
 * should to be called every time that we have a new date. 
 * The best idea should to be that the funtion was called by a event.
 * @author Glenius
 */
public class ControlDateJobs {
    private static ControlDateJobs instance = new ControlDateJobs();
        protected Fecha lastDayJobsExecuted;
        
        public static ControlDateJobs getInstance() {
        if(instance == null){
            instance = new ControlDateJobs();
        }
        return instance;
    }
    private ControlDateJobs() { 
        this.lastDayJobsExecuted = new Fecha();
    }
    
        /**
     * this function collect a date and update the last date that the jobs are executed
     * the function should to be called every time that we have a new date. 
     * @param dateOneJobIsExecuted
     */
    
    public void eventUpdateDateLastDayJobsExecuted(Fecha dateOneJobIsExecuted){
        this.lastDayJobsExecuted = this.lastDayJobsExecuted.getLastDay(dateOneJobIsExecuted);
    }
    
    public void eventUpdateDateLastDayJobsExecuted(String groupStartTime) throws Exception{
        Fecha dateOneJobIsExecuted = ControlDateJobs.collectDateFromGroupStartTime(groupStartTime);
        this.lastDayJobsExecuted = this.lastDayJobsExecuted.getLastDay(dateOneJobIsExecuted);
    }
    
    /**
     * picks a string with date and time information, and extracts date information
     * @param groupStartTime
     * @return 
     */

    public static Fecha collectDateFromGroupStartTime(String groupStartTime) throws Exception {
        
        String string;
        
        string = groupStartTime;
        int day;
        int month;
        int year;
        
        if(string.indexOf("/") == 2){
            day = Integer.parseInt(string.substring(0, 2));
            string = string.substring(3);
            
        }else{
            if(string.indexOf("/") == 1){
                day = Integer.parseInt(string.substring(0, 1));
                string = string.substring(2);
            }else{
                throw new Exception("error: la fecha del día introducida no esta suporteada");
            }
        }
        
        if(string.indexOf("/") == 2){
            month = Integer.parseInt(string.substring(0, 2));
            string = string.substring(3);
        }else{
            if(string.indexOf("/") == 1){
                month = Integer.parseInt(string.substring(0, 1));
                string = string.substring(2);
            }else{
                throw new Exception("error: la fecha del mes introducida no esta suporteada");
            }
        }
        
        if(string.indexOf(" ") == 2){  
            string = "20" + string.substring(0, 2);
            year = Integer.parseInt(string);
        }else{
            if(string.indexOf("/") == 1){
                string = "200" + string.substring(0, 1);
                year = Integer.parseInt(string);
            }else{
                if(string.indexOf(" ") == 4){
                    String str_year = string.substring(0, 4);
                    year = Integer.parseInt(str_year);
                    string = string.substring(5);
                }else{
                    throw new Exception("error: la fecha del año introducida no esta suporteada");
                }
            }
        }
        
        Fecha date = new Fecha(day,month,year);
        
        return date;
    }
    
    /**
     * picks a string with date and time information, and extracts date and hour information
     * @param groupStartTime
     * @return 
     */

    public static Fecha collectDateAndHourFromGroupStartTime(String groupStartTime) throws Exception {
        
        String string;
        
        string = groupStartTime;
        int day;
        int month;
        int year;
        int hour;
        int minut;
        int second;
        
        if(string.indexOf("/") == 2){
            day = Integer.parseInt(string.substring(0, 2));
            string = string.substring(3);
        }else{
            if(string.indexOf("/") == 1){
                day = Integer.parseInt(string.substring(0, 1));
                string = string.substring(2);
            }else{
                throw new Exception("error: la fecha del día introducida no esta suporteada");
            }
        }
        
        if(string.indexOf("/") == 2){
            month = Integer.parseInt(string.substring(0, 2));
            string = string.substring(3);
        }else{
            if(string.indexOf("/") == 1){
                month = Integer.parseInt(string.substring(0, 1));
                string = string.substring(2);
            }else{
                throw new Exception("error: la fecha del mes introducida no esta suporteada");
            }
        }
        
        if(string.indexOf(" ") == 2){
            String str_year = "20" + string.substring(0, 2);
            year = Integer.parseInt(str_year);
            string = string.substring(3);
        }else{
            if(string.indexOf("/") == 1){
                String str_year = "200" + string.substring(0, 1);
                year = Integer.parseInt(str_year);
                string = string.substring(2);
            }else{
                if(string.indexOf(" ") == 4){
                    String str_year = string.substring(0, 4);
                    year = Integer.parseInt(str_year);
                    string = string.substring(5);
                }else{
                    throw new Exception("error: la fecha del año introducida no esta suporteada");
                }
                
            }
        }
        
        if(string.indexOf(" ") == 0){
            string = string.substring(1);
        }
        
        if(string.indexOf(":") == 1){
            String str_hour = string.substring(0, 1);
            hour = Integer.parseInt(str_hour);
            string = string.substring(2);
        }else{
            if(string.indexOf(":") == 2){
                String str_hour = string.substring(0, 2);
                hour = Integer.parseInt(str_hour);
                string = string.substring(3);
            }else{
                throw new Exception("error: la hora introducida no esta suporteada");
            }
        }
        if(string.indexOf(":") == -1){
            minut = Integer.parseInt(string);
            second = 0;
        }else{
            String str_minut = string.substring(0,2);
            minut = Integer.parseInt(str_minut);
            string = string.substring(3);

            String str_second = string.substring(0,2);
            second = Integer.parseInt(str_second);
        }
        
        Fecha date = new Fecha(day,month,year,hour,minut,second);
        return date;
    }
    
    protected Fecha getDateLastDayJobsExecuted(){
        return this.lastDayJobsExecuted;
    }
}
