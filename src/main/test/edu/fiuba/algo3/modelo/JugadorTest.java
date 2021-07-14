package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class JugadorTest {

    Jugador jugador;
    Pais atacante;
    Pais defensor;

    @BeforeEach
    public void setUp(){
        jugador = new Jugador();
        atacante = mock(Pais.class);
        defensor = mock(Pais.class);
        when(atacante.nombre()).thenReturn("SoyAtacante");
        when(defensor.nombre()).thenReturn("SoyDefensor");
    }

    @Test
    public void JugadorNoPuedeAtacarConUnPaisQueNoTiene(){

        assertThrows(JugadorNoTienePais.class, () -> jugador.atacar(atacante,defensor,1));
    }

    @Test
    public void JugadorNoPuedeAtacarAUnPaisQueYaTiene(){

        jugador.ocupar(defensor);
        jugador.ocupar(atacante);

        assertThrows(PaisDelMismoPropietarioNoPuedeSerAtacado.class, () -> jugador.atacar(atacante,defensor,1));
    }

    @Test
    public void JugadorPuedeAtacarUnPais(){

        jugador.ocupar(atacante);

        for( int i=0; i<=4; i++ ){
            jugador.atacar(atacante,defensor,i);
            verify(atacante,times(1)).atacar(defensor,i);
        }
    }

    @Test
    public void JugadorPuedeAtacarAUnPaisQueDesocupa(){

        jugador.ocupar(defensor);
        jugador.ocupar(atacante);
        jugador.desocupar(defensor);
        jugador.atacar(atacante,defensor,2);
        verify(atacante,times(1)).atacar(defensor,2);
    }

    @Test
    public void JugadorNoPuedeAtacarConUnPaisQueDesocupa(){

        jugador.ocupar(atacante);
        jugador.desocupar(atacante);

        assertThrows(JugadorNoTienePais.class, () -> jugador.atacar(atacante,defensor,1));
    }

    @Test
    public void FichasObtenidasAlRealizarCanjesDeTarjetas(){

        assertEquals(jugador.realizarCanje(),4);
        assertEquals(jugador.realizarCanje(),7);

        for( int i=10; i<= 25; i+=5 ){
            assertEquals(jugador.realizarCanje(),i);
        }
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
}
