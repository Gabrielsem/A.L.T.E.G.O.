package edu.fiuba.algo3.interfaz.fases;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.Collection;

public abstract class FaseConSeleccionables implements Fase {
    private Collection<String> seleccionables;
    protected Scene scene;

    abstract public Fase tocoBoton(Button unBoton);

    public Fase tocoPais(Node nodoPais) {
        if( seleccionables.contains( nodoPais.getId() ) )
            return tocoSeleccionable(nodoPais);
        return this;
    }

    abstract protected Fase tocoSeleccionable(Node nodoPais);

    protected void setSeleccionables(Collection<String> seleccion, String codigoColor) {
        seleccionables = new ArrayList<>(seleccion);

        // Estilar seleccionables
        for (Node nodo : ((Group) scene.lookup("#_root")).getChildren()) {
            if (nodo.getStyleClass().contains("pais")) {
                //Clear
                SVGPath pais = (SVGPath) ((Group)nodo).getChildren().get(0);
                pais.setEffect(null);

                //Style
                if( seleccionables.contains( nodo.getId() ) ){
                    InnerShadow efecto = new InnerShadow();
                    Color color = Color.web(codigoColor);

                    efecto.setColor(color.darker().darker());
                    efecto.setWidth(pais.getLayoutBounds().getWidth());
                    efecto.setHeight(pais.getLayoutBounds().getHeight());

                    pais.setEffect(efecto);
                }
            }
        }
    }

}
