package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.TurnoInvalido;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class Turnos extends Observable {
    private ArrayList<Jugador> jugadores;
    private int actual, offset;

    public Turnos(Collection<Jugador> jugadores) {
        this.jugadores = new ArrayList<>(jugadores);
        offset = ThreadLocalRandom.current().nextInt(jugadores.size());
        actual = -1;
    }

    public boolean turnosCompletados() {
        return actual == jugadores.size() - 1;
    }

    private boolean turnosNoEmpezaron() {
        return actual == -1;
    }

    public Jugador turnoActual() {
        if (turnosNoEmpezaron()) {
            throw new TurnoInvalido("Se pidió jugador actual antes de pedir primer turno");
        }

        return jugadores.get((actual + offset) % jugadores.size());
    }

    public Jugador siguienteTurno() {
        if (turnosCompletados()){
            throw new TurnoInvalido("Se pidió siguiente cuando ya se llegó al último jugador");
        }
        actual++;
        notificarObservers();
        return turnoActual();
    }

    public void reiniciarTurnos() {
        actual = -1;
        offset = (offset + 1) % jugadores.size();
    }

    public void notificarObservers() {
        setChanged();notifyObservers();
    }
}
