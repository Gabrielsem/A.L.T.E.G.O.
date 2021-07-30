package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Turnos;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;

public class VistaTurno implements Observer {
    VBox cajaTurno;
    Label labelTurno;
    TitledPane panelConquistados;

    public VistaTurno(Scene scene) {
        cajaTurno = (VBox) scene.lookup("#playerBox");
        labelTurno = (Label) cajaTurno.getChildren().get(0);
        panelConquistados = ((Accordion) scene.lookup("#acordion")).getPanes().get(0);
    }

    public void update(Observable observable, Object arg) {
        Turnos turnos = (Turnos) observable;
        int numeroActual = turnos.turnoActual().numero();
        String color = VistaPais.getColorJugador(numeroActual);
        cajaTurno.setStyle("-fx-background-color: "+color+";");
        labelTurno.setText("Jugador "+ numeroActual);
        panelConquistados.expandedProperty().set(true);
        Event.fireEvent(panelConquistados, new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));
    }
}
