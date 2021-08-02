package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.modelo.Juego;
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
    static private Juego juego;

    public VistaJugador(Scene scene) {
        this.scene = scene;
        cajaObjetivos = nuevaCaja();
        cajaTarjetas = nuevaCaja();
        cajaConquistados = nuevaCaja();
    }

    static public void setJuego(Juego unJuego) { juego = unJuego;}

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

    private void actualizarConquistados(Jugador jugador) { //FIXME: Work in progress. Habría que mostrarlo mejor, con las cantidades por cada continente
        cajaConquistados.getChildren().clear();
        String texto = "";
        int totalPaisesJugador = 0;
        int totalPaises = 0;
        HashMap<String, Integer> cantidadPaisesPorContinenteJugador = jugador.paisesPorContinente();
        HashMap<String, Integer> cantidadPaisesPorContinente = juego.cantidadPaisesPorContinente();

        for (int cantidad : jugador.paisesPorContinente().values()) {
            totalPaisesJugador += cantidad;
        }
        for (int cantidad : cantidadPaisesPorContinente.values()) {
            totalPaises += cantidad;
        }

        texto += "Total: " + totalPaisesJugador + "/" + totalPaises + "\n";

        for (String nombreContinente : cantidadPaisesPorContinenteJugador.keySet()){
            texto += nombreContinente + ": " + cantidadPaisesPorContinenteJugador.get(nombreContinente) + "/" + cantidadPaisesPorContinente.get(nombreContinente) + "\n";
        }

        Label paises = new Label(texto);
        cajaConquistados.getChildren().add(paises);
    }

    private void actualizarTarjetas(Jugador jugador) {
        cajaTarjetas.getChildren().clear();
        Collection<Tarjeta> tarjetas = jugador.getTarjetas();
        String texto = "";
        for (Tarjeta t : tarjetas) {
            texto = texto.concat(String.format("%s - %s\n", t.obtenerSimbolo().obtenerNombre(), t.pais()));
        }
        Label objetivo = new Label(texto); //El label no debería llamarse tarjetas?
        cajaTarjetas.getChildren().add(objetivo);
    }

    private void actualizarObjetivos(Jugador jugador) {
        cajaObjetivos.getChildren().clear();
        Label objetivo = new Label(jugador.descripcionObjetivos());
        cajaObjetivos.getChildren().add(objetivo);
    }

    public Node getVistaObjetivos() {
        return cajaObjetivos;
    }

    public Node getVistaTarjetas() {
        return cajaTarjetas;
    }

    public Node getVistaConquistados() {
        return cajaConquistados;
    }
}
