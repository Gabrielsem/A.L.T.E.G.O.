package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Turnos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;

public class VistaTurno implements Observer {
    VBox cajaTurno;
    Label labelTurno;

    public VistaTurno(Scene scene) {
        cajaTurno = (VBox) scene.lookup("#playerBox");
        labelTurno = (Label) cajaTurno.getChildren().get(0);
    }

    public void update(Observable observable, Object arg) {
        Turnos turnos = (Turnos) observable;
        int numeroActual = turnos.turnoActual().numero();
        String color = VistaPais.getColorJugador(numeroActual);
        cajaTurno.setStyle("-fx-background-color: "+color+";");
        labelTurno.setText("Jugador "+ numeroActual);
    }
}
