package edu.fiuba.algo3.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BotonIniciarJuego implements EventHandler<ActionEvent> {

    int cantidadDeJugadores;

    public BotonIniciarJuego(int cantJugadores ) {
        cantidadDeJugadores = cantJugadores;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println("Comenzar Juego con "+cantidadDeJugadores+" jugadores");
    }
}
