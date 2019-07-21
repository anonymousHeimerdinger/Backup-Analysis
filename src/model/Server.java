/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Glenius
 */
public class Server implements Serializable{
    private ArrayList<Client> clients;
    private String nameServer;
    private Fecha dateTimeFile;

    public Server(String nameServer, ArrayList<Client> clients) {
        this.clients = clients;
        this.nameServer = nameServer;
        this.dateTimeFile = new Fecha();
    }
    
    public Server(String nameServer){
        this.clients = new ArrayList<Client>();
        this.nameServer = nameServer;
        this.dateTimeFile = new Fecha();
    }
    
    public ArrayList<Client> getClients() {
        return clients;
    }

    protected void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public String getNameServer() {
        return nameServer;
    }

    protected void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public Fecha getDateTimeFile() {
        return dateTimeFile;
    }

    public void setDateTimeFile(Fecha dateTimeFile) {
        this.dateTimeFile = dateTimeFile;
    }
    
    protected void clear(){
        for(Client client:this.clients){
            client.clear(this.dateTimeFile);
        }
    }
    
}
