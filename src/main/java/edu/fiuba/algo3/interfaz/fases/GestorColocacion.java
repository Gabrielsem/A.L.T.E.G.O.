package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.PaisNoEsDeEsteJugador;
import edu.fiuba.algo3.interfaz.VistaPais;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GestorColocacion implements Fase {
    Juego juego;
    Scene scene;
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
        this.scene = scene;
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

    public void tocoPais(Node pais) {
        try {
            actual.ponerFichas(pais.getId(), 1);
            cambiarInstruccionAgregarFichas(actual);
        } catch (JugadorNoTieneFichasSuficientes | PaisNoEsDeEsteJugador e) {
            return;
        }

        if (!actual.tieneFichas()) {
            botonSiguiente.setVisible(true);
            cambiarInstruccionSiguiente();
        }
    }

    public Fase tocoBoton(Button unBoton) {
        if (juego.turnosCompletados()) {
            siguienteFase.iniciar();
            botonSiguiente.setVisible(false);
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
        actualizarUI();
    }

    private void cambiarInstruccionSiguiente() {
        instruccion.setText("Tocá siguiente para pasar al siguiente turno.");
    }

    private void actualizarUI() {
        VBox box = (VBox) scene.lookup("#playerBox");
        String color = VistaPais.getColorJugador(actual.numero());
        box.setStyle("-fx-background-color: "+color+";");

        ((Label) box.getChildren().get(0)).setText("Jugador "+actual.numero());
    }
}
