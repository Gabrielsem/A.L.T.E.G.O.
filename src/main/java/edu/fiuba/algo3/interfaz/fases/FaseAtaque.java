package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.interfaz.VistaPais;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Collection;

public class FaseAtaque implements Fase {
    Scene scene;
    Juego juego;
    Fase gestor;

    Collection<String> seleccionables;

    Jugador jugadorActual;

    Label instruccion;

    public FaseAtaque(Juego juego, Scene scene) {
        this.scene = scene;
        this.juego = juego;
        instruccion = (Label) scene.lookup("#instruccion");
    }

    public void iniciar() {
        juego.reiniciarTurnos();
        jugadorActual = juego.siguienteTurno();
        jugadorActual.prepararAtaques();

        scene.lookup("#botonSiguiente").setVisible(true);

        setGestor( new GestorAtacante(this) );
    }

    public void tocoPais(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();
        if( seleccionables.contains( pais.nombre() ) )
            gestor.tocoPais(nodoPais);
    }

    public Fase tocoBoton(Button unBoton) {
        gestor.tocoBoton(unBoton);
        return this; //TODO: siguiente jugador/etapa reagrupacion
    }

    public void setGestor(Fase gestor ) {
        this.gestor = gestor;
        this.gestor.iniciar();
    }

    public void setSeleccionables(Collection<String> seleccion) {
        seleccionables = seleccion;

        // Estilar seleccionables
        for (Node nodo : ((Group) scene.lookup("#_root")).getChildren()) {
            if (nodo.getStyleClass().contains("pais")) {
                //Clear
                nodo.getStyleClass().remove("paisSeleccionable");

                //Style
                if( seleccionables.contains( nodo.getId() ) ){
                    nodo.getStyleClass().add("paisSeleccionable");
                }
            }
        }
    }

}
