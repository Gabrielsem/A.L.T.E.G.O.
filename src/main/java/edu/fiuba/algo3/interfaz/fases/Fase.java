package edu.fiuba.algo3.interfaz.fases;

import javafx.scene.Node;
import javafx.scene.control.Button;

public interface Fase {
    public void iniciar();
    public Fase tocoBoton(Button unBoton);
    public void tocoPais(Node pais);
}
