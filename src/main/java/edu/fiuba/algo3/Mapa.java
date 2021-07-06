package edu.fiuba.algo3;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Collections.shuffle;

public class Mapa {
    private HashMap<String, Pais> paises;

    public Mapa() {
        this.paises = this.crearPaises();
    }

    private HashMap crearPaises() {
        paises = new HashMap();

        ArrayList limitrofesSahara = new ArrayList<String>();
        ArrayList limitrofesZaire = new ArrayList<String>();
        ArrayList limitrofesBrasil = new ArrayList<String>();
        ArrayList limitrofesArgentina = new ArrayList<String>();
        ArrayList limitrofesColombia = new ArrayList<String>();

        limitrofesSahara.add("Brasil");
        limitrofesSahara.add("Zaire");
        limitrofesZaire.add("Sahara");
        limitrofesBrasil.add("Argentina");
        limitrofesBrasil.add("Colombia");
        limitrofesBrasil.add("Sahara");
        limitrofesArgentina.add("Brasil");
        limitrofesColombia.add("Colombia");

        paises.put("Brasil", new Pais("Brasil", "America del sur", limitrofesBrasil));
        paises.put("Argentina", new Pais("Argentina", "America del sur", limitrofesArgentina));
        paises.put("Colombia", new Pais("Colombia", "America del sur", limitrofesColombia));
        paises.put("Sahara", new Pais("Sahara", "Africa", limitrofesSahara));
        paises.put("Zaire", new Pais("Zaire", "Africa", limitrofesZaire));

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
}
