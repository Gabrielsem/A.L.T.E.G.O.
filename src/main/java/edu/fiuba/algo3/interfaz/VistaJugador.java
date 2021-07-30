package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tarjeta;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VistaJugador implements Observer {
    private Scene scene;
    private VBox cajaObjetivos;
    private VBox cajaConquistados;
    private VBox cajaTarjetas;
    static private HashMap<Integer, VistaJugador> vistas = new HashMap<>();

    public VistaJugador(Scene scene, int numJugador) {
        this.scene = scene;
        cajaObjetivos = nuevaCaja();
        cajaTarjetas = nuevaCaja();
        cajaConquistados = nuevaCaja();
        vistas.put(numJugador, this);
    }

    private VBox nuevaCaja() {
        VBox caja = new VBox();
        caja.setAlignment(Pos.TOP_CENTER);
        return caja;
    }

    @Override
    public void update(Observable o, Object arg) {
        Jugador jug = (Jugador) o;
        actualizarObjetivos(jug);
        actualizarTarjetas(jug);
        actualizarConquistados(jug);
    }

    private void actualizarConquistados(Jugador jugador) {
    }

    private void actualizarTarjetas(Jugador jugador) {
        cajaTarjetas.getChildren().clear();
        Collection<Tarjeta> tarjetas = jugador.getTarjetas();
        String texto = "";
        for (Tarjeta t : tarjetas) {
            texto = texto.concat(String.format("%s - %s\n", t.obtenerSimbolo().obtenerNombre(), t.pais()));
        }
        Label objetivo = new Label(texto);
        cajaTarjetas.getChildren().add(objetivo);
    }

    private void actualizarObjetivos(Jugador jugador) {
        cajaObjetivos.getChildren().clear();
        Label objetivo = new Label(jugador.descripcionObjetivos());
        cajaObjetivos.getChildren().add(objetivo);
    }

    static public Node getVistaObjetivos(int numJugador) {
        return vistas.get(numJugador).cajaObjetivos;
    }

    static public Node getVistaTarjetas(int numJugador) {
        return vistas.get(numJugador).cajaTarjetas;
    }

    static public Node getVistaConquistados(int numJugador) {
        return vistas.get(numJugador).cajaConquistados;
    }
}
