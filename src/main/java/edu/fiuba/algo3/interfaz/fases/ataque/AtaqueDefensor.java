package edu.fiuba.algo3.interfaz.fases.ataque;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.interfaz.vistas.VistaJugador;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AtaqueDefensor extends FaseConSeleccionables {

    Juego juego;
    Pais atacante;
    Label instruccion;

    public AtaqueDefensor(Scene scene, Juego juego, Pais atacante) {

        this.juego = juego;
        super.scene = scene;
        this.atacante = atacante;
        instruccion = (Label) scene.lookup("#instruccion");
        scene.lookup("#botonSiguiente").setVisible(false);

        instruccion.setText("Clickeá el país al que queres atacar");
        super.setSeleccionables( atacante.getPaisesAtacables() ,VistaJugador.getColorJugador(juego.turnoActual().numero() ));
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return this;
    }

    @Override
    public Fase tocoSeleccionable(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();
        return new AtaqueFichas(scene, juego, atacante, pais);
    }
}
