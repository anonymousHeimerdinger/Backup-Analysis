package model;

import java.text.ParseException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Model Class
 * @author Genis
 */
public class DataManager {
    
    private ArrayList<Server> serversList; 
    private ArrayList<Server> serverListfailedConsecutives;
    private boolean isAnalyzeDone;

    
    /**
     * Constructor class
     */
    
    public DataManager() {
        this.serversList = new ArrayList<Server>();
        this.serverListfailedConsecutives = new ArrayList<Server>();
        this.isAnalyzeDone = false;
    }
    
    /**
     * Constructor class
     */
    
    public DataManager(ArrayList<Server> listServers) {
        this.serversList = listServers;
        this.serverListfailedConsecutives = new ArrayList<Server>();
        this.isAnalyzeDone = false;
        this.clearTableJobs();
    }
    
    private void clearTableJobs(){
        for(Server server:this.serversList){
            server.clear();
        }
    }
    
    /**
     * Add a server to the list
     * @param nameServer
     * @param clients 
     */
    
    public void addServer(String nameServer, ArrayList<Client> clients) {  
        Server server = new Server(nameServer,clients);
        serversList.add(server);
    }
    
    /**
     * Add a server to the list
     * @param nameServer
     */
    
    public void addServer(String nameServer) {  
        Server server = new Server(nameServer);
        serversList.add(server);
    }
    
    /**
     * get a Server that is it in the position recollected
     * @param pos
     * @return 
     */

    public Server getServer(int pos) {
        Server server = this.serversList.get(pos);
        return server;
    }
    
    /**
     * analyze the servers list of the clients that faults the last 3 jobs consecutive times
     * @return 
     */
    
    public void initAnalizeFaileds3ConsecutiveTimes() throws ParseException, Exception{
        
        for(int i=0;i<this.serversList.size();++i){
            for(Client client:this.serversList.get(i).getClients()){
                client.analyzeInfoBackup();
            }
        }
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverContainClientsFaileds;
            serverContainClientsFaileds = new Server(server.getNameServer(),this.getClientsFaileds3ConsecutiveTimes(server));
            this.serverListfailedConsecutives.add(serverContainClientsFaileds);
        }
        
        this.isAnalyzeDone = true;
    }
    
    /**
     * return a servers list of the clients that faults the last 3 jobs consecutive time
     * this function require to call before one time to function initAnalizeFaileds3ConsecutiveTimes()
     * @return
     * @throws Exception 
     */
    
    public ArrayList<Server> getServerWhereClientsFaileds3ConsecutiveTimes() throws Exception{
        
        if(!this.isAnalyzeDone){
            throw new Exception("The analysis on the information has not made");
        }
        
        return this.serverListfailedConsecutives;
    }
    
    /**
     * take by parameter a server and return the clients belong to server that faults the last 3 jobs consecutive time
     * @param server
     * @return 
     */
    
    private ArrayList<Client> getClientsFaileds3ConsecutiveTimes(Server server){
        ArrayList<Client> clientsFaileds3ConsecutiveTimes = new ArrayList<Client>();
        
        for(Client client:server.getClients()){
            if(client.isClientStateFailed()){
                clientsFaileds3ConsecutiveTimes.add(client);
            }
        }
        return clientsFaileds3ConsecutiveTimes;   
    }
    
    
    
    
    
    /**
     * take by parameter a client list and return the clients that faults the last 3 jobs days
     * @param clients
     * @return 
     */
    
    private ArrayList<Client> getClientsCheckedFaults3Days(ArrayList<Client> clients){
        ArrayList<Client> clientsFault3days = new ArrayList<Client>();
        int size = clients.size();
        for(int i=0;i<size;++i){
            if(clients.get(i).isClientFault3Days()){
                clientsFault3days.add(clients.get(i));
            }
        }
        return clientsFault3days;
    }
    
    /**
     * return all servers with all clients
     * @return 
     */
    
    public ArrayList<Server> getServers(){
        return this.serversList;
    }    
    
    /**
     * return the clients that contain the name client pass by parameter
     * @param nameClient
     * @return 
     */
    
    public ArrayList<Client> getClients(String nameClient){
        ArrayList<Client> listClients = new ArrayList<Client>();
        
        
        this.serversList.forEach((server) -> {
            server.getClients().stream().filter((client) -> (client.getName().toLowerCase().contains(nameClient.toLowerCase()))).forEachOrdered((client) -> {
                client.setNameServer(server.getNameServer());
                listClients.add(client);
            });
        });
        
        return listClients;
    }
    
    public void addNameServerForEachClient(){
        for(Server server: this.serversList){
            for(Client client: server.getClients()){
                client.setNameServer(server.getNameServer());
            }
        }
    }

    public void setDateLastDayJobsExecuted(Fecha dateTimeJobsExecuted) {
        for(Server server:this.getServers()){
            server.setDateTimeFile(dateTimeJobsExecuted);
        }
    }

    public ArrayList<Server> getServersWithClientsPending(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsPending;
            serverClientsPending = new Server(server.getNameServer(),this.getClientsPending(server));
            servers.add(serverClientsPending);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsPending(Server server){
        ArrayList<Client> clientsPending = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isPending()){
                clientsPending.add(client);
            }
        }
        return clientsPending;
    }
    
    public ArrayList<Server> getServersWithClientsPendingToday(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsPendingToday;
            serverClientsPendingToday = new Server(server.getNameServer(),this.getClientsPendingToday(server));
            servers.add(serverClientsPendingToday);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsPendingToday(Server server){
        ArrayList<Client> clientsPendingToday = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isPendingToday()){
                clientsPendingToday.add(client);
            }
        }
        return clientsPendingToday;
    }
    
    public ArrayList<Server> getServersWithClientsPendingNotToday(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsPendingNotToday;
            serverClientsPendingNotToday = new Server(server.getNameServer(),this.getClientsPendingNotToday(server));
            servers.add(serverClientsPendingNotToday);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsPendingNotToday(Server server){
        ArrayList<Client> clientsPendingNotToday = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isPendingNotToday()){
                clientsPendingNotToday.add(client);
            }
        }
        return clientsPendingNotToday;
    }
    
    public ArrayList<Server> getServersWithClientsLaunched(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsLaunched;
            serverClientsLaunched = new Server(server.getNameServer(),this.getClientsLaunched(server));
            servers.add(serverClientsLaunched);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsLaunched(Server server){
        ArrayList<Client> clientsLaunched = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isLaunched()){
                clientsLaunched.add(client);
            }
        }
        return clientsLaunched;
    }
    
    public ArrayList<Server> getServersWithClientsLaunchedToday(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsLaunchedToday;
            serverClientsLaunchedToday = new Server(server.getNameServer(),this.getClientsLauncheToday(server));
            servers.add(serverClientsLaunchedToday);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsLauncheToday(Server server){
        ArrayList<Client> clientsLaunchedToday = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isLaunchedToday()){
                clientsLaunchedToday.add(client);
            }
        }
        return clientsLaunchedToday;
    }
    
    public ArrayList<Server> getServersWithClientsLaunchedNotToday(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsLaunchedNotToday;
            serverClientsLaunchedNotToday = new Server(server.getNameServer(),this.getClientsLaunchedNotToday(server));
            servers.add(serverClientsLaunchedNotToday);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsLaunchedNotToday(Server server){
        ArrayList<Client> clientsLaunchedNotToday = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isLaunchedNotToday()){
                clientsLaunchedNotToday.add(client);
            }
        }
        return clientsLaunchedNotToday;
    }
    
    public ArrayList<Server> getServersWithClientsDisabled(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsDisabled;
            serverClientsDisabled = new Server(server.getNameServer(),this.getClientsDisabled(server));
            servers.add(serverClientsDisabled);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsDisabled(Server server){
        ArrayList<Client> clientsDisabled = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isDisable()){
                clientsDisabled.add(client);
            }
        }
        return clientsDisabled;
    }
    
    public ArrayList<Server> getServersWithClientsPossibleFalseSucceded(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsPossibleFalseSucceded;
            serverClientsPossibleFalseSucceded = new Server(server.getNameServer(),this.getClientsPossibleFalseSucceded(server));
            servers.add(serverClientsPossibleFalseSucceded);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsPossibleFalseSucceded(Server server){
        ArrayList<Client> clientsPossibleFalseSucceded = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isClientPossibleFalseSucceded()){
                clientsPossibleFalseSucceded.add(client);
            }
        }
        return clientsPossibleFalseSucceded;
    }
    
    public ArrayList<Server> getServersWithClientsSolved(){
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for(int i=0;i<this.serversList.size();++i){
            Server server = this.serversList.get(i);
            Server serverClientsSolved;
            serverClientsSolved = new Server(server.getNameServer(),this.getClientsSolved(server));
            servers.add(serverClientsSolved);
        }
        
        return servers;
    }
    
    private ArrayList<Client> getClientsSolved(Server server){
        ArrayList<Client> clientsSolved = new ArrayList<Client>();
        for(Client client:server.getClients()){
            if(client.isSolved()){
                clientsSolved.add(client);
            }
        }
        return clientsSolved;
    }
    
}
