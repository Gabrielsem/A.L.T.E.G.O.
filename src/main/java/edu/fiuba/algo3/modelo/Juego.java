package edu.fiuba.algo3.modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Juego extends Observable {

    private final ArrayList<Jugador> jugadores;
    int turnoActual;
    private final Mapa mapa;
    private ArrayList<Tarjeta> tarjetas;
    private boolean finalizado;

    public Juego(int cantJugadores, String archivoPaises) throws FileNotFoundException {
        this.jugadores = new ArrayList<>();
        this.mapa = new Mapa(archivoPaises);
        //this.crearTarjetas(); FIXME

        for ( int numJugador = 0 ; numJugador < cantJugadores; numJugador++ ) {
            this.jugadores.add(new Jugador(numJugador + 1, this));
        }

        turnoActual = 0;
        this.mapa.repartirPaises(this.jugadores);
    }

    public void addJugador(Jugador jug) {
        jugadores.add(jug);
    }

    public boolean turnosCompletados() {
        return turnoActual == jugadores.size();
    }

    public Jugador siguienteTurno() {
        if (turnosCompletados()) {
            return null;
        }
        return jugadores.get(turnoActual++);
    }

    public void reiniciarTurnos() {
        turnoActual = 0;
    }

    public int cantidadFichas(String nombrePais) {
        return mapa.cantidadFichas(nombrePais);
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

    public void rondaAtaques() {
        for ( Jugador jugador : this.jugadores) {
            jugador.turnoAtaque();
        }
    }

    public Pais obtenerPais(String nombrePais){
        return mapa.obtenerPais(nombrePais);
    }

    public Tarjeta pedirTarjeta() {

        Random rand = new Random();

        return this.tarjetas.get(rand.nextInt(this.tarjetas.size()));
    }

    public Jugador propietarioDe(String nombrePais) {
        for (Jugador j : jugadores) {
            if (j.tienePais(nombrePais)) return j;
        }
        return null;
    }

    public void devolverTarjetas(ArrayList<Tarjeta> tarjetas ) {

        for (Tarjeta tarjeta : tarjetas ) {
            tarjeta.desactivar();
            this.tarjetas.add(tarjeta);
        }
    }

    public int fichasSegunContinentes(Set<String> paises ) {
        return mapa.fichasSegunContinentes(paises);
    }

    public ArrayList<Jugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    //Esto es horrible - x suerte fdelu va a refactorizar - 🤔 tmb se podria resolver con un metodo tipo add controlers 🤔
    public void agregarFichas(String nombrePais, int cantFichas) {
        mapa.obtenerPais(nombrePais).agregarFichas(cantFichas);
    }

    public void addPaisObservers(HashMap<String, Observer> observers) {
        mapa.addObservers(observers);
        mapa.notificarObservers();
    }
}
