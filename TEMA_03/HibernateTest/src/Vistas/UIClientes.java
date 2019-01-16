/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controladores.NewHibernateUtil;
import Modelos.Articulos;
import Modelos.Clientes;
import Modelos.Facturas;
import java.util.ArrayList;

import java.util.List;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Manuel
 */
public class UIClientes extends javax.swing.JDialog {

    private DefaultTableModel tablaClientes;

    private static String sql;
    private static Integer clienteActual;

    /**
     * Creates new form Clientes
     */
    public UIClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        iniciarVista();
        iniciarListenerTablas();
    }

    private void iniciarVista() {
//        tablaClientes = (DefaultTableModel) jt_clientes.getModel();
        System.out.println("Cargar Vista");
    }

    private void ejecutarHQL(String hql) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            // Abrimos la Session
            //  session.beginTransaction();

            // Realizamos la consulta HSQL
            Query q = session.createQuery(hql);
            List resultList = q.list();

            // Mostramos los datos en el JTable
            rellenarJTableClientes(resultList);

            // Cerramos la Session
            // session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void ejecutarHQLFacturas(String hql) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            // Abrimos la Session
            //  session.beginTransaction();

            // Realizamos la consulta HSQL
            Query q = session.createQuery(hql);
            List resultList = q.list();

            // Mostramos los datos en el JTable
            rellenarJTableFacturas(resultList);

            // Cerramos la Session
            // session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void rellenarJTableClientes(List resultList) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();

        tableHeaders.add("Codigo");
        tableHeaders.add("Nombre");

        for (Object c : resultList) {

            Clientes cli = (Clientes) c;

            Vector<Object> filaJTable = new Vector<Object>();

            filaJTable.add(cli.getCodigo());
            filaJTable.add(cli.getNombre());
            tableData.add(filaJTable);

        }

        jt_clientes.setModel(new DefaultTableModel(tableData, tableHeaders));
//        jt_clientes.getColumn(0).setMinWidth(30);
//        jt_clientes.getColumn(0).setMaxWidth(30);
//        
    }

    private void rellenarJTableFacturas(List resultList) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();

        tableHeaders.add("Codigo");
        tableHeaders.add("Nombre");

        for (Object c : resultList) {

            Clientes cli = (Clientes) c;

            Vector<String> facturaHeaders = new Vector<String>();
            Vector facturaData = new Vector();

            facturaHeaders.add("Num Factura");
            facturaHeaders.add("Fecha Factura");
            facturaHeaders.add("Cod Cliente");
            facturaHeaders.add("Total");
            facturaHeaders.add("Pagada");

            List<Facturas> lf = new ArrayList<Facturas>();
            lf.addAll(cli.getFacturases());

            for (Facturas f : lf) {

                Vector<Object> facturaFila = new Vector<Object>();

                facturaFila.add(f.getNfactura());
                facturaFila.add(f.getFecfactura());
                facturaFila.add(f.getClientes().getCodigo());
                facturaFila.add(f.getTotal());
                facturaFila.add(f.getPagada());

                facturaData.add(facturaFila);
            }

            jt_facturas.setModel(new DefaultTableModel(facturaData, facturaHeaders));

        }
    }

    private void iniciarListenerTablas() {
        jt_clientes.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting() && jt_clientes.getSelectedRow() != -1) {
                tablaClientes = (DefaultTableModel) jt_clientes.getModel();
                clienteActual = jt_clientes.getSelectedRow();
//                System.out.println("Valor vista facturas: " + clienteActual);
                jtf_codigo.setText(tablaClientes.getValueAt(clienteActual, 0).toString());
                jtf_nombre.setText(tablaClientes.getValueAt(clienteActual, 1).toString());
            }
        });

        jt_facturas.setAutoCreateRowSorter(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtf_codigo = new javax.swing.JTextField();
        jtf_nombre = new javax.swing.JTextField();
        jb_baja = new javax.swing.JButton();
        jb_alta1 = new javax.swing.JButton();
        jb_modificar = new javax.swing.JButton();
        jb_cliente_codigo = new javax.swing.JButton();
        jb_cliente_nombre = new javax.swing.JButton();
        jb_cliente_facturas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_clientes = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jt_facturas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Código");

        jLabel2.setText("Nombre");

        jb_baja.setText("Baja");
        jb_baja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_baja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_bajaActionPerformed(evt);
            }
        });

        jb_alta1.setText("Alta");
        jb_alta1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_alta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_alta1ActionPerformed(evt);
            }
        });

        jb_modificar.setText("Modificar");
        jb_modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_modificarActionPerformed(evt);
            }
        });

        jb_cliente_codigo.setText("Consulta por código");
        jb_cliente_codigo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_cliente_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_cliente_codigoActionPerformed(evt);
            }
        });

        jb_cliente_nombre.setText("Consulta por nombre");
        jb_cliente_nombre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_cliente_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_cliente_nombreActionPerformed(evt);
            }
        });

        jb_cliente_facturas.setText("Consulta facturas asociadas");
        jb_cliente_facturas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_cliente_facturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_cliente_facturasActionPerformed(evt);
            }
        });

        jt_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jt_clientes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jt_clientes);

        jt_facturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NFactura", "FechaFactura", "Cliente", "Total", "Pagada"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jt_facturas.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jt_facturas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtf_codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jtf_nombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jb_cliente_codigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_cliente_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jb_alta1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jb_baja)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jb_modificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jb_cliente_facturas, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jb_cliente_codigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtf_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jb_cliente_nombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jb_baja)
                    .addComponent(jb_alta1)
                    .addComponent(jb_modificar)
                    .addComponent(jb_cliente_facturas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jb_alta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_alta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_alta1ActionPerformed

    private void jb_bajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_bajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_bajaActionPerformed

    private void jb_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_modificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_modificarActionPerformed

    private void jb_cliente_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_cliente_codigoActionPerformed
        if (jtf_codigo.getText().equals("")) {
            sql = "from Clientes";
        } else {
            sql = "from Clientes where codigo = " + jtf_codigo.getText().trim();
        }
        ejecutarHQL(sql);
    }//GEN-LAST:event_jb_cliente_codigoActionPerformed

    private void jb_cliente_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_cliente_nombreActionPerformed
        sql = "from Clientes where nombre like '%" + jtf_nombre.getText().trim() + "%'";
        ejecutarHQL(sql);
    }//GEN-LAST:event_jb_cliente_nombreActionPerformed

    private void jb_cliente_facturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_cliente_facturasActionPerformed
        if (!jtf_codigo.getText().equals("")) {
            sql = "from Clientes where codigo = " + jtf_codigo.getText().trim();
            ejecutarHQLFacturas(sql);
        } else if (!jtf_nombre.getText().equals("")) {
            sql = "from Clientes where nombre like '%" + jtf_nombre.getText().trim() + "%'";
            ejecutarHQLFacturas(sql);
        }
    }//GEN-LAST:event_jb_cliente_facturasActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIClientes dialog = new UIClientes(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jb_alta1;
    private javax.swing.JButton jb_baja;
    private javax.swing.JButton jb_cliente_codigo;
    private javax.swing.JButton jb_cliente_facturas;
    private javax.swing.JButton jb_cliente_nombre;
    private javax.swing.JButton jb_modificar;
    private javax.swing.JTable jt_clientes;
    private javax.swing.JTable jt_facturas;
    private javax.swing.JTextField jtf_codigo;
    private javax.swing.JTextField jtf_nombre;
    // End of variables declaration//GEN-END:variables
}
