package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GestorFichasAtaque implements Fase{

    FaseAtaque fase;
    Scene scene;
    Juego juego;
    Pais paisAtacante;
    Pais paisAtacado;

    Label instruccion;

    public GestorFichasAtaque(FaseAtaque faseAtaque, Pais atacante, Pais defensor) {
        fase = faseAtaque;
        scene = fase.scene;
        juego = fase.juego;
        this. paisAtacante = atacante;
        this.paisAtacado =  defensor;
        instruccion = (Label) scene.lookup("#instruccion");
    }

    @Override
    public void iniciar() {
        instruccion.setText("Elegí con cuantas fichas atacar");
        fase.setSeleccionables( Arrays.asList( paisAtacado.nombre(),paisAtacante.nombre() ));
        agregarBotonesAtaque();
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return null;
    }

    @Override
    public void tocoPais(Node nodoPais) {

    }

    private void agregarBotonesAtaque() {
        HBox box = (HBox) scene.lookup("#cajaBotones");

        for (int i = 0; i < (paisAtacante.cantidadFichas() - 1) && i < 3; i++) {
            int index = i+1;
            Button boton = new Button("Atacar con "+index);
            boton.setOnAction( (a) -> atacarCon(index) );
            box.getChildren().add(boton);
        }
    }
    private void ocultarBotonesAtaque() {

        HBox box = (HBox) scene.lookup("#cajaBotones");
        box.getChildren().clear();
    }

    public void atacarCon(int fichas) {

        ocultarBotonesAtaque();

        paisAtacante.atacar(paisAtacado, fichas);
        if( paisAtacado.invadible() )
            fase.setGestor( new GestorInvasion(fase,paisAtacante,paisAtacado) );
        else
            fase.setGestor( new GestorAtacante(fase) );
    }
}
