package edu.fiuba.algo3.modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.NoExisteJugadorConNumeroIndicado;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Juego extends Observable {

    private final ArrayList<Jugador> jugadores;
    int turnoActual, turnoOffset;
    private final Mapa mapa;
    private ArrayList<Tarjeta> tarjetas;
    private boolean finalizado;
    private ArrayList<Objetivo> objetivosIndividuales;
    private ObjetivoComun objetivoComun;

    public Juego(int cantJugadores, String archivoPaises, String archivoObjetivos) throws FileNotFoundException {
        this.jugadores = new ArrayList<>();
        this.mapa = new Mapa(archivoPaises);
        this.crearTarjetas();

        for ( int numJugador = 0 ; numJugador < cantJugadores; numJugador++ ) {
            this.jugadores.add(new Jugador(numJugador + 1, this));
        }

        this.tarjetas = new ArrayList<Tarjeta>();
        turnoActual = 0;
        turnoOffset = ThreadLocalRandom.current().nextInt(cantJugadores);
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
        Jugador jug = jugadores.get((turnoActual + turnoOffset) % jugadores.size());
        turnoActual++;
        return jug;
    }

    public void reiniciarTurnos() {
        turnoActual = 0;
        turnoOffset = (turnoOffset + 1) % jugadores.size();
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

    private void crearObjetivos() throws FileNotFoundException {
        this.objetivosIndividuales = new ArrayList<>();

        FileReader lector = new FileReader("archivos/objetivos.json");

        JsonObject objetoJson = JsonParser.parseReader(lector).getAsJsonObject();
        crearObjetivoComun(objetoJson.get("objetivoComun").getAsJsonObject());
        crearObjetivosOcupacion(objetoJson.get("objetivoOcupacion").getAsJsonArray());
        crearObjetivosDestruccion(objetoJson.get("objetivoDestruccion").getAsJsonArray());
    }

    private void crearObjetivoComun(JsonObject objetivoComunJson) {
        int cantidad = objetivoComunJson.get("CantidadAConquistar").getAsInt();
        this.objetivoComun = new ObjetivoComun(cantidad, mapa);
    }

    private void crearObjetivosOcupacion(JsonArray objetivosOcupacionJson) {
        for (int i = 0; i < objetivosOcupacionJson.size(); i++) {
            JsonObject objetivoOcupacionJsonObj = objetivosOcupacionJson.get(i).getAsJsonObject();
            JsonArray arregloJsonContinentes = objetivoOcupacionJsonObj.get("Continentes").getAsJsonArray();
            JsonArray arregloJsonCantidadesPorContinente = objetivoOcupacionJsonObj.get("CantidadPorContinente").getAsJsonArray();
            HashMap<String, Integer> cantidadesPorContinente = new HashMap<>();

            for (int j = 0; j < arregloJsonContinentes.size(); j++) {

                String cantidad = arregloJsonCantidadesPorContinente.get(j).getAsString();
                String continente = arregloJsonContinentes.get(j).getAsString();

                if(cantidad.equals("*")) {
                    cantidadesPorContinente.put(continente, mapa.cantidadDePaises(continente));
                }else{
                    cantidadesPorContinente.put(continente, Integer.parseInt(cantidad));
                }
            }
            this.objetivosIndividuales.add(new ObjetivoOcupacion(cantidadesPorContinente, mapa));
        }
    }

    private void crearObjetivosDestruccion(JsonArray objetivosDestruccionJson) {
        for (int i = 0; i < objetivosDestruccionJson.size(); i++) {
            JsonObject objetivoDestruccionJson = objetivosDestruccionJson.get(i).getAsJsonObject();

            int numJugador = objetivoDestruccionJson.get("JugadorADerrotar").getAsInt();
            /*
            try {

            }catch{

            }
            // hay que evitar que le toque destruirse a si mismo y ver a quien le pasamos si el jugador no existe
            // hay que actualizar el jugador a destruir una vez ya asignado el objetivo a otro jugador


            this.objetivosIndividuales.add(new ObjetivoDestruccion());
            */

        }
    }
/*
    public void rondaAtaques() {
        for ( Jugador jugador : this.jugadores) {
            jugador.turnoAtaque();
        }
    }
*/ // TODO: Borrar este metodo

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

    public Jugador obtenerJugadorConNumero(int numeroDeJugador) {
        for (Jugador jugador : jugadores) {
            if(jugador.numero() == numeroDeJugador) return jugador;
        }
        throw new NoExisteJugadorConNumeroIndicado(String.format("No existe jugador con numero %d", numeroDeJugador));
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
