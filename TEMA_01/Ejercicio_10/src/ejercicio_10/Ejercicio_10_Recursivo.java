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
 * @version Ejercicio_10 de forma recursiva (ejercicio de los catalogos)
 */
public class Ejercicio_10_Recursivo {

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
                NodeList listado = nodoRaiz.getElementsByTagName("CD");

                Node nodo_elemento;

                for (int i = 0; i < listado.getLength(); i++) {
                    nodo_elemento = (Node) listado.item(i);
                    recursiva(nodo_elemento);
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

    public static void recursiva(Node node) {

        if (node instanceof Node) {

            NodeList lista_nodos = node.getChildNodes();
            Node nodo_hijo;
            
            if (node.getNodeName().equals("CD")) {
                System.out.println(node.getNodeName());
            }
            
            if (node.hasAttributes()) {
                NamedNodeMap atributos = node.getAttributes();
                for (int x = 0; x < atributos.getLength(); x++) {
                    System.out.println("\t Atributo \"" + atributos.item(x).getNodeName() + "\" Valor \"" + atributos.item(x).getNodeValue() + "\"");
                }
                System.out.println();
            }

            if (lista_nodos.getLength() > 0) {

                for (int i = 0; i < lista_nodos.getLength(); i++) {
                    nodo_hijo = (Node) lista_nodos.item(i);
                    recursiva(nodo_hijo);
                }
            }

            if (node.getNodeType() == Node.ELEMENT_NODE && !node.getNodeName().equals("CD")) {
                System.out.println("\t Etiqueta \"" + node.getNodeName() + "\" Valor \"" + node.getTextContent() + "\"");
            }

        }

    }

}
