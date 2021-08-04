package edu.fiuba.algo3.interfaz.fases.ataque;

import edu.fiuba.algo3.interfaz.vistas.VistaSlider;
import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Arrays;

public class AtaqueFichas extends FaseConSeleccionables {

    Juego juego;
    Pais paisAtacante;
    Pais paisAtacado;

    Label instruccion;
    VistaSlider slider;
    Button botonSiguiente;

    public AtaqueFichas(Scene scene, Juego juego, Pais atacante, Pais defensor) {
        super.scene = scene;
        this.juego = juego;
        this. paisAtacante = atacante;
        this.paisAtacado =  defensor;
        instruccion = (Label) scene.lookup("#instruccion");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        slider = (VistaSlider) scene.lookup("#slider").getUserData();
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        iniciar();
    }

    private void iniciar() {
        instruccion.setText("Elegí con cuantas fichas atacar");
        super.setSeleccionables( Arrays.asList(paisAtacado.nombre(), paisAtacante.nombre() ));

        botonSiguiente.setText("Atacar");
        botonSiguiente.setVisible(true);

        int fichasDisp = paisAtacante.cantidadFichas() - 1;
        slider.mostrar(Math.min(fichasDisp, 3));
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        paisAtacante.atacar(paisAtacado, slider.getValue());
        if( paisAtacado.invadible() ){
            return new AtaqueInvasion(scene, juego, paisAtacante,paisAtacado);
        } else {
            slider.ocultar();
            botonSiguiente.setVisible(false);
            return new AtaqueAtacante(scene, juego);
        }
    }

    @Override
    public Fase tocoSeleccionable(Node nodoPais) {
        return this;
    }
}
