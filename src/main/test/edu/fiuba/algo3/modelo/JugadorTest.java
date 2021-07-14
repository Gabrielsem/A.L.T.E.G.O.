package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
