package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JuegoTest {

    Juego juego;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        juego = new Juego(2, "archivos/paises.json", "archivos/objetivos.json","archivos/tarjetas.json");
    }

    @Test
    public void fichasPorContinenteVacio() {
        assertEquals(0,juego.fichasSegunContinentes(new HashSet<>() ));
    }

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

    @Test
    public void juegoDe2JugadoresDa2Turnos() throws FileNotFoundException {
        Juego juego2jug = new Juego(2, "archivos/paises.json", "archivos/objetivos.json","archivos/tarjetas.json");
        int i = 0;
        while (!juego2jug.turnosCompletados()) {
            juego2jug.siguienteTurno();
            i++;
        }
        assertEquals(2, i);
    }

    @Test
    public void juegoDe6JugadoresDa6Turnos() throws FileNotFoundException {
        Juego juego6jug = new Juego(6, "archivos/paises.json", "archivos/objetivos.json","archivos/tarjetas.json");
        int i = 0;
        while (!juego6jug.turnosCompletados()) {
            juego6jug.siguienteTurno();
            i++;
        }
        assertEquals(6, i);
    }

    @Test
    public void pedirTarjetasDaTarjetasDiferentes() {

        HashSet<Tarjeta> tarjetas = new HashSet<>();

        for( int i=0; i<50; i++ )
            tarjetas.add(juego.pedirTarjeta() );
        // El hashset no puede tener elementos repetidos

        assertEquals(50,tarjetas.size());
    }

    @Test
    public void cantidadPaisesPorContinente() {
        HashMap<String, Integer> cantidadPaises = new HashMap<>(Map.of(
                "Africa", 6,
                "Oceania", 4,
                "Europa", 9,
                "America del Norte", 10,
                "Asia", 15,
                "America del Sur", 6
        ));

        assertEquals(cantidadPaises, juego.cantidadPaisesPorContinente());
    }
}