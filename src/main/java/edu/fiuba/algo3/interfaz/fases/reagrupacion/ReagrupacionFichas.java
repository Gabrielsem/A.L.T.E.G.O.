package edu.fiuba.algo3.interfaz.fases.reagrupacion;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class ReagrupacionFichas extends FaseConSeleccionables {
    private Juego juego;
    private Pais origen, destino;
    private Label instruccion;
    private Slider slider;
    private Button botonSiguiente;

    public ReagrupacionFichas(Scene scene, Juego juego, Pais origen, Pais destino) {
        super.scene = scene;
        this.juego = juego;
        this.origen = origen;
        this.destino = destino;
        instruccion = (Label) scene.lookup("#instruccion");
        slider = (Slider) scene.lookup("#slider");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        iniciar();
    }

    private void iniciar() {
        instruccion.setText("Elegí la cantidad de fichas a mover");
        slider.setMax(origen.cantidadFichas() - 1);
        slider.setValue(1);
        slider.setManaged(true);
        slider.setVisible(true);
        botonSiguiente.setText("Reagrupar");
        botonSiguiente.setVisible(true);
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        juego.turnoActual().reagrupar(origen.nombre(), destino.nombre(), (int) slider.getValue());
        //FIXME sirve tener ese metodo de jugador? (el reagrupar), podria llamar directo al de pais, no se
        //si es bueno o malo
        slider.setVisible(false);
        slider.setManaged(false);
        return new ReagrupacionOrigen(scene, juego);
    }

    @Override
    protected Fase tocoSeleccionable(Node nodoPais) {
        return this;
    }
}
