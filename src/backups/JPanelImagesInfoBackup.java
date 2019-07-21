/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Job;

/**
 *
 * @author Glenius
 */
class JPanelImagesInfoBackup extends JPanel{
    private ArrayList<JLabel> labelsImage;
    private static boolean isFirstTimeInitThisClass = true;
    private static Icon iconImageCircleNotExecuted;
    private static Icon iconImageCircleExecuted;
    private static Icon iconImageCircleFailed;
    private static Icon iconImageCircleUnexpectedlyExited;
    private static Icon iconImageCircleIncomplete;
    private static Icon iconImageCircleAbandoned;
    private JLabel labelInfo;

    public JPanelImagesInfoBackup() {
        this.labelsImage = new ArrayList<JLabel>();
        String stringInfo = "last copy success";
        this.labelInfo = new JLabel(stringInfo);
        this.labelInfo.setToolTipText("last copy successful");
        if(isFirstTimeInitThisClass){
            initIconImages();
            isFirstTimeInitThisClass = false;
        }
        this.initLabelsImage();
    }
    
    public JPanelImagesInfoBackup(Color colorPanelImageGraph) {
        this.labelsImage = new ArrayList<JLabel>();
        String stringInfo = "last copy success";
        this.labelInfo = new JLabel(stringInfo);
        this.setBackground(colorPanelImageGraph);
        if(isFirstTimeInitThisClass){
            initIconImages();
            isFirstTimeInitThisClass = false;
        }
        this.initLabelsImage();
    }
    
    protected void emptyLabelsImage(){
        this.labelInfo.setText("last copy success");
        for(int i=0;i<10;++i){
            this.labelsImage.get(i).setIcon(iconImageCircleNotExecuted);
        }
    }
    
    private void initLabelsImage(){
        this.add(this.labelInfo);
        for(int i=0;i<10;++i){
            JLabel label = new JLabel(iconImageCircleNotExecuted);
            label.setToolTipText(9 - i + " days ago");
            this.labelsImage.add(label);
            this.add(labelsImage.get(i));
        }
    }
    
    public ArrayList<JLabel> getLabelsImage() {
        return labelsImage;
    }
    
    public void fillLabelsImage(Job[] table, String lastCopySuccessful){
        this.labelInfo.setText(lastCopySuccessful);
        for(int i=0;i<10;++i){
           Job job = table[i];
            switch(job.getStatusJob()){
                case SUCCEEDED:
                    labelsImage.get(9-i).setIcon(iconImageCircleExecuted);
                    break;
                case UNEXPECTEDLY_EXITED:
                    labelsImage.get(9-i).setIcon(iconImageCircleUnexpectedlyExited);
                    break;
                case INCOMPLETE:
                    labelsImage.get(9-i).setIcon(iconImageCircleIncomplete);
                    break;
                case ABANDONED:
                    labelsImage.get(9-i).setIcon(iconImageCircleAbandoned);
                    break;
                case FAILED:
                    labelsImage.get(9-i).setIcon(iconImageCircleFailed);
                    break;
                default:
                    labelsImage.get(9-i).setIcon(iconImageCircleNotExecuted);
                    break;
            } 
        }   
    }
    
    public void fillLabelsImage(Job[] table){
        for(int i=0;i<10;++i){
           Job job = table[i];
            switch(job.getStatusJob()){
                case SUCCEEDED:
                    labelsImage.get(9-i).setIcon(iconImageCircleExecuted);
                    break;
                case UNEXPECTEDLY_EXITED:
                    labelsImage.get(9-i).setIcon(iconImageCircleUnexpectedlyExited);
                    break;
                case INCOMPLETE:
                    labelsImage.get(9-i).setIcon(iconImageCircleIncomplete);
                    break;
                case ABANDONED:
                    labelsImage.get(9-i).setIcon(iconImageCircleAbandoned);
                    break;
                case FAILED:
                    labelsImage.get(9-i).setIcon(iconImageCircleFailed);
                    break;
                default:
                    labelsImage.get(9-i).setIcon(iconImageCircleNotExecuted);
                    break;
            } 
        }   
    }
    
    private void initIconImages(){
        ImageIcon imageCircleFailed = new ImageIcon("imatges/circleFailed.png");
        this.iconImageCircleFailed = new ImageIcon(imageCircleFailed.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        
        
        ImageIcon imageCircleNotExecuted = new ImageIcon("imatges/circleNone.png");
        this.iconImageCircleNotExecuted = new ImageIcon(imageCircleNotExecuted.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        
        
        ImageIcon imageCircleExecuted = new ImageIcon("imatges/circleOK.png");
        this.iconImageCircleExecuted = new ImageIcon(imageCircleExecuted.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        
        ImageIcon imageCircleUnexpectedlyExited = new ImageIcon("imatges/circleFailedYellow.png");
        this.iconImageCircleUnexpectedlyExited = new ImageIcon(imageCircleUnexpectedlyExited.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        
        
        ImageIcon imageCircleIncomplete = new ImageIcon("imatges/circleFailedOrange.png");
        this.iconImageCircleIncomplete = new ImageIcon(imageCircleIncomplete.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        
        
        ImageIcon imageCircleAbandoned = new ImageIcon("imatges/circleFailedPurple.png");
        this.iconImageCircleAbandoned = new ImageIcon(imageCircleAbandoned.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    }
    
}
