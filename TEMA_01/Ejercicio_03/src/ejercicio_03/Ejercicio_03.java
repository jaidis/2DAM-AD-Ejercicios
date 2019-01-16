/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_03;

import java.io.*;

/**
 *
 * @author jaidis
 */
public class Ejercicio_03 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {

        //String url = args[0];
        String url = "/home/jaidis/NetBeansProjects/0 - Ejercicios/TEMA_01/Ejercicio_02/src/ejercicio_02/Ejercicio_02.java";

        File fichero = new File(url);
        FileReader lector = new FileReader(fichero);
        BufferedReader lectura = new BufferedReader(lector);

        FileWriter fich = new FileWriter("leeme.txt");
        BufferedWriter f_in = new BufferedWriter(fich);

        String linea;

        while ((linea = lectura.readLine()) != null) {
            f_in.write(linea);
            f_in.newLine();
        }

        f_in.close();
        fich.close();
        lectura.close();
        lector.close();

    }

}
