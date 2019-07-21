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
public class CompareDateTime implements Comparator<Fecha>{

    @Override
    public int compare(Fecha t1, Fecha t2) {
        if(t1.getAny()> t2.getAny() ){
            return 1;
        }else if(t1.getAny()== t2.getAny()){
            return this.compareMonth(t1,t2);
        }else{
            return -1;
        }
    }
    
    private int compareMonth(Fecha t1, Fecha t2){
        if(t1.getMes()> t2.getMes()){
            return 1;
        }else if(t1.getMes() == t2.getMes()){
            return this.compareDay(t1, t2);
        }else{
            return -1;
        }
    }
    
    private int compareDay(Fecha t1, Fecha t2){
        if(t1.getDia() > t2.getDia() ){
            return 1;
        }else if(t1.getDia() == t2.getDia()){
            return 0;
        }else{
            return -1;
        }
    }
    
}
