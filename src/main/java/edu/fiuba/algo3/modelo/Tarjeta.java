package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Tarjeta {

    final private Pais pais;
    final private Simbolo simbolo;
    private boolean activable;

    public Tarjeta(Pais pais, Simbolo simbolo ) {
        this.pais = pais;
        this.simbolo = simbolo;
        this.activable = true;
    }

    public void activar() {
        if (activable) {
            this.pais.agregarFichas(2);
            this.activable = false;
        }
    }

    public Simbolo obtenerSimbolo() { return this.simbolo; }

    public void desactivar() { this.activable = true; }

    static private ArrayList<Tarjeta> obtenerTresTarjetasIguales(HashMap<String, ArrayList<Tarjeta>> tarjetasPorSimbolo) {

        for (HashMap.Entry<String, ArrayList<Tarjeta>> entry : tarjetasPorSimbolo.entrySet()) {
            ArrayList<Tarjeta> tarjetasActuales = entry.getValue();
            if (tarjetasActuales.size() == 3) { return tarjetasActuales; }
        }

        return null;
    }

    static public int cantidadFichasCanje(int cantidadCanjes){
        if( cantidadCanjes == 0 ) return 4;
        if( cantidadCanjes == 1 ) return 7;
        return cantidadCanjes * 5;
    }

    static private ArrayList<Tarjeta> obtenerTresTarjetasDistintas(HashMap<String, ArrayList<Tarjeta>> tarjetasPorSimbolo) {

        if (tarjetasPorSimbolo.size() < 3) return null;

        ArrayList<Tarjeta> tresTarjetasDistintas = new ArrayList<>();

        for (HashMap.Entry<String, ArrayList<Tarjeta>> entry : tarjetasPorSimbolo.entrySet()) {
            tresTarjetasDistintas.add(entry.getValue().get(0));
        }

        return tresTarjetasDistintas;
    }

    static public ArrayList<Tarjeta> grupoCanjeable(ArrayList<Tarjeta> tarjetas ) {
        HashMap<String, ArrayList<Tarjeta>> tarjetasPorSimbolo = new HashMap<>();

        for ( Tarjeta tarjeta : tarjetas) {
            String simboloActual = tarjeta.obtenerSimbolo().nombre();
            ArrayList<Tarjeta> tarjetasActuales = tarjetasPorSimbolo.get(simboloActual);

            if (tarjetasActuales == null) { tarjetasActuales = new ArrayList<>(); }

            tarjetasActuales.add(tarjeta);
            tarjetasPorSimbolo.put(simboloActual, tarjetasActuales);
        }

        ArrayList<Tarjeta> grupoCanjeable = Tarjeta.obtenerTresTarjetasIguales(tarjetasPorSimbolo);
        if (grupoCanjeable != null) return grupoCanjeable;

        return Tarjeta.obtenerTresTarjetasDistintas(tarjetasPorSimbolo);
    }

    public String displayPais(){
        return pais.nombre()+((activable)?"":" ✓");
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarjeta tarjeta = (Tarjeta) o;
        return pais.equals(tarjeta.pais) && simbolo.equals(tarjeta.simbolo);
    }
}
