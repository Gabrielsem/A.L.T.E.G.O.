package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;



public class BatallaTest {

    Pais atacante;
    Pais defensor;

    @BeforeEach
    public void setUp(){
        atacante = mock(Pais.class);
        defensor = mock(Pais.class);
    }


    @Test
    public void algunoDeLosPaisesPierdeFichasEnUnaBatalla(){
        when(atacante.cantidadFichas()).thenReturn(4);
        when(defensor.cantidadFichas()).thenReturn(3);
        Batalla batalla = new Batalla(defensor, atacante, 3);

        verify(defensor, times(1)).perderFichas(anyInt());
        verify(atacante, times(1)).perderFichas(anyInt());
    }
}
