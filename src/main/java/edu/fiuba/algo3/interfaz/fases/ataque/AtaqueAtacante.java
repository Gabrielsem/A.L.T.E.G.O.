package edu.fiuba.algo3.interfaz.fases.ataque;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.interfaz.fases.reagrupacion.ReagrupacionOrigen;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AtaqueAtacante extends FaseConSeleccionables {

    Juego juego;
    Label instruccion;
    HBox cajaBoton;
    Button botonSiguiente;

    public AtaqueAtacante(Scene scene, Juego juego){

        this.juego = juego;
        super.scene = scene;
        instruccion = (Label) scene.lookup("#instruccion");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        iniciar();
    }

    private void iniciar() {
        instruccion.setText("Clickeá el país con el que queres atacar");
        super.setSeleccionables( juego.turnoActual().paisesDisponiblesParaAtacar() );
        botonSiguiente.setText("Pasar a reagrupación");
        botonSiguiente.setVisible(true);

        Label faseActual = (Label) scene.lookup("#faseActual");
        faseActual.setText("Fase Ataque");
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return new ReagrupacionOrigen(scene, juego);
    }

    @Override
    public Fase tocoSeleccionable(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();
        botonSiguiente.setVisible(false);

        return new AtaqueDefensor(scene, juego, pais );
    }

}
