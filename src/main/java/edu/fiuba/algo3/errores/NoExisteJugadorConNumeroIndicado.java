package edu.fiuba.algo3.errores;

public class NoExisteJugadorConNumeroIndicado extends RuntimeException {
    public NoExisteJugadorConNumeroIndicado(String mensaje) {
        super(mensaje);
    }
}
