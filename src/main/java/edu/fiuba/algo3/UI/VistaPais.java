package edu.fiuba.algo3.UI;

import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VistaPais implements Observer {

    private Group vista;

    static private String[] colores = {"#0077BB", "#cc3311", "#ee7733", "#009988", "#ee3377", "#000000"};

    public VistaPais(Node unaVista ){
        vista =  (Group) unaVista;
    }

    @Override
    public void update(Observable o, Object arg) {
        Pais pais = (Pais) o;

        Shape img = (Shape)vista.getChildren().get(0);
        String color = colores[pais.getNumeroPropietario()];
        img.setFill( Color.web( color ) );
    }
}
