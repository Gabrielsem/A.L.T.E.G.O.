package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.Jugador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JugadorTest {

    Jugador jugador = new Jugador();

    @Test
    public void testDePrueba(){

        assertEquals(jugador.invadir(null,null), 0);
    }
}
