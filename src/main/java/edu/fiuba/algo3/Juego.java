package edu.fiuba.algo3;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Juego {
    private int cantJugadores;
    private  ArrayList<Jugador> jugadores;
    private Mapa mapa;

    public Juego(int cantJugadores) {
        this.cantJugadores = cantJugadores;
        this.jugadores = new ArrayList<>();
        this.mapa = new Mapa();
    }

    public void inicializar() {
        for ( int numJugador = 0 ; numJugador <= this.cantJugadores; numJugador = numJugador + 1) {
            this.jugadores.add(new Jugador(numJugador));
        }

        this.mapa.repartirPaises(this.jugadores);
    }
    
    public void colocarFichasIniciales() {
        for ( Jugador jugador : this.jugadores) {
            jugador.agregarFichas(5);
        }
        for ( Jugador jugador : this.jugadores) {
            jugador.agregarFichas(3);
        }
    }
}
