package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.JugadorNoTienePais;
import edu.fiuba.algo3.errores.PaisDelMismoPropietarioNoPuedeSerAtacado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class JugadorTest {

    Jugador jugador;
    Pais atacante;
    Pais defensor;
    private static final InputStream DEFAULT_STDIN = System.in;


    @BeforeEach
    public void setUp(){
        jugador = new Jugador();
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
        when(juego.obtenerPais("Chile")).thenReturn(chile);

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
        when(juego.obtenerPais("Chile")).thenReturn(chile);

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
        when(juego.obtenerPais("Chile")).thenReturn(chile);
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
        when(juego.obtenerPais("Chile")).thenReturn(chile);
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
        when(juego.obtenerPais("Chile")).thenReturn(chile);
        jugador.ocupar(chile);

        assertThrows(JugadorNoTienePais.class, () -> {
            jugador.reagrupar("Argentina", "Chile", 2);
        });
    }

    @Test
    public void jugadorRecibeFichasAlCanjearTarjetas() throws FileNotFoundException {

        jugador = new Jugador(1, new Juego(1, "archivos/paises.json", "objetivos.json") );

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

        jugador.canjearTarjetas();
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
}
