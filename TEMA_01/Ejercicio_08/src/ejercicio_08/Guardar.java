/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_08;

import ejercicio_08.Alumno;
import java.io.*;

/**
 *
 * @author jaidis
 */
public class Guardar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here

        /* A tener en cuenta:
            - La posición en memoria es un puñetero Long
            - Se ha de mover el puntero a la dirección de memoria desde donde se
            quiere guardar la información
            - No existe forma de guardar un String, así que es necesario 
            convertir la información a un Array de Char ?! y guardarlo con el 
            metodo correspondiente.
         */
        try {
            File f = new File("Alumnos.dat");
            RandomAccessFile raf = new RandomAccessFile(f, "rw");

            Alumno a = new Alumno(1, 5.0);

            raf.seek((1 - 1) * 12);
            raf.writeInt(a.getId_matricula());
            raf.writeDouble(a.getNota_1());

            Alumno b = new Alumno(5, 7.0);

            raf.seek((5 - 1) * 12);
            raf.writeInt(b.getId_matricula());
            raf.writeDouble(b.getNota_1());

            Alumno c = new Alumno(8, 8.0);

            raf.seek((8 - 1) * 12);
            raf.writeInt(c.getId_matricula());
            raf.writeDouble(c.getNota_1());

            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("No es posible acceder al fichero");
        }

    }

}
