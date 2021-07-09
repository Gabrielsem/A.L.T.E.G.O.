package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.Jugador;
import edu.fiuba.algo3.Mapa;
import edu.fiuba.algo3.Pais;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class MapaTest {

    Mapa mapa;

    @BeforeEach
    public void setUp() throws IOException, ParseException {
        mapa = new Mapa("src/paisesTest.json");
    }

    @Test
    public void paisesRepartidosEquitativamente() throws IOException, ParseException {

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador(1));
        jugadores.add(new Jugador(2));
        jugadores.add(new Jugador(3));

        mapa.repartirPaises(jugadores);

        int cantMaxDePaises = 0;
        int cantMinDePaises = 0;

        for (Jugador jugador : jugadores){
            int cantPaisesActual = jugador.obtenerCantidadPaises();
            if(cantPaisesActual > cantMaxDePaises) cantMaxDePaises = cantPaisesActual;
            if(cantPaisesActual < cantMinDePaises || cantMinDePaises == 0) cantMinDePaises = cantPaisesActual;
        }

        assertTrue(cantMaxDePaises - cantMinDePaises <= 1);
    }
}
