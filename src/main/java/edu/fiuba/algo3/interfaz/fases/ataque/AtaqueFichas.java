package edu.fiuba.algo3.interfaz.fases.ataque;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.Arrays;

public class AtaqueFichas extends FaseConSeleccionables {

    Juego juego;
    Pais paisAtacante;
    Pais paisAtacado;

    Label instruccion;
    Slider slider;
    Button botonSiguiente;

    public AtaqueFichas(Scene scene, Juego juego, Pais atacante, Pais defensor) {
        super.scene = scene;
        this.juego = juego;
        this. paisAtacante = atacante;
        this.paisAtacado =  defensor;
        instruccion = (Label) scene.lookup("#instruccion");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        slider = (Slider) scene.lookup("#slider");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        iniciar();
    }

    private void iniciar() {
        instruccion.setText("Elegí con cuantas fichas atacar");
        super.setSeleccionables( Arrays.asList(paisAtacado.nombre(), paisAtacante.nombre() ));
        agregarSlider();
        botonSiguiente.setText("Atacar");
        botonSiguiente.setVisible(true);
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        paisAtacante.atacar(paisAtacado, (int) slider.getValue());
        if( paisAtacado.invadible() ){
            return new AtaqueInvasion(scene, juego, paisAtacante,paisAtacado);
        } else {
            slider.setManaged(false);
            slider.setVisible(false);
            botonSiguiente.setVisible(false);
            return new AtaqueAtacante(scene, juego);
        }
    }

    @Override
    public Fase tocoSeleccionable(Node nodoPais) {
        return this;
    }

    private void agregarSlider() {
        int fichas = 3;
        if (paisAtacante.cantidadFichas() <= 3) fichas = paisAtacante.cantidadFichas() - 1;
        slider.setMax(fichas);
        slider.setValue(1);
        slider.setManaged(true);
        slider.setVisible(true);
    }
}
