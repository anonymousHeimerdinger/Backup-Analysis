/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Glenius
 */
public class Group implements Serializable{
    private String name;
    private int schedule;  // the schedule indicate us the average of the amount of times every mounth that the goup is executed
    private int counter;  // allow us to establish a counter for every group of the total number of times that the group was executed.
    private int counterMounths; // allow us to have the counter of mounths that the group was executed
    private Job[] table;
    private Job.Status status;
    private boolean isAnalyzed;
    private static final int MAX = 10;
    private static final int timeConsidererJobFailedNextDay = 10;  //hour time for to considerer a job failed to next day
    private Color color;
    
    public Group(String name) {
        this.name = name;
        this.schedule = 0;
        this.counter = 0;
        this.counterMounths = 0;
        this.table = new Job[MAX];
        for(int i = 0; i<MAX; ++i){
            Job job = new Job(new Fecha());
            this.table[i] = job;
        }
        this.status = this.status.NOT_EXECUTED;
        this.isAnalyzed = false;
        this.color = Color.white;
    }

    public Group(String name, int schedule) {
        this.name = name;
        this.schedule = schedule;
        this.counter = 0;
        this.counterMounths = 0;
        this.table = new Job[MAX];
        for(int i = 0; i<MAX; ++i){
            Job job = new Job(new Fecha());
            this.table[i] = job;
        }
        this.status = this.status.NOT_EXECUTED;
        this.isAnalyzed = false;
        this.color = Color.white;
    }
    
    public void setStatus(Job.Status status) {
        this.status = status;
    }

    public Job[] getTable() {
        return table;
    }

    protected void setTable(Job[] table) {
        this.table = table;
    }
    
    public Job getJob(int position){
        return this.table[position];
    }
    
    public void setJob(int position, Job job){
        this.table[position] = job;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * collecte the number of mounths to analyze
     * @param numberMounth 
     */
    
    private void setCounterMounths(int numberMounth){
        this.counterMounths = numberMounth;
    }
    
    /**
     * increase the counter that allow us to establish for every group of the total number of times that the group was executed. 
     */
    
    protected void increaseCounter(){
        ++this.counter;
    }
    
    /**
     * decrease the counter that allow us to establish for every group of the total number of times that the group was executed. 
     */
    
    protected void decreaseCounter(){
        --this.counter;
    }

    /**
     * increase the counter of Months
     */
    
    protected void increaseCounterMounths(){
        ++this.counterMounths;
    }
    
    /**
     * decrease the counter of Months 
     */
    
    protected void decreaseCounterMounths(){
        --this.counterMounths;
    }
    
    /**
     * return the group name
     * @return 
     */
    
    public String getName() {
        return name;
    }
    
    /**
     * collecte the group name
     * @param name 
     */

    protected void setName(String name) {
        this.name = name;
    }
    
    /**
     * return the schedule of the group
     * @return 
     */

    protected int getSchedule() {
        return schedule;
    }

    /**
     * collecte the schedule of the group
     * @param schedule 
     */
    
    private void setSchedule(int schedule) {
        this.schedule = schedule;
    }
    
    
    /**
     * calculate and save the average of the group schedule
     * You need give it the number of Mounths for to use the function
     * @param numberMounths 
     */
    
    protected void calculatedAverageScheduleGroup(int numberMounths){
        
        this.setCounterMounths(numberMounths);
        
        this.setSchedule(this.counter/this.counterMounths);
    }
    
    
    @Override
    public boolean equals(Object object){
        Group group;
        if(object instanceof Group){
            group = (Group) object;
        }else{
            return false;
        }
        return this.getName().equals(group.getName());       
    }

    @Override
    public String toString() {
        return  this.name + " (" + this.schedule +"/month)";
    }
       
}
