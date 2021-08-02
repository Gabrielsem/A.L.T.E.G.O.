package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimboloTest {

    @Test
    public void simboloContieneNombreCorrecto() {
        String nombreSimbolo = "Barco";
        Simbolo simbolo = new Simbolo(nombreSimbolo);

        assertEquals(nombreSimbolo, simbolo.nombre());
    }

    @Test
    public void simbolosConElMismoNombreSonIguales() {
        String nombreSimbolo = "Globo";
        Simbolo simbolo1 = new Simbolo(nombreSimbolo);
        Simbolo simbolo2 = new Simbolo(nombreSimbolo);

        assertEquals( simbolo1, simbolo2 );
    }
}
