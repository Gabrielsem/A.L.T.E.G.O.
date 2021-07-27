package edu.fiuba.algo3.controladores;

import edu.fiuba.algo3.controladores.Menu;
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
        Scene scene = new Scene(new Label("Cargando..."), 854, 480);
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

        new Menu(scene);
    }

    public static void main(String[] args) {
        launch();
    }


}