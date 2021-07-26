package edu.fiuba.algo3.UI;

import edu.fiuba.algo3.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

public class sandbox {

    static private Juego juego;
    private Scene scene;
    static private int cantJug;
    private Function<Event,Integer> tocarAction = (e)->{return 0;};

    @FXML
    Label Estado;

    public sandbox() throws FileNotFoundException {

        scene = App.getScene();
        juego = new Juego(cantJug,"archivos/paises.json");
        System.out.println("Load Sandbox");
    }

    public static void startNew(int cantJugadores) throws IOException {
        cantJug = cantJugadores;
        App.setRoot("sandbox");
    }

    public void start() {

        App.controladoresVista = new HashMap<>();
        juego.addObservers(scene);
        juego.inicializar();
        Estado.setText("New");

        tocarAction = (Event mouseEvent)->{
            String nombrePais = (String) ( (Node) mouseEvent.getSource() ).getId();
            System.out.println("Tocaste el Pais: "+nombrePais);
            return 0;
        };
    }

    // Al tocar un pais se activa un evento de tipo MouseEvent, por lo que el parametro sera un MouseEvent
    // JavaFX usa reflexion para encontrar 'eventHandlers', pero no funciona para MouseEvent ( esta pensado para ActionEvent ? )
    // Ergo, le pongo que es de tipo Event ( Que parece funcionar para lo que requerimos )
    @FXML
    public void tocarPais(Event mouseEvent) {
        tocarAction.apply(mouseEvent);
    }


    public void atacar(ActionEvent actionEvent) {

        System.out.println("ATK");
    }

    public void darFichas(ActionEvent actionEvent) {
        Estado.setText("Dar Fichas");

        tocarAction = (Event mouseEvent)->{
            String nombrePais = (String) ( (Node) mouseEvent.getSource() ).getId();

            juego.agregarFichas(nombrePais,1);

            return 0;
        };

    }
}
