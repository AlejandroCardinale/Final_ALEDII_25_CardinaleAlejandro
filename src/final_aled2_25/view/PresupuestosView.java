/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package final_aled2_25.view;

import final_aled2_25.model.dao.PresupuestosDAO;
import final_aled2_25.model.dao.JdbcPresupuestosDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author aleja
 */
public class PresupuestosView extends javax.swing.JFrame {
    
    
    //GLOBALES o CAMPOS
    private final PresupuestosDAO dao = new JdbcPresupuestosDAO();
    private DefaultTableModel model;
    private boolean cargando = false;
    
    

    /**
     * Creates new form PresupuestosView
     */
    public PresupuestosView() {
        initComponents();
        setTitle("Mecanica");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // cierra solo la ventana 
        
        configurarTabla();
        cargarComboVehiculos();
        refrescar();       
        
    }
    
    //MIS METODOS
    
    private void configurarTabla(){
        model = new DefaultTableModel (new Object[][]{}, new String[]{
            "Tipo","Descripcion","Cant/Horas","Precio","Subtotal"
        }){
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    tblDetalle.setModel(model);
    tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void cargarComboVehiculos(){
        cargando = true;
        try{
            cboVehiculo.removeAllItems();
            for (String dom : dao.vehiculosConItems()) cboVehiculo.addItem(dom);
            if(cboVehiculo.getItemCount()>0 && cboVehiculo.getSelectedIndex()<0){
                cboVehiculo.setSelectedIndex(0);
            }
        } finally {
            cargando = false;
        }
    }
    
    //LOGICA
    
    private String domSel() {
        Object it = cboVehiculo.getSelectedItem();
        return it == null ? "" : it.toString();
    }
    
    private void refrescar(){
        model.setRowCount(0);
        String dom = domSel();
        if(dom.isEmpty()){
            setTotales(BigDecimal.ZERO, BigDecimal.ZERO);
            lblEstado.setText("Estado: -");
            btnCompletar.setEnabled(false);
            return;
        }
        
        //asegurarnos fila de presupuesto y recalcular totales desde nuestro item
        
        dao.asegurarPresupuesto(dom);
        List<Object[]> filas = dao.itemsPorVehiculo(dom);
        for ( Object[] f : filas) model.addRow(f);
        
        BigDecimal tCh = dao.totalChapa(dom).setScale(2,RoundingMode.HALF_UP);        
        BigDecimal tMe = dao.totalMecanica(dom).setScale(2,RoundingMode.HALF_UP);    
        
        dao.actualizarTotales(dom,tCh, tMe); //guardar los totales en la tabla
        setTotales(tCh,tMe);
        
        String estado = dao.obtenerEstado(dom);
        if (estado == null) estado = "BORRADOR"; //por las dudas siempre validamos
        lblEstado.setText ( "Estado: "+ estado);
        btnCompletar.setEnabled(!"COMPLETO".equalsIgnoreCase(estado));
        
    }
    
    private void setTotales (BigDecimal totalChapa, BigDecimal totalMecanica) {
        BigDecimal totalGeneral = totalChapa.add(totalMecanica).setScale(2, RoundingMode.HALF_UP);
        lblTotalChapa.setText("$ "+ totalChapa);
        lblTotalMecanica.setText("$ "+ totalMecanica);
        lblTotalGeneral.setText("$ "+ totalGeneral);
    }
    
    private void exportarCSV(){
        if(model.getRowCount() ==0){
            JOptionPane.showMessageDialog(this, "No hay datos para exportar");
            return;
        }
        
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new java.io.File("Preupuesto_"+ domSel() + ".csv"));
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try(FileWriter w = new FileWriter(fc.getSelectedFile())){
                //encabezados
                for (int c=0;c<model.getColumnCount();c++){
                    if(c>0) w.write(";");
                    w.write(model.getColumnName(c));
                }
                w.write("\n");
                //filas
                for(int r=0;r<model.getRowCount();r++){
                    for(int c=0;c<model.getColumnCount();c++){
                        if (c>0) w.write(";");
                        Object val = model.getValueAt(r, c);
                        w.write(val==null? "" : val.toString());
                    }
                    w.write("\n");
                }
                //totales y el estado
                w.write("\n");
                w.write(lblEstado.getText()+"\n");
                w.write(lblTotalChapa.getText()+"\n");
                w.write(lblTotalMecanica.getText()+"\n");
                w.write(lblTotalGeneral.getText()+"\n");
                JOptionPane.showMessageDialog(this, "Exportado OK");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Error exportando: "+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalir = new javax.swing.JButton();
        cboVehiculo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblTotalChapa = new javax.swing.JLabel();
        lblTotalMecanica = new javax.swing.JLabel();
        lblTotalGeneral = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnRecalcular = new javax.swing.JButton();
        btnCompletar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        btnSalir1 = new javax.swing.JButton();

        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cboVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVehiculoActionPerformed(evt);
            }
        });

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDetalle);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Total General: ");

        jLabel2.setText("Total Mecanica:");

        jLabel3.setText("Total Chapa:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotalChapa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(lblTotalGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalMecanica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 7, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTotalChapa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(lblTotalMecanica, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        btnExportar.setText("EXPORTAR");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        jLabel4.setText("Vehiculo:");

        btnRecalcular.setBackground(new java.awt.Color(255, 255, 51));
        btnRecalcular.setText("RECALCULAR");
        btnRecalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecalcularActionPerformed(evt);
            }
        });

        btnCompletar.setBackground(new java.awt.Color(51, 255, 51));
        btnCompletar.setText("COMPLETAR");
        btnCompletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompletarActionPerformed(evt);
            }
        });

        jLabel5.setText("PRESUPUESTOS--TALLER MINGO");

        btnSalir1.setBackground(new java.awt.Color(255, 0, 0));
        btnSalir1.setText("SALIR");
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnRecalcular)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCompletar))
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cboVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jLabel5)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSalir1)
                .addGap(155, 155, 155))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCompletar)
                            .addComponent(btnExportar)
                            .addComponent(btnRecalcular)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37)
                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompletarActionPerformed
        // TODO add your handling code here:
        String dom = domSel();
        if (dom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehiculo");
            return;
        }
        if(JOptionPane.showConfirmDialog(this, "desea marcar COMPLETO el presupuesto de " +dom + "?"
                , "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            try{
                java.math.BigDecimal tCh = dao.totalChapa(dom);
                java.math.BigDecimal tMe = dao.totalMecanica(dom);
                dao.actualizarTotales(dom,tCh, tMe);
                dao.marcarCompleto(dom);
                refrescar();
                JOptionPane.showMessageDialog(this, "Presupuesto marcado como COMPLETO");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }        
    }//GEN-LAST:event_btnCompletarActionPerformed

    private void btnRecalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecalcularActionPerformed
        // TODO add your handling code here:
        refrescar();
    }//GEN-LAST:event_btnRecalcularActionPerformed

    private void cboVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboVehiculoActionPerformed
        // TODO add your handling code here:
        if(cargando) return;
        refrescar();
    }//GEN-LAST:event_cboVehiculoActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        exportarCSV();
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PresupuestosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PresupuestosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PresupuestosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PresupuestosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PresupuestosView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompletar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnRecalcular;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JComboBox<String> cboVehiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblTotalChapa;
    private javax.swing.JLabel lblTotalGeneral;
    private javax.swing.JLabel lblTotalMecanica;
    private javax.swing.JTable tblDetalle;
    // End of variables declaration//GEN-END:variables
}
