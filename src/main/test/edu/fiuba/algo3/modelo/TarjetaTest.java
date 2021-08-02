package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TarjetaTest {

    Pais argentina;
    Pais brasil;
    Pais colombia;
    Tarjeta tarjetaArgentina;
    Tarjeta tarjetaBrasil;
    Tarjeta tarjetaColombia;
    Simbolo simboloArgentina;
    Simbolo simboloBrasil;
    Simbolo simboloColombia;

    @BeforeEach
    public void setUp() {
        simboloArgentina = mock(Simbolo.class);
        simboloBrasil = mock(Simbolo.class);
        simboloColombia = mock(Simbolo.class);

        brasil = mock(Pais.class);
        when(brasil.nombre()).thenReturn("Brasil");
        colombia = mock(Pais.class);
        when(colombia.nombre()).thenReturn("Colombia");
        argentina = mock(Pais.class);
        when(argentina.nombre()).thenReturn("Argentina");

        tarjetaArgentina = new Tarjeta(argentina, simboloArgentina);
        tarjetaBrasil = new Tarjeta(brasil, simboloBrasil);
        tarjetaColombia = new Tarjeta(colombia, simboloColombia);
    }

    @Test
    public void activarTarjetaSinActivarAgregaDosFichasASuPais() {
        tarjetaColombia.activar();
        verify(colombia, times(1)).agregarFichas(2);
    }

    @Test
    public void activarTarjetaActivadaNoAgregaDosFichasASuPais() {
        tarjetaColombia.activar();
        tarjetaColombia.activar();
        verify(colombia, times(1)).agregarFichas(2);
    }

    @Test
    public void activarTarjetaDesactivadaAgregaDosFichasASuPais() {
        tarjetaColombia.activar();
        tarjetaColombia.desactivar();
        tarjetaColombia.activar();
        verify(colombia, times(2)).agregarFichas(2);
    }

    @Test
    public void obtenerGrupoCanjeableSinCanjeDevuelveNull() {
        when(simboloArgentina.nombre()).thenReturn("Barco");
        when(simboloColombia.nombre()).thenReturn("Barco");
        when(simboloBrasil.nombre()).thenReturn("Globo");

        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        tarjetas.add(tarjetaBrasil);
        tarjetas.add(tarjetaColombia);
        tarjetas.add(tarjetaArgentina);

        assertNull(Tarjeta.grupoCanjeable(tarjetas));
    }

    @Test
    public void obtenerGrupoCanjeableConTresTarjetasConMismoSimboloLasDevuelve() {
        when(simboloBrasil.nombre()).thenReturn("Barco");
        when(simboloArgentina.nombre()).thenReturn("Barco");
        when(simboloColombia.nombre()).thenReturn("Barco");

        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        tarjetas.add(tarjetaBrasil);
        tarjetas.add(tarjetaColombia);
        tarjetas.add(tarjetaArgentina);

        ArrayList<Tarjeta> canje = Tarjeta.grupoCanjeable(tarjetas);

        assertTrue(canje.size() == 3 && canje.contains(tarjetaColombia) && canje.contains(tarjetaBrasil) && canje.contains(tarjetaArgentina));
    }

    @Test
    public void obtenerGrupoCanjeableConTresTarjetasConDistintoSimboloLasDevuelve() {
        when(simboloColombia.nombre()).thenReturn("Globo");
        when(simboloBrasil.nombre()).thenReturn("Barco");
        when(simboloArgentina.nombre()).thenReturn("Cañon");

        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        tarjetas.add(tarjetaBrasil);
        tarjetas.add(tarjetaColombia);
        tarjetas.add(tarjetaArgentina);

        ArrayList<Tarjeta> canje = Tarjeta.grupoCanjeable(tarjetas);

        assertTrue(canje.size() == 3 && canje.contains(tarjetaColombia) && canje.contains(tarjetaBrasil) && canje.contains(tarjetaArgentina));
    }

    @Test
    public void pedirPaisTarjeta() {
        assertEquals("Argentina",tarjetaArgentina.pais());
        assertEquals("Brasil",tarjetaBrasil.pais());
        assertEquals("Colombia",tarjetaColombia.pais());
    }

    @Test
    public void FichasObtenidasAlRealizarCanjesDeTarjetas() {

        assertEquals(Tarjeta.cantidadFichasCanje(0),4);
        assertEquals(Tarjeta.cantidadFichasCanje(1),7);

        for (int i = 2; i <= 10; i++) {
            assertEquals(i * 5, Tarjeta.cantidadFichasCanje(i));
        }
    }

    @Test
    public void dosTarjetasConLosMismosDatosSonIguales() {
        Tarjeta t1 =new Tarjeta(new Pais("P","C",new ArrayList<>()),new Simbolo("S"));
        Tarjeta t2 =new Tarjeta(new Pais("P","C",new ArrayList<>()),new Simbolo("S"));

        assertEquals(t1, t2);
    }

    @Test
    public void dosTarjetasConDistintosDatosSonDistintas() {
        Tarjeta t1 =new Tarjeta(new Pais("P1","C1",new ArrayList<>()),new Simbolo("S1"));
        Tarjeta t2 =new Tarjeta(new Pais("P2","C2",new ArrayList<>()),new Simbolo("S2"));

        assertNotEquals(t1, t2);
        assertNotEquals(t1, null);
    }

}