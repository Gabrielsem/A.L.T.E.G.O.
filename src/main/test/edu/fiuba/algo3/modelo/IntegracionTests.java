package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.PaisDelMismoPropietarioNoPuedeSerAtacado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.*;

public class IntegracionTests {

    Juego juego;
    Mapa mapa;
    Jugador j1, j2;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        // Mapa reducido con 13 países en total de Asia, Oceanía y Europa
        mapa = new Mapa("archivos/paises_reducido.json");
        juego = mock(Juego.class);
        // Mockeo la delegación que hace juego
        when(juego.fichasSegunContinentes(any())).thenAnswer(delegatesTo(mapa));

        j1 = new Jugador(1, juego);
        j2 = new Jugador(2, juego);
    }

    // Devuelve un diccionario de países con todos los países con los nombres dados
    // Los ocupa por un jugador y fichas dadas.
    private HashMap<String, Pais> obtenerPaises(String[] nombresPaises, Jugador jugador, int cantFichas) {
        HashMap<String, Pais> paises = new HashMap<>();
        for ( String n : nombresPaises) {
            Pais p = mapa.obtenerPais(n);
            p.ocupadoPor(jugador, cantFichas);
            paises.put(n, p);
        }

        return paises;
    }

    @Test
    public void rondaDosJugadoresPonenFichas() {
        // Le doy 8 países al jugador 1, sin que complete ningún continente
        String[] nombresPaisesJ1 = {"Francia", "Tartaria", "Australia", "Kamtchatka", "Israel", "Taymir", "Sumatra", "India"};
        HashMap<String, Pais> paisesJ1 = obtenerPaises(nombresPaisesJ1, j1, 1);

        Pais alemania = mapa.obtenerPais("Alemania");
        alemania.ocupadoPor(j2, 1);
        j1.actualizarFichas();
        j2.actualizarFichas();

        j1.ponerFichas("Francia", 1);
        j1.ponerFichas("Australia", 3);

        assertThrows(JugadorNoTieneFichasSuficientes.class, () -> {
            j1.ponerFichas("Israel", 1);
        });

        j2.ponerFichas("Alemania", 3);

        assertThrows(JugadorNoTieneFichasSuficientes.class, () -> {
            j2.ponerFichas("Alemania", 1);
        });


        // Deberían haber puesto 4 fichas el jugador 1 y 3 (el mínimo) el jugador 2,
        // en principio no tienen tarjetas ni continentes enteros
        assertEquals(12, paisesJ1.values().stream().mapToInt(Pais::cantidadFichas).sum()); // 8 que tenia + 4 nuevas
        assertEquals(4, alemania.cantidadFichas()); // 1 que tenía + 3 nuevas
    }

    @Test
    public void rondaDosJugadoresPonenFichasUnoTieneTodoAsia() {
        // Le doy todos los países de Asia al jugador 1
        String[] nombresAsia = {"Tartaria", "Kamtchatka", "Israel", "Taymir", "Arabia", "India", "China"};
        HashMap<String, Pais> paisesJ1 = obtenerPaises(nombresAsia, j1, 1);

        Pais alemania = mapa.obtenerPais("Alemania");
        alemania.ocupadoPor(j2, 1);
        j1.actualizarFichas();
        j2.actualizarFichas();

        System.setIn(new ByteArrayInputStream("Tartaria\n2\nIndia\n1\nChina\n3\nArabia\n4".getBytes()));
        j1.ponerFichas("Tartaria", 2);
        j1.ponerFichas("India", 8);
        assertThrows(JugadorNoTieneFichasSuficientes.class, () -> {
            j1.ponerFichas("Arabia", 1);
        });
        j2.ponerFichas("Alemania", 3);
        assertThrows(JugadorNoTieneFichasSuficientes.class, () -> {
            j2.ponerFichas("Alemania", 1);
        });

        // Deberían haber puesto, ya que no tienen tarjetas:
        // jugador 1: 3 fichas (por tener 7 paises) + 7 fichas (por tener Asia completo) = 10 fichas
        // jugador 2: 3 fichas, el minimo
        assertEquals(17, paisesJ1.values().stream().mapToInt(Pais::cantidadFichas).sum()); // 7 que tenia + 10 nuevas
        assertEquals(4, alemania.cantidadFichas()); // 1 que tenía + 3 nuevas
    }

/*    @Test
    public void rondaDosJugadoresJugador1Conquista2PaisesDelJugador2() {
        // Le doy 5 países al jugador 1, sin completar continente, con muchísimas fichas
        String[] nombresPaisesJ1 = {"Francia", "Tartaria", "Australia", "Kamtchatka", "Israel"};
        HashMap<String, Pais> paisesJ1 = obtenerPaises(nombresPaisesJ1, j1, 50000);

        // Le doy 5 países al jugador 2, sin completar continente, con 1 ficha
        String[] nombresPaisesJ2 = {"Alemania", "Italia", "China", "Arabia", "Borneo"};
        HashMap<String, Pais> paisesJ2 = obtenerPaises(nombresPaisesJ2, j2, 1);

        // En principio con 50000 fichas debería poder asumir que se conquista el país tras varios intentos
        while (paisesJ1.get("Francia").cantidadFichas() > 0) {
            try {
                j1.atacar(paisesJ1.get("Francia"), paisesJ2.get("Alemania"), 3);
            } catch (PaisDelMismoPropietarioNoPuedeSerAtacado e) {
                break;
            }
        }
        while (paisesJ1.get("Australia").cantidadFichas() > 0) {
            try {
                j1.atacar(paisesJ1.get("Australia"), paisesJ2.get("Borneo"), 3);
            } catch (PaisDelMismoPropietarioNoPuedeSerAtacado e) {
                break;
            }
        }

        assertEquals(7, j1.obtenerCantidadPaises());
        assertEquals(3, j2.obtenerCantidadPaises());
    }
*/
    @Test
    public void activacionDeTarjetasEnRondaDeColocacion() throws FileNotFoundException {

        Juego juego = new Juego(1, "archivos/paises.json", "objetivos.json");
        Jugador jugador = new Jugador(1,juego);

        Pais p1 = new Pais("P1","C",new ArrayList<>());
        Tarjeta t1 = new Tarjeta(p1,new Simbolo("S"));

        Pais p2 = new Pais("P2","C",new ArrayList<>());
        Tarjeta t2 = new Tarjeta(p2,new Simbolo("S"));

        p1.ocupadoPor(jugador,1);
        jugador.recibirTarjeta(t1);
        jugador.recibirTarjeta(t2);

        jugador.actualizarFichas();
        jugador.ponerFichas("P1", 3);

        assertEquals(1+2+3,p1.cantidadFichas());// 1 original + 2 por tarjeta + 3 minimo
        assertEquals(0,p2.cantidadFichas());

    }
}
