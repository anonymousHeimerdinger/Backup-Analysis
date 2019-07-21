/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import model.BackupsInformationOfOneSaveSet;
import model.Client;
import model.Group;
import model.Job;
import model.ManageOfOneClient.StatusClient;
import model.ManageOfOneClient.StatusMarked;
import model.ManageOfOneClient;
import model.NoteClient.Action;
import model.Server;

/**
 * Subclass of Jframe, this class has been implemented by the Genís Gilavert Berlana.
 * This class contain 3 Jlist that they interact with the Class AplicacioBackup.
 * Attention: for to use this class you need to call to the function initShowDates().
 * @author Glenius
 */

public class FrameApplicationInit extends javax.swing.JFrame{
    
    public enum Status {NOT_EXECUTED, EXECUTED, FAILED};
    
    //.setBorder(BorderFactory.createMatteBorder(32, 2, 2, 2, colorApp));
    
    private Server server;
    private Client clientSelected;
    
    protected Controlador controlador;
    protected Color colorFrame;
    protected Color colorApp;
    
    protected ArrayList<Server> servers;
    private ArrayList<Client> clients;
    protected ArrayList<BackupsInformationOfOneSaveSet> saveSetsFaileds;
    protected ArrayList<BackupsInformationOfOneSaveSet> saveSetsAll;
    protected ArrayList<Group> groupsFaileds;
    protected ArrayList<Group> allGroups;
    
    protected JList jListServers;
    protected JList jListClients;
    protected JList jListSaveSetsFaileds;
    protected JList jListAllSaveSets;
    protected JList jListGroupsFaileds;
    protected JList jListAllGroups;
    
    //private JPanel panelImages;
    private JPanelImagesInfoBackup panelImageGraphClient;
    private JPanelImagesInfoBackup panelImageGraphSaveSetFailed;
    private JPanelImagesInfoBackup panelImageGraphSaveSetAll;
    private JPanelImagesInfoBackup panelImageGraphGroupFailed;
    private JPanelImagesInfoBackup panelImageGraphGroupAll;
    private JPanel panelSaveSets;
    private JPanel panelServers;
    private JPanel panelClients;
    private Notes anotations;
    private Notes noteSaveComments;
    private Notes noteDescription;
    private JPanel panelGroups;
    
    private JScrollPane scrollServers;
    private JScrollPane scrollClients;
    protected JScrollPane scrollSaveSetsFaileds;
    protected JScrollPane scrollAllSaveSets;
    private JScrollPane scrollGroupsFaileds;
    private JScrollPane scrollAllGroups;
    
    protected OpenDetailsClient JDDescription;
    
    private JPopupMenu popupMenuRightClick;
    private JPopupMenu popupMenuRightClickSaveSet;
    
    JMenuItem itemAddComment;
    JMenuItem itemShowDescription;
    JMenuItem itemMarkAsOpenedIncident;
    JMenuItem itemClientLaunched;
    JMenuItem itemMarkAsReaded;
    JMenuItem itemMarkAsPending;
    JMenuItem itemDeleteComment;
    JMenuItem itemMarkAsFalseSucceded;
    JMenuItem itemDisableClient;
    JMenuItem itemEnableClient;
    JMenuItem itemUnmark;
    
    JMenuItem itemAddCommentSaveSet;
    JMenuItem itemDisableSaveSet;
    JMenuItem itemEnableSaveSet;
    
    boolean isSearching;
    String incidence;
    
    
    public FrameApplicationInit(Controlador controlador,String nameProgram, Color colorApp) {
        this.setDefaultLookAndFeelDecorated(true);
        this.controlador = controlador;
        this.setTitle(nameProgram);
        this.setSize(1400, 900);
        this.colorApp = colorApp;
        this.incidence = " ";
         
        this.colorFrame = new Color(255,255,255);
        
        this.isSearching = false;
        this.popupMenuRightClick = new JPopupMenu();
        this.popupMenuRightClickSaveSet = new JPopupMenu();
        this.setVisible(true);
    }
    
    protected void saveDates(){
        try{
            this.controlador.saveDates();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    protected void loadDates(){
        
        try{
            this.LoadRuteData();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage() 
                + "\nThe application can not to save the rute of the file file.bbdd, is possible that have failed the synchronization. \n"
                        + "Anyway the application will try to load the file file.bbdd"); 
        }
        
        try{
            controlador.loadDates();
            //this.fillServers();
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage() 
                    + "\nThe application could not find the file file.bbdd");
            new FrmDades(this, true);
            try {
                this.controlador.saveRuteData();
            } catch (Exception ex1) {
                JOptionPane.showMessageDialog(null, ex1.getMessage() 
                + "\nThe application can not to save the rute of the file file.bbdd, is possible that have failed the synchronization. \n"
                        + "Anyway the application will try to load the file file.bbdd"); 
            }
            this.loadDates();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(0);
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(0);
        }
    }
    
    protected void LoadRuteData() throws Exception{
        try{
            this.controlador.loadRuteData();
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage() 
                    + "\nThe application has not assigned the rute. Please, indicate us the rute of file.bbdd"); 
            new FrmDades(this, true);
            this.controlador.saveRuteData();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public String getRuteDataBBDD() {
        return this.controlador.getRuteDataBBDD();
    }
    
    public void setRuteDataBBDD(String ruteDataBBDD) {
        this.controlador.setRuteDataBBDD(ruteDataBBDD);
    }
    
    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }
    
    private void setSelectedClient(Client client){
        this.clientSelected = client;
    }
    
    protected Client getSelectedClient(){
        return this.clientSelected;
    }
    
    protected boolean isInitVSS(){
        return this.controlador.isInitVSS();
    }
    
    /**
     * return all servers with all clients
     * @return 
     */
    
    public ArrayList<Server> getServers(){
        return this.controlador.getServers();
    }
    
    public ArrayList<Server> getServerListVSS() throws Exception{
        return this.controlador.getServerListVSS();
    }
    
    /**
     * return all servers with all clients faileds
     * @return 
     */
    
    public ArrayList<Server> getServersJustWithFailedsClients(){
        try{
            return this.controlador.getServerWhereClientsFaileds3ConsecutiveTimes();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return this.controlador.getServers();
    }
    
    
    /**
     * fill the dates and initialize the interface of the Frame
     */
    
    protected void initShowDates(){
         
        this.getContentPane().setBackground(this.colorFrame);
         
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.initPanelServers();
        this.initPanelClients();
        this.initPanelSaveSets();
        this.initPanelGroups();
        
        this.setVisible(true);
    }
    
    private void initPanelServers(){
        this.panelServers = new JPanel();
        this.panelServers.setLayout(new BoxLayout(this.panelServers,BoxLayout.Y_AXIS));
        
        this.initListServers();
        this.noteSaveComments = new Notes("Comments", "save comments here", true, this.colorFrame);
        this.anotations = new Notes("Anotations", "... you can write here ...", true, this.colorFrame);
        
        this.panelServers.add(this.scrollServers);
        this.panelServers.add(this.noteSaveComments.getJScrollPane());
        this.panelServers.add(this.anotations.getJScrollPane());
        
        this.getContentPane().add(this.panelServers);
        this.panelServers.setVisible(true);
    }
    
    protected void showDataGroupLauncheds(){
        String string = "Launcheds:\n\n";
        
        for(Server server:this.servers){
            for(Client client:server.getClients()){
                if(client.isLaunched()){
                    string += client.getName() + " in group: " + client.getGroupLaunched() + "\n";
                }
            }
        }
        
        this.anotations.setAnotations(string);
    }
    
    
    private void initPanelClients(){
        this.panelClients = new JPanel();
        this.panelClients.setLayout(new BoxLayout(this.panelClients,BoxLayout.Y_AXIS));
        this.initListClients();
        this.panelClients.add(this.scrollClients);
        this.panelImageGraphClient = new JPanelImagesInfoBackup(this.colorFrame);
        this.panelClients.add(this.panelImageGraphClient);
        
        this.noteDescription = new Notes("Comments Client selected", "there is not client selected", false, this.colorFrame);
        this.panelClients.add(this.noteDescription.getJScrollPane());
        
        this.getContentPane().add(this.panelClients);
        this.panelClients.setVisible(true);
    }
    
    private void initPanelSaveSets(){
        this.panelSaveSets = new JPanel();
        this.panelSaveSets.setLayout(new BoxLayout(this.panelSaveSets,BoxLayout.Y_AXIS));
        this.initListSaveSets();
        this.panelSaveSets.add(this.scrollSaveSetsFaileds);
        this.panelImageGraphSaveSetFailed = new JPanelImagesInfoBackup(this.colorFrame);
        this.panelSaveSets.add(this.panelImageGraphSaveSetFailed);
        this.panelSaveSets.add(this.scrollAllSaveSets);
        this.panelImageGraphSaveSetAll = new JPanelImagesInfoBackup(this.colorFrame);
        this.panelSaveSets.add(this.panelImageGraphSaveSetAll);
        this.getContentPane().add(this.panelSaveSets);
    }
    
    private void initPanelGroups(){
        this.panelGroups = new JPanel();
        this.panelGroups.setLayout(new BoxLayout(this.panelGroups,BoxLayout.Y_AXIS));
        this.initListGroups();
        this.panelGroups.add(this.scrollGroupsFaileds);
        this.panelImageGraphGroupFailed = new JPanelImagesInfoBackup(this.colorFrame);
        this.panelGroups.add(this.panelImageGraphGroupFailed);
        this.panelGroups.add(this.scrollAllGroups);
        this.panelImageGraphGroupAll = new JPanelImagesInfoBackup(this.colorFrame);
        this.panelGroups.add(this.panelImageGraphGroupAll);
        this.getContentPane().add(this.panelGroups);
    }
    
    protected FrameApplicationInit getFrame(){
        return this;
    }
    
    private void fillServers() {
        this.servers = this.getServersJustWithFailedsClients();
        if(this.servers.size() > 0){
            DefaultListModel model = new DefaultListModel();

            model.clear();
            
            for(Server server: this.servers){
                model.addElement(server.getNameServer());
            }
            this.jListServers.setModel(model);
            
        }
    }
    
    private Icon getImatge(Status status){
        ImageIcon imageStatus;
        Icon iconStatus;
        
        switch(status){
            case NOT_EXECUTED:
                imageStatus = new ImageIcon("src/Imatges/circleNone.png");
                break;
            case FAILED:
                imageStatus = new ImageIcon("src/Imatges/circleFailed.png");
                break;
            case EXECUTED:
                imageStatus = new ImageIcon("src/Imatges/circleOK.png");
                break;
            default:
                imageStatus = new ImageIcon("src/Imatges/circleFailed.png");
                break;
        }

        
        iconStatus = new ImageIcon(imageStatus.getImage().getScaledInstance(100,32,Image.SCALE_DEFAULT));
        return iconStatus;
    }
    
    private void initListServers() {
        this.jListServers = new JList();
        this.jListServers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.scrollServers = new JScrollPane(this.jListServers);
       
        String stringTitle = "List of servers";
        this.scrollServers.setBorder(BorderFactory.createTitledBorder(stringTitle));
        this.scrollServers.setBackground(this.colorApp);
        this.scrollServers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.scrollServers.setPreferredSize(new Dimension(120,500));
        fillServers();
        this.scrollServers.setVisible(true);
    
        this.jListServers.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                int index = jListServers.getSelectedIndex();
                server = servers.get(index);
                closeSearched();
                fillListClients(server); 
                emptyListSaveSetsFaileds();
                emptyListAllSaveSets();
                emptyListGroupsFaileds();
                emptyListAllGroups();
                updateNotesDescription();
                
                /**
                int keyCode = ke.getKeyCode();
                if(keyCode == KeyEvent.VK_RIGHT){
                    jListClients.setSelectedIndex(0); 
                }
                **/
            }
        });
        
        this.jListServers.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
                isSearching = false;
                if(SwingUtilities.isRightMouseButton(me)){
                    int index= jListServers.locationToIndex(me.getPoint());
                    if(index != -1){
                        jListServers.setSelectedIndex(index);
                        server = servers.get(index);
                        closeSearched();
                        fillListClients(server); 
                        emptyListSaveSetsFaileds();
                        emptyListAllSaveSets();
                        emptyListGroupsFaileds();
                        emptyListAllGroups();
                        updateNotesDescription();
                    }else{
                        closeSearched();
                        emptyListClients();
                        emptyListSaveSetsFaileds();
                        emptyListAllSaveSets();
                        emptyListGroupsFaileds();
                        emptyListAllGroups();
                        updateNotesDescription();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                isSearching = false;
                fillLabelsAllImagesEmpty();
                if(SwingUtilities.isLeftMouseButton(me)){
                    int index= jListServers.locationToIndex(me.getPoint());
                    if(index != -1){
                        closeSearched();
                        server = servers.get(index);
                        fillListClients(server); 
                        emptyListSaveSetsFaileds();
                        emptyListAllSaveSets();
                        emptyListGroupsFaileds();
                        emptyListAllGroups();
                        updateNotesDescription();
                    }else{
                        closeSearched();
                        emptyListClients();
                        emptyListSaveSetsFaileds();
                        emptyListAllSaveSets();
                        emptyListGroupsFaileds();
                        emptyListAllGroups();
                        updateNotesDescription();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                }

            @Override
            public void mouseExited(MouseEvent me) {
                } 

        });
        
    }
    
   
    private void initListClients() {
        this.jListClients = new JList();
        this.scrollClients = new JScrollPane(this.jListClients);
        String stringTitle = "List of clients";
        this.scrollClients.setBorder(BorderFactory.createTitledBorder(stringTitle));
        this.scrollClients.setBackground(this.colorApp);
        this.scrollClients.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollClients.setPreferredSize(new Dimension(250,900));
        
        
        this.jListClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.jListClients.setBackground(this.colorFrame);
        
        this.jListClients.setComponentPopupMenu(this.popupMenuRightClick);
        initPopupMenuRightClick();
        
        this.jListClients.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                fillLabelsAllImagesEmpty();
                int index = jListClients.getSelectedIndex();
                setSelectedClient(clients.get(index));
                fillDatesClient(getSelectedClient());
                updateNotesDescription(getSelectedClient());
            }
        });
        
        this.jListClients.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {  
                if (me.getClickCount() >= 2) {
                    int index = jListClients.getSelectedIndex();
                    if(index != -1){
                        setSelectedClient(clients.get(index));
                        if(isSearching){
                        //String nameClient = (String) jListClients.getModel().getElementAt(index);
                        //ArrayList<Client> listClients = controlador.getClients(nameClient);
                        //setSelectedClient(listClients.get(0));
                        new OpenDetailsClient(getFrame(), true, getSelectedClient(), getSelectedClient().getNameServer(), Color.white);
                        finishSearched();
                        }else{
                            OpenDetailsClient jDDescription = new OpenDetailsClient(getFrame(), true, getSelectedClient(),
                                servers.get(jListServers.getSelectedIndex()).getNameServer(),colorFrame);
                        }
                    }
                    
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if(SwingUtilities.isRightMouseButton(me)){
                    int index= jListClients.locationToIndex(me.getPoint());
                    if (index !=-1){
                        jListClients.setSelectedIndex(index);
                        setSelectedClient(clients.get(index));
                        fillDatesClient(getSelectedClient());
                        changeStatePopupMenu();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                fillLabelsAllImagesEmpty();
                if(SwingUtilities.isLeftMouseButton(me)){
                    int index= jListClients.locationToIndex(me.getPoint());
                    if (index !=-1){
                        index = jListClients.getSelectedIndex();
                        setSelectedClient(clients.get(index));
                        fillDatesClient(getSelectedClient());
                        updateNotesDescription(getSelectedClient());
                    }else{
                        emptyDatesClient();
                    }
               } 
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
                }
        });
        
    }
    
    
    
    private ArrayList<BackupsInformationOfOneSaveSet> getSaveSetsFaileds(Client client){
        return client.getListSaveSetsFaileds();
    }
    
    private ArrayList<BackupsInformationOfOneSaveSet> getSaveSetsAll(Client client){
        return client.getListAllSaveSets();
    }
    
    private ArrayList<Group> getGroupsAll(Client client){
        return client.getListAllGroups();
    }
    
    private ArrayList<Group> getGroupsFaileds(Client client){
        return client.getListGroupsFaileds();
    }
    
    private void fillListSaveSetsFaileds(Client client) {
        saveSetsFaileds = this.getSaveSetsFaileds(client);
        
        if(saveSetsFaileds.size() > 0){
            DefaultListModel<String> model = new DefaultListModel();
            
            model.clear();
            for (BackupsInformationOfOneSaveSet saveSet: saveSetsFaileds){
                String name = saveSet.getSaveSetName();
                
                if(!saveSet.isEnabled()){
                    name += " (DISABLED)";
                }
                
                model.addElement(name);
            }
            this.jListSaveSetsFaileds.setModel(model);
            this.jListSaveSetsFaileds.setVisible(true);
        }else{
            emptyListSaveSetsFaileds();
        }
    }
    
    private void fillListAllSaveSets(Client client){
        saveSetsAll = this.getSaveSetsAll(client);
        
        if(saveSetsAll.size() > 0){
            DefaultListModel<String> model = new DefaultListModel();
            
            model.clear();
            for (BackupsInformationOfOneSaveSet saveSet: saveSetsAll){
                String name = saveSet.getSaveSetName();
                
                if(!saveSet.isEnabled()){
                    name += " (DISABLED)";
                }
                
                model.addElement(name);
            }
            this.jListAllSaveSets.setModel(model);
            this.jListAllSaveSets.setVisible(true);
        }else{
            emptyListAllSaveSets();
        }
    }
    
    private void fillListAllGroups(Client client){
        this.allGroups = this.getGroupsAll(client);
        
        if(allGroups.size() > 0){
            DefaultListModel<String> model = new DefaultListModel();
            
            model.clear();
            for (Group group: allGroups){
                String name = group.getName();
                
                model.addElement(name);
            }
            this.jListAllGroups.setModel(model);
            this.jListAllGroups.setVisible(true);
        }else{
            emptyListAllGroups();
        }
    }
    
    private void fillListGroupsFaileds(Client client) {
        this.groupsFaileds = this.getGroupsFaileds(client);
        
        if(groupsFaileds.size() > 0){
            DefaultListModel<String> model = new DefaultListModel();
            
            model.clear();
            for (Group group: groupsFaileds){
                String name = group.getName();
                
                model.addElement(name);
            }
            this.jListGroupsFaileds.setModel(model);
            this.jListGroupsFaileds.setVisible(true);
        }else{
            emptyListGroupsFaileds();
        }
    }
    
    private void fillListClients(){
        emptyListSaveSetsFaileds();
        emptyListAllSaveSets();
        
        if(clients.size() > 0){
            DefaultListModel<String> model = new DefaultListModel();
            
            model.clear();
            for(Client client: clients){
                
                String name = client.getName();
                
                name += getStateMarked(client) + getStateClient(client);
                
                try{
                    if(client.isTypeVSS()){
                        name += " ::: VSS";
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                
                
                model.addElement(name);
            }
            this.jListClients.setModel(model);   
            this.jListClients.setVisible(true);
            
        }else{
            emptyListClients();
        }
    }
    
    protected void fillListClientsListSearcheds(ArrayList<Client> listClients){
        clients = listClients;
        this.fillListClients();
    }
    
    private void fillListClients(Server server){
        clients = server.getClients();
        this.fillListClients();
        
    }
    
    private void emptyListClients() {
        this.jListClients.clearSelection();
        this.jListClients.removeAll();
        DefaultListModel<String> model = new DefaultListModel();
        model.clear();
        this.jListClients.setModel(model);
        
    }

    private void emptyListSaveSetsFaileds(){
        this.jListSaveSetsFaileds.clearSelection();
        this.jListSaveSetsFaileds.removeAll();
        DefaultListModel<String> model = new DefaultListModel();
        model.clear();
        this.jListSaveSetsFaileds.setModel(model);
        this.panelImageGraphSaveSetFailed.emptyLabelsImage();
    }
    
    private void emptyListAllSaveSets(){
        this.jListAllSaveSets.clearSelection();
        this.jListAllSaveSets.removeAll();
        DefaultListModel<String> model = new DefaultListModel();
        model.clear();
        this.jListAllSaveSets.setModel(model);
        this.panelImageGraphSaveSetAll.emptyLabelsImage();
    }

    private void emptyListGroupsFaileds(){
        this.jListGroupsFaileds.clearSelection();
        this.jListGroupsFaileds.removeAll();
        DefaultListModel<String> model = new DefaultListModel();
        model.clear();
        this.jListGroupsFaileds.setModel(model);
        this.panelImageGraphGroupFailed.emptyLabelsImage();
    }
    
    private void emptyListAllGroups(){
        this.jListAllGroups.clearSelection();
        this.jListAllGroups.removeAll();
        DefaultListModel<String> model = new DefaultListModel();
        model.clear();
        this.jListAllGroups.setModel(model);
        this.panelImageGraphGroupAll.emptyLabelsImage();
    }
    
    private void updateNotesDescription(Client client){
        this.noteDescription.setAnotations(client.printfNotes());
        this.noteDescription.setTitleBorder("comments client " + client.getName());
    }
    
    private void updateNotesDescription(){
        this.noteDescription.setAnotations("...");
        this.noteDescription.setTitleBorder("comments client selected");
    }
    
    private void initPopupMenuRightClick() {
        this.itemAddComment = new JMenuItem("add comment");
        this.itemShowDescription = new JMenuItem("show details");
        this.itemMarkAsOpenedIncident = new JMenuItem("mark as incidence");
        this.itemClientLaunched = new JMenuItem("mark as launched");
        this.itemMarkAsReaded = new JMenuItem("mark as readed");
        this.itemMarkAsPending = new JMenuItem("mark as pending");
        this.itemDeleteComment = new JMenuItem("delete comment");
        this.itemMarkAsFalseSucceded = new JMenuItem("mark as false succeded");
        this.itemDisableClient = new JMenuItem("to disable client");
        this.itemEnableClient = new JMenuItem("to enable client");
        this.itemUnmark = new JMenuItem("unmark");
        
        
        // events for actions to selection the differents options from popMenu
        
        itemAddComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                new JDFillDescription(getFrame(), true, client, Action.COMMENT);             
                updateNotesDescription(client);
            }
        });
        
        itemShowDescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                OpenDetailsClient jDDescription = new OpenDetailsClient(getFrame(), true, client,
                            client.getNameServer(),colorFrame); 
            }
        });
        
        itemMarkAsOpenedIncident.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                new JDOpenIncident(getFrame(), true, client);   
                new JDFillDescription(getFrame(), true, client, Action.OPEN_INCIDENT, client.getManageClient().getIncidence()); 
                updateNotesDescription(client);
                fillListClients();
            }
        });
        
        itemClientLaunched.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());  
                new JDGroupLaunched(getFrame(), true, client);
                updateNotesDescription(client);
                fillListClients();
            }
        });
        
        itemMarkAsReaded.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                getSelectedClient().getManageClient().setStatusMarked(StatusMarked.READED);
                
                fillListClients();
            }
        });
        
        itemMarkAsPending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                client.getManageClient().setStatusMarked(StatusMarked.PENDING_TODAY);
                fillListClients();
            }
        });
        
        itemDeleteComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                new JDDeleteComments(getFrame(), true, client);
                updateNotesDescription(client);
            }
        });
        
        itemMarkAsFalseSucceded.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                client.getManageClient().setStatusClient(StatusClient.REPORTED_SUCCEDED_FALSE);
                fillListClients();
            }
        });
        
        itemDisableClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                new JDFillDescription(getFrame(), true, client, Action.DISABLE_CLIENT);             
                updateNotesDescription(client);
                fillListClients();
            }
        });
        
        itemEnableClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                client.enableClient("unknown");
                updateNotesDescription(client);
                fillListClients();
            }
        });
        
        itemUnmark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                client.getManageClient().setStatusMarked(StatusMarked.UNMARKED);
                fillListClients();
            }
        });
        
        this.popupMenuRightClick.add(itemAddComment);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemShowDescription);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemMarkAsOpenedIncident);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemClientLaunched);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemMarkAsReaded);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemMarkAsPending);
        this.popupMenuRightClick.add(new JSeparator()); 
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemDeleteComment);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemMarkAsFalseSucceded);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemDisableClient);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(itemEnableClient);
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(new JSeparator());
        this.popupMenuRightClick.add(this.itemUnmark);
    }
    
    private void changeStatePopupMenu(){
        //int index = jListClients.getSelectedIndex();
        //Client client = server.getClients().get(index);
        
        
        enableItemsPopupMenu();
        
        switch(getSelectedClient().getManageClient().getStatusMarked()){
            case DISABLED:
                this.itemDisableClient.setEnabled(false);
                this.itemEnableClient.setEnabled(true);
                this.itemUnmark.setEnabled(false);
                
                this.disableItemsMarkPopupMenu();
                break;
            case INCIDENT:
            case LAUNCH_TODAY:
                this.disableItemsMarkPopupMenu();
                break;
            default:
                break;
        }
        
    }
    
    private void enableItemsPopupMenu(){
        this.itemAddComment.setEnabled(true);
        this.itemClientLaunched.setEnabled(true);
        this.itemDeleteComment.setEnabled(true);
        this.itemDisableClient.setEnabled(true);
        this.itemMarkAsFalseSucceded.setEnabled(true);
        this.itemMarkAsOpenedIncident.setEnabled(true);
        this.itemMarkAsPending.setEnabled(true);
        this.itemMarkAsReaded.setEnabled(true);
        this.itemShowDescription.setEnabled(true);
        this.itemUnmark.setEnabled(true);
        
        this.itemEnableClient.setEnabled(false);
    }
    
    private void disableItemsMarkPopupMenu(){
        this.itemClientLaunched.setEnabled(false);
        this.itemMarkAsFalseSucceded.setEnabled(false);
        this.itemMarkAsOpenedIncident.setEnabled(false);
        this.itemMarkAsPending.setEnabled(false);
        this.itemMarkAsReaded.setEnabled(false);
    }
    
    private void initPopupMenuRightClickSaveSet(){
        this.itemAddCommentSaveSet = new JMenuItem("add comment");
        this.itemEnableSaveSet = new JMenuItem("to enable save set");
        this.itemDisableSaveSet = new JMenuItem("to disable save set");
        
        itemAddCommentSaveSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());  
                int index = jListSaveSetsFaileds.getSelectedIndex();
                BackupsInformationOfOneSaveSet  saveSet = saveSetsFaileds.get(index);
                new JDFillDescription(getFrame(), true, client, Action.COMMENT, saveSet);             
                updateNotesDescription(client);
                fillListSaveSetsFaileds(client);
                fillListAllSaveSets(client);
            }
        });
        
        itemDisableSaveSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                int index = jListSaveSetsFaileds.getSelectedIndex();
                BackupsInformationOfOneSaveSet  saveSet = saveSetsFaileds.get(index);
                new JDFillDescription(getFrame(), true, client, Action.DISABLE_SAVE_SET, saveSet);             
                updateNotesDescription(client);
                fillListSaveSetsFaileds(client);
                fillListAllSaveSets(client);
            }
        });
        
        itemEnableSaveSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = clients.get(jListClients.getSelectedIndex());
                int index = jListSaveSetsFaileds.getSelectedIndex();
                BackupsInformationOfOneSaveSet  saveSet = saveSetsFaileds.get(index);
                saveSet.setEnable(true);
                client.enableClient("unknown");
                updateNotesDescription(client);
                fillListSaveSetsFaileds(client);
                fillListAllSaveSets(client);
            }
        });
        
        this.popupMenuRightClickSaveSet.add(itemAddCommentSaveSet);
        this.popupMenuRightClickSaveSet.add(new JSeparator());
        this.popupMenuRightClickSaveSet.add(this.itemEnableSaveSet);
        this.popupMenuRightClickSaveSet.add(new JSeparator());
        this.popupMenuRightClickSaveSet.add(this.itemDisableSaveSet);
    }
    
    private void changeStatePopupMenuSaveSet(){
        Client client = clients.get(jListClients.getSelectedIndex());
        int index = jListSaveSetsFaileds.getSelectedIndex();
        BackupsInformationOfOneSaveSet  saveSet = saveSetsFaileds.get(index);
        
        if(saveSet.isEnabled()){
            this.itemEnableSaveSet.setEnabled(false);
            this.itemDisableSaveSet.setEnabled(true);
        }else{
            this.itemDisableSaveSet.setEnabled(false);
            this.itemEnableSaveSet.setEnabled(true);
        }
        
    }
    
    private void initListSaveSets(){   
        this.initListSaveSetsFaileds();
        this.initListAllSaveSets();
    }
    
    private void initListGroups(){
        this.initListGroupsFaileds();
        this.initListAllGroups();
    }
    
    private void initListAllSaveSets(){
        this.jListAllSaveSets = new JList();
        this.scrollAllSaveSets = new JScrollPane(this.jListAllSaveSets);
        String stringTitle = "List of all save sets";
        this.scrollAllSaveSets.setBorder(BorderFactory.createTitledBorder(stringTitle));
        this.scrollAllSaveSets.setBackground(this.colorApp);
        this.scrollAllSaveSets.setPreferredSize(new Dimension(250,900));
        
        this.jListAllSaveSets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.jListAllSaveSets.setBackground(this.colorFrame);
        this.jListAllSaveSets.setVisible(true);
        
        this.jListAllSaveSets.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                int index = jListAllSaveSets.getSelectedIndex();
                chooseAllSaveSet(index);
            }
        });
        
        this.jListAllSaveSets.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() >= 2) {
                    int index = jListAllSaveSets.getSelectedIndex();
                    if(index != -1){
                        chooseAllSaveSet(index);
                        BackupsInformationOfOneSaveSet  saveSet = saveSetsAll.get(index);
                        new OpenDetailsSaveSet(getFrame(), false, saveSet, saveSet.getSaveSetName(),colorFrame);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if(SwingUtilities.isRightMouseButton(me)){
                    int index= jListAllSaveSets.locationToIndex(me.getPoint());
                    if(index != -1){
                        chooseAllSaveSet(index);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(SwingUtilities.isLeftMouseButton(me)){
                    int index = jListAllSaveSets.getSelectedIndex();
                    if(index != -1){
                        chooseAllSaveSet(index);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
        
    }

    private void initListSaveSetsFaileds() {
        this.jListSaveSetsFaileds = new JList();
        this.scrollSaveSetsFaileds = new JScrollPane(this.jListSaveSetsFaileds);
        String stringTitle = "List of save sets faileds";
        this.scrollSaveSetsFaileds.setBorder(BorderFactory.createTitledBorder(stringTitle));
        this.scrollSaveSetsFaileds.setBackground(this.colorApp);
        this.scrollSaveSetsFaileds.setPreferredSize(new Dimension(250,900));
        
        this.jListSaveSetsFaileds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.jListSaveSetsFaileds.setComponentPopupMenu(this.popupMenuRightClickSaveSet);
        initPopupMenuRightClickSaveSet();
        
        this.jListSaveSetsFaileds.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                int index = jListSaveSetsFaileds.getSelectedIndex();
                    chooseSaveSetFaileds(index);
            }
        });
        
        this.jListSaveSetsFaileds.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() >= 2) {
                    int index = jListSaveSetsFaileds.getSelectedIndex();
                    if(index != -1){
                        chooseSaveSetFaileds(index);
                        BackupsInformationOfOneSaveSet  saveSet = saveSetsFaileds.get(index);
                        new OpenDetailsSaveSet(getFrame(), false, saveSet, saveSet.getSaveSetName(),colorFrame);
                    }  
                }else{
                    int index = jListSaveSetsFaileds.getSelectedIndex();
                    if(index != -1){
                        chooseSaveSetFaileds(index);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if(SwingUtilities.isRightMouseButton(me)){
                    int index= jListSaveSetsFaileds.locationToIndex(me.getPoint());
                    if(index != -1){
                        chooseSaveSetFaileds(index);
                        changeStatePopupMenuSaveSet();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(SwingUtilities.isLeftMouseButton(me)){
                    int index = jListSaveSetsFaileds.getSelectedIndex();
                    if(index != -1){
                        chooseSaveSetFaileds(index);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
        
        this.jListSaveSetsFaileds.setBackground(this.colorFrame);
        this.jListSaveSetsFaileds.setVisible(true);
    }
    
    private void initListAllGroups() {
        this.jListAllGroups = new JList();
        this.scrollAllGroups = new JScrollPane(this.jListAllGroups);
        String stringTitle = "List of all groups";
        this.scrollAllGroups.setBorder(BorderFactory.createTitledBorder(stringTitle));
        this.scrollAllGroups.setBackground(this.colorApp);
        this.scrollAllGroups.setPreferredSize(new Dimension(250,900));
        
        this.jListAllGroups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.jListAllGroups.setBackground(this.colorFrame);
        this.jListAllGroups.setVisible(true);
        
        this.jListAllGroups.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() >= 2) {
                    int index = jListAllGroups.getSelectedIndex();
                    if(index != -1){
                        chooseAllGroups(index);
                        }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if(SwingUtilities.isRightMouseButton(me)){
                    int index= jListAllGroups.locationToIndex(me.getPoint());
                    if(index != -1){
                        chooseAllGroups(index);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(SwingUtilities.isLeftMouseButton(me)){
                    int index = jListAllGroups.getSelectedIndex();
                    if(index != -1){
                        chooseAllGroups(index);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
    }
    
    private void initListGroupsFaileds() {
        this.jListGroupsFaileds = new JList();
        this.scrollGroupsFaileds = new JScrollPane(this.jListGroupsFaileds);
        String stringTitle = "List of groups faileds";
        this.scrollGroupsFaileds.setBorder(BorderFactory.createTitledBorder(stringTitle));
        this.scrollGroupsFaileds.setBackground(this.colorApp);
        this.scrollGroupsFaileds.setPreferredSize(new Dimension(250,900));
        
        this.jListGroupsFaileds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.jListGroupsFaileds.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() >= 2) {
                    int index = jListGroupsFaileds.getSelectedIndex();
                    if(index != -1){
                        chooseGroupFailed(index);
                        //new OpenDetailsClient(getFrame(), true, client, client.getNameServer(), Color.white);
                    }  
                }else{
                    int index = jListGroupsFaileds.getSelectedIndex();
                    if(index != -1){
                        chooseGroupFailed(index);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if(SwingUtilities.isRightMouseButton(me)){
                    int index= jListGroupsFaileds.locationToIndex(me.getPoint());
                    if(index != -1){
                        chooseGroupFailed(index);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(SwingUtilities.isLeftMouseButton(me)){
                    int index = jListGroupsFaileds.getSelectedIndex();
                    if(index != -1){
                        chooseGroupFailed(index);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
        
        this.jListGroupsFaileds.setBackground(this.colorFrame);
        this.jListGroupsFaileds.setVisible(true);
    }
    
    protected void finishSearched(){
        this.closeSearched();
        emptyListSaveSetsFaileds();
        emptyListClients();
        emptyListSaveSetsFaileds();
    }
    
    private void chooseSaveSetFaileds(int index){
        jListSaveSetsFaileds.setSelectedIndex(index);
        BackupsInformationOfOneSaveSet  saveSet = saveSetsFaileds.get(index);
        Job [] table = saveSet.getTable();
        this.panelImageGraphSaveSetFailed.fillLabelsImage(table, saveSet.getLastCopySuccessful().getDate());
    }
    
    private void chooseAllSaveSet(int index){
        jListAllSaveSets.setSelectedIndex(index);
        BackupsInformationOfOneSaveSet  saveSet = saveSetsAll.get(index);
        Job [] table = saveSet.getTable();
        this.panelImageGraphSaveSetAll.fillLabelsImage(table, saveSet.getLastCopySuccessful().getDate());
    }
    
    private void chooseGroupFailed(int index){
        jListGroupsFaileds.setSelectedIndex(index);
        Group  group = this.groupsFaileds.get(index);
        Job [] table = group.getTable();
        this.panelImageGraphGroupFailed.fillLabelsImage(table);
    }
    
    private void chooseAllGroups(int index){
        jListAllGroups.setSelectedIndex(index);
        Group  group = this.allGroups.get(index);
        Job [] table = group.getTable();
        this.panelImageGraphGroupAll.fillLabelsImage(table);
    }
    
    protected void initSearched(){
        this.scrollClients.setBorder(BorderFactory.createTitledBorder("clients found"));
        this.isSearching = true;
    }
    
    protected void closeSearched(){
        this.scrollClients.setBorder(BorderFactory.createTitledBorder("List of clients"));
        this.isSearching = false;
    }
    
    private String getStateMarked(Client client) {
        StatusMarked status= client.getManageClient().getStatusMarked();
        String answer = " ";
        switch(status){
            case UNMARKED:
            case SUCCESSFUL:
            case SOLVED:
                return answer;
            case READED:
                answer = "Readed";
                break;
            case INCIDENT:
                answer = client.getManageClient().getIncidence();
                break;
            case LAUNCH_TODAY:
                answer = "Launched today";
                break;
            case LAUNCH_NOT_TODAY:
                answer = "Launched";
                break;
            case PENDING_TODAY:
                answer = "Pending today";
                break;
            case PENDING_NOT_TODAY:
                answer = "Pending";
                break;
            case DISABLED:
                answer = "DISABLED";
                break;
            default:
                answer = "¿?";
                break;
        }
        return " (" + answer + ")";
    }
    
    private String getStateClient(Client client){
        StatusClient status= client.getManageClient().getStatusClient();
        String answer = " ";
        switch(status){
            case SUCCESSFUL:
                answer = "-> Successful";
                break;
            case POSSIBLE_SUCCEDED_FALSE_TODAY:
            case POSSIBLE_SUCCEDED_FALSE_NOT_TODAY:
                answer = "-> possible succeded false";
                break;
            case REPORTED_SUCCEDED_FALSE:
                answer = "-> reported as succeded false";
                break;
            case FAILED_TODAY:
            case FAILED_NOT_TODAY:
                answer = "-> Failed";
                break;
            case UNKNOWN:
                answer = "-> ?";
                break;
            default:
                answer = "-> ¿?";
                break;
        }
        return answer;
    }
    
    
    
    
    private void fillLabelsAllImagesEmpty() {
        this.panelImageGraphClient.emptyLabelsImage();
        this.panelImageGraphGroupAll.emptyLabelsImage();
        this.panelImageGraphGroupFailed.emptyLabelsImage();
        this.panelImageGraphSaveSetAll.emptyLabelsImage();
        this.panelImageGraphSaveSetFailed.emptyLabelsImage();
    }
    
    private void fillLabelsAllImagesEmptyExceptClient(){
        this.panelImageGraphSaveSetFailed.emptyLabelsImage();
        this.panelImageGraphSaveSetAll.emptyLabelsImage();
        this.panelImageGraphGroupFailed.emptyLabelsImage();
        this.panelImageGraphGroupAll.emptyLabelsImage();
    }
    
    private void fillDatesClient(Client client) {  
        Job [] table = client.getBackupsInformationOfOneClient().getTable();
        this.panelImageGraphClient.fillLabelsImage(table);
        fillLabelsAllImagesEmptyExceptClient();
        
        this.fillPanelSaveSets(client);
        this.fillPanelGroups(client);
        
       
    }
    
    private void fillPanelSaveSets(Client client){
        this.panelImageGraphSaveSetFailed.emptyLabelsImage();
        this.panelImageGraphSaveSetAll.emptyLabelsImage();
        this.fillListSaveSetsFaileds(client);
        this.fillListAllSaveSets(client);
    }
    
    private void fillPanelGroups(Client client){
        this.panelImageGraphGroupFailed.emptyLabelsImage();
        this.panelImageGraphGroupAll.emptyLabelsImage();
        this.fillListGroupsFaileds(client);
        this.fillListAllGroups(client); 
    }
    

    private void emptyDatesClient() {
        this.emptyPanelClients();
        this.emptyPanelSaveSets();
        this.emptyPanelGroups();
    }
    
    private void emptyPanelSaveSets(){
        this.emptyListSaveSetsFaileds();
        this.emptyListAllSaveSets();
        this.panelImageGraphSaveSetFailed.emptyLabelsImage();
        this.panelImageGraphSaveSetAll.emptyLabelsImage();
    }
    
    private void emptyPanelClients(){
        this.emptyListClients();
        this.panelImageGraphClient.emptyLabelsImage();
    }
    
    private void emptyPanelGroups(){
        this.emptyListGroupsFaileds();
        this.emptyListAllGroups();
        this.panelImageGraphGroupFailed.emptyLabelsImage();
        this.panelImageGraphGroupAll.emptyLabelsImage();
    }
    
    protected void clearLists() {
        this.emptyDatesClient();
        this.jListServers.clearSelection();
    }
}
