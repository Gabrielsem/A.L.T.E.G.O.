package edu.fiuba.algo3;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

public class Juego {

    private int cantJugadores;
    private  ArrayList<Jugador> jugadores;
    private Mapa mapa;

    public Juego(int cantJugadores) throws IOException, ParseException {
        this.cantJugadores = cantJugadores;
        this.jugadores = new ArrayList<>();
        this.mapa = new Mapa("src/paises.json");
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

    public void rondaAtaques(){
        for ( Jugador jugador : this.jugadores) {
            jugador.turnoAtaque();
        }
    }

    public Pais obtenerPais(String nombrePais){
        mapa.obtenerPais(nombrePais);
    }
    public Tarjeta pedirTarjeta(){
        //TODO - Marce & Gabo
        return null;
    }

    public void rondaColocacion(){
        //TODO - Z
    }

    public void devolverTarjetas( Collection<Tarjeta> tarjetas ){
        //TODO - Z
    }

    public int fichasSegunContinentes( Collection<Pais> paises ){
        //TODO - Z
        return 0;
    }
}
