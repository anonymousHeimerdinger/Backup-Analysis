/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import model.Fecha;

/**
 *Utility Class Singleton
 * @author Glenius
 */
public class CalendarAnonymous {
    private static CalendarAnonymous instance = new CalendarAnonymous();
    public Calendar date;
    
    
    public static CalendarAnonymous getInstance() {
        if(instance == null){
            instance = new CalendarAnonymous();
        }
        return instance;
    }
    private CalendarAnonymous() { 
        date = new GregorianCalendar();
    }
    
    /**
     * return a vector of the class Fecha
     * The positión 0 is the date of today,so the positión 9 then is the date of 9 days ago
     * @return 
     */
    public Fecha[] getDateOfTheLastTenDays(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String days;
        Fecha[] positionLastTenDays = new Fecha[10];
        for(int i = 0; i<10; ++i){
            days = dateFormat.format(conversionToDate(i));
            
            int year = Integer.parseInt(days.substring(0,4));
            int month = Integer.parseInt(days.substring(5,7));
            int day = Integer.parseInt(days.substring(8,10));
            
            positionLastTenDays[i] = new Fecha(day,month,year);
        }      
        
        return positionLastTenDays;
    }
    
    
    /**
     * return a vector of the class Fecha
     * The positión 0 is the date of last job was executed,
     * so the positión 9 then is the date of 9 days ago since last job was executed
     * @param numberDaysMounth
     * @return 
     */
    
    public Fecha[] getDateOfTheLastTenDaysSinceLastJobWasExecuted() throws ParseException{
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        
        Fecha[] positionLastTenDays = new Fecha[10];
        String days;
        
        ControlDateJobs controlDateJobs = ControlDateJobs.getInstance();
        
        positionLastTenDays[0] = new Fecha(controlDateJobs.lastDayJobsExecuted.getDia(),
                controlDateJobs.lastDayJobsExecuted.getMes(),controlDateJobs.lastDayJobsExecuted.getAny());
        
        for(int i = 1; i<10; ++i){
            Date date = CalendarAnonymous.conversionToDate(i, controlDateJobs.lastDayJobsExecuted);
            days = dateFormat.format(date);
            int year = Integer.parseInt(days.substring(0,4));
            int month = Integer.parseInt(days.substring(5,7));
            int day = Integer.parseInt(days.substring(8,10));
            
            positionLastTenDays[i] = new Fecha(day,month,year);
        }      
        
        return positionLastTenDays;
    }
    
    /**
     * return the date of the 1 day ago since the day passed by parameter. 
     * This function required the number of days of the last mounth
     * @param dateToday
     * @param numberDaysMounth
     * @return 
     */
    
    public Fecha getLastDay(Fecha dateToday, int numberDaysMounth){
        if(dateToday.getDia() != 1){
            return new Fecha(dateToday.getDia(),dateToday.getMes(),dateToday.getAny());
        }
        if(dateToday.getMes() != 1){
            return new Fecha(numberDaysMounth,dateToday.getMes()-1,dateToday.getAny());
        }
        
        return new Fecha(numberDaysMounth,12,dateToday.getAny()-1);
        
    }
    
    
    
    
    protected Fecha conversionToClassFecha(int position) throws Exception{
        
        if(position < 0 || position >= 10){
            throw new Exception("The position not belong to last ten days");       
        }
        
        Fecha[] dateLastTenDays = this.getDateOfTheLastTenDays();
        
        return dateLastTenDays[position];
        
    }
    
    
    /**
     * the function calculate the date -i since today
     * @param i, int
     * @return 
     */
    protected static Date conversionToDate(int i){
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -i);
        return cal.getTime();
    }
    
    public static String conversionToString(Fecha date){
        String str_date;
        str_date = date.getDia() + "-" + date.getMes() + "-" + date.getAny();
        
        return str_date;
    }
    
    /**
     * Pass the object of the class Fecha to Format of type Long. 
     * This function is not using it.
     * @param fechaDate
     * @return
     * @throws ParseException 
     */
    
    public static long dateToLong(Fecha fechaDate) throws ParseException{
         long longDate;
        try {
            String str_date = CalendarAnonymous.conversionToString(fechaDate);
            DateFormat formatter ; 
            Date date; 
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = formatter.parse(str_date); 
            longDate= date.getTime();
            
        }catch (ParseException e){
            throw new ParseException("date incorrect", 0);
        }
        return longDate; 
    }  
    /**
     * the function calculate the date -i since the day pass by parameter
     * @param i
     * @param dateLastJob
     * @return
     * @throws ParseException 
     */
    
    protected static Date conversionToDate(int i, Fecha dateLastJob) throws ParseException{
        
        Calendar cal = Calendar.getInstance();
        cal.set(dateLastJob.getAny(), dateLastJob.getMes()-1, dateLastJob.getDia());
        cal.add(Calendar.DATE, -i);
        Date date = cal.getTime();
        return date;
    }
    
    /**
     * return the number (position) = the difference of days between the date collected by parameter about this day of today.
     * if the date collected by parameter is not a day of the last ten days, then the function return -1
     * 
     * @param date
     * @return 
     */
    
    public int conversionToInt(Fecha date) throws ParseException{
        
        int position = 0;
        Fecha[] dateLastTenDays;
        ControlDateJobs controlDateJobs = ControlDateJobs.getInstance();
        
        Fecha dateToday = this.getDateFechaToday();
        if(controlDateJobs.lastDayJobsExecuted.equals(dateToday)){
            dateLastTenDays = this.getDateOfTheLastTenDays();
        }else{
            dateLastTenDays = this.getDateOfTheLastTenDaysSinceLastJobWasExecuted();
        }
        
        
        for(Fecha iteratorDate:dateLastTenDays){
            if(iteratorDate.equals(date)){
                return position;
            }
            ++position;
        } 
        return -1;
    }
    
    /**
     * return the date of today in the format of the class Fecha
     * @return 
     */
    
    public Fecha getDateFechaToday(){
        
        int day = date.get(Calendar.DAY_OF_MONTH);
        
        int month = date.get(Calendar.MONTH);
        ++month;
        
        int year = date.get(Calendar.YEAR);
        
        Fecha dateToday = new Fecha(day, month, year);
        
        return dateToday;     
    }
    
    /**
     * return the date of today in the format of the class String (ddmmyyyy) d = day, m = month, y = year
     * @return 
     */
    
    protected String getDateStringToday(){
        
        Fecha dateToday = this.getDateFechaToday();
        
        String day = String.valueOf(dateToday.getDia());
        
        
        if(Integer.parseInt(day) < 10){
            day = "0" + day;
        }
        
        int monthInt = dateToday.getMes();
        String month = String.valueOf(monthInt);
        
        if(monthInt < 10){
            month = "0" + month;
        }
        
        String year = String.valueOf(dateToday.getAny());
        
        return day + month + year;
    }
    
}
