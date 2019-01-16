/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_04;

import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jaidis
 */
public class Ejercicio_04 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {

        //String url = "/home/jaidis/NetBeansProjects/0 - Ejercicios/TEMA_01/Ejercicio_02/src/ejercicio_02/Ejercicio_02.java";
        try {

            String url = "";

            if (args.length == 1) {
                url = args[0];
                File fichero = new File(url);
                FileReader lector = new FileReader(fichero);

                if (fichero.exists() && fichero.isFile() && fichero.length() > 0) {
                    String extension = comprobarExtension(fichero);

                    if (extension.equals("java")) {

                        String nombre = comprobarNombre(fichero);

                        FileWriter destino = new FileWriter(nombre + ".cod");
                        int caracter = 0;

                        while ((caracter = lector.read()) != -1) {
                            if (caracter >= 10) {
                                destino.write((char) caracter / 10 + caracter);
                            } else {
                                destino.write((char) caracter);
                            }
                        }

                        destino.close();
                        lector.close();
                    } else {
                        //throw new IOException("La extensión no es válida");
                        JOptionPane.showMessageDialog(null, "La extensión no es válida");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parámetros no admitidos");
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo no existe");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error: " + e);
        }

    }

    public static String comprobarExtension(File fichero) {

        String extension = "";
        String nombre = fichero.getName();
        int i = nombre.lastIndexOf('.');

        if (i > 0) {
            extension = nombre.substring(i + 1);
        }

        return extension;

    }

    public static String comprobarNombre(File fichero) {

        String nombreLimpio = "";
        String nombre = fichero.getName();
        int i = nombre.lastIndexOf('.');

        if (i > 0) {
            nombreLimpio = nombre.substring(0, i);
        }

        return nombreLimpio;

    }

}
/*
Modal, mientras que exista un dialogo no se puede pasar a la otra pantalla

Para crear una GUI solo se admite un JFrame, para el resto de pantallas se 
utiliza los JDialog (JOPtionPane).

JOptionPane.showMessageDialog(null, "Parámetros no admitidos", "Titulo", JOptionPane.WARNING_MESSAGE);
JOptionPane.showConfirmDialog(null, "¿Estás seguro?"); return a integer

 */
