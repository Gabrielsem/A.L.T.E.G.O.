package edu.fiuba.algo3;

import java.util.HashSet;
import java.util.List;

public class Jugador {
    HashSet<Pais> paisesConquistados = new HashSet<Pais>();

    public void desocupar(Pais unPais) {
        paisesConquistados.remove(unPais);
    }

    public void ocupar(Pais unPais){
        paisesConquistados.add(unPais);
    }

    public int invadir(Pais atacante, Pais defensor) {
        // pedir un dato al jugador y devolverlo
        return 0;
    }

    public void atacar(Pais atacante, Pais defensor, int cantFichas){
        if( !paisesConquistados.contains(atacante) ) throw new JugadorNoTienePais(String.format("El jugador no puede atacar con el pais %s porque no es suyo",atacante.nombre()));
        if( paisesConquistados.contains(defensor) ) throw new PaisDelMismoPropietarioNoPuedeSerAtacado(String.format("El jugador no puede atacar a el pais %s porque ya es suyo",defensor.nombre()));

        atacante.atacar(defensor,cantFichas);
    }
}
