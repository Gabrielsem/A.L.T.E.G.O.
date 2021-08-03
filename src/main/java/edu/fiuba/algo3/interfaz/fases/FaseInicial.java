package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FaseInicial implements Fase {
    Scene scene;
    GestorColocacion gestor;
    Juego juego;

    public FaseInicial(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        GestorColocacion gestorSiguiente = new GestorColocacion(juego, scene, 3, new FaseAtaque(juego, scene));
        gestor = new GestorColocacion(juego, scene, 5, gestorSiguiente);

    }

    public void iniciar() {
        gestor.iniciar();

        Label faseActual = (Label) scene.lookup("#faseActual");
        faseActual.setText("Fase Inicial");
    }

    public void tocoPais(Node pais) {
        gestor.tocoPais(pais);
    }

    public Fase tocoBoton(Button unBoton) {
        return gestor.tocoBoton(unBoton);
    }
}
