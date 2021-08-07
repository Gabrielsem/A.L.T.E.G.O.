package edu.fiuba.algo3.interfaz.vistas;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tarjeta;
import edu.fiuba.algo3.util.FileLoader;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class VistaJugador implements Observer {
    private Scene scene;
    private final VBox cajaObjetivos;
    private final VBox cajaConquistados;
    private final VBox cajaTarjetas;

    static private Juego juego;
    static HashMap<Integer, String>colorJugadores;

    public VistaJugador(Scene scene) {
        this.scene = scene;
        cajaObjetivos = nuevaCaja();
        cajaTarjetas = nuevaCaja();
        cajaConquistados = nuevaCaja();
    }

    static public void setJuego(Juego unJuego) { juego = unJuego;}

    public static void setColoresJugadores(HashMap<Integer,String> colores ) {
        colorJugadores = colores;
    }

    public static String getColorJugador(int nJug){
        return colorJugadores.getOrDefault(nJug,"");
    };

    private VBox nuevaCaja() {
        VBox caja = new VBox();
        caja.setAlignment(Pos.TOP_CENTER);
        caja.setPadding(new Insets(10));
        caja.getStyleClass().add("dataBoxSection");
        return caja;
    }

    @Override
    public void update(Observable o, Object arg) {
        Jugador jug = (Jugador) o;
        actualizarObjetivos(jug);
        actualizarTarjetas(jug);
        actualizarConquistados(jug);

        if( Objects.nonNull(arg) ) App.notificacion( String.valueOf(arg) );
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
            ImageView imagen = FileLoader.imagen(t.obtenerSimbolo().nombre()+".png");
            HBox pais = new HBox();

            String url = "imagenes/"+t.obtenerSimbolo().nombre()+".png";
            try {
                imagen = new ImageView(new Image(FileLoader.resourceInputStream((url))) );
            } catch (Throwable e){
                System.out.println("Didnt find "+url);
                e.printStackTrace();
            }
            StackPane.setAlignment(imagen,Pos.CENTER_LEFT);
            imagen.setPreserveRatio(true);
            imagen.setFitHeight(30);
            StackPane.setMargin(imagen,new Insets(0,0,0,10));

            pais.getChildren().add(new Label( t.displayPais() ));
            pais.setAlignment(Pos.CENTER_RIGHT);
            pais.setPadding(new Insets(5, 15, 5, 0));
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
