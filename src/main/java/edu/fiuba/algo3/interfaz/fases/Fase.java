package edu.fiuba.algo3.interfaz.fases;

import javafx.scene.Node;
import javafx.scene.control.Button;

public interface Fase {

    public Fase tocoPais(Node nodoPais);

    public Fase tocoBoton(Button unBoton);
}
