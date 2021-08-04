package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.modelo.Turnos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VistaTurno implements Observer {

    //FIXME ? - no deberia ser privado?
    VBox cajaTurno;
    Label labelTurno;
    TitledPane panelConquistados;
    TitledPane panelObjetivos;
    TitledPane panelTarjetas;
    HashMap<Integer, VistaJugador> vistasJugadores;

    public VistaTurno(Scene scene, HashMap<Integer, VistaJugador> vistasJugadores) {
        cajaTurno = (VBox) scene.lookup("#playerBox");
        labelTurno = (Label) scene.lookup("#labelJugadorActual");
        Accordion acc = (Accordion) scene.lookup("#acordion");
        panelConquistados = acc.getPanes().get(0);
        panelObjetivos = acc.getPanes().get(1);
        panelTarjetas = acc.getPanes().get(2);
        this.vistasJugadores = vistasJugadores;
    }

    public void update(Observable observable, Object arg) {
        Turnos turnos = (Turnos) observable;
        int numeroActual = turnos.turnoActual().numero();
        String color = VistaJugador.getColorJugador(numeroActual);
        cajaTurno.setStyle("-fx-background-color: "+color+";");
        labelTurno.setText("Jugador "+ numeroActual);
        panelConquistados.expandedProperty().set(true);
        VistaJugador vistaActual = vistasJugadores.get(turnos.turnoActual().numero());
        actualizarPanel(panelConquistados, vistaActual.getVistaConquistados());
        actualizarPanel(panelObjetivos, vistaActual.getVistaObjetivos());
        actualizarPanel(panelTarjetas, vistaActual.getVistaTarjetas());
    }

    private void actualizarPanel(TitledPane titulo, Node vista) {
        AnchorPane panel = (AnchorPane) titulo.getContent();
        panel.getChildren().clear();
        panel.getChildren().add(vista);
    }
}
