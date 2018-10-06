/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_06;
import java.io.*;
import java.util.Locale;

/**
 *
 * @author jaidis
 */
public class Ejercicio_06 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String url = "/home/jaidis/NetBeansProjects/Ejercicio_06/src/ejercicio_06/Datos.dat";
       
        File fichero = new File(url);

        FileOutputStream f_out = new FileOutputStream(fichero,true);
        
        for(int i = 0; i <= 100; i++){
            f_out.write(i);            
        }
        
        f_out.close();
        
        
        
        FileInputStream f_input = new FileInputStream(fichero);
        
        System.out.println(f_input.available());

        Integer temp = 0;
        
        while ((temp = f_input.read()) != -1)
        {
            System.out.println(temp);
        }
        
        f_input.close();
        
    }
    
}
