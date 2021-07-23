package edu.fiuba.algo3.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class Menu {

    @FXML
    public void pedirJugadores(ActionEvent actionEvent) throws IOException {
        App.setRoot("pedirJugadores");
    }
}
