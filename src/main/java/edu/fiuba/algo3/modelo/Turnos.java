package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class Turnos {
    private ArrayList<Jugador> jugadores;
    private int actual, offset;

    public Turnos(Collection<Jugador> jugadores) {
        this.jugadores = new ArrayList<>(jugadores);
        offset = ThreadLocalRandom.current().nextInt(jugadores.size());
        actual = 0;
    }

    public boolean turnosCompletados() {
        return actual == jugadores.size();
    }

    public Jugador turnoActual() {
        return jugadores.get((actual + offset) % jugadores.size());
    }

    public Jugador siguienteTurno() {
        if (turnosCompletados()) {
            return null;
        }

        Jugador ret = turnoActual();
        actual++;
        return ret;
    }

    public void reiniciarTurnos() {
        actual = 0;
        offset = (offset + 1) % jugadores.size();
    }
}
