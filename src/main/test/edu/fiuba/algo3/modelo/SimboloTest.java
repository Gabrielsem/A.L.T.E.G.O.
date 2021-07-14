package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.Simbolo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimboloTest {

    @Test
    public void simboloContieneNombreCorrecto() {
        String nombreSimbolo = "Barco";
        Simbolo simbolo = new Simbolo(nombreSimbolo);

        assertEquals(nombreSimbolo, simbolo.obtenerNombre());
    }

    @Test
    public void simbolosConElMismoNombreSonIguales() {
        String nombreSimbolo = "Globo";
        Simbolo simbolo1 = new Simbolo(nombreSimbolo);
        Simbolo simbolo2 = new Simbolo(nombreSimbolo);

        assertTrue(simbolo1.esIgualA(simbolo2));
    }
}
