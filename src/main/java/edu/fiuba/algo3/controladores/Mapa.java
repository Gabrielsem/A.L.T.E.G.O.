package edu.fiuba.algo3.controladores;


import edu.fiuba.algo3.controladores.fases.Fase;
import edu.fiuba.algo3.controladores.fases.FaseInicial;
import edu.fiuba.algo3.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Mapa {

    Juego juego;
    Scene scene;
    Fase fase;

    public Mapa(Scene scene, Juego juego) throws IOException {
        this.scene = scene;
        this.juego = juego;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaMapa.fxml"));
        fxmlLoader.setController(this);
        scene.setRoot(fxmlLoader.load());
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
}
