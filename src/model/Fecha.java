/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * @author Xenius
 */
public class Fecha implements Serializable{
    private int dia;
    private int mes;
    private int any;
    private int hour;
    private int minute;
    private int second;
    
    
    public Fecha() {
        this.dia = 0;
        this.mes = 0;
        this.any = 0;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }
    
    public Fecha(int dia, int mes, int any) {
        this.dia = dia;
        this.mes = mes;
        this.any = any;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }
    
    public Fecha(int dia, int mes, int any,int hour, int minute, int second) {
        this.dia = dia;
        this.mes = mes;
        this.any = any;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Fecha(String ddMMyy) {
        String day = ddMMyy.substring(0, 2);
        this.dia = Integer.parseInt(day);
        
        String month = ddMMyy.substring(3, 5);
        this.mes = Integer.parseInt(month);
        
        String year = ddMMyy.substring(6, 8);
        this.any = Integer.parseInt(year);
        
        
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }
    
    
    
    public Fecha parseObject(String text){
        return new Fecha(text);
    }
    
    public String format(Fecha date){
        return date.dia + "/" + date.mes + "/" + date.any;
    }
    
    public String getDate(){
        String day;
        String month;
        
        if(this.dia == 0 && this.mes == 0 && this.any == 0){
            return "unknown";
        }
        
        if(this.dia < 10){
            day = "0" + String.valueOf(this.dia);
        }else{
            day = String.valueOf(this.dia);
        }
        
        if(this.mes < 10){
            month = "0" + String.valueOf(mes);
        }else{
            month = String.valueOf(this.mes);
        }
        
        return day + "/" + month + "/" + String.valueOf(this.any);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        this.any = any;
    }
    
    /**
     * compare this date with the date pass by parameter and return the date with the day more new (the last day).
     * @param dateToCompare
     * @return 
     */
    
    public Fecha getLastDay(Fecha dateToCompare){
        if(this.any > dateToCompare.any){
            return this;
        }
        if(this.any < dateToCompare.any){
            return dateToCompare;
        }
        if(this.mes > dateToCompare.mes){
            return this;
        }
        if(this.mes < dateToCompare.mes){
            return dateToCompare;
        }
        if(this.dia > dateToCompare.dia){
            return this;
        }
        if(this.dia < dateToCompare.dia){
            return dateToCompare;
        }
        
        return this;
    }
    
    
    @Override
    public boolean equals(Object object){
        
        Fecha date;
        
        if(object instanceof Fecha){
            date = (Fecha) object;
        }else{
            return false;
        }
        
        if(this.dia == date.dia && this.mes == date.mes && this.any == date.any){
            return true;
        }
        
        return false;
        
    }

    @Override
    public String toString() {
        
        String day;
        String month;
        String hour;
        String minute;
        String second;
        
        if(this.dia < 10){
            day = "0" + String.valueOf(this.dia);
        }else{
            day = String.valueOf(this.dia);
        }
        
        if(this.mes < 10){
            month = "0" + String.valueOf(mes);
        }else{
            month = String.valueOf(this.mes);
        }
        
        if(this.hour < 10){
            hour = "0" + String.valueOf(this.hour);
        }else{
            hour = String.valueOf(this.hour);
        }
        
        if(this.minute < 10){
            minute = "0" + String.valueOf(this.minute);
        }else{
            minute = String.valueOf(this.minute);
        }
        
        if(this.second < 10){
            second = "0" + String.valueOf(this.second);
        }else{
            second = String.valueOf(this.second);
        }
        
        return day + "/" + month + "/" + String.valueOf(this.any) + 
                " " + hour + ":" + minute + ":" + second;
    }
    
    
    
}
