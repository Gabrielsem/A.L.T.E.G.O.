package edu.fiuba.algo3.interfaz.pantallas;


import edu.fiuba.algo3.App;
import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.colocacion.Inicial;
import edu.fiuba.algo3.interfaz.vistas.VistaJugador;
import edu.fiuba.algo3.interfaz.vistas.VistaSlider;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.AudioClip;

import java.io.IOException;
import java.util.ArrayList;

public class ControladorPantallaJuego {

    private Juego juego;
    private Scene scene;
    private Fase fase;
    private AudioClip audio;
    private enum EjeCambioEscala {
        HORIZONTAL, VERTICAL, NINGUNO;
    }

    public ControladorPantallaJuego(Scene scene, Juego juego) throws IOException {
        this.scene = scene;
        this.juego = juego;

        App.cancion("cancion1");
        
        App.loadFXML("VistaPantallaJuego.fxml", this);
        this.fase = new Inicial(juego, scene);

        Slider nodoSlider = (Slider) scene.lookup("#slider");
        nodoSlider.setUserData(new VistaSlider(nodoSlider, (Button) scene.lookup("#botonSiguiente")));
        mostrarTabJugadores();
        inicializarAjusteEscala();
    }

    @FXML
    public void modificarMusica(ActionEvent actionEvent) {
        ToggleButton botonMusica = (ToggleButton) actionEvent.getSource();

        if (botonMusica.isSelected()) App.detenerCancion();
        else App.reproducirCancion();
    }

    @FXML


    private void mostrarTabJugadores(){
        HBox caja = (HBox) scene.lookup("#tabJugadores");
        ArrayList<Node> sobrantes = new ArrayList<>();

        for( int i=0 ; i<6; i++ ){
            String color = VistaJugador.getColorJugador(i+1);
            if( color=="" ) {
                sobrantes.add( caja.getChildren().get(i) );
                continue;
            }
            HBox tab = (HBox) caja.getChildren().get(i);
            tab.setStyle(String.format("-fx-background-color: %s;",color));
            tab.setVisible(true);

            ( (Label) tab.getChildren().get(0) ).setText(String.valueOf(i+1));
        }
        caja.getChildren().removeAll(sobrantes);

    }

    private void inicializarAjusteEscala() {
        ChangeListener<Number> alturaListener = (observable, oldValue, newValue) -> {
            ajustarEscala(EjeCambioEscala.VERTICAL, newValue);
        };
        ChangeListener<Number> anchoListener= (observable, oldValue, newValue) -> {
            ajustarEscala(EjeCambioEscala.HORIZONTAL, newValue);
        };
        GridPane grilla = (GridPane) scene.lookup("#grilla");
        grilla.heightProperty().addListener(alturaListener);
        grilla.widthProperty().addListener(anchoListener);
        ajustarEscala(EjeCambioEscala.NINGUNO, 0);
    }

    @FXML
    public void tocarPais(MouseEvent mouseEvent) {

        Node node = (Node) mouseEvent.getSource();
        Pais pais = (Pais) node.getUserData();
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            fase = fase.tocoPais(node);
        }
    }

    @FXML
    public void tocoBoton(ActionEvent actionEvent) throws IOException {
        fase = fase.tocoBoton((Button) actionEvent.getSource());
        verificarGanador();
    }

    @FXML
    public void mostrarNombrePais(MouseEvent mouseEvent) {
        Group nodoPais = (Group) mouseEvent.getSource();
        String nombrePais = nodoPais.getId();
        nombrePais = nombrePais.replace("_", " ");

        Label labelNombrePais = (Label) scene.lookup("#nombrePais");
        Label labelNombreContinente = (Label) scene.lookup("#nombreContinente");

        labelNombrePais.setText(nombrePais);
        labelNombreContinente.setText(((Pais) nodoPais.getUserData()).continente());
    }

    private void ajustarEscala(EjeCambioEscala cambio, Number nuevoValor) {

        Group mapa = (Group) scene.lookup("#_root");
        GridPane grilla = (GridPane) scene.lookup("#grilla");
        ColumnConstraints col1 = grilla.getColumnConstraints().get(0);
        RowConstraints fila2 = grilla.getRowConstraints().get(1);
        Bounds boundsMapa = mapa.getLayoutBounds();

        Insets padding = grilla.getPadding();
        Insets margen = GridPane.getMargin(mapa);
        double dx = padding.getRight() + padding.getRight() + margen.getRight() + margen.getLeft();
        double dy = padding.getTop() + padding.getBottom() + margen.getTop() + margen.getBottom();

        double x = cambio == EjeCambioEscala.HORIZONTAL ? nuevoValor.doubleValue() : grilla.getWidth();
        double y = cambio == EjeCambioEscala.VERTICAL ? nuevoValor.doubleValue() : grilla.getHeight();
        x = x * col1.getPercentWidth() / 100;
        y = y * fila2.getPercentHeight() / 100;

        double relacionAspectoMapa = boundsMapa.getHeight()/boundsMapa.getWidth();
        double relacionAspectoGrilla = y/x;

        double factor = 0;
        if (relacionAspectoGrilla > relacionAspectoMapa) {
            factor = (x - dx)/ boundsMapa.getWidth();
        } else {
            factor = (y - dy) / boundsMapa.getHeight();
        }

        mapa.setScaleX(factor);
        mapa.setScaleY(factor);
    }

    private void verificarGanador()  throws IOException {

        ArrayList<Jugador> jugadores = juego.getJugadores();
        ArrayList<Jugador> ganadores = new ArrayList<>();

        for (Jugador jug: jugadores) {
            if(jug.gane()) ganadores.add(jug);
        }

        if(ganadores.size() == 0) return;

        new ControladorPantallaFinal(scene, ganadores);
    }
}
