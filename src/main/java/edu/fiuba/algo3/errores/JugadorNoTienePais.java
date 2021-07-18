package edu.fiuba.algo3.errores;

public class JugadorNoTienePais extends RuntimeException {
    public JugadorNoTienePais(String mensaje) {
        super(mensaje);
    }
}
