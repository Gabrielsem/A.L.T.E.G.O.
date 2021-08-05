package edu.fiuba.algo3.interfaz.pantallas;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ControladorPantallaFinal {

    private Scene scene;
    private HashMap<Integer, String> coloresJugadores;

    public ControladorPantallaFinal(Scene scene, ArrayList<Jugador> ganadores) throws IOException {
        this.scene = scene;
        this.coloresJugadores = new HashMap<>();
        App.loadFXML("VistaPantallaFinal.fxml", this);

        App.clearPopUps();
        App.detenerCancion();
        mostrarGanadores(ganadores);
        App.sonido("ganar", 1);
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

    @FXML
    public void switchVista(ActionEvent actionEvent) {
        ToggleButton botonSwitch = (ToggleButton) actionEvent.getSource();

        scene.getStylesheets().clear();
        if (botonSwitch.isSelected()){
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles-dark.css")).toExternalForm());
        } else {
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles-clear.css")).toExternalForm());
        }
    }
}
