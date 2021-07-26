package edu.fiuba.algo3.modelo;

import java.util.Collection;

public class ObjetivoComun {

    private final int cantidadAConquistar;
    private final Mapa mapa;

    public ObjetivoComun(int unaCantidad, Mapa unMapa) {
        cantidadAConquistar = unaCantidad;
        mapa = unMapa;
    }

    public boolean gano(Jugador jugador) {
        Collection<Pais> paisesDelMapa = mapa.obtenerPaises();

        int cantidadPaisesDeJugador = 0;

        for(Pais pais: paisesDelMapa) {
            if(pais.getNumeroPropietario() == jugador.getNumero()) cantidadPaisesDeJugador ++;
        }

        return cantidadPaisesDeJugador >= cantidadAConquistar;
    }
}
