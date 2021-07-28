package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ControladorPantallaInicial {

    Scene scene;

    public ControladorPantallaInicial(Scene scene) throws IOException {
        this.scene = scene;

        //FIXME - Estas 3 lineas se repiten en varios lados, hacer una funcion
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPantallaInicial.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    public void pedirJugadores(ActionEvent actionEvent) throws IOException {
        new ControladorPantallaPedirJugadores(scene);
    }

}
