package edu.fiuba.algo3.modelo;

public class Simbolo {
    final private String nombre;

    public Simbolo(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerNombre() {
        return this.nombre;
    }

    public boolean esIgualA(Simbolo simbolo) {
        return simbolo.obtenerNombre().equals(this.nombre);
    }

}