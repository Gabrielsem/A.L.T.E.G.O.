package edu.fiuba.algo3.modelo.objetivos;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Mapa;
import edu.fiuba.algo3.modelo.Pais;

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

    public String descripcion(){
        return String.format("Ocupar %d paises", cantidadAConquistar);
    }
}
