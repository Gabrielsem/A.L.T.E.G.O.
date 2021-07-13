package edu.fiuba.algo3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Collections.shuffle;

public class Mapa {
    private HashMap<String, Pais> paises;

    public Mapa(String rutaArchivo) throws IOException, ParseException {
        this.paises = this.crearPaises(rutaArchivo);
    }

    private HashMap<String, Pais> crearPaises(String rutaArchivo) throws IOException, ParseException {
        this.paises = new HashMap<>();

        JSONParser jsonParser = new JSONParser();
        FileReader lector = new FileReader(rutaArchivo);
        JSONArray arregloJsonPaises = (JSONArray) jsonParser.parse(lector);

        for (int i = 0; i < arregloJsonPaises.size(); i++) {
            JSONObject paisJsonObj = (JSONObject) arregloJsonPaises.get(i);

            String nombrePais = (String) paisJsonObj.get("nombre");
            String nombreContinente = (String) paisJsonObj.get("continente");
            JSONArray arregloJsonLimitrofes = (JSONArray) paisJsonObj.get("limitrofes");

            ArrayList<String> limitrofes = new ArrayList<>();

            for (int j = 0; j < arregloJsonLimitrofes.size(); j++) {
                limitrofes.add((String) arregloJsonLimitrofes.get(j));
            }

            this.paises.put(nombrePais, new Pais(nombrePais, nombreContinente, limitrofes));
        }

        return paises;
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
    }
    //TODO
}
