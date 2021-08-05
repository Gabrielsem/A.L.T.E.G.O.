package edu.fiuba.algo3.interfaz.fases.reagrupacion;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.interfaz.vistas.VistaSlider;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Arrays;

public class ReagrupacionFichas extends FaseConSeleccionables {
    private Juego juego;
    private Pais origen, destino;
    private Label instruccion;
    private VistaSlider slider;
    private Button botonSiguiente;

    public ReagrupacionFichas(Scene scene, Juego juego, Pais origen, Pais destino) {
        super.scene = scene;
        this.juego = juego;
        this.origen = origen;
        this.destino = destino;
        instruccion = (Label) scene.lookup("#instruccion");
        slider = (VistaSlider) scene.lookup("#slider").getUserData();
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        iniciar();
    }

    private void iniciar() {
        super.setSeleccionables(Arrays.asList(origen.nombre(), destino.nombre()));

        instruccion.setText("Elegí la cantidad de fichas a mover");
        botonSiguiente.setText("Reagrupar");
        botonSiguiente.setVisible(true);
        slider.mostrar(origen.cantidadFichas() - 1);
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        App.sonido("marchar");
        juego.turnoActual().reagrupar(origen.nombre(), destino.nombre(), slider.getValue());
        //FIXME - Metodo puede se directamente de pais (?)
        slider.ocultar();
        return new ReagrupacionOrigen(scene, juego);
    }

    @Override
    protected Fase tocoSeleccionable(Node nodoPais) {
        return this;
    }
}
