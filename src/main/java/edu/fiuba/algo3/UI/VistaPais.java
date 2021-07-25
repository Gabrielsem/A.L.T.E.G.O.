package edu.fiuba.algo3.UI;

import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;

import java.util.Observable;
import java.util.Observer;

public class VistaPais implements Observer {

    private Node vista;

    public VistaPais(Node unaVista ){
        vista = unaVista;
    }

    @Override
    public void update(Observable o, Object arg) {
        Pais pais = (Pais) o;

        int cantFichas = pais.cantidadFichas();
        System.out.println( pais.nombre() );
    }
}
