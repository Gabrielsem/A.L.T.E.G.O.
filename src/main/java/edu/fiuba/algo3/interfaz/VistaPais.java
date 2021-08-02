package edu.fiuba.algo3.interfaz;


import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.*;

public class VistaPais implements Observer {

    private String nombre;
    private Group vista;
    private Circle ficha;
    private Label texto;
    private Shape pais;
    static HashMap<Integer, String>colorJugador;

    public VistaPais(Node unaVista){
        vista =  (Group) unaVista;
        pais = (Shape) vista.getChildren().get(0);

        for( Node child : vista.getChildren() )
            if( child instanceof Circle ) {
                ficha = (Circle) child;
                ficha.getStyleClass().add("ficha");
            }

        if( Objects.isNull(ficha) ) return;

        texto = new Label("X");
        texto.setLayoutX( ficha.centerXProperty().get()-ficha.getRadius()/3 );
        texto.setLayoutY( ficha.centerYProperty().get()-ficha.getRadius()*0.7 );
        vista.getChildren().add(texto);

    }

    @Override
    public void update(Observable o, Object arg) {
        Pais pais = (Pais) o;

        Shape img = (Shape)vista.getChildren().get(0);

        //this.pais.setFill( Color.web( this.colores.get(pais.getNumeroPropietario()) ) );//TODO - Si ponemos color x continente esto no va
        if( Objects.nonNull(ficha) )
            ficha.setFill( Color.web( VistaJugador.getColorJugador(pais.getNumeroPropietario()) ) );

        if( Objects.nonNull(texto) )
            texto.setText(String.valueOf(pais.cantidadFichas()));
        
        vista.setUserData(pais);
    }
}
