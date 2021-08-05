package edu.fiuba.algo3.interfaz.pantallas;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.interfaz.vistas.VistaBatalla;
import edu.fiuba.algo3.interfaz.vistas.VistaJugador;
import edu.fiuba.algo3.interfaz.vistas.VistaPais;
import edu.fiuba.algo3.interfaz.vistas.VistaTurno;
import edu.fiuba.algo3.modelo.Batalla;
import edu.fiuba.algo3.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observer;

public class ControladorPantallaPedirJugadores {

    private Scene scene;
    private HashMap<Integer, String> coloresJugadores;
    private HashSet<String> idLabelsActivas = new HashSet<>();

    public ControladorPantallaPedirJugadores(Scene scene) throws IOException {
        this.scene = scene;
        this.coloresJugadores = new HashMap<>();

        App.loadFXML("VistaPantallaPedirJugadores.fxml", this);
        setBackground();
        if (!App.hayMusica()) {
            ToggleButton botonMusica = (ToggleButton) scene.lookup("#botonMusica");
            botonMusica.setSelected(true);
        };
    }

    @FXML
    public void botonJugador(ActionEvent actionEvent) {

        ToggleButton botonColor = (ToggleButton) actionEvent.getSource();

        if(botonColor.isSelected()) agregarJugador(actionEvent);
        else eliminarJugador(actionEvent);

    }

    @FXML
    public void agregarJugador(ActionEvent actionEvent) {

        ToggleButton botonColor = (ToggleButton) actionEvent.getSource();
        int numeroJugadorActual = 0;

        for(int i = 1; i <= 6; i++){
            if(!coloresJugadores.containsKey(i)){
                numeroJugadorActual = i;
                break;
            }
        }

        String jugador = botonColor.getId();
        jugador = jugador.replace("color", "");

        coloresJugadores.put(numeroJugadorActual, botonColor.getAccessibleText());

        Label advertenciaCantidadJugadores = (Label) scene.lookup("#advertenciaCantidadJugadores");
        advertenciaCantidadJugadores.setVisible(false);

        Label labelJugador = (Label) scene.lookup(String.format("#%s", jugador));
        labelJugador.setText(String.format("Jugador %d", numeroJugadorActual));

        idLabelsActivas.add(labelJugador.getId());
        botonColor.setOpacity(0.5);
    }

    @FXML
    public void eliminarJugador(ActionEvent actionEvent) {

        ToggleButton botonColor = (ToggleButton) actionEvent.getSource();

        String jugador = botonColor.getId();
        jugador = jugador.replace("color", "");

        Label labelJugador = (Label) scene.lookup(String.format("#%s", jugador));
        String textoNombreJugador = labelJugador.getText();
        int numeroJugadorActual = Character.getNumericValue(textoNombreJugador.charAt(textoNombreJugador.length() - 1));

        coloresJugadores.remove(numeroJugadorActual);

        labelJugador.setText("");
        String idLabelJugador = labelJugador.getId();
        idLabelsActivas.remove(idLabelJugador);

        botonColor.setOpacity(1);
        botonColor.setSelected(false);
        actualizarJugadores();
    }

    private void actualizarJugadores() {
        for( int numeroJugador: coloresJugadores.keySet()){ //Recorro para ver si hay algun "hueco" en los numeros de los jugadores o si falta el jugador 1
            if((coloresJugadores.containsKey(numeroJugador + 2) && !coloresJugadores.containsKey(numeroJugador + 1)) || !coloresJugadores.containsKey(1)){
                if(!coloresJugadores.containsKey(1)) numeroJugador --;
                for( int numeroJugadorACambiar: new HashSet<>(coloresJugadores.keySet())){ //Muevo los colores hacia atrás para "llenar" el hueco
                    if(numeroJugadorACambiar > numeroJugador){
                        coloresJugadores.put(numeroJugadorACambiar - 1,coloresJugadores.get(numeroJugadorACambiar));
                        coloresJugadores.remove(numeroJugadorACambiar);
                    }
                }
                for (String idLabel: idLabelsActivas){ // Actualizo las labels para que se visualize el cambio
                    Label labelJugador = (Label) scene.lookup(String.format("#%s",idLabel));
                    String textoNombreJugador = labelJugador.getText();
                    int numeroJugadorACambiar = Character.getNumericValue(textoNombreJugador.charAt(textoNombreJugador.length() - 1));
                    if(numeroJugadorACambiar > numeroJugador){
                        labelJugador.setText(String.format("Jugador %d", numeroJugadorACambiar - 1));
                    }
                }
                break; // Como solo va a haber un hueco a la vez, dejo de recorrer
            }
        }
    }

    @FXML
    public void iniciarJuego(ActionEvent actionEvent) throws IOException {

        if (this.coloresJugadores.size() < 2) {
            Label advertenciaCantidadJugadores = (Label) scene.lookup("#advertenciaCantidadJugadores");
            advertenciaCantidadJugadores.setVisible(true);
            return;
        }

        Juego juego = new Juego(this.coloresJugadores.size(), "archivos/paises.json", "archivos/objetivos.json","archivos/tarjetas.json");
        VistaJugador.setColoresJugadores(coloresJugadores);

        new ControladorPantallaJuego(scene, juego);
        addPaisObservers(juego);
        addJugadorYTurnoObservers(juego);
        Batalla.agregarObservador( new VistaBatalla() );
    }

    private void addPaisObservers(Juego juego) {
        HashMap<String, Observer> observers = new HashMap<>();
        for (Node nodo : ((Group) scene.lookup("#_root")).getChildren()) {
            if (nodo.getStyleClass().contains("pais")) {
                observers.put(nodo.getId(), new VistaPais(nodo));
            }
        }

        juego.addPaisObservers(observers);
    }

    private void addJugadorYTurnoObservers(Juego juego) {
        HashMap<Integer, Observer> observers = new HashMap<>();
        HashMap<Integer, VistaJugador> vistasJugadores = new HashMap<>();
        VistaJugador.setJuego(juego);
        for (int i : coloresJugadores.keySet()) {
            vistasJugadores.put(i, new VistaJugador(scene));
            observers.put(i, vistasJugadores.get(i));
        }

        juego.addJugadorObserver(observers);
        juego.addObserverTurnos(new VistaTurno(scene, vistasJugadores));
    }

    @FXML
    public void modificarMusica(ActionEvent actionEvent) {
        ToggleButton botonMusica = (ToggleButton) actionEvent.getSource();

        if (botonMusica.isSelected()) App.detenerCancion();
        else App.reproducirCancion();
    }

    @FXML
    public void setBackground() throws IOException {

        GridPane grilla = (GridPane) scene.lookup("#grilla");
        Image imagen = new Image(new FileInputStream("imagenes/Guerra_dark_blur.png"));
        BackgroundImage fondoimg = new BackgroundImage(imagen,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1, 1, true, true, false, false));
        grilla.setBackground(new Background(fondoimg));
        grilla.setStyle("#fondo");
    }
}
