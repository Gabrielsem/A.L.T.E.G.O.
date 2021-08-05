package edu.fiuba.algo3.modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.fiuba.algo3.errores.PaisNoExiste;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.util.Collections.shuffle;

public class Mapa {
    final private HashMap<String, Pais> paises;
    final private HashMap<String, Integer> valorDeContinente;

    public Mapa(String rutaArchivo) throws FileNotFoundException {
        paises = new HashMap<>();
        valorDeContinente = new HashMap<>();
        this.leerArchivo(rutaArchivo);
    }

    private void leerArchivo(String rutaArchivo) throws FileNotFoundException {
        FileReader lector = new FileReader(rutaArchivo);

        JsonObject objetoJson = JsonParser.parseReader(lector).getAsJsonObject();
        cargarFichasPorContinente(objetoJson.get("fichasPorContinente").getAsJsonArray());
        cargarPaises(objetoJson.get("paises").getAsJsonArray());
    }

    private void cargarPaises(JsonArray paisesJson) {
        for (int i = 0; i < paisesJson.size(); i++) {
            JsonObject paisJsonObj = paisesJson.get(i).getAsJsonObject();

            String nombrePais = paisJsonObj.get("Pais").getAsString();
            String nombreContinente = paisJsonObj.get("Continente").getAsString();
            JsonArray arregloJsonLimitrofes = paisJsonObj.get("Limita con").getAsJsonArray();
            ArrayList<String> limitrofes = new ArrayList<>();

            for (int j = 0; j < arregloJsonLimitrofes.size(); j++) {
                limitrofes.add(arregloJsonLimitrofes.get(j).getAsString());
            }

            this.paises.put(nombrePais, new Pais(nombrePais, nombreContinente, limitrofes));
        }
    }

    private void cargarFichasPorContinente(JsonArray fichasPorContinenteJson) {
        for (int i = 0; i < fichasPorContinenteJson.size(); i++) {
            JsonObject continenteJson = fichasPorContinenteJson.get(i).getAsJsonObject();
            String nombreContinente = continenteJson.get("Continente").getAsString();
            int cantidad = continenteJson.get("Cantidad").getAsInt();

            valorDeContinente.put(nombreContinente, cantidad);
            //System.out.printf("%s : %d%n", nombreContinente, cantidad);
        }
    }

    public void repartirPaises(ArrayList<Jugador> jugadores) {

        ArrayList<String> nombresDePaises = new ArrayList<>(paises.keySet());
        shuffle(nombresDePaises); // Mezcla la lista de nombres de paises

        int contadorJugador = 0;

        while (nombresDePaises.size() > 0){
            //Saca el primer nombre (es random por mezclar) y se lo manda a uno de los jugadores, en orden
            this.paises.get(nombresDePaises.remove(0)).ocupadoPor(jugadores.get(contadorJugador%jugadores.size()),1);
            contadorJugador++;
        }
    }

    public Pais obtenerPais(String nombrePais){
        if(!paises.containsKey(nombrePais)) throw new PaisNoExiste(String.format("El Pais: %s no existe", nombrePais));
        return paises.get(nombrePais);
    }

    public int fichasSegunContinentes(Set<String> paisesConquistados) {

        HashMap<String, Pais> paisesNoConquistados = new HashMap<>(paises) ;
        paisesNoConquistados.keySet().removeAll(paisesConquistados);

        HashSet<String> continentesNoConquistados = new HashSet<>();
        for( Pais pais : paisesNoConquistados.values() ) continentesNoConquistados.add( pais.continente() );

        HashMap<String,Integer> continentesConquistados = new HashMap<>(valorDeContinente);
        continentesConquistados.keySet().removeAll(continentesNoConquistados);

        int fichas = 0;
        for( int valor : continentesConquistados.values() ) fichas += valor;
        return fichas;
    }

    public ArrayList<Pais> obtenerPaises() {

        return new ArrayList<>(paises.values());

    }

    public int cantidadDePaises(String continente) {
        int contador = 0;
        for (Pais pais : paises.values()) {
            if(pais.continente().equals(continente)) contador++;
        }
        return contador;
    }

    public HashMap<String, Integer> cantidadPaisesPorContinente() {
        HashMap<String, Integer> cantidadPaisesPorContinente = new HashMap<>();
        for (Pais pais : paises.values()) {
            String continente = pais.continente();
            cantidadPaisesPorContinente.put(continente, cantidadPaisesPorContinente.getOrDefault(continente, 0) +1);
        }
        return cantidadPaisesPorContinente;
    }

    public void addObservers(HashMap<String, Observer> observers) {
        for (String nombre : observers.keySet()) {
            paises.get(nombre).addObserver(observers.get(nombre));
        }
    }
}