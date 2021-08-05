package edu.fiuba.algo3.interfaz.fases.colocacion;

import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.JugadorNoTienePais;
import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConColocacion;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class AgregadorEjercitos extends FaseConSeleccionables {
    Juego juego;
    Jugador actual;
    Label instruccion;
    Button botonSiguiente;
    FaseConColocacion fase;
    private int fichasExtra;

    public AgregadorEjercitos(Juego juego, Scene scene, FaseConColocacion fase) {
        this(juego, scene, fase, 0);
    }

    public AgregadorEjercitos(Juego juego, Scene scene, FaseConColocacion fase, int fichasExtra) {
        instruccion = (Label) scene.lookup("#instruccion");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        this.juego = juego;
        this.fase = fase;
        super.scene = scene;
        this.fichasExtra = fichasExtra;
        iniciar();
    }

    private void iniciar() {
        for (Jugador j : juego.getJugadores()) {
            j.darFichas(fichasExtra);
        }

        botonSiguiente.setVisible(false);
        juego.reiniciarTurnos();
        actual = juego.siguienteTurno();
        super.setSeleccionables(actual.paises());
        cambiarInstruccionAgregarFichas(actual);
    }

    public Fase tocoSeleccionable(Node pais) {
        try {
            actual.ponerFichas(pais.getId(), 1);
            cambiarInstruccionAgregarFichas(actual);
        } catch (JugadorNoTieneFichasSuficientes | JugadorNoTienePais e) {
            return this;
        }

        if (!actual.tieneFichas()) {
            botonSiguiente.setVisible(true);
            cambiarInstruccionSiguiente();
        }
        return this;
    }

    public Fase tocoBoton(Button unBoton) {
        if (juego.turnosCompletados()) {
            botonSiguiente.setVisible(false);
            return fase.siguienteFase();
        }

        actual = juego.siguienteTurno();
        botonSiguiente.setVisible(false);
        cambiarInstruccionAgregarFichas(actual);
        super.setSeleccionables(actual.paises());

        return this;
    }

    private void cambiarInstruccionAgregarFichas(Jugador jugador) {
        instruccion.setText(String.format("Clickeá un país para agregarle fichas (te quedan %d fichas)", jugador.cantidadFichas()));
    }

    private void cambiarInstruccionSiguiente() {
        instruccion.setText("Tocá siguiente para pasar al siguiente turno.");
    }
}
