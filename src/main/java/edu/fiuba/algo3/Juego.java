package edu.fiuba.algo3;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

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
            this.jugadores.add(new Jugador(numJugador, this));
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
        return mapa.obtenerPais(nombrePais);
    }
    public Tarjeta pedirTarjeta(){
        //Ari hacé lo tuyo (devolver una tarjeta de una pool de objetos Tarjeta)
        return null;
    }

    public void rondaColocacion(){
        for ( Jugador jugador : jugadores) {
            jugador.turnoColocacion();
        }
    }

    public void devolverTarjetas( Collection<Tarjeta> tarjetas ){
        //TODO - Ari
    }

    public int fichasSegunContinentes(HashMap<String, Pais> paises ){
        //TODO - Z
        return 0;
    }
}
