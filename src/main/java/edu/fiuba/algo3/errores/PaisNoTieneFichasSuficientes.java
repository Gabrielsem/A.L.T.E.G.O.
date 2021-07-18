package edu.fiuba.algo3.errores;

public class PaisNoTieneFichasSuficientes extends RuntimeException {
    public PaisNoTieneFichasSuficientes(String mensaje) {
        super(mensaje);
    }
}
