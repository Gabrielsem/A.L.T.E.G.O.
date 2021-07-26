package edu.fiuba.algo3.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.io.IOException;

public class Menu {

    @FXML
    public void pedirJugadores(ActionEvent actionEvent) throws IOException {
        App.setRoot("pedirJugadores");
    }

    //FIXME - Esto no va en la version final -
    public void sandbox(ActionEvent actionEvent) throws IOException {

        Node node = (Node) actionEvent.getSource() ;
        int data = Integer.parseInt( (String) node.getUserData() );
        sandbox.startNew(data);
    }
}
