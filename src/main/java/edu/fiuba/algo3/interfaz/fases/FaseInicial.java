package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;

public class FaseInicial implements Fase {
    Scene scene;
    GestorColocacion gestor;
    Juego juego;

    public FaseInicial(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        GestorColocacion gestorSiguiente = new GestorColocacion(juego, scene, 3, new FaseAtaque(juego, scene));
        // TODO ponerle al gestor la siguiente fase
        gestor = new GestorColocacion(juego, scene, 5, gestorSiguiente);
    }

    public void iniciar() {
        gestor.iniciar();
    }

    public void tocoPais(Node pais) {
        gestor.tocoPais(pais);
    }

    public Fase tocoBoton(Button unBoton) {
        return gestor.tocoBoton(unBoton);
    }
}
