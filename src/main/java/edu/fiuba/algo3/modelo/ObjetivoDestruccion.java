package edu.fiuba.algo3.modelo;

import java.util.ArrayList;


public class ObjetivoDestruccion implements Objetivo{
    private final Mapa mapa;
    private final Jugador jugadorADerrotar;

    public ObjetivoDestruccion(Jugador unJugador, Mapa unMapa) {
        mapa = unMapa;
        jugadorADerrotar = unJugador;
    }

    public boolean gano(Jugador jugador) {
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            if(pais.getNumeroPropietario() == jugadorADerrotar.numero()) return false;
        }
        return true;
    }
}
