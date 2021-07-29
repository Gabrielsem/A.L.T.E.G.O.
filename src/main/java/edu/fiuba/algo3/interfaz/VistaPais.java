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
    private HashMap<Integer, String> colores;// TODO - Me parece mejor que sea estatico ya que es el mismo para todos
    static HashMap<Integer, String>colorJugador;// FIXME - En este momento es accedido publicamente pq hay varias partes de la interfaz que lo necesitan - Habria que ver si lo pasamos por parametro (no me gusta) o  reasignamos la tenencia a algo mas ligado con Jugador (VistaJugador ?)

    //static private final String[] colores = {"#FFFFFF", "#0077BB", "#cc3311", "#ee7733", "#009988", "#ee3377", "#000000"};
    static private final Map<String, String> colorDeContinente = Map.of(
            "Asia", "#555",
            "Europa", "#777",
            "America del Norte","#999",
            "America del Sur","#666",
            "Africa","#444",
            "Oceania","#888"
    );

    public VistaPais(Node unaVista, HashMap<Integer, String> colores){
        this.colores = colores;
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
        
        //FIXME - HAY UN BUG RARO
        //this.pais.setFill( Color.web( this.colores.get(pais.getNumeroPropietario()) ) );//TODO - Si ponemos color x continente esto no va
        if( Objects.nonNull(ficha) )
            ficha.setFill( Color.web( this.colores.get(pais.getNumeroPropietario()) ) );

        if( Objects.nonNull(texto) )
            texto.setText(String.valueOf(pais.cantidadFichas()));


        // TODO - No es necesario en cada update - podria recibir el pais en el constructor
        this.pais.setFill( Color.web( colorDeContinente.get( pais.continente() ) ) );
        vista.setUserData(pais);
    }


    public static String getColorJugador(int nJug){
        return colorJugador.get(nJug);
    };

    public static void setColorJugador( HashMap<Integer,String> colores ) {
        colorJugador = colores;
    }
}
