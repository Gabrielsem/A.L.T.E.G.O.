package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.TurnoInvalido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnosTest {
    Turnos turnos2jug;
    Turnos turnos6jug;

    @BeforeEach
    public void setUp() {
        ArrayList<Jugador> lista = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lista.add(mock(Jugador.class));
        }
        turnos2jug = new Turnos(lista.subList(0, 2));
        turnos6jug = new Turnos(lista);
    }

    @Test
    public void turnoSiguienteDevuelveElOtroJugadorSiHay2Jugadores() throws FileNotFoundException {
        assertNotEquals(turnos2jug.siguienteTurno(), turnos2jug.siguienteTurno());
    }

    @Test
    public void turnoCompletadoTrasRecorrer6Jugadores() throws FileNotFoundException {
        int i = 0;
        while(!turnos6jug.turnosCompletados()) {
            i++;
            Jugador jug = turnos6jug.siguienteTurno();
        }

        assertEquals(6, i);
    }

    @Test
    public void turnoSiguienteEnJuegoDe6PasaPorTodosLosJugadores() throws FileNotFoundException {
        HashSet<Jugador> jugadores = new HashSet<>();

        while(!turnos6jug.turnosCompletados()) {
            jugadores.add(turnos6jug.siguienteTurno());
        }

        assertEquals(6, jugadores.size());
    }

    @Test
    public void trasPasarPorLosJugadoresLanzaExcepcion() {
        while(!turnos6jug.turnosCompletados()) {
            Jugador jug = turnos6jug.siguienteTurno();
        }

        assertThrows(TurnoInvalido.class, () -> turnos6jug.siguienteTurno());
    }

    @Test
    public void trasReiniciarEmpiezaConElSegundo() {
        Jugador jug1 = turnos6jug.siguienteTurno();
        Jugador jug2 = turnos6jug.siguienteTurno();
        turnos6jug.nuevoCiclo();
        assertEquals(jug2, turnos6jug.siguienteTurno());
    }

    @Test
    public void trasNuevoCicloEsMismoOrdenEmpezandoConElSegundo() throws FileNotFoundException {
        ArrayList<Jugador> jugadores1 = new ArrayList<>();
        ArrayList<Jugador> jugadores2 = new ArrayList<>();

        while(!turnos6jug.turnosCompletados()) {
            jugadores1.add(turnos6jug.siguienteTurno());
        }
        turnos6jug.nuevoCiclo();
        while(!turnos6jug.turnosCompletados()) {
            jugadores2.add(turnos6jug.siguienteTurno());
        }

        Jugador primero = jugadores1.remove(0);
        jugadores1.add(primero);
        assertEquals(jugadores1, jugadores2);
    }

    @Test
    public void turnoActualNoPasaTurno() {
        assertEquals(turnos2jug.siguienteTurno(), turnos2jug.turnoActual());
    }

    @Test
    public void pedirTurnoActualAntesDePrimerTurnoLanzaExcepcion() {
        assertThrows(TurnoInvalido.class, () -> turnos6jug.turnoActual());
    }
}
