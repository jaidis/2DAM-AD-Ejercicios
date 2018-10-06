/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_07;

import java.io.*;
import javax.swing.JOptionPane;
import ejercicio_07.Alumno;

/**
 *
 * @author jaidis
 */
public class Ejercicio_07_Guardar {

    public static void main(String[] args) throws IOException, FileNotFoundException {

        try {
            String url = "/home/jaidis/NetBeansProjects/Ejercicio_07/src/ejercicio_07/Datos.dat";

            File fichero = new File(url);

            Alumno alumno_temp;

            FileOutputStream f_out = new FileOutputStream(fichero, false);
            ObjectOutputStream of_out = new ObjectOutputStream(f_out);

            alumno_temp = new Alumno(1, "Juan", 5.0, 6.0, 5.5);
            of_out.writeObject(alumno_temp);

            alumno_temp = new Alumno(2, "Miguel", 9.0, 9.0, 9.5);
            of_out.writeObject(alumno_temp);

            alumno_temp = new Alumno(3, "Jaime", 7.0, 7.0, 7.0);
            of_out.writeObject(alumno_temp);

            of_out.close();
            f_out.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo no existe");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error: " + e);
        }

    }

}
