package Vistas;

import Controladores.NewHibernateUtil;
import Modelos.Articulos;
import Modelos.Clientes;

import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author jaidis
 */
public class UIClientes extends javax.swing.JFrame {
    
    private static String ins;
    
    public UIClientes() {
        initComponents();
    }
    
    private void rellenarJTable(List resultList) {
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
      
        jTableCli.setModel(new DefaultTableModel(tableData, tableHeaders));
//        jTableCli.getColumn(0).setMinWidth(30);
//        jTableCli.getColumn(0).setMaxWidth(30);
//        
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
            rellenarJTable(resultList);
            
            // Cerramos la Session
           // session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        tbConsxCodigo = new javax.swing.JButton();
        tbConsxNombre = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCli = new javax.swing.JTable();
        btAlta = new javax.swing.JButton();
        btBaja = new javax.swing.JButton();
        btModificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Nombre");

        jLabel1.setText("Codigo");

        tbConsxCodigo.setText("Consulta x Codigo");
        tbConsxCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbConsxCodigoActionPerformed(evt);
            }
        });

        tbConsxNombre.setText("Consulta x Nombre");
        tbConsxNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbConsxNombreActionPerformed(evt);
            }
        });

        jTableCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Codigo", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableCli);

        btAlta.setText("Alta");
        btAlta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAltaActionPerformed(evt);
            }
        });

        btBaja.setText("Baja");
        btBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBajaActionPerformed(evt);
            }
        });

        btModificar.setText("Modificar");
        btModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)
                                .addComponent(tbConsxCodigo))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tbConsxNombre)))
                        .addGap(25, 25, 25)
                        .addComponent(btAlta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btBaja)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btModificar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbConsxCodigo)
                    .addComponent(btAlta)
                    .addComponent(btModificar)
                    .addComponent(btBaja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbConsxNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbConsxCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbConsxCodigoActionPerformed
        ins = "from Clientes where codigo = " + txtCodigo.getText().trim();
        ejecutarHQL(ins); 
    }//GEN-LAST:event_tbConsxCodigoActionPerformed

    private void tbConsxNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbConsxNombreActionPerformed
        ins = "from Clientes where nombre like '%" + txtNombre.getText().trim() + "%'";
        ejecutarHQL(ins); 
    }//GEN-LAST:event_tbConsxNombreActionPerformed

    private void btAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAltaActionPerformed
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            // Abrimos la Session
            session.beginTransaction();
            
            Clientes cli = new Clientes();
            cli.setCodigo(Integer.parseInt(txtCodigo.getText().trim()));
            cli.setNombre(txtNombre.getText().trim());
            
            session.save(cli);
            
            // Cerramos la Session
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        } finally {
            session.close();
        }
    }//GEN-LAST:event_btAltaActionPerformed

    private void btBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBajaActionPerformed
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            // Abrimos la Session
            session.beginTransaction();
            
            Clientes cli = (Clientes)session.get(Clientes.class, Long.parseLong(txtCodigo.getText().trim())); 
            session.delete(cli);             
            
            // Cerramos la Session
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        } finally {
            session.close();
        }
        
    }//GEN-LAST:event_btBajaActionPerformed

    private void btModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btModificarActionPerformed
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            // Abrimos la Session
            session.beginTransaction();
            
            Clientes cli = (Clientes)session.get(Clientes.class, Long.parseLong(txtCodigo.getText().trim())); 
            cli.setNombre(txtNombre.getText().trim());
            session.update(cli);             
            
            // Cerramos la Session
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        } finally {
            session.close();
        }
    }//GEN-LAST:event_btModificarActionPerformed

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
            java.util.logging.Logger.getLogger(UIClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UIClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlta;
    private javax.swing.JButton btBaja;
    private javax.swing.JButton btModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCli;
    private javax.swing.JButton tbConsxCodigo;
    private javax.swing.JButton tbConsxNombre;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
