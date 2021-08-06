package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.objetivos.ObjetivoComun;
import edu.fiuba.algo3.modelo.objetivos.ObjetivoDestruccion;
import edu.fiuba.algo3.modelo.objetivos.ObjetivoOcupacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ObjetivosTest {

    static String rutaArchivo = "archivos/paises_reducido.json";
    Mapa mapa;

    @BeforeEach
    public void setUp() throws IOException {
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

    @Test
    public void jugadorCumpleObjetivoOcupacion() {
        HashMap <String, Integer> cantidades = new HashMap<>();
        int contOceania = 2;
        int contAsia = 3;
        cantidades.put("Oceania", contOceania);
        cantidades.put("Asia", contAsia);

        //Creamos el objetivo con ciertas cantidades predeterminadas
        ObjetivoOcupacion objetivo = new ObjetivoOcupacion(cantidades, mapa);

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        //Ocupamos todos los paises por el jugador 2 para que no estén sin propietario
        for(Pais pais: paisesDelMapa) {
            jug2.ocupar(pais);
            pais.ocupadoPor(jug2, 1);
        }

        //Ocupamos la cantidad de paises indicada para ganar, con el jugador 1
        for (Pais pais: paisesDelMapa) {
            if (pais.continente().equals("Oceania") && contOceania > 0) {
                jug1.ocupar(pais);
                pais.ocupadoPor(jug1, 1);
                contOceania--;
            }
            if (pais.continente().equals("Asia") && contAsia > 0) {
                jug1.ocupar(pais);
                pais.ocupadoPor(jug1, 1);
                contAsia--;
            }
        }

        assert(objetivo.gano(jug1));
    }

    @Test
    public void jugadorCumpleObjetivoOcupacionConPaisesDeMas() {
        HashMap <String, Integer> cantidades = new HashMap<>();
        int contOceania = 2;
        int contAsia = 3;
        cantidades.put("Oceania", contOceania);
        cantidades.put("Asia", contAsia);

        //Creamos el objetivo con ciertas cantidades predeterminadas
        ObjetivoOcupacion objetivo = new ObjetivoOcupacion(cantidades, mapa);

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        //Ocupamos todos los paises por el jugador 2 para que no estén sin propietario
        for(Pais pais: paisesDelMapa) {
            jug2.ocupar(pais);
            pais.ocupadoPor(jug2, 1);
        }

        //Ocupamos una cantidad de paises mayor a la necesaria para ganar, con el jugador 1
        for (Pais pais: paisesDelMapa) {
            if (pais.continente().equals("Oceania") || pais.continente().equals("Asia") || pais.continente().equals("Africa")) {
                jug1.ocupar(pais);
                pais.ocupadoPor(jug1, 1);
            }
        }

        assert(objetivo.gano(jug1));
    }

    @Test
    public void jugadorNoCumpleObjetivoOcupacion() {
        HashMap <String, Integer> cantidades = new HashMap<>();
        int contOceania = 2;
        int contAsia = 3;
        cantidades.put("Oceania", contOceania);
        cantidades.put("Asia", contAsia);

        //Creamos el objetivo con ciertas cantidades predeterminadas
        ObjetivoOcupacion objetivo = new ObjetivoOcupacion(cantidades, mapa);

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);
        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        //Ocupamos todos los paises por el jugador 2 para que no estén sin propietario
        for(Pais pais: paisesDelMapa) {
            jug2.ocupar(pais);
            pais.ocupadoPor(jug2, 1);
        }

        //Ocupamos la cantidad de paises menor a la necesaria para ganar, con el jugador 1
        for (Pais pais: paisesDelMapa) {
            if (pais.continente().equals("Oceania") && contOceania > 0) {
                jug1.ocupar(pais);
                pais.ocupadoPor(jug1, 1);
                contOceania--;
            }
            }

        assert(!objetivo.gano(jug1));
    }

    @Test
    public void jugadorGanaAlDestruirAJugadorObjetivo(){

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jug1);
        jugadores.add(jug2);

        ObjetivoDestruccion objetivo = new ObjetivoDestruccion(2, mapa);
        objetivo.verificarObjetivoValido(jug1, jugadores);

        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            jug1.ocupar(pais);
            pais.ocupadoPor(jug1, 1);
        }

        assert(objetivo.gano(jug1));
    }

    @Test
    public void jugadorNoGanaPorNoDestruirAJugadorObjetivo(){

        Juego juego = mock(Juego.class);

        Jugador jug1 = new Jugador(1, juego);
        Jugador jug2 = new Jugador(2, juego);

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jug1);
        jugadores.add(jug2);

        ObjetivoDestruccion objetivo = new ObjetivoDestruccion(2, mapa);
        objetivo.verificarObjetivoValido(jug1, jugadores);

        ArrayList<Pais> paisesDelMapa = mapa.obtenerPaises();

        for(Pais pais: paisesDelMapa) {
            jug1.ocupar(pais);
            pais.ocupadoPor(jug1, 1);
        }

        jug2.ocupar(paisesDelMapa.get(0));
        paisesDelMapa.get(0).ocupadoPor(jug2, 1);

        assert(!objetivo.gano(jug1));
    }

    @Test
    public void objetivoOcupacionTieneDescripcionCorrecta() {
        HashMap <String, Integer> cantidades = new HashMap<>();
        cantidades.put("Oceania", 2);
        cantidades.put("Africa", 6);
        //Creamos el objetivo con ciertas cantidades predeterminadas
        ObjetivoOcupacion objetivo = new ObjetivoOcupacion(cantidades, mapa);

        assertEquals(objetivo.descripcion(), "Ocupar: \nAfrica\n2 paises de Oceania \n");
    }

    @Test
    public void objetivoComunTieneDescripcionCorrecta() {
        ObjetivoComun objetivo = new ObjetivoComun(30, mapa);

        assertEquals(objetivo.descripcion(), "Ocupar 30 paises");
    }
}
