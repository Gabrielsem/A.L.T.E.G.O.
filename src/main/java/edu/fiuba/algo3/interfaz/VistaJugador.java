package edu.fiuba.algo3.interfaz;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tarjeta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    static HashMap<Integer, String>colorJugador;

    public VistaJugador(Scene scene) {
        this.scene = scene;
        cajaObjetivos = nuevaCaja();
        cajaTarjetas = nuevaCaja();
        cajaConquistados = nuevaCaja();
    }

    static public void setJuego(Juego unJuego) { juego = unJuego;}

    public static void setColorJugador( HashMap<Integer,String> colores ) {
        colorJugador = colores;
    }

    public static String getColorJugador(int nJug){
        return colorJugador.getOrDefault(nJug,"");
    };

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
        cajaTarjetas.getStyleClass().add("tarjetas");

        for (Tarjeta t : tarjetas) {

            StackPane tarjeta = new StackPane();
            tarjeta.getStyleClass().add("tarjeta");
            ImageView imagen = new ImageView();
            HBox pais = new HBox();

            try {
                imagen = new ImageView(new Image(new FileInputStream("imagenes/canon.png")));
                imagen = new ImageView(new Image(new FileInputStream("imagenes/"+t.obtenerSimbolo().nombre()+".png")));
            } catch (FileNotFoundException e) {
                System.out.println("Didnt find imagenes/"+t.obtenerSimbolo().nombre()+".png");
            }
            StackPane.setAlignment(imagen,Pos.CENTER_LEFT);
            imagen.setPreserveRatio(true);
            imagen.setFitHeight(30);
            StackPane.setMargin(imagen,new Insets(0,0,0,10));


            pais.getChildren().add( new Label( t.pais() ) );
            pais.setAlignment(Pos.CENTER);
            StackPane.setMargin(pais, new Insets(3,0,3,0));

            tarjeta.getChildren().addAll(pais,imagen);
            tarjeta.setPadding( new Insets(3,0,3,0) );
            cajaTarjetas.getChildren().add(tarjeta);
        }

        AnchorPane.setLeftAnchor(cajaTarjetas,0.0);
        AnchorPane.setRightAnchor(cajaTarjetas,0.0);
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
