package edu.fiuba.algo3.interfaz;


import edu.fiuba.algo3.App;
import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseInicial;
import edu.fiuba.algo3.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControladorPantallaJuego {

    Juego juego;
    Scene scene;
    Fase fase;

    public ControladorPantallaJuego(Scene scene, Juego juego) throws IOException {
        this.scene = scene;
        this.juego = juego;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPantallaJuego.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());

        ajustarEscala(0.6);

        this.fase = new FaseInicial(juego, scene);
        this.fase.iniciar();
    }

    @FXML
    public void tocarPais(MouseEvent mouseEvent) {

        Node node = (Node) mouseEvent.getSource();

        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            fase.tocoPais(node.getId());
        } else {
            System.out.printf("País %s (%d fichas del Jugador %d)%n",
            node.getId(), juego.cantidadFichas(node.getId()), juego.propietarioDe(node.getId()).numero());
        }
    }

    @FXML
    public void tocoSiguiente(ActionEvent actionEvent) {
        fase = fase.tocoSiguiente();
    }

    public void ajustarEscala( double escala ) {

        Group mapa = (Group) scene.lookup("#_root");
        Bounds bounds = mapa.getLayoutBounds();

        double translacionX = -bounds.getWidth()*(1-escala)/2;
        double translacionY = -bounds.getHeight()*(1-escala)/2;

        //FIXME - constantes - a ver
        double shiftX = -10;
        double shiftY = 10;

        mapa.setTranslateX( translacionX+shiftX );
        mapa.setTranslateY( translacionY+shiftY );

        mapa.setScaleX(escala);
        mapa.setScaleY(escala);


    }
}
