/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_01;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jaidis
 */
public class Vista extends javax.swing.JFrame {

    private DefaultTableModel tabla;

    private static Connection conexion;

//    private static String path = "jdbc:sqlite:/home/jaidis/Escritorio/a.png";
    private static String path = "jdbc:sqlite:/home/jaidis/NetBeansProjects/0 - Ejercicios/TEMA_02/Ejercicio_01/facturas.sqlite";

    private static Statement sentencia;
    private static ResultSet resultado;
    private static int NumRegistros;

    /**
     * Creates new form Vista
     */
    public Vista() {
        initComponents();

        tabla = (DefaultTableModel) jt_clientes.getModel();
        /**
         * @todo El try catch de la conexión esta escrito de gratis, no salta la
         * excepción aunque la ruta sea incorrecta
         */
        try {
            conexion = DriverManager.getConnection(path);
            dibujarTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void nuevoCliente() {

        try {

            sentencia = conexion.createStatement();
            Integer id;
            try {
                id = Integer.parseInt(jtf_id.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "ID cliente", JOptionPane.WARNING_MESSAGE);
                id = 1;
            }
            String dni = jtf_dni.getText();
            String nombre = jtf_nombre.getText();
            String fecha = jtf_fecha.getText();

            NumRegistros = sentencia.executeUpdate("INSERT INTO clientes VALUES (" + id + ",'" + dni + "','" + nombre + "','" + fecha + "')");

            resultado.close();
            sentencia.close();

            JOptionPane.showMessageDialog(this, NumRegistros, "Insertar Cliente", JOptionPane.WARNING_MESSAGE);

            dibujarTabla();

            limpiarCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + " - " + e.getErrorCode() + " - " + e.getSQLState(), "Insertar Cliente", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void modificarCliente() {

        try {

            sentencia = conexion.createStatement();
            Integer id;
            try {
                id = Integer.parseInt(jtf_id.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "ID cliente", JOptionPane.WARNING_MESSAGE);
                id = 1;
            }
            String dni = jtf_dni.getText();
            String nombre = jtf_nombre.getText();
            String fecha = jtf_fecha.getText();

            NumRegistros = sentencia.executeUpdate("UPDATE clientes SET dni = '" + dni + "', nombre = '" + nombre + "', fecnac = '" + fecha + "' WHERE codigo = " + id);

            resultado.close();
            sentencia.close();

            JOptionPane.showMessageDialog(this, NumRegistros, "Modificar Cliente", JOptionPane.WARNING_MESSAGE);

            dibujarTabla();
            limpiarCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + " - " + e.getErrorCode() + " - " + e.getSQLState(), "Modificar Cliente", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void borrarCliente() {

        try {

            sentencia = conexion.createStatement();
            Integer id;
            try {
                id = Integer.parseInt(jtf_id.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "ID cliente", JOptionPane.WARNING_MESSAGE);
                id = 1;
            }

            NumRegistros = sentencia.executeUpdate("DELETE FROM clientes WHERE codigo =" + id);

            resultado.close();
            sentencia.close();

            JOptionPane.showMessageDialog(this, NumRegistros, "Borrar Cliente", JOptionPane.WARNING_MESSAGE);

            dibujarTabla();
            limpiarCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + " - " + e.getErrorCode() + " - " + e.getSQLState(), "Borrar Cliente", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void dibujarTabla() {
        try {

            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM Clientes");

            String[] fila = new String[4];
            tabla.setRowCount(0);

            while (resultado.next()) {
                //System.out.println(resultado.getInt(1) + " " + resultado.getString(2) + " " + resultado.getString(3));

                fila[0] = resultado.getInt(1) + "";
                fila[1] = resultado.getString(2);
                fila[2] = resultado.getString(3);
                fila[3] = resultado.getString(4);

                tabla.addRow(fila);
            }

            resultado.close();
            sentencia.close();

            jt_clientes.setAutoCreateRowSorter(true);

            jt_clientes.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
                if (!event.getValueIsAdjusting() && jt_clientes.getSelectedRow() != -1) {
                    //String x = tabla.getValueAt(jt_clientes.getSelectedRow(), 0) + " - " + jt_clientes.getValueAt(jt_clientes.getSelectedRow(), 1) + " - " + jt_clientes.getValueAt(jt_clientes.getSelectedRow(), 2) + " - " + jt_clientes.getValueAt(jt_clientes.getSelectedRow(), 3);
                    //System.out.println(x);
                    jtf_id.setText(tabla.getValueAt(jt_clientes.getSelectedRow(), 0).toString());
                    jtf_dni.setText(tabla.getValueAt(jt_clientes.getSelectedRow(), 1).toString());
                    jtf_nombre.setText(tabla.getValueAt(jt_clientes.getSelectedRow(), 2).toString());
                    jtf_fecha.setText(tabla.getValueAt(jt_clientes.getSelectedRow(), 3).toString());
                }
            });

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Cargando tabla", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCampos() {
        jtf_id.setText("");
        jtf_dni.setText("");
        jtf_nombre.setText("");
        jtf_fecha.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtf_id = new javax.swing.JTextField();
        jtf_dni = new javax.swing.JTextField();
        jtf_nombre = new javax.swing.JTextField();
        jtf_fecha = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_clientes = new javax.swing.JTable();
        jb_nuevo = new javax.swing.JButton();
        jb_refrescar = new javax.swing.JButton();
        jb_borrar = new javax.swing.JButton();
        jb_modificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Id del cliente");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Dni");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nombre completo");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Fecha nacimiento");

        jtf_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jtf_dni.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jtf_nombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jtf_fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtf_id)
                            .addComponent(jtf_dni)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtf_fecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jtf_nombre))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtf_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtf_dni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtf_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jt_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "Dni", "Nombre", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jt_clientes);

        jb_nuevo.setText("Añadir cliente");
        jb_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_nuevoActionPerformed(evt);
            }
        });

        jb_refrescar.setText("Refrescar tabla");
        jb_refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_refrescarActionPerformed(evt);
            }
        });

        jb_borrar.setText("Borrar cliente");
        jb_borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_borrarActionPerformed(evt);
            }
        });

        jb_modificar.setText("Modificar cliente");
        jb_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_modificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jb_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jb_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jb_borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jb_refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jb_nuevo)
                            .addComponent(jb_modificar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jb_borrar)
                            .addComponent(jb_refrescar))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jb_refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_refrescarActionPerformed
        dibujarTabla();
    }//GEN-LAST:event_jb_refrescarActionPerformed

    private void jb_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_nuevoActionPerformed
        nuevoCliente();
    }//GEN-LAST:event_jb_nuevoActionPerformed

    private void jb_borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_borrarActionPerformed
        borrarCliente();
    }//GEN-LAST:event_jb_borrarActionPerformed

    private void jb_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_modificarActionPerformed
        modificarCliente();
    }//GEN-LAST:event_jb_modificarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            conexion.close();
            JOptionPane.showMessageDialog(this, "See you space cowboy", "Cerrando programa", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Cerrando programa", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jb_borrar;
    private javax.swing.JButton jb_modificar;
    private javax.swing.JButton jb_nuevo;
    private javax.swing.JButton jb_refrescar;
    private javax.swing.JTable jt_clientes;
    private javax.swing.JTextField jtf_dni;
    private javax.swing.JTextField jtf_fecha;
    private javax.swing.JTextField jtf_id;
    private javax.swing.JTextField jtf_nombre;
    // End of variables declaration//GEN-END:variables
}
