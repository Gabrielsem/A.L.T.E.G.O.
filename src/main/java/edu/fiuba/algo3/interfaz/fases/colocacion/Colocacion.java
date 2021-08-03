package edu.fiuba.algo3.interfaz.fases.colocacion;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConColocacion;
import edu.fiuba.algo3.interfaz.fases.ataque.Ataque;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Colocacion implements FaseConColocacion {
    private Scene scene;
    private Juego juego;
    private Fase gestor;

    public Colocacion(Scene scene, Juego juego) {
        this.scene = scene;
        this.juego = juego;
        iniciar();
    }

    private void iniciar() {
        for (Jugador j : juego.getJugadores()) {
            j.actualizarFichas();
        }

        Label faseActual = (Label) scene.lookup("#faseActual");
        faseActual.setText("Fase Colocación");
        gestor = new AgregadorEjercitos(juego, scene, this);
    }

    @Override
    public Fase tocoPais(Node nodoPais) {
        return gestor.tocoPais(nodoPais);
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return gestor.tocoBoton(unBoton);
    }

    @Override
    public Fase siguienteFase() {
        return new Ataque(juego, scene);
    }
}
