package edu.fiuba.algo3.modelo;

public class Simbolo {
    final private String nombre;

    public Simbolo(String nombre) {
        this.nombre = nombre;
    }

    public String nombre() {
        return this.nombre;
    }

    public boolean esIgualA(Simbolo simbolo) {
        return simbolo.nombre().equals(this.nombre);
    }

}