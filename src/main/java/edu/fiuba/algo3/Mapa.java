package edu.fiuba.algo3;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Collections.shuffle;

public class Mapa {
    private HashMap<String, Pais> paises;

    public Mapa(String rutaArchivo) throws FileNotFoundException {
        this.crearPaises(rutaArchivo);
    }

    private void crearPaises(String rutaArchivo) throws FileNotFoundException {
        this.paises = new HashMap<>();

        FileReader lector = new FileReader(rutaArchivo);
        
        JsonElement elementoJson = JsonParser.parseReader(lector);
        JsonArray paisesJsonArreglo = elementoJson.getAsJsonArray();

        for (int i = 0; i < paisesJsonArreglo.size(); i++) {
            JsonObject paisJsonObj = paisesJsonArreglo.get(i).getAsJsonObject();

            String nombrePais = paisJsonObj.get("Pais").getAsString();
            String nombreContinente = paisJsonObj.get("Continente").getAsString();
            JsonArray arregloJsonLimitrofes = paisJsonObj.get("Limita con").getAsJsonArray();

            ArrayList<String> limitrofes = new ArrayList<>();
            //System.out.println("----------------------------------------");
            //System.out.println("Nombre pais: " + nombrePais + "Nombre continente: " + nombreContinente);
            //System.out.println("Limitrofes: ");

            for (int j = 0; j < arregloJsonLimitrofes.size(); j++) {
                limitrofes.add(arregloJsonLimitrofes.get(j).getAsString());
                //System.out.println("- " + arregloJsonLimitrofes.get(j).getAsString());
            }

            this.paises.put(nombrePais, new Pais(nombrePais, nombreContinente, limitrofes));
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
    //TODO
}
