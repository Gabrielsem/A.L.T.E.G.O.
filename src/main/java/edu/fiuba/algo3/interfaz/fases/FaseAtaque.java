package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collection;

public class FaseAtaque implements Fase {
    Scene scene;
    Juego juego;
    Pais paisAtacante;
    Pais paisAtacado;
    Collection<String> seleccionables;
    Jugador actual;
    Button[] botones = new Button[3];
    Label instruccion;

    public FaseAtaque(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        scene.lookup("#botonSiguiente").setVisible(true);
        instruccion = (Label) scene.lookup("#instruccion");
    }

    private void inicializarBotones() {
        botones[0] = (Button) scene.lookup("#botonAtaque1");
        botones[1] = (Button) scene.lookup("#botonAtaque2");
        botones[2] = (Button) scene.lookup("#botonAtaque3");
        botones[0].setOnAction((a) -> atacarCon(1));
        botones[1].setOnAction((a) -> atacarCon(2));
        botones[2].setOnAction((a) -> atacarCon(3));
    }

    public void iniciar() {
        juego.reiniciarTurnos();
        actual = juego.siguienteTurno();
        instruccion.setText(String.format("Jugador %d, toca el país del que quieras atacar", actual.numero()));
        inicializarBotones();
    }

    public void tocoPais(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();
        if (paisAtacante == null) {
            if (actual.tienePais(nodoPais.getId())) {
                seleccionables = actual.paisesAtacables(pais.getVecinos());
                // TODO: resaltar seleccionables
                paisAtacante = pais;
                instruccion.setText(String.format("Jugador %d, toca el país que quieras atacar", actual.numero()));
            }
        } else if (seleccionables.contains(nodoPais.getId())) {
            // TODO: des-resaltar
            paisAtacado = pais;
            instruccion.setText(String.format("Jugador %d, elegí con cuantas fichas atacar", actual.numero()));
            agregarBotonesAtaque();

        } else {
            // TODO: des-resaltar
            seleccionables = null;
            paisAtacante = null;
            paisAtacado = null;
            instruccion.setText(String.format("Jugador %d, toca el país del que quieras atacar", actual.numero()));
        }
    }

    private void agregarBotonesAtaque() {
        for (int i = 0; i < (paisAtacante.cantidadFichas() - 1) && i < 3; i++) {
            botones[i].setVisible(true);
        }
    }

    private void ocultarBotonesAtaque() {
        Arrays.stream(botones).forEach((Button b) -> b.setVisible(false));
    }

    public Fase tocoBoton(Button unBoton) {
        return this; //TODO: siguiente jugador/etapa reagrupacion
    }

    public void atacarCon(int fichas) {
        paisAtacante.atacar(paisAtacado, fichas);
        if (paisAtacante.ataqueExitoso()) {
            paisAtacante.invadirPaisConquistado(3);
        }
    }
}
