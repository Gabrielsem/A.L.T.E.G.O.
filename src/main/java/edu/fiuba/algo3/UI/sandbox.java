package edu.fiuba.algo3.UI;

import edu.fiuba.algo3.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class sandbox {

    static private Juego juego;
    private Scene scene;
    static private int cantJug;

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

        juego.addObservers(scene);
        juego.inicializar();
    }

    // Al tocar un pais se activa un evento de tipo MouseEvent, por lo que el parametro sera un MouseEvent
    // JavaFX usa reflexion para encontrar 'eventHandlers', pero no funciona para MouseEvent ( esta pensado para ActionEvent ? )
    // Ergo, le pongo que es de tipo Event ( Que parece funcionar para lo que requerimos )
    @FXML
    public void tocarPais(Event mouseEvent) {

        Node node = (Node) mouseEvent.getSource() ;
        String nombrePais = (String) node.getId();
        Node nodo = scene.lookup("#Francia");
        if( Objects.isNull( nodo ) )
            System.out.println("Broken At - Sandbox");

        System.out.println("Tocaste el Pais: "+nombrePais);
    }


    public void atacar(ActionEvent actionEvent) {

        System.out.println("ATK");
    }

}
