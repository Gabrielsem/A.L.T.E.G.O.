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
    Juego juego;// FIXME - Sobra ?
    Scene scene;
    Jugador jugadorActual;
    Pais atacante;

    Collection<String> seleccionables;

    Label instruccion;

    public GestorDefensor(FaseAtaque faseAtaque, Juego juego, Scene scene, Jugador jugadorActual, Pais atacante) {

        this.fase = faseAtaque;
        this.juego = juego;
        this.jugadorActual = jugadorActual;
        this.scene= scene;
        this.atacante = atacante;
        instruccion = (Label) scene.lookup("#instruccion");

    }

    private void setSeleccionables(Collection<String> seleccion) {
        seleccionables = seleccion;
        //TODO limpiar y agregar styleClass seleccionable
        //FIXME este metodo se repite igualito - podria heredarse o ser estatico de app(?)
    }

    @Override
    public void iniciar() {
        instruccion.setText(String.format("Jugador %d, toca el país que quieras atacar", jugadorActual.numero()));
        setSeleccionables( jugadorActual.paisesAtacables(atacante.getVecinos()) );
        System.out.println(seleccionables);
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return null;
    }

    @Override
    public void tocoPais(Node nodoPais) {
        Pais pais = (Pais) nodoPais.getUserData();

        System.out.println(pais.nombre());
        System.out.println(seleccionables);
        if( seleccionables.contains( pais.nombre() ) )
            fase.setGestor(new GestorFichasAtaque(fase,juego, scene, jugadorActual,atacante, pais));
    }
}
