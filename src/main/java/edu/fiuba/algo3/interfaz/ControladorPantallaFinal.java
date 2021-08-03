package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observer;

public class ControladorPantallaFinal {

    Scene scene;
    Jugador jugador;
    HashMap<Integer, String> coloresJugadores;

    public ControladorPantallaFinal(Scene scene, ArrayList<Jugador> ganadores) throws IOException {
        this.scene = scene;
        this.coloresJugadores = new HashMap<>();

        //FIXME - Estas 3 lineas se repiten en varios lados, hacer una funcion
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPantallaFinal.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());

        mostrarGanadores(ganadores);
    }

    @FXML
    public void mostrarGanadores(ArrayList<Jugador> ganadores) {

        String textoGanador = "";

        for(Jugador jug : ganadores) {
            textoGanador = textoGanador.concat(" " + "Jugador " + jug.numero() + " ");
        }

        Label labelGanadores = (Label) scene.lookup("#textoGanadores");
        labelGanadores.setText(textoGanador);
    }

    @FXML
    public void botonSalir(ActionEvent actionEvent) {
        System.exit(0);
    }
}
