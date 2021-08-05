package edu.fiuba.algo3.errores;

public class PaisNoPuedeInvadirAPaisNoVecino extends RuntimeException {
    public PaisNoPuedeInvadirAPaisNoVecino(String mensaje) {
        super(mensaje);
    }
}
