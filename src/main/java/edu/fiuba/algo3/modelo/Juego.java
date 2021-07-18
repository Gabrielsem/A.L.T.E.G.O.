package edu.fiuba.algo3.modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Juego {

    final private int cantJugadores;
    private  ArrayList<Jugador> jugadores;
    private Mapa mapa;
    private ArrayList<Tarjeta> tarjetas;

    public Juego(int cantJugadores) throws FileNotFoundException {
        this.cantJugadores = cantJugadores;
        this.jugadores = new ArrayList<>();
        this.mapa = new Mapa("archivos/paises.json");
        this.crearTarjetas();
    }

    private void crearTarjetas() throws FileNotFoundException {
        this.tarjetas = new ArrayList<>();

        FileReader lector = new FileReader("archivos/tarjetas.json");

        JsonElement elementoJson = JsonParser.parseReader(lector);
        JsonArray tarjetasArregloJson = elementoJson.getAsJsonArray();

        for (int i = 0; i < tarjetasArregloJson.size(); i++) {
            JsonObject tarjetaJsonObj =tarjetasArregloJson.get(i).getAsJsonObject();

            Pais pais = this.mapa.obtenerPais(tarjetaJsonObj.get("Pais").getAsString());
            this.tarjetas.add(new Tarjeta(pais, new Simbolo(tarjetaJsonObj.get("Simbolo").getAsString())));
        }

    }

    public void inicializar() {
        for ( int numJugador = 0 ; numJugador <= this.cantJugadores; numJugador++ ) {
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

    public void rondaAtaques() {
        for ( Jugador jugador : this.jugadores) {
            jugador.turnoAtaque();
        }
    }

    //Ari: no se si este getter hace falta, para que lo usan?
    public Pais obtenerPais(String nombrePais){
        return mapa.obtenerPais(nombrePais);
    }

    public Tarjeta pedirTarjeta() {

        Random rand = new Random();

        return this.tarjetas.get(rand.nextInt(this.tarjetas.size()));
    }

    public void rondaColocacion() {
        for ( Jugador jugador : jugadores) {
            jugador.turnoColocacion();
        }
    }

    public void devolverTarjetas( Collection<Tarjeta> tarjetas ) {
        //TODO - Ari
    }

    public int fichasSegunContinentes(Set<String> paises ) {
        return mapa.fichasSegunContinentes(paises);
    }
}
