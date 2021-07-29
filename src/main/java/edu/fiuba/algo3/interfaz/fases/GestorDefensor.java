package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Collection;

public class GestorDefensor implements Fase{


    FaseAtaque fase;
    Scene scene;
    Juego juego;
    Pais atacante;

    Label instruccion;

    public GestorDefensor(FaseAtaque faseAtaque, Pais atacante) {

        this.fase = faseAtaque;
        juego = fase.juego;
        scene= fase.scene;
        this.atacante = atacante;
        instruccion = (Label) scene.lookup("#instruccion");

    }

    @Override
    public void iniciar() {
        instruccion.setText("Toca el país que quieras atacar");
        fase.setSeleccionables( juego.turnoActual().paisesAtacables(atacante.getVecinos()) );
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return null;
    }

    @Override
    public void tocoPais(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();
        fase.setGestor(new GestorFichasAtaque(fase,atacante, pais));
    }
}
