package edu.fiuba.algo3.controladores.fases;

import edu.fiuba.algo3.modelo.Juego;
import javafx.scene.Scene;

public class FaseAtaque implements Fase {
    Scene scene;
    Juego juego;
    GestorColocacion gestor;

    public FaseAtaque(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
    }

    public void iniciar() {

    }

    public void tocoPais(String nombrePais) {

    }

    public Fase tocoSiguiente() {
        return this;
    }
}
