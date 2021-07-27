package edu.fiuba.algo3.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class Menu {

    Scene scene;

    public Menu(Scene scene) throws IOException {
        this.scene = scene;

        //FIXME - Estas 3 lineas se repiten en varios lados, hacer una funcion
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("menu.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    public void pedirJugadores(ActionEvent actionEvent) throws IOException {
        new PedirJugadores(scene);
    }

}
