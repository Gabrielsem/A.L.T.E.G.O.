package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class ObjetivosTest {

    static String rutaArchivo = "archivos/paises_reducido.json";
    Mapa mapa;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        mapa = new Mapa(rutaArchivo);
    }

    @Test
    public void jugadorCumpleObjetivoComun() {

        ObjetivoComun objetivo = new ObjetivoComun(3, mapa);

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            jug2.ocupar(pais);
            pais.ocupadoPor(jug2, 1);
        }

        for(int i = 0; i<3;i++) {
            paisesDelMapa.get(i).ocupadoPor(jug1, 1);
            jug1.ocupar(paisesDelMapa.get(i));
        }

        assert(objetivo.gano(jug1));
    }
    @Test
    public void jugadorNoCumpleObjetivoComun() {

        ObjetivoComun objetivo = new ObjetivoComun(3, mapa);

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            jug2.ocupar(pais);
            pais.ocupadoPor(jug2, 1);
        }

        for(int i = 0; i<2;i++) {
            paisesDelMapa.get(i).ocupadoPor(jug1, 1);
            jug1.ocupar(paisesDelMapa.get(i));
        }

        assert(!objetivo.gano(jug1));

    }
    /*
    @Test
    public void jugadorCumpleObjetivoOcupacion() {
        HashMap <String, Integer> cantidades = new HashMap<>();
        cantidades.put("Oceania", 2);
        cantidades.put("Asia", 3);
        ObjetivoOcupacion objetivo = new ObjetivoOcupacion(cantidades, mapa);

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            jug2.ocupar(pais);
            pais.ocupadoPor(jug2, 1);
        }

        for (Pais pais: paisesDelMapa) {

        }
    }

     */
}
