/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_07;

import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jaidis
 */
public class Ejercicio_07 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        try {
            
            String url = "/home/jaidis/NetBeansProjects/Ejercicio_07/src/ejercicio_07/Datos.dat";

            File fichero = new File(url);

            Alumno alumno_temp;

            FileInputStream f_in = new FileInputStream(fichero);
            ObjectInputStream of_in = new ObjectInputStream(f_in);

            try {

                alumno_temp = (Alumno) of_in.readObject();

                while (alumno_temp != null) {
                    System.out.println("Matricula: " + alumno_temp.getNumeroMatricula());
                    System.out.println("Nombre: " + alumno_temp.getNombre());
                    System.out.println("Nota 1ª Evaluación: " + alumno_temp.getNota_1());
                    System.out.println("Nota 2ª Evaluación: " + alumno_temp.getNota_2());
                    System.out.println("Nota Final Evaluación: " + alumno_temp.getNota_Final() + "\n");
                    alumno_temp = (Alumno) of_in.readObject();
                }

            } catch (EOFException e) {
                JOptionPane.showMessageDialog(null, "Fichero finalizado");
            }
            
            of_in.close();
            f_in.close();
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo no existe");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error: " + e);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error de importación de clase: " + e);
        }

    }

}
