package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.Jugador;
import edu.fiuba.algo3.Mapa;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapaTest {
    @Test
    public void paisesRepartidosEquitativamente(){

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador(1));
        jugadores.add(new Jugador(2));
        jugadores.add(new Jugador(3));

        Mapa mapa = new Mapa();

        mapa.repartirPaises(jugadores);

        int cantMaxDePaises = 0;
        int cantMinDePaises = 0;

        for (Jugador jugador : jugadores){
            int cantPaisesActual = jugador.obtenerCantidadPaises();
            System.out.println(cantPaisesActual);
            if(cantPaisesActual > cantMaxDePaises) cantMaxDePaises = cantPaisesActual;
            if(cantPaisesActual < cantMinDePaises || cantMinDePaises == 0) cantMinDePaises = cantPaisesActual;
        }
        assertTrue(cantMaxDePaises - cantMinDePaises <= 1);
    }
}
