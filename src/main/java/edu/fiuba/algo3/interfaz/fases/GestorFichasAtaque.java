package edu.fiuba.algo3.interfaz.fases;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GestorFichasAtaque implements Fase{

    FaseAtaque fase;
    Juego juego;// FIXME - Sobra ?
    Scene scene;
    Jugador jugadorActual;
    Pais paisAtacante;
    Pais paisAtacado;

    Collection<String> seleccionables;
    Button[] botones = new Button[3];

    Label instruccion;

    public GestorFichasAtaque(FaseAtaque faseAtaque, Juego juego, Scene scene, Jugador jugadorActual, Pais atacante, Pais defensor) {
        this.juego = juego;
        this.scene = scene;
        this.jugadorActual = jugadorActual;
        this. paisAtacante = atacante;
        this.paisAtacado =  defensor;
        instruccion = (Label) scene.lookup("#instruccion");
    }

    private void setSeleccionables(Collection<String> seleccion) {
        seleccionables = seleccion;
        //TODO limpiar y agregar styleClass seleccionable
        //FIXME este metodo se repite igualito - podria heredarse o ser estatico de app(?) o a FaseAtaque
    }

    @Override
    public void iniciar() {
        instruccion.setText(String.format("Jugador %d, elegí con cuantas fichas atacar", jugadorActual.numero()));
        setSeleccionables( new ArrayList<>());
        inicializarBotones();
        agregarBotonesAtaque();
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        return null;
    }

    @Override
    public void tocoPais(Node nodoPais) {

    }


    private void inicializarBotones() {
        botones[0] = (Button) scene.lookup("#botonAtaque1");
        botones[1] = (Button) scene.lookup("#botonAtaque2");
        botones[2] = (Button) scene.lookup("#botonAtaque3");
        botones[0].setOnAction((a) -> atacarCon(1));
        botones[1].setOnAction((a) -> atacarCon(2));
        botones[2].setOnAction((a) -> atacarCon(3));
    }
    private void agregarBotonesAtaque() {
        for (int i = 0; i < (paisAtacante.cantidadFichas() - 1) && i < 3; i++) {
            botones[i].setVisible(true);
        }
    }
    private void ocultarBotonesAtaque() {
        Arrays.stream(botones).forEach((Button b) -> b.setVisible(false));
    }

    public void atacarCon(int fichas) {
        paisAtacante.atacar(paisAtacado, fichas);
        if (paisAtacante.ataqueExitoso()) {
            paisAtacante.invadirPaisConquistado(3);
        }
    }
}
