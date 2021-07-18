package edu.fiuba.algo3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    String version = "0.3";
    int cantJugadores = 2;

    @Override
    public void start(Stage stage) {

        stage.setTitle("TEG "+version);

        menuPrincipal(stage);

    }

    public static void main(String[] args) {
        launch();
    }

    private void menuPrincipal( Stage stage ) {

        StackPane layout = new StackPane();

        Button botonJugar = new Button("Jugar");
        Button botonReglas = new Button("Reglas");

        layout.getChildren().add(botonJugar);

        var scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

}