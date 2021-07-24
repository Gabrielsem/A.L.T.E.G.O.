package edu.fiuba.algo3.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class PedirJugadores {

    @FXML
    public void iniciarJuego(ActionEvent actionEvent) throws IOException {
        System.out.println("Comenzar Juego");
        App.setRoot("juegoUI");
    }
}
