/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backups;

import controlador.CalendarAnonymous;
import java.util.ArrayList;
import model.BackupsInformationOfOneSaveSet;
import model.Client;
import model.Fecha;
import model.NoteClient;
import model.NoteClient.Action;

/**
 *
 * @author Glenius
 */
public class JDFillDescription extends javax.swing.JDialog {
    
    Client client;
    NoteClient noteClient;
    Action action;
    BackupsInformationOfOneSaveSet saveSet;
    
    /**
     * Creates new form JDFillDescription
     */
    public JDFillDescription(java.awt.Frame parent, boolean modal, Client client, Action action) {
        super(parent, modal);
        initComponents();
        this.setDefaultLookAndFeelDecorated(true);
        this.setLocationRelativeTo(parent);
        this.client = client;
        this.jLabelClientName.setText(this.client.getName());
        this.action = action;
        this.btnAction.setText(this.action.toString());
        if(this.action == Action.DISABLE_CLIENT){
            this.jComboboxTypeSaveSet.setEnabled(false);
            this.jComboboxTypeSaveSet.setVisible(false);
        }else{
            fillComboboxTypeSaveSets();
        }
        
        Fecha dateTimeToday = CalendarAnonymous.getInstance().getDateFechaToday();
        this.txtDateTime.setText(dateTimeToday.getDate());
        
        this.setVisible(true);
    }
    
    public JDFillDescription(java.awt.Frame parent, boolean modal, Client client, Action action, BackupsInformationOfOneSaveSet saveSet) {
        super(parent, modal);
        initComponents();
        this.setDefaultLookAndFeelDecorated(true);
        this.setLocationRelativeTo(parent);
        this.client = client;
        this.jLabelClientName.setText(this.client.getName());
        this.action = action;
        this.btnAction.setText(this.action.toString());
        this.saveSet = saveSet;
        this.jComboboxTypeSaveSet.addItem(saveSet.getSaveSetName());
        this.jComboboxTypeSaveSet.setEditable(false);
        Fecha dateTimeToday = CalendarAnonymous.getInstance().getDateFechaToday();
        this.txtDateTime.setText(dateTimeToday.getDate());
        this.setVisible(true);
    }
    
    /**
     * Creates new form JDFillDescription
     */
    public JDFillDescription(java.awt.Frame parent, boolean modal, Client client, Action action, String incident) {
        super(parent, modal);
        initComponents();
        this.setDefaultLookAndFeelDecorated(true);
        this.setLocationRelativeTo(parent);
        this.client = client;
        this.jLabelClientName.setText(this.client.getName());
        this.action = action;
        this.btnAction.setText(this.action.toString());
        this.txtIncidence.setText(incident);
        this.txtIncidence.setEditable(false);
        fillComboboxTypeSaveSets();
        Fecha dateTimeToday = CalendarAnonymous.getInstance().getDateFechaToday();
        this.txtDateTime.setText(dateTimeToday.getDate());
        
        if(action == Action.OPEN_INCIDENT){
            if(this.client.getListSaveSetsFaileds().size() == 1){
                int size = this.client.getListAllSaveSets().size();
                boolean find = false;
                int i = 0;
                do{
                    if(this.client.getListSaveSetsFaileds().get(0).getSaveSetName().equals(this.client.getListAllSaveSets().get(i).getSaveSetName())){
                        find = true;
                    }else{
                        ++i;
                    }
                }while(i<size && !find);
                
                if(find){
                    this.jComboboxTypeSaveSet.setSelectedIndex(i+2);
                }
            }
        }  
        this.setVisible(true);
    }
    
    private void fillComboboxTypeSaveSets(){
        this.jComboboxTypeSaveSet.addItem("select a option");
        this.jComboboxTypeSaveSet.addItem("All");
        for(BackupsInformationOfOneSaveSet saveSet : this.client.getListAllSaveSets()){
            this.jComboboxTypeSaveSet.addItem(saveSet.getSaveSetName());
        }
        this.jComboboxTypeSaveSet.setEditable(true);
    }
    
    private boolean isFilledAll(){
        int index = this.jComboboxTypeSaveSet.getSelectedIndex();
        if(index == 0  && this.jComboboxTypeSaveSet.isEditable()){
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAction = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        jComboboxTypeSaveSet = new javax.swing.JComboBox<>();
        jLabelSaveSet = new javax.swing.JLabel();
        jLabelClient = new javax.swing.JLabel();
        jLabelClientName = new javax.swing.JLabel();
        jLabelDateTime = new javax.swing.JLabel();
        jLabelIncident = new javax.swing.JLabel();
        txtIncidence = new javax.swing.JTextField();
        txtDateTime = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImages(null);

        btnAction.setText("Accept");
        btnAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionActionPerformed(evt);
            }
        });

        txtAreaDescription.setColumns(20);
        txtAreaDescription.setRows(5);
        jScrollPane1.setViewportView(txtAreaDescription);

        jLabelSaveSet.setText("Save set:");

        jLabelClient.setText("Client:");

        jLabelClientName.setText("jLabel3");

        jLabelDateTime.setText("DateTime:");

        jLabelIncident.setText("Incidence:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelClient, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelIncident, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIncidence)
                            .addComponent(jLabelClientName, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                        .addGap(187, 187, 187)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSaveSet, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDateTime))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboboxTypeSaveSet, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
            .addGroup(layout.createSequentialGroup()
                .addGap(347, 347, 347)
                .addComponent(btnAction, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelDateTime)
                                    .addComponent(txtDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabelClient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboboxTypeSaveSet, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSaveSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtIncidence, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                        .addComponent(jLabelIncident, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAction, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionActionPerformed
        if(this.action == Action.DISABLE_CLIENT){
            this.client.getManageClient().disableClient("unknown");
            this.client.addNote(this.txtAreaDescription.getText(), this.txtDateTime.getText(), this.txtIncidence.getText(), " ", this.client.getName(), this.action);
            this.dispose();
            return;
        }
        if(this.action == Action.DISABLE_SAVE_SET){
            this.saveSet.setEnable(false);
            this.client.addNote(this.txtAreaDescription.getText(), this.txtDateTime.getText(), this.txtIncidence.getText(), this.saveSet.getSaveSetName(), this.client.getName(), this.action);
            this.dispose();
            return;
        }
        
        if(isFilledAll()){
            if(!this.jComboboxTypeSaveSet.isEditable()){
                this.client.addNote(this.txtAreaDescription.getText(), this.txtDateTime.getText(), this.txtIncidence.getText(), this.saveSet.getSaveSetName(), this.client.getName(), this.action);
                this.dispose(); 
                return;
            }
            int index = this.jComboboxTypeSaveSet.getSelectedIndex();
            ArrayList<BackupsInformationOfOneSaveSet> listSaveSets = this.client.getListAllSaveSets();
            String saveSet;
            if(index == 1){
                saveSet = "All"; 
            }else{
                saveSet = listSaveSets.get(index-2).getSaveSetName();
            }
            this.client.addNote(this.txtAreaDescription.getText(), this.txtDateTime.getText(), this.txtIncidence.getText(), saveSet, this.client.getName(), this.action);
            this.dispose();        // TODO add your handling code here:
            
        }else{
            //Unsupported
        }
        
    }//GEN-LAST:event_btnActionActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAction;
    private javax.swing.JComboBox<String> jComboboxTypeSaveSet;
    private javax.swing.JLabel jLabelClient;
    private javax.swing.JLabel jLabelClientName;
    private javax.swing.JLabel jLabelDateTime;
    private javax.swing.JLabel jLabelIncident;
    private javax.swing.JLabel jLabelSaveSet;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAreaDescription;
    private javax.swing.JTextField txtDateTime;
    private javax.swing.JTextField txtIncidence;
    // End of variables declaration//GEN-END:variables
}