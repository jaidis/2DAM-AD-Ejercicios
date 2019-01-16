/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_02;

import java.io.*;

/**
 *
 * @author jaidis
 */
public class Ejercicio_02 {

    /**
     * @param args the command line arguments
     * @TODO Pasar como parámetro el fichero, sino cogerá el propio fichero de
     * java
     * @TODO Comprobar que el fichero existe y se puede leer
     * @TODO Realizar la variante de leer desde un buffer, de esta forma permite
     * leer linea a linea. (guardar en un string, comprobar si es distinto de
     * null)
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {
        String fichero = args[0];
        int i;
        //Declarar el fichero
        File file_fichero = new File(fichero);
        if (file_fichero.exists()) {
            //Abrir un flujo de entrada
            FileReader file_read = new FileReader(file_fichero);
            //Se lee el fichero caracter a caracter
            while ((i = file_read.read()) != -1) {
                System.out.print((char) i);
            }
            //Se cierra el fichero
            file_read.close();
        } else {
            System.out.println("El fichero no se puede leer");
        }

    }

}
