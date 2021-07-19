package edu.fiuba.algo3.errores;

public class JugadorNoTieneFichasSuficientes extends RuntimeException {
    public JugadorNoTieneFichasSuficientes(String mensaje) {
        super(mensaje);
    }
}
