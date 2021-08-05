package edu.fiuba.algo3.modelo.objetivos;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Mapa;
import edu.fiuba.algo3.modelo.Pais;

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
    public void verificarObjetivoValido(Jugador jugadorPropietario, ArrayList<Jugador> jugadores){

        // Se fija si existe el jugador objetivo, y si existe lo asigna
        for(Jugador jugador: jugadores){
            if(jugador.numero() == numeroJugadorAsignado) jugadorADerrotar = jugador;
        }

        // Si el jugador asignado no existe, o es el propietario del objetivo, se elige al siguiente
        if(numeroJugadorAsignado == jugadorPropietario.numero() || jugadorADerrotar == null){
            if(jugadorPropietario.numero() == jugadores.size()) jugadorADerrotar = jugadores.get(0);
            else jugadorADerrotar = jugadores.get(jugadorPropietario.numero());
        }
    }

    public boolean gano(Jugador jugador) {
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            if(pais.getNumeroPropietario() == jugadorADerrotar.numero()) return false;
        }
        return true;
    }

    public String descripcion(){
        return String.format("Destruir al jugador: %d\n", jugadorADerrotar.numero());
    }
}
