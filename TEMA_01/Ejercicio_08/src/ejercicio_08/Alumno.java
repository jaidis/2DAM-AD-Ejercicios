/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_08;

/**
 *
 * @author jaidis
 */
public class Alumno {

    Integer id_matricula;
    String nombre;
    Double nota_1;
    Double nota_2;
    Double nota_3;

    public Alumno(Integer id_matricula, Double nota_1) {
        this.id_matricula = id_matricula;
        this.nota_1 = nota_1;
    }

    public Alumno(Integer id_matricula, String nombre, Double nota_1, Double nota_2, Double nota_3) {
        this.id_matricula = id_matricula;
        this.nombre = nombre;
        this.nota_1 = nota_1;
        this.nota_2 = nota_2;
        this.nota_3 = nota_3;
    }

    public Integer getId_matricula() {
        return id_matricula;
    }

    public void setId_matricula(Integer id_matricula) {
        this.id_matricula = id_matricula;
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

    public Double getNota_3() {
        return nota_3;
    }

    public void setNota_3(Double nota_3) {
        this.nota_3 = nota_3;
    }

}
