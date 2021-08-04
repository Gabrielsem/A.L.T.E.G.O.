package edu.fiuba.algo3.interfaz.fases;

import javafx.scene.Node;
import javafx.scene.control.Button;

public interface Fase {

    Fase tocoPais(Node nodoPais);

    Fase tocoBoton(Button unBoton);
}
