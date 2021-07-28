package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FaseAtaque implements Fase {
    Scene scene;
    Juego juego;
    Fase gestor;

    Pais paisAtacante;
    Pais paisAtacado;

    Jugador jugadorActual;

    Label instruccion;

    public FaseAtaque(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        scene.lookup("#botonSiguiente").setVisible(true);
        instruccion = (Label) scene.lookup("#instruccion");
    }

    public void iniciar() {
        juego.reiniciarTurnos();
        jugadorActual = juego.siguienteTurno();

        setGestor( new GestorAtacante(this,juego,scene,jugadorActual) );
    }

    public void tocoPais(Node nodoPais) {
        gestor.tocoPais(nodoPais);
    }

    public Fase tocoBoton(Button unBoton) {
        return this; //TODO: siguiente jugador/etapa reagrupacion
    }

    public void setGestor(Fase gestor ) {
        this.gestor = gestor;
        this.gestor.iniciar();
    }

}
