package edu.fiuba.algo3.interfaz.vistas;

import edu.fiuba.algo3.App;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VistaBatalla {

    private static boolean habilitado = false;
    public static void habilitar(){ habilitado=true; }

    public VistaBatalla(int[] dadosAtacante, int[] dadosDefensor) {

        if( !habilitado ) return;

        VBox caja = new VBox();
        caja.getStyleClass().add("Batalla");
        HBox cajaTitulo = new HBox( new Label("BATALLA") );
        HBox cajaAtacante = new HBox( new Label("Atacante: ") );
        HBox cajaDefensor = new HBox( new Label("Defensor: ") );
        caja.getChildren().addAll(cajaTitulo,cajaAtacante,cajaDefensor);

        for (int j : dadosAtacante) {
            cajaAtacante.getChildren().add(new Label(String.valueOf(j)));// TODO - Imagenes
        }

        for( int j : dadosDefensor ){
            cajaDefensor.getChildren().add( new Label( String.valueOf( j ) ) );// TODO - Imagenes
        }

        caja.setPadding( new Insets(10) );
        cajaAtacante.setPadding( new Insets(10) );
        cajaDefensor.setPadding( new Insets(10) );

        App.popUpWindow( caja );
    }
}
