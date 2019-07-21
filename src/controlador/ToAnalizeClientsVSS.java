/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import model.Client;
import model.Server;

/**
 * Class singleton
 * @author Glenius
 */
public class ToAnalizeClientsVSS {
    private static ToAnalizeClientsVSS instance = new ToAnalizeClientsVSS();
    private ArrayList<String> stringClientsVSS;
    private ArrayList<Server> serverListVSS;
    private boolean isInformationUpdate;
    
 
    public static ToAnalizeClientsVSS getInstance() {
        if(instance == null){
            instance = new ToAnalizeClientsVSS();
        }
        return instance;
    }
    private ToAnalizeClientsVSS() { 
        this.stringClientsVSS = new ArrayList<String>();
        this.isInformationUpdate = false;
    }
    
    /**
     * the function calculate and return a list of type String with all information of the failed clients of type VSS
     * @throws Exception 
     */
    
    public ArrayList<String> getTextInformationFailedTypeVSS(ArrayList<Server> servers) throws Exception{
        String serverName;
        ArrayList<Client> clients;
        Client client;
        this.stringClientsVSS = new ArrayList<String>();
        this.serverListVSS = new ArrayList<Server> ();
        
        for(int i=0;i < servers.size();++i){
            serverName = servers.get(i).getNameServer();
            Server server = new Server(serverName);
            this.serverListVSS.add(server);
            this.stringClientsVSS.add(serverName + ":\n");
            clients = servers.get(i).getClients();
            for(int j=0;j < clients.size(); ++j){
                client = clients.get(j);
                client.updateVSS();
                if(client.isTypeVSS()){
                    this.stringClientsVSS.add(client.getName());
                    this.serverListVSS.get(i).getClients().add(client);
                }
            }
            this.stringClientsVSS.add("\n");
        }
        
        this.isInformationUpdate = true;
        
        return stringClientsVSS;
        
    }
    
    /**
     * the function return a list of type String with all information of the failed clients of type VSS
     * Attention: this function return a error if the list of string has not been calculated before, 
     * that is to say you need first to call to function getTextInformationFailedTypeVSS(ArrayList servers)
     * for to can use this function after.
     * @throws Exception 
     */
    
    public ArrayList<String> getTextInformationFailedTypeVSS() throws Exception{
        
        if(!this.isInformationUpdate){
            throw new Exception("The list of string has not been calculated before."
                    + "Please, you first need to call getTextInformationFailedTypeVSS(ArrayList<Server> servers) "
                    + "for to can use this function");
        }
        
        return this.stringClientsVSS;
    }
    
    protected ArrayList<Server> getServerListVSS() throws Exception{
         if(!this.isInformationUpdate){
            throw new Exception("The list of string has not been calculated before."
                    + "Please, you first need to call getTextInformationFailedTypeVSS(ArrayList<Server> servers) "
                    + "for to can use this function");
        }
         return this.serverListVSS;
    }

    public boolean isInitVSS(){
        return this.isInformationUpdate;
    }
    
}
