package edu.fiuba.algo3.controladores;

import edu.fiuba.algo3.UI.VistaPais;
import edu.fiuba.algo3.modelo.Pais;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.function.Function;

public class paisControler {

    Pais pais;
    VistaPais vista;
    static public Function<Event, Integer > estrategia = (e)->{return 0;};

    public paisControler(Pais pais, VistaPais vistaPais) {
        this.pais = pais;
        vista = vistaPais;
        vistaPais.addClickHandler( (Event e)->{ estrategia.apply(e); } );
    }


}
