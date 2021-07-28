package edu.fiuba.algo3.modelo;

import java.util.ArrayList;


public class ObjetivoDestruccion implements Objetivo{
    private final Mapa mapa;
    private final int numeroJugadorAsignado;
    private Jugador jugadorADerrotar = null;

    public ObjetivoDestruccion(int unNumeroDeJugador, Mapa unMapa) {
        mapa = unMapa;
        numeroJugadorAsignado = unNumeroDeJugador;
    }

    //Al crear los objetivos de destruccion no sabemos a quien se le van a asignar, por lo tanto, una vez asignado
    //es necesario verificar que el objetivo no sea inválido
    public void verificarJugadorADerrotar(Jugador jugadorPropietario, ArrayList<Jugador> jugadores){

        // Se fija si existe el jugador objetivo, y si existe lo asigna
        for(Jugador jugador: jugadores){
            if(jugador.numero() == numeroJugadorAsignado) jugadorADerrotar = jugador;
        }

        // Si el jugador asignado no existe, o es el propietario del objetivo, se elige al siguiente
        if(numeroJugadorAsignado == jugadorPropietario.numero() || jugadorADerrotar == null){
            if(jugadorPropietario.numero() == jugadores.size()) jugadorADerrotar = jugadores.get(0);
            else jugadorADerrotar = jugadores.get(jugadorPropietario.numero()+1);
        }
    }

    public boolean gano(Jugador jugador) {
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            if(pais.getNumeroPropietario() == jugadorADerrotar.numero()) return false;
        }
        return true;
    }
}
