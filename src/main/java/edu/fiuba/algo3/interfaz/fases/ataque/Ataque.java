package edu.fiuba.algo3.interfaz.fases.ataque;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.modelo.Juego;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Ataque implements Fase {
    Scene scene;
    Juego juego;
    Fase gestor;
    Label instruccion;

    public Ataque(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        instruccion = (Label) scene.lookup("#instruccion");
        iniciar();
    }

    private void iniciar() {
        juego.nuevoCiclo();
        Label faseActual = (Label) scene.lookup("#faseActual");
        faseActual.setText("Fase Ataque");

        juego.siguienteTurno().prepararAtaques();
        gestor = new AtaqueAtacante(scene, juego);
    }

    @Override
    public Fase tocoPais(Node nodoPais) {
        return gestor.tocoPais(nodoPais);
    }

    public Fase tocoBoton(Button unBoton) {
        return gestor.tocoBoton(unBoton);
    }
}
