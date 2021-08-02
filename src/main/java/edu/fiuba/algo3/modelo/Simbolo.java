package edu.fiuba.algo3.modelo;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simbolo simbolo = (Simbolo) o;
        return nombre.equals(simbolo.nombre);
    }

}