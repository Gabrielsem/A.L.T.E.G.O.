package edu.fiuba.algo3.modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.fiuba.algo3.errores.PaisNoExiste;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapaTest {

    Mapa mapa;
    static String[] continentes = {"Asia", "Europa", "America del Norte", "America del Sur","Africa","Oceania"};
    static Map<String, HashSet<String>> paisesPorContinente;
    static String rutaArchivo = "archivos/paises.json";

    @BeforeAll
    static public void cargarPaisesPorContinente() {

        paisesPorContinente = new HashMap<>();
        for( String continente : continentes ) paisesPorContinente.put(continente, new HashSet<>());

        JsonArray arregloJsonPaises;
        try {
            arregloJsonPaises = JsonParser.parseReader(new FileReader(rutaArchivo)).getAsJsonObject().get("paises").getAsJsonArray();
        } catch (FileNotFoundException e) { throw new RuntimeException("Error al cargar paisesPorContinente"); }

        for( JsonElement pais : arregloJsonPaises ) {
            String nombrePais = pais.getAsJsonObject().get("Pais").getAsString();
            String nombreContinente = pais.getAsJsonObject().get("Continente").getAsString();
            paisesPorContinente.get(nombreContinente).add(nombrePais);
        }
    }

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

    @Test
    public void fichasPorContinentes() {
        assertEquals(7,mapa.fichasSegunContinentes( paisesPorContinente.get("Asia") ));
        assertEquals(5,mapa.fichasSegunContinentes( paisesPorContinente.get("Europa") ));
        assertEquals(5,mapa.fichasSegunContinentes( paisesPorContinente.get("America del Norte") ));
        assertEquals(3,mapa.fichasSegunContinentes( paisesPorContinente.get("America del Sur") ));
        assertEquals(3,mapa.fichasSegunContinentes( paisesPorContinente.get("Africa") ));
        assertEquals(2,mapa.fichasSegunContinentes( paisesPorContinente.get("Oceania") ));
        assertEquals(0,mapa.fichasSegunContinentes(new HashSet<>() ));
    }

    @Test
    public void paisInexistenteLanzaError() {
        assertThrows(PaisNoExiste.class, () -> {
            mapa.obtenerPais("Pais que no existe");
        });
    }

    @Test
    public void cantidadPaisesPorContinente() {
        HashMap<String, Integer> cantidadPaises = new HashMap<>(Map.of(
                "Africa", 6,
                "Oceania", 4,
                "Europa", 9,
                "America del Norte", 10,
                "Asia", 15,
                "America del Sur", 6
        ));

        assertEquals(cantidadPaises, mapa.cantidadPaisesPorContinente());
    }

    @Test
    public void agregarObserversAPaises() {
        Observer observerChina = mock(Observer.class);

        HashMap<String, Observer> observers = new HashMap<>(Map.of(
                "China", observerChina
        ));

        mapa.addObservers(observers);
        Pais china = mapa.obtenerPais("China");

        verify(observerChina, times(1)).update(china, null);

        china.agregarFichas(2);

        verify(observerChina, times(2)).update(china, null);
    }

}
