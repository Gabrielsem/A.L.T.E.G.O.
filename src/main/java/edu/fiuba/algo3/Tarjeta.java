package edu.fiuba.algo3;

import java.util.ArrayList;

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

    public void desactivar() { this.activable = true; }

    public boolean tienenTodasMismoSimbolo(ArrayList<Tarjeta> tarjetas) {

        for (Tarjeta tarjeta : tarjetas) if (!tarjeta.tieneSimbolo(this.simbolo)) return false;

        return true;
    }

    public boolean tieneSimbolo(Simbolo simbolo) { return this.simbolo.esIgualA(simbolo); }

    //Falta refactorizacion
    static private ArrayList<Tarjeta> obtenerTarjetasIguales(ArrayList<Tarjeta> tarjetas) {
        ArrayList<Tarjeta> tarjetasIguales = new ArrayList<>();

        for (int i = 0; i < tarjetas.size(); i++) {
            Tarjeta tarjetaActual = tarjetas.get(i);
            tarjetasIguales.add(tarjetaActual);
            for (int j = 0; j < tarjetas.size(); j++) {
                if (i == j) continue;
                Tarjeta tarjetaAComparar = tarjetas.get(j);
                if (tarjetaActual.tienenTodasMismoSimbolo(tarjetasIguales)) tarjetasIguales.add(tarjetaAComparar);
                if (tarjetasIguales.size() == 3) return tarjetasIguales;
            }
            tarjetasIguales = new ArrayList<>();
        }
        return null;
    }

    // Recibe una colleccion de tarjetas y devuelve un grupo de 3 tarjetas canjeable si hay, null si no hay
    // Un grupo canjeable son 3 iguales o 3 diferentes
    //Falta refactorizacion
    //Falta consirar caso todas distintas
    static public ArrayList<Tarjeta> grupoCanjeable(ArrayList<Tarjeta> tarjetas ) {

        ArrayList<Tarjeta> tarjetasIguales = obtenerTarjetasIguales(tarjetas);
        return tarjetasIguales;
    }

}
