/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_10;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author jaidis
 */
public class Ejercicio_10 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        try {

            File fichero = new File("catalogo2.xml");
            DocumentBuilder builder;
            Document doc;
            if (fichero.exists()) {
                builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                doc = builder.parse(fichero);

                Element nodoRaiz = doc.getDocumentElement();
                NodeList hijosN1 = nodoRaiz.getElementsByTagName("CD");

                Node nodoHijoN1;
                Node nodoHijoN2;

                for (int i = 0; i < hijosN1.getLength(); i++) {
                    
                    

                    nodoHijoN1 = (Node) hijosN1.item(i);
                    
                    if (nodoHijoN1.hasAttributes())
                    {
                        NamedNodeMap atributos = nodoHijoN1.getAttributes();
                        
                        for (int x=0; x<atributos.getLength(); x++)
                        {
                            System.out.println(atributos.item(x).getNodeName() + " - " + atributos.item(x).getNodeValue());
                        }
                        
                    }

                    NodeList hijosN2 = nodoHijoN1.getChildNodes();

                    if (nodoHijoN1 instanceof Node) {
                        System.out.println(nodoHijoN1.getNodeName());
                        for (int j = 0; j < hijosN2.getLength(); j++) {
                            nodoHijoN2 = (Node) hijosN2.item(j);
                            if (nodoHijoN2.getNodeType() == Node.ELEMENT_NODE) {
                                System.out.println("\t Etiqueta \"" + nodoHijoN2.getNodeName() + "\" Valor \"" + nodoHijoN2.getTextContent() + "\"");
                            }

                        }
                        System.out.println("");
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
    
    public void recursiva(Node node)
    {
        
    }

}
