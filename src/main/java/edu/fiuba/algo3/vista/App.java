package edu.fiuba.algo3.vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

        VBox contenedor = new VBox();
        contenedor.setSpacing(10);
        contenedor.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Menu Principal");
        contenedor.getChildren().add(titulo);

        Button botonJugar = new Button("Jugar");
        botonJugar.setOnAction( e->{
            System.out.println("Jugar");
            seleccionarJugadores(stage);
        } );
        contenedor.getChildren().add(botonJugar);

        Button botonReglas = new Button("Reglas");
        botonReglas.setOnAction( e->{
            System.out.println("Reglas");
        } );
        contenedor.getChildren().add(botonReglas);

        layout.getChildren().add(contenedor);

        var scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void seleccionarJugadores( Stage stage ) {

        StackPane layout = new StackPane();

        VBox contenedor = new VBox();
        contenedor.setSpacing(10);
        contenedor.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Seleccionar cantidad de Jugadores");
        contenedor.getChildren().add(titulo);

        HBox opciones = new HBox();
        opciones.setSpacing(10);
        opciones.setAlignment(Pos.TOP_CENTER);

        for( int i=2; i<=6; i++ ) {
            Button boton = new Button(""+i);
            BotonIniciarJuego handler = new BotonIniciarJuego(i);
            boton.setOnAction(handler);
            opciones.getChildren().add(boton);
        }

        contenedor.getChildren().add(opciones);

        layout.getChildren().add(contenedor);

        var scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

}