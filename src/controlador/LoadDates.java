/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *Class Universal that load the dates of the class that there is on the path collecteds
 * @author Glenius
 */
public class LoadDates {
    
     private static LoadDates instance = new LoadDates();
    
    
    public static LoadDates getInstance() {
        if(instance == null){
            instance = new LoadDates();
        }
        return instance;
    }
    private LoadDates() {    
    }
    
     /**
      * Load the dates of the object of the class from file path collecteds
      * @param object
      * @param namePathIncludingNameFile
      * @return
      * @throws Exception 
      */
    
    public static Object carregarDadesDisc(String namePathIncludingNameFile) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        
        File fileLoaded = new File(namePathIncludingNameFile);
        FileInputStream fin;
        ObjectInputStream ois;
            try{
                fin= new FileInputStream(fileLoaded);
                ois = new ObjectInputStream(fin);              
                Object dates = (Object) ois.readObject();
                fin.close();
                return dates;
                
            }catch(FileNotFoundException variable){
                throw new FileNotFoundException(variable.getMessage());
            }catch(IOException variable){
                throw new IOException(variable.getMessage());
            }catch(ClassNotFoundException variable){
                throw new ClassNotFoundException(variable.getMessage());
            }
    }
}
