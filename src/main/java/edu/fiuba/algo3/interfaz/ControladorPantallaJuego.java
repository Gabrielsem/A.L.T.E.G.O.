package edu.fiuba.algo3.interfaz;


import edu.fiuba.algo3.App;
import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseInicial;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

public class ControladorPantallaJuego {

    private Juego juego;
    private Scene scene;
    private Fase fase;
    private enum EjeCambioEscala {
        HORIZONTAL, VERTICAL, NINGUNO;
    }

    public ControladorPantallaJuego(Scene scene, Juego juego) throws IOException {
        this.scene = scene;
        this.juego = juego;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPantallaJuego.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());
        this.fase = new FaseInicial(juego, scene);
        this.fase.iniciar();

        inicializarAjusteEscala();
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
            fase.tocoPais(node);
        } else {
            System.out.printf("País %s (%d fichas del Jugador %d)%n",
                node.getId(), pais.cantidadFichas(), pais.getPropietario().numero());
        }
    }

    @FXML
    public void tocoBoton(ActionEvent actionEvent) {
        fase = fase.tocoBoton((Button) actionEvent.getSource());
    }

    private void ajustarEscala(EjeCambioEscala cambio, Number nuevoValor) {

        Group mapa = (Group) scene.lookup("#_root");
        Bounds boundsMapa = mapa.getLayoutBounds();
        GridPane grilla = (GridPane) scene.lookup("#grilla");
        ColumnConstraints col1 = grilla.getColumnConstraints().get(0);
        RowConstraints fila2 = grilla.getRowConstraints().get(1);

        double x = cambio == EjeCambioEscala.HORIZONTAL ? nuevoValor.doubleValue() : grilla.getWidth();
        double y = cambio == EjeCambioEscala.VERTICAL ? nuevoValor.doubleValue() : grilla.getHeight();
        x = x * col1.getPercentWidth() / 100;
        y = y * fila2.getPercentHeight() / 100;

        double relacionAspectoMapa = boundsMapa.getHeight()/boundsMapa.getWidth();
        double relacionAspectoGrilla = y/x;

        double factor = 0;
        Insets padding = grilla.getPadding();
        if (relacionAspectoGrilla > relacionAspectoMapa) {
            factor = (x - padding.getLeft() - padding.getRight())/ boundsMapa.getWidth();
        } else {
            factor = (y - padding.getTop() - padding.getBottom()) / boundsMapa.getHeight();
        }


        mapa.setScaleX(factor);
        mapa.setScaleY(factor);
    }
}
