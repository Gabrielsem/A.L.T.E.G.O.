package edu.fiuba.algo3.interfaz.fases.colocacion;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConColocacion;
import edu.fiuba.algo3.interfaz.fases.ataque.Ataque;
import edu.fiuba.algo3.modelo.Juego;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Inicial implements FaseConColocacion {
    Scene scene;
    AgregadorEjercitos gestor;
    Juego juego;
    boolean parte1 = true;

    public Inicial(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        gestor = new AgregadorEjercitos(juego, scene, this, 5);

        Label faseActual = (Label) scene.lookup("#faseActual");
        faseActual.setText("Fase Inicial");
    }

    @Override
    public Fase tocoPais(Node nodoPais) {
        return gestor.tocoPais(nodoPais);
    }

    public Fase tocoBoton(Button unBoton) {
        return gestor.tocoBoton(unBoton);
    }

    public Fase siguienteFase() {
        if (parte1) {
            parte1 = false;
            return new AgregadorEjercitos(juego, scene, this, 3);
        }
        return new Ataque(juego, scene);
    }
}
