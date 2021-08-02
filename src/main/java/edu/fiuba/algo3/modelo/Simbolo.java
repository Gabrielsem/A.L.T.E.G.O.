package edu.fiuba.algo3.modelo;

public class Simbolo {
    final private String nombre;

    public Simbolo(String nombre) {
        this.nombre = nombre;
    }

    public String nombre() {
        return this.nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simbolo simbolo = (Simbolo) o;
        return nombre.equals(simbolo.nombre);
    }

}