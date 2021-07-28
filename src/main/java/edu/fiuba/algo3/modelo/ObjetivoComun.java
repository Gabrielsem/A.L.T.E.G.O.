package edu.fiuba.algo3.modelo;

import java.util.ArrayList;


public class ObjetivoComun implements Objetivo{

    private final int cantidadAConquistar;
    private final Mapa mapa;

    public ObjetivoComun(int unaCantidad, Mapa unMapa) {
        cantidadAConquistar = unaCantidad;
        mapa = unMapa;
    }

    public boolean gano(Jugador jugador) {
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        int cantidadPaisesDeJugador = 0;

        for(Pais pais: paisesDelMapa) {
            if(pais.getNumeroPropietario() == jugador.numero()) cantidadPaisesDeJugador ++;
        }

        return cantidadPaisesDeJugador >= cantidadAConquistar;
    }
}
