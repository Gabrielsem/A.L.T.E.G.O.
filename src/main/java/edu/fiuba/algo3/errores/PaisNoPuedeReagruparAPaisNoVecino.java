package edu.fiuba.algo3.errores;

public class PaisNoPuedeReagruparAPaisNoVecino extends RuntimeException{
    public PaisNoPuedeReagruparAPaisNoVecino(String mensaje) {
        super(mensaje);
    }
}
