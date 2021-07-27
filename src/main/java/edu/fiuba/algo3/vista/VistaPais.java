package edu.fiuba.algo3.vista;


import edu.fiuba.algo3.modelo.Pais;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

    static private final String[] colores = {"#FFFFFF", "#0077BB", "#cc3311", "#ee7733", "#009988", "#ee3377", "#000000"};
    static private final Map<String, String> colorDeContinente = Map.of(
            "Asia", "#555",
            "Europa", "#777",
            "America del Norte","#999",
            "America del Sur","#666",
            "Africa","#444",
            "Oceania","#888"
    );

    public VistaPais(Node unaVista ){
        vista =  (Group) unaVista;
        pais = (Shape) vista.getChildren().get(0);

        for( Node child : vista.getChildren() )
            if( child instanceof Circle ) {
                ficha = (Circle) child;
                ficha.getStyleClass().add("ficha");
            }

        if( Objects.isNull(ficha) ) return;

        texto = new Label("2");
        texto.setLayoutX( ficha.centerXProperty().get()-ficha.getRadius()/3 );
        texto.setLayoutY( ficha.centerYProperty().get()-ficha.getRadius()*0.7 );
        vista.getChildren().add(texto);
    }

    @Override
    public void update(Observable o, Object arg) {
        Pais pais = (Pais) o;

        Shape img = (Shape)vista.getChildren().get(0);


        if( Objects.nonNull(ficha) )
            ficha.setFill( Color.web( colores[pais.getNumeroPropietario()] ) );

        if( Objects.nonNull(texto) )
            texto.setText(String.valueOf(pais.cantidadFichas()));

        //this.pais.setFill( Color.web( colores[pais.getNumeroPropietario()] ) );
        this.pais.setFill( Color.web( colorDeContinente.get( pais.continente() ) ) );

    }


    public void addClickHandler(EventHandler eventHandler){
        vista.addEventHandler( MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}
