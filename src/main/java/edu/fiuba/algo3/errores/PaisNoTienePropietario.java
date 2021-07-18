package edu.fiuba.algo3.errores;

public class PaisNoTienePropietario extends RuntimeException {
    public PaisNoTienePropietario(String mensaje) {
        super(mensaje);
    }
}
