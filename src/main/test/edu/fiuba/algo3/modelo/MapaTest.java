package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.Jugador;
import edu.fiuba.algo3.Mapa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapaTest {

    Mapa mapa;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        mapa = new Mapa("archivos/paises.json");
    }

    @Test
    public void paisesRepartidosEquitativamente() {

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador(1, null));
        jugadores.add(new Jugador(2, null));
        jugadores.add(new Jugador(3, null));

        mapa.repartirPaises(jugadores);

        int cantMaxDePaises = 0;
        int cantMinDePaises = 0;

        for (Jugador jugador : jugadores){
            int cantPaisesActual = jugador.obtenerCantidadPaises();
            if (cantPaisesActual > cantMaxDePaises) cantMaxDePaises = cantPaisesActual;
            if (cantPaisesActual < cantMinDePaises || cantMinDePaises == 0) cantMinDePaises = cantPaisesActual;
        }

        assertTrue(cantMaxDePaises - cantMinDePaises <= 1);
    }
}
