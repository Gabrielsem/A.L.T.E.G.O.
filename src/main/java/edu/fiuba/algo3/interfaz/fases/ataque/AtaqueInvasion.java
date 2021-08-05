package edu.fiuba.algo3.interfaz.fases.ataque;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.interfaz.vistas.VistaSlider;
import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Arrays;

public class AtaqueInvasion extends FaseConSeleccionables {

    Juego juego;

    Pais paisAtacante;
    Pais paisAtacado;

    HBox cajaBotones;
    Label instruccion;
    VistaSlider slider;
    Button botonSiguiente;

    public AtaqueInvasion(Scene scene, Juego juego, Pais paisAtacante, Pais paisAtacado) {

        super.scene = scene;
        this.juego = juego;
        this.paisAtacante = paisAtacante;
        this.paisAtacado =  paisAtacado;
        instruccion = (Label) scene.lookup("#instruccion");
        slider = (VistaSlider) scene.lookup("#slider").getUserData();
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        cajaBotones = (HBox) scene.lookup("#cajaBotones");
        iniciar();
    }

    private void iniciar() {
        instruccion.setText("Elegí cuantas fichas mover al país conquistado");
        paisAtacado.ocupadoPor(juego.turnoActual(),0);
        super.setSeleccionables( Arrays.asList( paisAtacado.nombre(),paisAtacante.nombre() ));
        botonSiguiente.setText("Invadir");
        slider.mostrar(paisAtacante.cantidadFichas() - 1);
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        App.sonido("marchar");
        paisAtacante.invadir(paisAtacado, slider.getValue());
        botonSiguiente.setVisible(false);
        slider.ocultar();

        return new AtaqueAtacante(scene, juego);
    }

    @Override
    public Fase tocoSeleccionable(Node nodoPais) {
        return this;
    }

}
