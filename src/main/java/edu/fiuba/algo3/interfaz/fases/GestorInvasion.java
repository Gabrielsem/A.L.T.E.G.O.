package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Collection;

public class GestorInvasion implements Fase {

    FaseAtaque fase;
    Scene scene;
    Jugador jugadorActual;
    Pais paisAtacante;
    Pais paisAtacado;

    Collection<String> seleccionables;
    Button[] botones = new Button[3];

    Label instruccion;

    public GestorInvasion(FaseAtaque faseAtaque, Scene scene, Jugador jugadorActual, Pais paisAtacante, Pais paisAtacado) {

        fase = faseAtaque;
        this.scene = scene;
        this.jugadorActual = jugadorActual;
        this. paisAtacante = paisAtacante;
        this.paisAtacado =  paisAtacado;
        instruccion = (Label) scene.lookup("#instruccion");
    }

    @Override
    public void iniciar() {
        paisAtacado.ocupadoPor(jugadorActual,1);
        fase.setGestor( new GestorAtacante(fase,scene,jugadorActual) );
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return null;
    }

    @Override
    public void tocoPais(Node nodoPais) {

    }
}
