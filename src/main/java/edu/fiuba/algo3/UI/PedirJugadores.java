package edu.fiuba.algo3.UI;

import edu.fiuba.algo3.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class PedirJugadores {

    @FXML
    public void iniciarJuego(ActionEvent actionEvent) throws IOException {
        System.out.println("Comenzar Juego");

        int cantJ = 2 ;// TODO -
        Juego juego = new Juego(cantJ);
        App.setRoot("juegoUI");

        juego.addObservers( App.getScene() );
    }
}
