package edu.fiuba.algo3;

import edu.fiuba.algo3.interfaz.ControladorPantallaInicial;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    String version = "0.3";

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("TEG "+version);
        Scene scene = new Scene(new Label("Cargando..."), 920, 540);
        stage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        new ControladorPantallaInicial(scene);
    }

    public static void main(String[] args) {
        launch();
    }


}