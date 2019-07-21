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
public class CompareEverythingDateTime implements Comparator<Fecha>{

    @Override
    public int compare(Fecha t1, Fecha t2) {
        CompareDateTime compareDateTime = new CompareDateTime();
        int solution = compareDateTime.compare(t1, t2);
        if(solution == 0){
            CompareTimeHour compareTimeHour = new CompareTimeHour();
            return compareTimeHour.compare(t1, t2);
        }
        
        return solution;
    }
    
}
