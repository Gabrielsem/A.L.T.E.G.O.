package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collection;

public class GestorInvasion implements Fase {

    FaseAtaque fase;
    Scene scene;
    Jugador jugadorActual;
    Pais paisAtacante;
    Pais paisAtacado;

    Collection<String> seleccionables;
    Button[] botones = new Button[3];

    Label instruccion;

    public GestorInvasion(FaseAtaque faseAtaque, Scene scene, Jugador jugadorActual, Pais paisAtacante, Pais paisAtacado) {

        fase = faseAtaque;
        this.scene = scene;
        this.jugadorActual = jugadorActual;
        this. paisAtacante = paisAtacante;
        this.paisAtacado =  paisAtacado;
        instruccion = (Label) scene.lookup("#instruccion");
    }

    @Override
    public void iniciar() {
        instruccion.setText(String.format("Elegí con cuantas fichas invadir"));
        paisAtacado.ocupadoPor(jugadorActual,0);
        fase.setSeleccionables( new ArrayList<>());
        agregarBotonesInvasion();
    }

    @Override
    public Fase tocoBoton(Button unBoton) { return null; }

    @Override
    public void tocoPais(Node nodoPais) {

    }

    private void agregarBotonesInvasion() {
        HBox box = (HBox) scene.lookup("#cajaBotones");

        for (int i = 0; i < (paisAtacante.cantidadFichas() - 1) && i < 3; i++) {
            int index = i+1;
            Button boton = new Button("Invadir con "+index);
            boton.setOnAction( (a) -> invadirCon(index) );
            box.getChildren().add(boton);
        }
    }
    private void ocultarBotones() {//FIXME - REPETIDA

        HBox box = (HBox) scene.lookup("#cajaBotones");
        box.getChildren().clear();
    }

    public void invadirCon(int fichas) {

        ocultarBotones();

        paisAtacante.perderFichas(fichas);
        paisAtacado.ocupadoPor(jugadorActual,fichas);

        fase.setGestor( new GestorAtacante(fase,scene,jugadorActual) );
    }
}
