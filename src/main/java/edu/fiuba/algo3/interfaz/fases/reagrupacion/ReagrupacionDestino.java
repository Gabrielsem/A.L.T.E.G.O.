package edu.fiuba.algo3.interfaz.fases.reagrupacion;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.interfaz.vistas.VistaJugador;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ReagrupacionDestino extends FaseConSeleccionables {
    private Juego juego;
    private Pais origen;
    private Label instruccion;

    public ReagrupacionDestino(Scene scene, Juego juego, Pais origen) {
        super.scene = scene;
        this.juego = juego;
        this.origen = origen;
        instruccion = (Label) scene.lookup("#instruccion");

        super.setSeleccionables(juego.turnoActual().reagrupablesDesde(origen.nombre()), VistaJugador.getColorJugador(juego.turnoActual().numero()));
        scene.lookup("#botonSiguiente").setVisible(false);
        instruccion.setText("Elegi el país al que quieras reagrupar");
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return this;
    }

    @Override
    protected Fase tocoSeleccionable(Node nodoPais) {
        Pais p = (Pais) nodoPais.getUserData();
        return new ReagrupacionFichas(scene, juego, origen, p);
    }
}
