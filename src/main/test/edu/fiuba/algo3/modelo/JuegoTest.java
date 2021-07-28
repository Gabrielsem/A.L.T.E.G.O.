package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JuegoTest {

    Juego juego;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        juego = new Juego(2, "archivos/paises.json", "objetivos.json");
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
    public void turnoSiguienteDevuelveElOtroJugadorSiHay2Jugadores() throws FileNotFoundException {
        Juego juego2jug = new Juego(2, "archivos/paises.json", "objetivos.json");
        assertNotEquals(juego.siguienteTurno(), juego.siguienteTurno());
    }

    @Test
    public void turnoCompletadoTrasRecorrer6Jugadores() throws FileNotFoundException {
        Juego juego6jug = new Juego(6, "archivos/paises.json", "objetivos.json");
        int i = 0;
        while(!juego6jug.turnosCompletados()) {
            i++;
            Jugador jug = juego6jug.siguienteTurno();
        }

        assertEquals(6, i);
    }

    @Test
    public void turnoSiguienteEnJuegoDe6PasaPorTodosLosJugadores() throws FileNotFoundException {
        Juego juego6jug = new Juego(6, "archivos/paises.json", "objetivos.json");
        HashSet<Jugador> jugadores = new HashSet<>();

        while(!juego6jug.turnosCompletados()) {
            jugadores.add(juego6jug.siguienteTurno());
        }

        assertEquals(6, jugadores.size());
    }

    @Test
    public void trasPasarPorLosJugadoresDevuelveNull() {
        while(!juego.turnosCompletados()) {
            Jugador jug = juego.siguienteTurno();
        }

        assertNull(juego.siguienteTurno());
    }

    @Test
    public void trasReiniciarTurnosVuelveADevolverJugadoresEmpezandoPorElSegundo() throws FileNotFoundException {
        Juego juego6jug = new Juego(6, "archivos/paises.json", "objetivos.json");
        ArrayList<Jugador> jugadores1 = new ArrayList<>();
        ArrayList<Jugador> jugadores2 = new ArrayList<>();

        while(!juego6jug.turnosCompletados()) {
            jugadores1.add(juego6jug.siguienteTurno());
        }
        juego6jug.reiniciarTurnos();
        while(!juego6jug.turnosCompletados()) {
            jugadores2.add(juego6jug.siguienteTurno());
        }

        Jugador primero = jugadores1.remove(0);
        jugadores1.add(primero);
        assertEquals(jugadores1, jugadores2);
    }
}