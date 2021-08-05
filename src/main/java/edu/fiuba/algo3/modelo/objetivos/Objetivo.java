package edu.fiuba.algo3.modelo.objetivos;

import edu.fiuba.algo3.modelo.Jugador;

import java.util.ArrayList;

public interface Objetivo {
    public boolean gano(Jugador jugador);
    public void verificar(Jugador jugadorPropietario, ArrayList<Jugador> jugadores);//FIXME - Nombre No representativo
    public String descripcion();
}
