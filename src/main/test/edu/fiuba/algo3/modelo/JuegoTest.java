package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JuegoTest {

    Juego juego;
/*
    @BeforeEach
    public void setUp() throws FileNotFoundException {

        juego = new Juego(2);
    }

    @Test
    public void fichasPorContinenteVacio() {
        assertEquals(0,juego.fichasSegunContinentes(new HashSet<>() ));
    }// Delegado a mapa. Implementacion y testeo

    @Test
    public void devolverTarjetasLasDesactiva() {
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        Tarjeta tarjetaArgentina = mock(Tarjeta.class);
        Tarjeta tarjetaColombia = mock(Tarjeta.class);
        Tarjeta tarjetaBrasil = mock(Tarjeta.class);

        tarjetas.add(tarjetaBrasil);
        tarjetas.add(tarjetaArgentina);
        tarjetas.add(tarjetaColombia);

        juego.devolverTarjetas(tarjetas);

        verify(tarjetaArgentina, times(1)).desactivar();
        verify(tarjetaColombia, times(1)).desactivar();
        verify(tarjetaBrasil, times(1)).desactivar();
    }
*/ //TODO: arreglar estos tests
}