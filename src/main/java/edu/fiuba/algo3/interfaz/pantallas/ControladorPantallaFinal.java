package edu.fiuba.algo3.interfaz.pantallas;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorPantallaFinal {

    //FIXME - Privatize
    Scene scene;
    HashMap<Integer, String> coloresJugadores;

    public ControladorPantallaFinal(Scene scene, ArrayList<Jugador> ganadores) throws IOException {
        this.scene = scene;
        this.coloresJugadores = new HashMap<>();

        //FIXME - Repeated - Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPantallaFinal.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());

        App.clearPopUps();
        mostrarGanadores(ganadores);
    }

    @FXML
    public void mostrarGanadores(ArrayList<Jugador> ganadores) {

        if(ganadores.size() > 1) {
            Label labelGanadores = (Label) scene.lookup("#tituloPantalla");
            labelGanadores.setText("Felicitaciones, los ganadores son: ");
        }

        String textoGanador = "";

        for(int i = 0; i < ganadores.size(); i++) {
            textoGanador = textoGanador.concat("Jugador " + ganadores.get(i).numero());
            if(i == ganadores.size() - 2) textoGanador = textoGanador.concat(" y ");
            else if(ganadores.size() > 1 && i != ganadores.size() - 1) textoGanador = textoGanador.concat(", ");
        }

        Label labelGanadores = (Label) scene.lookup("#textoGanadores");
        labelGanadores.setText(textoGanador);
    }

    @FXML
    public void botonSalir(ActionEvent actionEvent) {
        System.exit(0);
    }
}
