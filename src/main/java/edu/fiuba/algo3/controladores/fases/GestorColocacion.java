package edu.fiuba.algo3.controladores.fases;

import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.PaisNoEsDeEsteJugador;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GestorColocacion implements Fase {
    Juego juego;
    Jugador actual;
    Label instruccion;
    Button botonSiguiente;
    int fichasExtra;
    Fase siguienteFase;

    public GestorColocacion(Juego juego, Scene scene, int fichasExtra, Fase siguienteFase) {
        instruccion = (Label) scene.lookup("#instruccion");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        this.juego = juego;
        this.fichasExtra = fichasExtra;
        this.siguienteFase = siguienteFase;
    }

    public void iniciar() {
        for (Jugador j : juego.getJugadores()) {
            j.darFichas(fichasExtra);
        }
        botonSiguiente.setVisible(false);
        juego.reiniciarTurnos();
        actual = juego.siguienteTurno();

        cambiarInstruccionAgregarFichas(actual);
    }

    public void tocoPais(String nombrePais) {
        try {
            actual.ponerFichas(nombrePais, 1);
            cambiarInstruccionAgregarFichas(actual);
        } catch (JugadorNoTieneFichasSuficientes | PaisNoEsDeEsteJugador e) {
            return;
        }

        if (!actual.tieneFichas()) {
            botonSiguiente.setVisible(true);
            cambiarInstruccionSiguiente();
        }
    }

    public Fase tocoSiguiente() {
        if (juego.turnosCompletados()) {
            siguienteFase.iniciar();
            return siguienteFase;
        }

        actual = juego.siguienteTurno();
        botonSiguiente.setVisible(false);
        cambiarInstruccionAgregarFichas(actual);
        return this;
    }

    private void cambiarInstruccionAgregarFichas(Jugador jugador) {
        instruccion.setText(String.format("Jugador %d, clickeá un país para agregarle fichas (te quedan %d fichas)",
                jugador.numero(), jugador.cantidadFichas()));
    }

    private void cambiarInstruccionSiguiente() {
        instruccion.setText("Tocá siguiente para pasar al siguiente turno.");
    }
}
