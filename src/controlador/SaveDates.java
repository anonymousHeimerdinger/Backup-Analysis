/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *Class Universal that save the dates of the class to path collecteds
 * @author Glenius
 */
public class SaveDates {
    
    private static SaveDates instance = new SaveDates();
    
    
    public static SaveDates getInstance() {
        if(instance == null){
            instance = new SaveDates();
        }
        return instance;
    }
    private SaveDates() {    
    }
    
    /**
     * save the dates files of the class object on the path collecteds
     * @param nomFitxer
     * @throws AplicacioException 
     */
    
    public void guardarDadesDisc(Object object, String nameFileRute) throws Exception {
        
        File fileCreated = new File(nameFileRute);
        FileOutputStream fout;
        ObjectOutputStream oos;

        try{
            fout= new FileOutputStream(fileCreated);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(object);
            fout.close();
        }catch(FileNotFoundException variable){
            throw new Exception(variable.getMessage());
        }catch(IOException variable){
            throw new Exception(variable.getMessage());
        }
    }
    
}
