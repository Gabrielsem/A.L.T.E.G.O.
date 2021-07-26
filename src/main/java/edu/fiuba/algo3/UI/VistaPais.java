package edu.fiuba.algo3.UI;

import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class VistaPais implements Observer {

    private Group vista;
    private Circle ficha;
    private Label texto;
    private Shape pais;

    static private String[] colores = {"#0077BB", "#cc3311", "#ee7733", "#009988", "#ee3377", "#000000"};

    public VistaPais(Node unaVista ){
        vista =  (Group) unaVista;
        pais = (Shape) vista.getChildren().get(0);

        String nombrePais = vista.getId();

        for( Node child : vista.getChildren() )
            if( child instanceof Circle ) {
                ficha = (Circle) child;
                ficha.getStyleClass().add("ficha");
            }

        if( Objects.isNull(ficha) ) return;

        texto = new Label("2");
        texto.setLayoutX( ficha.centerXProperty().get()-ficha.getRadius()/2 );
        texto.setLayoutY( ficha.centerYProperty().get()-ficha.getRadius()/2 );
        vista.getChildren().add(texto);
    }

    @Override
    public void update(Observable o, Object arg) {
        Pais pais = (Pais) o;

        Shape img = (Shape)vista.getChildren().get(0);
        String color = colores[pais.getNumeroPropietario()];
        img.setFill( Color.web( color ) );
        if( Objects.nonNull(ficha) )
            ficha.setFill( Color.web( color ) );


    }
}
