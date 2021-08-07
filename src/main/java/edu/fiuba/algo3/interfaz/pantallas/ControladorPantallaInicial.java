package edu.fiuba.algo3.interfaz.pantallas;

import edu.fiuba.algo3.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;

import java.io.IOException;

public class ControladorPantallaInicial {

    Scene scene;

    public ControladorPantallaInicial(Scene scene) throws IOException {
        this.scene = scene;

        App.loadFXML("VistaPantallaInicial.fxml", this);
        App.cancion("cancionInicio");
        App.reproducirCancion();

        ((ToggleButton) scene.lookup("#botonMusica")).setOnAction(new ControladorBotonMusica());
    }

    @FXML
    public void pedirJugadores(ActionEvent actionEvent) throws IOException {
        new ControladorPantallaPedirJugadores(scene);
    }

    @FXML
    public void cambiarVista(ActionEvent actionEvent) { App.cambiarVista(scene, (ToggleButton) actionEvent.getSource()); }
}
