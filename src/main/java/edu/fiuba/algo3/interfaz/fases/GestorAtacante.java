package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class GestorAtacante implements Fase {

    FaseAtaque fase;
    Juego juego;// FIXME - Sobra ?
    Jugador jugadorActual;
    Scene scene;

    Collection<String> seleccionables;

    Label instruccion;


    public GestorAtacante(FaseAtaque faseAtaque, Juego juego, Scene scene, Jugador jugadorActual){

        fase = faseAtaque;
        this.juego = juego;
        this.jugadorActual = jugadorActual;
        this.scene= scene;
        instruccion = (Label) scene.lookup("#instruccion");

    }

    private void setSeleccionables(Collection<String> seleccion) {
        seleccionables = seleccion;
        //TODO limpiar y agregar styleClass seleccionable
    }

    @Override
    public void iniciar() {
        instruccion.setText(String.format("Jugador %d, toca el país del que quieras atacar", jugadorActual.numero()));
        setSeleccionables( jugadorActual.paisesConquistados() );
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return null;
    }

    @Override
    public void tocoPais(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();
        if( seleccionables.contains( pais.nombre() ) )
            fase.setGestor( new GestorDefensor( fase,juego, scene, jugadorActual, pais ) );
    }
}
