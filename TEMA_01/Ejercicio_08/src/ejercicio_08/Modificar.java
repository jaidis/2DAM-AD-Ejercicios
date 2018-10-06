/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_08;

import java.io.*;

/**
 *
 * @author jaidis
 */
public class Modificar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        try {
            if (args.length == 1) {
                Integer valor = new Integer(Integer.parseInt(args[0]));

                File f = new File("Alumnos.dat");
                RandomAccessFile raf = new RandomAccessFile(f, "r");

                raf.seek((valor - 1) * 12);
                System.out.println("Matricula: " + raf.readInt());
                System.out.println("Nota: " + raf.readDouble());

                raf.close();
            } else {
                System.out.println("Sin argumento");
            }

        } catch (FileNotFoundException e) {
            System.out.println("No es posible acceder al fichero");
        } catch (EOFException e) {
            System.out.println("Fin del fichero");
        } catch (IOException e) {
            System.out.println("Indice inválido");
        }
    }

}
