package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Collection;

public class GestorDefensor implements Fase{


    FaseAtaque fase;
    Scene scene;
    Jugador jugadorActual;
    Pais atacante;

    Label instruccion;

    public GestorDefensor(FaseAtaque faseAtaque, Scene scene, Jugador jugadorActual, Pais atacante) {

        this.fase = faseAtaque;
        this.jugadorActual = jugadorActual;
        this.scene= scene;
        this.atacante = atacante;
        instruccion = (Label) scene.lookup("#instruccion");

    }

    @Override
    public void iniciar() {
        instruccion.setText(String.format("Jugador %d, toca el país que quieras atacar", jugadorActual.numero()));
        fase.setSeleccionables( jugadorActual.paisesAtacables(atacante.getVecinos()) );
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return null;
    }

    @Override
    public void tocoPais(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();
        fase.setGestor(new GestorFichasAtaque(fase, scene, jugadorActual,atacante, pais));
    }
}
