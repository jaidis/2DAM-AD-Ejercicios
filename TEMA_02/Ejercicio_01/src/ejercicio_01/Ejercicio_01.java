/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_01;

/**
 *
 * @author jaidis
 */
public class Ejercicio_01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            Class.forName("org.sqlite.JDBC");
            Vista vista = new Vista();
            vista.setVisible(true);

        } catch (ClassNotFoundException e) {
            System.out.println("Error cargando driver" + e.getMessage());
        }

    }

}
