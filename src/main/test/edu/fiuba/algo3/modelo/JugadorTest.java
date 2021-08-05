package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.JugadorNoTienePais;
import edu.fiuba.algo3.errores.PaisDelMismoPropietarioNoPuedeSerAtacado;
import edu.fiuba.algo3.modelo.objetivos.ObjetivoComun;
import edu.fiuba.algo3.modelo.objetivos.ObjetivoOcupacion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JugadorTest {

    Jugador jugador;
    Pais atacante;
    Pais defensor;
    private static final InputStream DEFAULT_STDIN = System.in;


    @BeforeEach
    public void setUp(){
        jugador = new Jugador(1, mock(Juego.class));
        atacante = mock(Pais.class);
        defensor = mock(Pais.class);
        when(atacante.nombre()).thenReturn("SoyAtacante");
        when(defensor.nombre()).thenReturn("SoyDefensor");
    }
    @AfterEach
    public void rollbackChangesToStdin() { // regresa el estado del System.in al default
        System.setIn(DEFAULT_STDIN);
    }
    @Test
    public void JugadorNoPuedeAtacarConUnPaisQueNoTiene(){
        jugador.prepararAtaques();
        assertThrows(JugadorNoTienePais.class, () -> jugador.atacar(atacante,defensor,1));
    }

    @Test
    public void JugadorNoPuedeAtacarAUnPaisQueYaTiene(){

        jugador.ocupar(defensor);
        jugador.ocupar(atacante);

        jugador.prepararAtaques();
        assertThrows(PaisDelMismoPropietarioNoPuedeSerAtacado.class, () -> jugador.atacar(atacante,defensor,1));
    }

    @Test
    public void JugadorPuedeAtacarUnPais(){

        jugador.ocupar(atacante);

        for( int i=0; i<=4; i++ ){
            jugador.prepararAtaques();
            jugador.atacar(atacante,defensor,i);
            verify(atacante,times(1)).atacar(defensor,i);
        }
    }

    @Test
    public void JugadorPuedeAtacarAUnPaisQueDesocupa(){

        jugador.ocupar(defensor);
        jugador.ocupar(atacante);
        jugador.desocupar(defensor);
        jugador.prepararAtaques();
        jugador.atacar(atacante,defensor,2);
        verify(atacante,times(1)).atacar(defensor,2);
    }

    @Test
    public void JugadorNoPuedeAtacarConUnPaisQueDesocupa(){

        jugador.ocupar(atacante);
        jugador.desocupar(atacante);
        jugador.prepararAtaques();
        assertThrows(JugadorNoTienePais.class, () -> jugador.atacar(atacante,defensor,1));
    }

    @Test
    public void FichasPorConquistaDeMenosQue8Paises(){

        Juego juego = mock(Juego.class);
        when(juego.fichasSegunContinentes( any() )).thenReturn(0);

        jugador = new Jugador(0,juego);

        for(int i=1; i<8; i++){

            Pais unPais = new Pais("nombre"+i,"cont", new ArrayList<>() );
            jugador.ocupar(unPais);
            assertEquals( jugador.fichasPorConquista(), 3 );
        }

    }

    @Test
    public void FichasPorConquistaDeMenosQue8PaisesYContinentes(){

        Juego juego = mock(Juego.class);
        when(juego.fichasSegunContinentes( any() )).thenReturn(5);

        jugador = new Jugador(0,juego);

        for(int i=1; i<6; i++){

            Pais unPais = new Pais("nombre"+i,"cont", new ArrayList<>() );
            jugador.ocupar(unPais);
        }
        assertEquals( jugador.fichasPorConquista(), 3+5 );

        when(juego.fichasSegunContinentes( any() )).thenReturn(10);
        assertEquals( jugador.fichasPorConquista(), 3+10 );

    }

    @Test
    public void FichasPorConquistaDeMasQue8Paises(){

        Juego juego = mock(Juego.class);
        when(juego.fichasSegunContinentes( any() )).thenReturn(0);

        jugador = new Jugador(0,juego);

        for(int i=1; i<=7; i++){
            Pais unPais = new Pais("nombre"+i,"cont", new ArrayList<>() );
            jugador.ocupar(unPais);
        }

        for(int i=8; i<30; i++){

            Pais unPais = new Pais("nombre"+i,"cont", Arrays.asList("Argentina", "Uruguay"));
            jugador.ocupar(unPais);
            assertEquals( jugador.fichasPorConquista(), i/2 );
        }

    }

    @Test
    public void FichasPorConquistaDeMasQue8PaisesYContinentes(){

        Juego juego = mock(Juego.class);
        when(juego.fichasSegunContinentes( any() )).thenReturn(6);

        jugador = new Jugador(0,juego);

        for(int i=1; i<=10; i++){

            Pais unPais = new Pais("nombre"+i,"cont", Arrays.asList("Argentina", "Uruguay"));
            jugador.ocupar(unPais);

        }

        assertEquals( jugador.fichasPorConquista(), 5+6 );

        when(juego.fichasSegunContinentes( any() )).thenReturn(8);
        assertEquals( jugador.fichasPorConquista(), 5+8 );

    }

    @Test
    public void jugadorNoAtacaYNoGanaTarjeta(){
        Juego juego = mock(Juego.class);
        Jugador jug = new Jugador(1, juego);

        verify(juego,times(0)).pedirTarjeta();
    }

    @Test
    public void jugadorAtacaYNoGanaTarjeta(){
        Juego juego = mock(Juego.class);
        Jugador jug = new Jugador(1, juego);
        Pais arg = mock(Pais.class);
        Pais chile = mock(Pais.class);
        when(arg.nombre()).thenReturn("Argentina");
        when(chile.nombre()).thenReturn("Chile");

        jug.ocupar(arg);

        jug.prepararAtaques();
        jug.atacar(arg, chile, 1);

        verify(arg,times(1)).atacar(chile, 1);
        verify(juego,times(0)).pedirTarjeta();
    }

    @Test
    public void jugadorAtacaYGanaTarjeta(){
        Juego juego = mock(Juego.class);
        Jugador jug = new Jugador(1, juego);
        Pais arg = mock(Pais.class);
        Pais chile = mock(Pais.class);
        when(arg.nombre()).thenReturn("Argentina");
        when(chile.nombre()).thenReturn("Chile");

        doAnswer(invocation ->{
            jug.ocupar(chile);
            return null;
        }).when(arg).atacar(chile,1);

        jug.ocupar(arg);
        jug.prepararAtaques();
        jug.atacar(arg, chile, 1);

        verify(arg,times(1)).atacar(chile, 1);
        verify(juego,times(1)).pedirTarjeta();
    }

    @Test
    public void jugadorReagrupa2Fichas(){
        Juego juego = mock(Juego.class);
        Jugador jug = new Jugador(1, juego);
        Pais arg = mock(Pais.class);
        Pais chile = mock(Pais.class);
        when(arg.nombre()).thenReturn("Argentina");
        when(chile.nombre()).thenReturn("Chile");
        jug.ocupar(arg);
        jug.ocupar(chile);

        jug.reagrupar("Argentina", "Chile", 2);
        verify(arg, times(1)).reagruparA(chile, 2);
    }

    @Test
    public void jugadorNoPuedeReagruparAUnPaisAjeno(){
        Juego juego = mock(Juego.class);
        Jugador jugador = new Jugador(1, juego);
        Pais arg = mock(Pais.class);
        Pais chile = mock(Pais.class);
        when(arg.nombre()).thenReturn("Argentina");
        when(chile.nombre()).thenReturn("Chile");
        jugador.ocupar(arg);

        assertThrows(JugadorNoTienePais.class, () -> {
            jugador.reagrupar("Argentina", "Chile", 2);
        });
    }

    @Test
    public void jugadorNoPuedeReagruparDesdeUnPaisAjeno(){
        Juego juego = mock(Juego.class);
        Jugador jugador = new Jugador(1, juego);
        Pais arg = mock(Pais.class);
        Pais chile = mock(Pais.class);
        when(arg.nombre()).thenReturn("Argentina");
        when(chile.nombre()).thenReturn("Chile");
        jugador.ocupar(chile);

        assertThrows(JugadorNoTienePais.class, () -> {
            jugador.reagrupar("Argentina", "Chile", 2);
        });
    }

    @Test
    public void jugadorRecibeFichasAlCanjearTarjetas() throws FileNotFoundException {

        jugador = new Jugador(1, new Juego(1, "src/main/resources/archivos/paises.json", "src/main/resources/archivos/objetivos.json","src/main/resources/archivos/tarjetas.json") );

        Tarjeta t1 = new Tarjeta(new Pais("N1","C",new ArrayList<>()),
                new Simbolo("S1") );
        Tarjeta t2 = new Tarjeta(new Pais("N2","C",new ArrayList<>()),
                new Simbolo("S2") );
        Tarjeta t3 = new Tarjeta(new Pais("N3","C",new ArrayList<>()),
                new Simbolo("S3") );

        assertEquals(0,jugador.canjearTarjetas());
        jugador.recibirTarjeta(t1);jugador.recibirTarjeta(t2);jugador.recibirTarjeta(t3);
        assertEquals(4,jugador.canjearTarjetas());

        assertEquals(0,jugador.canjearTarjetas());
        jugador.recibirTarjeta(t1);jugador.recibirTarjeta(t2);jugador.recibirTarjeta(t3);
        assertEquals(7,jugador.canjearTarjetas());

        assertEquals(0,jugador.canjearTarjetas());
        jugador.recibirTarjeta(t1);jugador.recibirTarjeta(t2);jugador.recibirTarjeta(t3);
        assertEquals(10,jugador.canjearTarjetas());
    }

    @Test
    public void jugadorActivaTarjetasDePaisesPropios() {
        Pais p1 = new Pais("P1","C",new ArrayList<>());
        Tarjeta t1 = new Tarjeta(p1,new Simbolo("S"));
        Pais p2 = new Pais("P2","C",new ArrayList<>());
        Tarjeta t2 = new Tarjeta(p2,new Simbolo("S"));

        p1.ocupadoPor(jugador,0);
        jugador.recibirTarjeta(t1);
        p2.ocupadoPor(jugador,0);
        jugador.recibirTarjeta(t2);

        jugador.activarTarjetas();
        assertEquals(2,p1.cantidadFichas());
        assertEquals(2,p2.cantidadFichas());
    }

    @Test
    public void jugadorNoActivaTarjetasDePaisesAjenos() {
        Pais p1 = new Pais("P1","C",new ArrayList<>());
        Tarjeta t1 = new Tarjeta(p1,new Simbolo("S"));
        Pais p2 = new Pais("P2","C",new ArrayList<>());
        Tarjeta t2 = new Tarjeta(p2,new Simbolo("S"));

        jugador.recibirTarjeta(t1);
        jugador.recibirTarjeta(t2);

        jugador.canjearTarjetas();
        assertEquals(0,p1.cantidadFichas());
        assertEquals(0,p2.cantidadFichas());
    }

    @Test
    public void obtenerPaisesPorContinente() {
        Pais pais1 = new Pais("juan", "pedro", new ArrayList<>());
        Pais pais2 = new Pais("pedro", "pedro", new ArrayList<>());
        Pais pais3 = new Pais("marce", "marce", new ArrayList<>());

        jugador.ocupar(pais1);
        jugador.ocupar(pais2);
        jugador.ocupar(pais3);

        HashMap<String, Integer> paises = new HashMap<>(Map.of("pedro", 2, "marce", 1));
        assertEquals(paises, jugador.paisesPorContinente());
    }

    @Test
    public void alDarFichaAJugadorTieneFichas() {
        jugador.darFichas(4);

        assertTrue(jugador.tieneFichas());
    }

    @Test
    public void poner5FichasAPaisConquistado() {
        Pais alaska = new Pais("Alaska", "America del Norte", new ArrayList<>());

        jugador.ocupar(alaska);
        jugador.darFichas(5);

        assertEquals(jugador.cantidadFichas(), 5);

        jugador.ponerFichas("Alaska", 3);

        assertEquals(jugador.cantidadFichas(), 2);

        jugador.ponerFichas("Alaska", 2);

        assertEquals(jugador.cantidadFichas(), 0);
        assertEquals(alaska.cantidadFichas(), 5);
    }

    @Test
    public void ponerFichasEnUnPaisQueNoLePerteneceLanzaError() {
        Pais alaska = new Pais("Alaska", "America del Norte", new ArrayList<>());

        assertThrows(JugadorNoTienePais.class, () -> {
            jugador.ponerFichas("Alaska", 1);
        });
    }

    @Test
    public void ponerFichasSinTenerFichasDisponiblesLanzaError() {
        Pais alaska = new Pais("Alaska", "America del Norte", new ArrayList<>());

        jugador.ocupar(alaska);
        assertEquals(jugador.cantidadFichas(), 0);

        assertThrows(JugadorNoTieneFichasSuficientes.class, () -> {
            jugador.ponerFichas("Alaska", 1);
        });
    }

    @Test
    public void obtenerTarjetas() {
        Tarjeta tarjeta1 = new Tarjeta(new Pais("P1", "C1", new ArrayList<>()), new Simbolo("S1"));
        Tarjeta tarjeta2 = new Tarjeta(new Pais("P2", "C2", new ArrayList<>()), new Simbolo("S2"));
        Tarjeta tarjeta3 = new Tarjeta(new Pais("P3", "C3", new ArrayList<>()), new Simbolo("S3"));

        ArrayList<Tarjeta> tarjetas = new ArrayList<>(Arrays.asList(tarjeta1, tarjeta2, tarjeta3));

        for (Tarjeta tarjeta : tarjetas) {
            jugador.recibirTarjeta(tarjeta);
        }

        assertEquals(tarjetas, jugador.getTarjetas());
    }
    
    @Test 
    public void jugadorGanaConObjetivoComun() {
        ObjetivoComun objetivoComun = mock(ObjetivoComun.class);
        ObjetivoOcupacion objetivoOcupacion = mock(ObjetivoOcupacion.class);
        
        jugador.asignarObjetivos(objetivoComun, objetivoOcupacion);
        
        when(objetivoComun.gano(jugador)).thenReturn(true);
        when(objetivoOcupacion.gano(jugador)).thenReturn(false);
        
        assertTrue(jugador.gane());
    }

    @Test
    public void jugadorGanaConobjetivoOcupacion() {
        ObjetivoComun objetivoComun = mock(ObjetivoComun.class);
        ObjetivoOcupacion objetivoOcupacion = mock(ObjetivoOcupacion.class);

        jugador.asignarObjetivos(objetivoComun, objetivoOcupacion);

        when(objetivoComun.gano(jugador)).thenReturn(false);
        when(objetivoOcupacion.gano(jugador)).thenReturn(true);

        assertTrue(jugador.gane());
    }

    @Test
    public void jugadorNoGana() {
        ObjetivoComun objetivoComun = mock(ObjetivoComun.class);
        ObjetivoOcupacion objetivoOcupacion = mock(ObjetivoOcupacion.class);

        jugador.asignarObjetivos(objetivoComun, objetivoOcupacion);

        when(objetivoComun.gano(jugador)).thenReturn(false);
        when(objetivoOcupacion.gano(jugador)).thenReturn(false);

        assertFalse(jugador.gane());
    }

    @Test
    public void tienePais() {
        Pais p1 = new Pais("P1", "B", new ArrayList<>());
        Pais p2 = new Pais("P2", "B", new ArrayList<>());

        Jugador jug1 = new Jugador(1, null);

        p1.ocupadoPor(jug1, 1);

        assertTrue(jug1.tienePais(p1.nombre()));
    }

    @Test
    public void noTienePais() {
        Pais p1 = new Pais("P1", "B", new ArrayList<>());
        Pais p2 = new Pais("P2", "B", new ArrayList<>());

        Jugador jug1 = new Jugador(1, null);

        p1.ocupadoPor(jug1, 1);

        assertFalse(jug1.tienePais(p2.nombre()));
    }

    @Test
    public void paisesDisponiblesParaAtacar() {
        Pais p1 = new Pais("P1", "B", Arrays.asList("P3", "P2"));
        Pais p2 = new Pais("P2", "B", Arrays.asList("P1", "P3"));
        Pais p3 = new Pais("P3", "B", Arrays.asList("P1", "P2"));

        Jugador jug1 = new Jugador(1, null);
        Jugador jug2 = new Jugador(2, null);

        p1.ocupadoPor(jug2, 1);
        p2.ocupadoPor(jug1, 3);
        p3.ocupadoPor(jug2, 8);

        assertEquals(Arrays.asList("P3"), new ArrayList<>(jug2.paisesDisponiblesParaAtacar()));
    }
}
