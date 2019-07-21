/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import controlador.Controlador;
import controlador.SaveDates;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import model.Client;
import model.Fecha;
import model.Server;

/**
 *
 * @author Glenius
 */
public class ApplicacioBackup {
    
    
    private JMenuBar barra;
    private FrameApplicationInit jfrm;
     
    private JMenu menuDate;
    
    Color colorBarra;
    
    private JMenu menuGetReports;
    protected JComboBox comboBox;
    
    private Controlador controlador;
    
    protected JTextField txtSearch;

    public ApplicacioBackup() {
        
        this.controlador = new Controlador();
        
        iniciarVentanaPrincipal();
        iniciarBarra(); 
        iniciarColores();
        this.loadDates();
        obtenerInforme(0);
        obtenerInforme(1);
        obtenerInforme(2);
        
        try{
            this.controlador.initAnalyzeFaileds();
            resolverInforme(0);
            resolverInforme(1);
            resolverInforme(2);

            this.jfrm.initShowDates();
            this.jfrm.showDataGroupLauncheds();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        //this.jfrm.controlador.saveDates("data/file.bbdd");
        this.updateMenusDate();
        
    }
    
    private void loadDates(){
        this.jfrm.loadDates();
    }
    
    private void saveDates(){
        jfrm.saveDates();
    }
    
    /**
     * devuelve una lista de tipo string con toda la información de los clientes fallados que son de VSS
     * @return 
     * @throws java.lang.Exception 
     */
    
    public ArrayList<String> getListStringClientsVSS() throws Exception{
        return this.controlador.getTextClientsVSS();
    }
    
    public ArrayList<Server> getServerListVSS() throws Exception{
        return this.controlador.getServerListVSS();
    }
    
    
    protected void resolverInforme(int pos){
        try{
            this.controlador.exportReport(pos);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    protected void obtenerInforme(int pos){
        try{
            this.importData(pos);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    private void importData(int pos) throws Exception{
        ///Users/Glenius/NetBeansProjects/backups/src/dades/
        File file;
        switch(pos){
            case 0:
                file = new File("snt00010036.csv");
                this.controlador.collectingDataAllJobs(file);
                break;
            case 1:
                file = new File("nt00010049.csv");
                this.controlador.collectingDataAllJobs(file);
                break;
            case 2:
                file = new File("snt00010190.csv");
                this.controlador.collectingDataAllJobs(file);
                break;
            default:
                throw new Exception("Error:La opción introducida es incorrecta");
        }
        if(!file.isFile()){
            throw new Exception("Error:el fichero no existe en esta dirección");
        }
        
        
    }

    private void iniciarBarra() {
        
        this.barra = new JMenuBar();
        this.barra.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.jfrm.setJMenuBar(this.barra);  // establece la barra de menús para el objeto JFrame
        
        this.crearMenuAccions();
        this.crearMenuUsuari();
        this.crearMenuGet();
        this.crearMenuShow();
        this.crearMenuOptions();
        this.crearMenuMail();
        this.crearMenuDate();
        this.crearSeparator();
        this.crearMenuHelp();
        this.crearSeparator();
        this.crearMenuSearch();
    }

    private void iniciarVentanaPrincipal() {
        this.jfrm = new FrameApplicationInit(this.controlador,"Backup EROSKI 5.3",new Color(231,229,234));
        this.jfrm.setDefaultLookAndFeelDecorated(true);
        this.jfrm.setLocationRelativeTo(null);
        this.jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.jfrm.getContentPane().setLayout(new BoxLayout(this.jfrm.getContentPane(), BoxLayout.X_AXIS));
         
        Image im = Toolkit.getDefaultToolkit().getImage("imatges/Backup.png");
        //Image image = new ImageIcon(getClass().getResource("imatges/savesets.jpg")).getImage();
        this.jfrm.setIconImage(im);
        
        this.jfrm.setVisible(true);
    }

    private void iniciarColores() {
        //this.jfrm.getContentPane().setBackground(colorVentana);
        
        //colores de la barra de menú
        this.colorBarra = new Color(231,229,234);
        this.barra.setBackground(colorBarra);               
    }

    private void crearMenuUsuari() {
        JMenu menuUsuari;  // Menu usuario
        menuUsuari =  new JMenu("");
        menuUsuari.setMnemonic('u');
        menuUsuari.setMnemonic('U');
        
        ImageIcon imagen = new ImageIcon("imatges/User.png");
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT));
        menuUsuari.setIcon(icono);
        
        this.barra.add(menuUsuari);
    }




    private void crearMenuAccions() {
        JMenu menuActions;  // Menu de acciones
        menuActions =  new JMenu("File");
        menuActions.setMnemonic('f');
        menuActions.setMnemonic('F');
        
        JMenuItem itemSave = new JMenuItem("Save");
        itemSave.setMnemonic('s');
        itemSave.setMnemonic('S');
        
        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveDates();
            }
        });
        
        
        
        JMenuItem itemImport = new JMenuItem("Import to analyze"); // importa un fichero de fallos de clientes
        JMenuItem itemExport = new JMenuItem("Export file analyzed");  //Exporta un fichero de fallos de clientes que ha sido analizado
        
        JMenuItem itemSaveExit = new JMenuItem("Exit and save");
        itemSaveExit.setMnemonic('e');
        itemSaveExit.setMnemonic('E');
        
        itemSaveExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveDates();
                JDSalir panelSalir = new JDSalir(null, true, "   ¿Do you want to exit?");
                panelSalir.setTitle("Exit");
                panelSalir.setVisible(true);
                if(panelSalir.setOptionSelected() == true){
                    System.exit(0);
                }
            }
        });
        
        menuActions.add(itemSave);
        menuActions.add(itemImport);
        menuActions.add(itemExport);
        menuActions.add(itemSaveExit);
        
        this.barra.add(menuActions);
        
        this.exportSetEnabled(false); // equivalente a itemExport.setEnabled(false);
        this.importSetEnabled(false); // equivalente a itemImport.setEnabled(false); 
    }
    
    private void exportSetEnabled(boolean bol){
        this.barra.getMenu(0).getItem(2).setEnabled(bol); // cambia la habilitación del JMenuItem export
    }
    
    private void importSetEnabled(boolean bol){
        this.barra.getMenu(0).getItem(1).setEnabled(bol); // cambia la habilitación del JMenuItem import
    }

    private void crearMenuDate() {
        this.menuDate = new JMenu("Date Files: Loading date...");
        this.menuDate.setToolTipText("shows the date of the files entered");
        this.barra.add(this.menuDate);
        this.menuDate.setEnabled(true);
    }
    
    protected void updateMenusDate(){
        Fecha dateFilesAnalzyeds = this.controlador.getDateFilesAnalyzeds();
        this.menuDate.setText("Date Files: " + dateFilesAnalzyeds.getDate());
    }

    private void crearMenuGet() {
        this.menuGetReports = new JMenu("Get report");
        
        this.menuGetReports.setMnemonic('r');
        this.menuGetReports.setMnemonic('R');
        this.menuGetReports.setToolTipText("Get a report of the information you need");
        
        JMenuItem VSS = new JMenuItem("VSS"); 
        VSS.setMnemonic('v');
        VSS.setMnemonic('V');
        
        JMenuItem  groupsFailedsToday = new JMenuItem("Groups in state failed today");
        groupsFailedsToday.setEnabled(false);
        groupsFailedsToday.setMnemonic('g');
        groupsFailedsToday.setMnemonic('G');
        
        VSS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    JDShowListVSS VSS = new JDShowListVSS(jfrm);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        
        this.menuGetReports.add(VSS);
        this.menuGetReports.add(groupsFailedsToday);
        
        
        this.barra.add(this.menuGetReports);
        this.menuGetReports.setEnabled(true);
    }

    private void crearMenuShow() {    
        
        this.crearSeparator();
        
        this.comboBox = new JComboBox();
        
        Border border = BorderGlenius.getInstance().getBorder(new Color(231,229,234), 10);
        this.comboBox.setBorder(border);
        
        this.comboBox.setToolTipText("change the list of clients showed");
        this.comboBox.setBackground(this.colorBarra);
        this.comboBox.addItem("Clients failed");//0
        this.comboBox.addItem("Clients pending");//1
        this.comboBox.addItem("Clients pending today");//2
        this.comboBox.addItem("Clients pending not today");//3
        this.comboBox.addItem("Clients launched");//4
        this.comboBox.addItem("Clients launched today");//5
        this.comboBox.addItem("Clients launched not today");//6
        this.comboBox.addItem("Clients solved");//7
        this.comboBox.addItem("Possible false succeded");//8
        this.comboBox.addItem("Clients disabled");//9
        this.comboBox.addItem("All clients");//10
        
        ListenerItemsComboBox listenerItems = new ListenerItemsComboBox(this);
        this.comboBox.addItemListener(listenerItems);
        
        this.barra.add(this.comboBox);
        this.crearSeparator();
    }

    private void crearMenuOptions() {
        JMenu menuOptions = new JMenu("Options"); 
        menuOptions.setMnemonic('o');
        menuOptions.setMnemonic('O');
        menuOptions.setToolTipText("show options");
        
        JMenuItem itemToChangeRuteDates = new JMenuItem("to change rute dates");
        itemToChangeRuteDates.setMnemonic('c');
        itemToChangeRuteDates.setMnemonic('C');
        
        itemToChangeRuteDates.setEnabled(false);
        itemToChangeRuteDates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Unsupported
            }
        });
        
        JMenuItem itemAddNameServer = new JMenuItem("add name server for each client");
        itemAddNameServer.setMnemonic('a');
        itemAddNameServer.setMnemonic('A');
        
        itemAddNameServer.setEnabled(true);
        itemAddNameServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                controlador.addNameServerForEachClient();
            }
        });
        
        menuOptions.setSize(new Dimension(100, 50));
        menuOptions.setBorderPainted(true);
        menuOptions.setBackground(this.colorBarra);
        
        
        menuOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Unsupported
            }
        });
        
        menuOptions.add(itemToChangeRuteDates);
        menuOptions.add(itemAddNameServer);
        this.barra.add(menuOptions);
    }
    
    
    private void crearMenuHelp(){
        JButton btnHelp = new JButton("Help");
        btnHelp.setMnemonic('h');
        btnHelp.setMnemonic('H');
        btnHelp.setToolTipText("you can check the last changes of the last versión of the application\n"
                + " and to obtain the direction for to search the manual of the application");
        
        btnHelp.setSize(new Dimension(100, 50));
        btnHelp.setBorderPainted(true);
        btnHelp.setBackground(this.colorBarra);
        
        
        btnHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new Help(jfrm);
            }
        });
        
        this.barra.add(btnHelp);
    }

    protected void clearLists() {
        this.jfrm.clearLists();
    }
    
    private Icon getIcon(String address, int scale_X,int scale_Y){
        ImageIcon imageIcon = new ImageIcon(address);
        Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(scale_X,scale_Y,Image.SCALE_DEFAULT));
        return icon;
    }

    private void crearMenuMail() {
        JMenu menuMail;  // Menu usuario
        menuMail =  new JMenu("");
        menuMail.setMnemonic('m');
        menuMail.setMnemonic('M');
        
        ImageIcon imagen = new ImageIcon("imatges/running_mail.png");
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(65,55,Image.SCALE_DEFAULT));
        menuMail.setIcon(icono);
        
        menuMail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveDates();
            }
        });
        
        //menuMail.setEnabled(false);
        this.barra.add(menuMail);
    }
    
    
    /**
     * Class intern that implements the hit on the button search
     */
    
    private class DriveButtonSearch implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            jfrm.clearLists();
            String nameClient = txtSearch.getText();
            if(!nameClient.equals("")){
                ArrayList<Client> listClients = controlador.getClients(nameClient);
                int size = listClients.size();
                if(size == 0){
                    return;
                }
                initMenuSearched(listClients);
            }
        }

        private void initMenuSearched(ArrayList<Client> listClients) {
            jfrm.initSearched();
            jfrm.fillListClientsListSearcheds(listClients);
        }
    }
    
    private void crearMenuSearch() {
        this.crearSeparator();
        
        JButton btnSearch = new JButton("Search");
        
        btnSearch.addActionListener(new DriveButtonSearch());
        
        txtSearch = new JTextField();
        txtSearch.setHorizontalAlignment(SwingConstants.CENTER);  //Alinea el texto interior del JtextField al centro
        txtSearch.setToolTipText("Enter the client you want to search. "
                + "You can hit the \"enter\" button for to perform a fast searched");
        
        txtSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DriveButtonSearch drive = new DriveButtonSearch();
                drive.actionPerformed(ae);
            }
        });
        
        Border border = BorderGlenius.getInstance().getBorder(new Color(231,229,234), 10);

        txtSearch.setBorder(border);
        this.barra.add(txtSearch);
        
        btnSearch.setMnemonic('s');
        btnSearch.setMnemonic('S');
        btnSearch.setToolTipText("Search information of the client");
        
        btnSearch.setSize(new Dimension(100, 50));
        btnSearch.setBorderPainted(true);
        btnSearch.setBackground(this.colorBarra);
        
        JPanel panel2 = new JPanel();
        panel2.setBackground(this.colorBarra);
        this.barra.add(panel2);
        
        this.barra.add(btnSearch);
        
        this.crearSeparator();
    }

    private void crearSeparator() {
        JPanel panel = new JPanel();
        panel.setBackground(this.colorBarra);
        this.barra.add(panel);
    }
    
    protected void changeServersToAllServers(){
        this.jfrm.servers = this.controlador.getServers();
    }
    
    protected void changeServersToFailedServers(){
        try{
            this.jfrm.servers = this.controlador.getServerWhereClientsFaileds3ConsecutiveTimes();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    protected void changeServersToClientsPending(){
        this.jfrm.servers = this.controlador.getServersWithClientsPending();
    }
    
    protected void changeServersToClientsPendingToday(){
        this.jfrm.servers = this.controlador.getServersWithClientsPendingToday();
    }
    
    protected void changeServersToClientsPendingNotToday(){
        this.jfrm.servers = this.controlador.getServersWithClientsPendingNotToday();
    }
    
    protected void changeServersToClientsLaunched(){
        this.jfrm.servers = this.controlador.getServersWithClientsLaunched();
    }
    
    protected void changeServersToClientsLaunchedToday(){
        this.jfrm.servers = this.controlador.getServersWithClientsLaunchedToday();
    }
    
    protected void changeServersToClientsLaunchedNotToday(){
        this.jfrm.servers = this.controlador.getServersWithClientsLaunchedNotToday();
    }
    protected void changeServersToClientsSolved(){
        this.jfrm.servers = this.controlador.getServersWithClientsSolved();
    }
    
    protected void changeServersToClientsPossibleFalseSucceded(){
        this.jfrm.servers = this.controlador.getServersWithClientsPossibleFalseSucceded();
    }
    
    protected void changeServersToClientsDisabled(){
        this.jfrm.servers = this.controlador.getServersWithClientsDisabled();
    }
    
}
