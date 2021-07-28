package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import javafx.scene.Scene;

public class FaseInicial implements Fase {
    Scene scene;
    GestorColocacion gestor;
    Juego juego;

    public FaseInicial(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        GestorColocacion gestorSiguiente = new GestorColocacion(juego, scene, 3, this);
        // TODO ponerle al gestor la siguiente fase
        gestor = new GestorColocacion(juego, scene, 5, gestorSiguiente);
    }

    public void iniciar() {
        gestor.iniciar();
    }

    public void tocoPais(String nombrePais) {
        gestor.tocoPais(nombrePais);
    }

    public Fase tocoSiguiente() {
        return gestor.tocoSiguiente();
    }
}
