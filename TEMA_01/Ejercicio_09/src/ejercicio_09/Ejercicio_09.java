/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_09;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.transform.*;

/**
 *
 * @author jaidis
 */
public class Ejercicio_09 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        try {

            File fichero = new File("catalogo1.xml");
            DocumentBuilder builder;
            Document doc;
            if (fichero.exists()) {
                builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                doc = builder.parse(fichero);

                Element nodoRaiz = doc.getDocumentElement();
                NodeList hijosN1 = nodoRaiz.getElementsByTagName("CD");

                Node nodoHijoN1;

                for (int i = 0; i < hijosN1.getLength(); i++) {
                    nodoHijoN1 = (Node) hijosN1.item(i);

                    if (nodoHijoN1 instanceof Node) {

                        System.out.println("Etiqueta del nodo: " + nodoHijoN1.getNodeName());
                        System.out.println("Valor de la etiqueta: " + nodoHijoN1.getTextContent());
                        System.out.println("Tipo de nodo: " + nodoHijoN1.getNodeType());
                        System.out.println("Padre del nodo: " + nodoHijoN1.getParentNode() + "\n");

                    }

                }

            } else {
                System.out.println("El fichero XML no es accesible");
            }

        } catch (ParserConfigurationException e) {
            System.out.println("Error al realizar el parseo del archivo XML \n" + e);
        } catch (SAXException e) {
            System.out.println("Explotó \n" + e);
        } catch (FileNotFoundException e) {
            System.out.println("No es posible acceder al XML \n" + e);
        } catch (IOException e) {
            System.out.println("Error en la operación entrada/salida \n" + e);
        }

    }

}

//                nodoHijoN1 = (Node) hijosN1.item(i);
//                System.out.println(nodoHijoN1.getTextContent());
//
//                if (nodoHijoN1.getNodeType() == Node.TEXT_NODE)
//                {
//                    System.out.println(nodoHijoN1.getNodeValue());
//                }
//
