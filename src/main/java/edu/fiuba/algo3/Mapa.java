package edu.fiuba.algo3;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Mapa {
    private Dictionary<String, Pais> paises;

    public Mapa() {
        this.paises = this.crearPaises();
    }

    private Dictionary crearPaises() {
        paises = new Hashtable();

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
        this.paises.get("Argentina").ocupadoPor(jugadores.get(0), 1);
        this.paises.get("Brasil").ocupadoPor(jugadores.get(1), 1);
        this.paises.get("Colombia").ocupadoPor(jugadores.get(0), 1);
        this.paises.get("Sahara").ocupadoPor(jugadores.get(0), 1);
        this.paises.get("Zaire").ocupadoPor(jugadores.get(1), 1);
    }


}



