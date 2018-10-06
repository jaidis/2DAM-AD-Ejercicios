/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_07;

import java.io.Serializable;

/**
 *
 * @author jaidis
 */
public class Alumno implements Serializable {

    private Integer numeroMatricula;
    private String nombre;
    private Double nota_1;
    private Double nota_2;
    private Double nota_Final;

    public Alumno(Integer numeroMatricula, String nombre, Double nota_1, Double nota_2, Double nota_Final) {
        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.nota_1 = nota_1;
        this.nota_2 = nota_2;
        this.nota_Final = nota_Final;
    }

    public Alumno() {
        this.numeroMatricula = 0;
        this.nombre = "";
        this.nota_1 = nota_1;
        this.nota_2 = nota_2;
        this.nota_Final = nota_Final;
    }

    public Integer getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(Integer numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getNota_1() {
        return nota_1;
    }

    public void setNota_1(Double nota_1) {
        this.nota_1 = nota_1;
    }

    public Double getNota_2() {
        return nota_2;
    }

    public void setNota_2(Double nota_2) {
        this.nota_2 = nota_2;
    }

    public Double getNota_Final() {
        return nota_Final;
    }

    public void setNota_Final(Double nota_Final) {
        this.nota_Final = nota_Final;
    }

}
