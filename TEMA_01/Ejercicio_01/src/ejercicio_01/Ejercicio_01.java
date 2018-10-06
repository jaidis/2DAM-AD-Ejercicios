/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_01;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author jaidis
 */
public class Ejercicio_01 {

    /**
     * @param args the command line arguments
     */
    
    public static Integer nivel = 0;
    public static String espacios = "";
    
    public static void main(String[] args) {
        
        // System.out.println("separator = "+File.separator);
        // System.out.println("pathSeparator = "+File.pathSeparator);
        
        // Detectar si se le pasa un parametro, en caso de que sea así utilizar
        // dicha ruta 
        
        File f = new File (".");
        
        //String[] directorios;
        ArrayList myList = new ArrayList();
        
        if (args.length > 0 ){
            System.out.println("Argumentos: " + args[0]);
            f = null;
            f = new File (args[0]);
            System.out.println("getAbsolutePath: " + f.getAbsolutePath());

        }
        
        System.out.println("Directorio actual: " + f.getAbsolutePath() + "\n");
               
        String[] archivos = f.list();
        
        for (int i = 0; i <archivos.length; i++){
            //System.out.println(archivos[i]);
            f = new File (archivos[i]);
            //System.out.println("Nombre del elemento: " + f.getName());
            if (f.isDirectory()) 
            {
                //System.out.println(f.getName() + " es un directorio  \n"); 
                
                // Si es un directorio se accede a él y se muestra sus datos
                // recursivamente 
               
                System.out.println(espacios + f.getName());
                listado(f);
                
                //myList.add(f.getAbsolutePath());
 
            }
            else{
                espacios = "";
                System.out.println(espacios + f.getName());
            }
            //else
              //  System.out.println(f.getName() + " es un fichero");
            
            //System.out.println("Tamaño: " + f.length());    
            //System.out.println("Path largo: " + f.getAbsolutePath() + "\n");

        }
        
        //System.out.println("Directorios detectados: " + myList.size());
        
        String[] x = (String[]) myList.toArray(new String[0]);
        
        /*
        for (int i = 0; i <x.length; i++){
             //System.out.println(x[i]);
             busqueda(x[i]);
        }
        */
    }
    
    public static void listado(File f){
        nivel++;
        espacios="";
        for (int i=0; i <= nivel; i++){
            espacios = espacios + "-";
        }
        
        //System.out.println("Contenido del directorio"); 
        File directory = new File (f.getAbsolutePath());
        File[] files = directory.listFiles();
        //System.out.println(espacios+" - Directorio : "+ directory.getName()); 
              
        for (File file : files) {
            //System.out.println(file.getName());
            if (file.isDirectory()){
                //nivel++
                System.out.println(espacios + file.getName());
                listado(file);
            }
            else{
                System.out.println(espacios + file.getName());
            }
            //espacios="";
            /*
                for (int i=0; i<=nivel; i++){
                    System.out.println(file.getName());
                }
            */
            
        }
    
    }
    
    public static void busqueda(String args){
        File temp = new File (args);
        System.out.println(temp.getAbsolutePath());
        String[] temp_archivos = temp.list();
        for (int i = 0; i <temp_archivos.length; i++){
            temp = new File (temp_archivos[i]);
            System.out.println(temp.getName()); 
        }
    }
    
}


