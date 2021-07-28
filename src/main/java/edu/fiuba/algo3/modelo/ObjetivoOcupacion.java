package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjetivoOcupacion implements Objetivo{
    private final Mapa mapa;
    private final HashMap<String, Integer> cantidadesPorContinente;

    public ObjetivoOcupacion(HashMap<String, Integer> unasCantidadesPorContinente, Mapa unMapa) {
        mapa = unMapa;
        cantidadesPorContinente = unasCantidadesPorContinente;
    }

    public boolean gano(Jugador jugador) {
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();
        HashMap<String, Integer> cantidadesConquistadasPorJugador = new HashMap<>(); // guardo cantidades que jugador ya conquistó, para luego comparar con el objetivo

        for(Pais pais: paisesDelMapa) {
            if(pais.getNumeroPropietario() == jugador.numero()) {
                cantidadesConquistadasPorJugador.put(pais.continente(),cantidadesConquistadasPorJugador.getOrDefault(pais.continente(), 0) +1) ;
            }
        }

        for (String continente : cantidadesPorContinente.keySet()){
            if (! cantidadesConquistadasPorJugador.containsKey(continente)) return false;
            if (cantidadesConquistadasPorJugador.get(continente) < cantidadesPorContinente.get(continente) ) return false;
        }
        return true;
    }

    public String descripcion(){
        String mensaje = "Ocupar: \n";

        for(String continente : cantidadesPorContinente.keySet()){
            if(cantidadesPorContinente.get(continente) >= mapa.cantidadDePaises(continente)) mensaje = mensaje + continente + "\n";
            else mensaje = mensaje + String.format("%d paises de %s \n", cantidadesPorContinente.get(continente), continente);
        }

        return  mensaje;
    }
}
