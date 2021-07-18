
package edu.fiuba.algo3.errores;

public class PaisNoExiste extends RuntimeException{
    public PaisNoExiste(String mensaje) {
        super(mensaje);
    }
}
