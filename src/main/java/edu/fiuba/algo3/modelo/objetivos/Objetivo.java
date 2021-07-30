package edu.fiuba.algo3.modelo.objetivos;

import edu.fiuba.algo3.modelo.Jugador;

public interface Objetivo {
    public boolean gano(Jugador jugador);
    public String descripcion();
}
