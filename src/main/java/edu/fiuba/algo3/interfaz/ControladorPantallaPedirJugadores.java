package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observer;

public class ControladorPantallaPedirJugadores {

    Scene scene;
    HashMap<Integer, String> coloresJugadores;

    public ControladorPantallaPedirJugadores(Scene scene) throws IOException {
        this.scene = scene;
        this.coloresJugadores = new HashMap<>();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPantallaPedirJugadores.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    public void agregarJugador(ActionEvent actionEvent) {
        Button botonColor = (Button) actionEvent.getSource();

        int numeroJugadorActual = this.coloresJugadores.size() + 1;

        String jugador = botonColor.getId();
        jugador = jugador.replace("color", "");

        coloresJugadores.put(numeroJugadorActual, botonColor.getAccessibleText());

        Label advertenciaCantidadJugadores = (Label) scene.lookup("#advertenciaCantidadJugadores");
        advertenciaCantidadJugadores.setVisible(false);

        Label labelJugador = (Label) scene.lookup(String.format("#%s", jugador));
        labelJugador.setText(String.format("Jugador %d", numeroJugadorActual));

        botonColor.setOnAction(null);
        botonColor.setOpacity(0.5);
    }

    @FXML
    public void iniciarJuego(ActionEvent actionEvent) throws IOException {

        if (this.coloresJugadores.size() < 2) {
            Label advertenciaCantidadJugadores = (Label) scene.lookup("#advertenciaCantidadJugadores");
            advertenciaCantidadJugadores.setVisible(true);
            return;
        }

        Juego juego = new Juego(this.coloresJugadores.size(), "archivos/paises.json", "objetivos.json");
        VistaPais.setColorJugador(coloresJugadores);//FIXME
        new ControladorPantallaJuego(scene, juego);
        addPaisObservers(juego);
        juego.addObserverTurnos(new VistaTurno(scene));
    }

    private void addPaisObservers(Juego juego) {
        HashMap<String, Observer> observers = new HashMap<>();
        for (Node nodo : ((Group) scene.lookup("#_root")).getChildren()) {
            if (nodo.getStyleClass().contains("pais")) {
                observers.put(nodo.getId(), new VistaPais(nodo, this.coloresJugadores));
            }
        }

        juego.addPaisObservers(observers);
    }
}
