/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;

/**
 *
 * @author Glenius
 */
public class CompareTimeHour implements Comparator<Fecha>{

    @Override
    public int compare(Fecha t1, Fecha t2) {
        if(t1.getHour() > t2.getHour() ){
            return 1;
        }else if(t1.getHour() == t2.getHour()){
            return this.compareMin(t1,t2);
        }else{
            return -1;
        }
    }
    
    
    private int compareMin(Fecha t1, Fecha t2){
        if(t1.getMinute() > t2.getMinute() ){
            return 1;
        }else if(t1.getMinute() == t2.getMinute()){
            return this.compareSecond(t1, t2);
        }else{
            return -1;
        }
    }
    
    private int compareSecond(Fecha t1, Fecha t2){
        if(t1.getSecond() > t2.getSecond() ){
            return 1;
        }else if(t1.getSecond() == t2.getSecond()){
            return 0;
        }else{
            return -1;
        }
    }
}
