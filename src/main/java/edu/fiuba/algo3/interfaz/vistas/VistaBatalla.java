package edu.fiuba.algo3.interfaz.vistas;

import edu.fiuba.algo3.App;
import edu.fiuba.algo3.modelo.Batalla;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

public class VistaBatalla implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Batalla batalla = (Batalla) o;

        int[] dadosAtacante = batalla.dadosAtacante();
        int[] dadosDefensor = batalla.dadosDefensor();

        VBox caja = new VBox();
        caja.getStyleClass().add("Batalla");
        HBox cajaTitulo = new HBox( new Label("BATALLA") );
        HBox cajaAtacante = new HBox( new Label("Atacante: ") );
        HBox cajaDefensor = new HBox( new Label("Defensor: ") );
        caja.getChildren().addAll(cajaTitulo,cajaAtacante,cajaDefensor);

        for (int j : dadosAtacante) {
            ImageView imagen = new ImageView();
            String url = "src/main/resources/imagenes/Dados/"+String.valueOf(j)+".png";
            try {
                imagen = new ImageView(new Image(new FileInputStream(url)));
            } catch (FileNotFoundException e) {
                System.out.println(url);
            }
            cajaAtacante.getChildren().add( imagen );
            imagen.setPreserveRatio(true);
            imagen.setFitHeight(60);
            HBox.setMargin(imagen,new Insets(3) );
        }

        for( int j : dadosDefensor ){
            ImageView imagen = new ImageView();
            String url = "src/main/resources/imagenes/Dados/"+String.valueOf(j)+".png";
            try {
                imagen = new ImageView(new Image(new FileInputStream(url)));
            } catch (FileNotFoundException e) {
                System.out.println(url);
            }
            cajaDefensor.getChildren().add( imagen );
            imagen.setPreserveRatio(true);
            imagen.setFitHeight(60);
            HBox.setMargin(imagen,new Insets(3) );
        }

        caja.setPadding( new Insets(10) );
        cajaAtacante.setPadding( new Insets(10) );
        cajaDefensor.setPadding( new Insets(10) );

        if (App.hayMusica()) App.sonido("batalla", 0.5);
        App.popUpWindow( caja );
    }
}
