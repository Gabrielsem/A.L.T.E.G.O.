package edu.fiuba.algo3.errores;

public class PaisNoEsDeEsteJugador extends RuntimeException {
    public PaisNoEsDeEsteJugador(String format) {
        super(format);
    }
}
