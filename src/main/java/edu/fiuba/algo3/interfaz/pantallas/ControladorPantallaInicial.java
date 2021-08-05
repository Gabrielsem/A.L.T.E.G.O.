package edu.fiuba.algo3.interfaz.pantallas;

import edu.fiuba.algo3.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.IOException;

public class ControladorPantallaInicial {

    Scene scene;

    public ControladorPantallaInicial(Scene scene) throws IOException {
        this.scene = scene;

        //FIXME - Repeated - Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPantallaInicial.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());

        setBackground();
        App.cancion("cancionInicio");
    }

    @FXML
    public void pedirJugadores(ActionEvent actionEvent) throws IOException {
        new ControladorPantallaPedirJugadores(scene);
    }

    @FXML
    public void modificarMusica(ActionEvent actionEvent) {
        ToggleButton botonMusica = (ToggleButton) actionEvent.getSource();

        if (botonMusica.isSelected()) App.detenerCancion();
        else App.reproducirCancion();
    }

    @FXML
    public void setBackground() throws IOException {

        GridPane grilla = (GridPane) scene.lookup("#grilla");
        Image imagen = new Image(new FileInputStream("imagenes/Guerra_dark_blur.png"));
        BackgroundImage fondoimg = new BackgroundImage(imagen,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1, 1, true, true, false, false));
        grilla.setBackground(new Background(fondoimg));
        grilla.setStyle("#fondo");
    }
}
