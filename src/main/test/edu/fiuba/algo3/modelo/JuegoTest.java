package edu.fiuba.algo3.modelo;

import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.Juego;
import edu.fiuba.algo3.Mapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class JuegoTest {

    Juego juego;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        juego = new Juego(2);
    }

    @Test
    public void fichasPorContinenteVacio() {
        assertEquals(0,juego.fichasSegunContinentes(new HashSet<>() ));
    }// Delegado a mapa. Implementacion y testeo

}