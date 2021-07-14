package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.Pais;
import edu.fiuba.algo3.Simbolo;
import edu.fiuba.algo3.Tarjeta;
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
    Simbolo simbolo;
    Simbolo simboloBrasil;

    @BeforeEach
    public void setUp() {
        simbolo = mock(Simbolo.class);
        simboloBrasil = mock(Simbolo.class);
        brasil = mock(Pais.class);
        colombia = mock(Pais.class);
        argentina = mock(Pais.class);
        tarjetaArgentina = new Tarjeta(argentina, simbolo);
        tarjetaBrasil = new Tarjeta(brasil, simbolo);
        tarjetaColombia = new Tarjeta(colombia, simbolo);
    }

    @Test
    public void tarjetasConIgualSimboloTienenIgualSimbolo() {
        when(simbolo.esIgualA(simbolo)).thenReturn(true);
        when(simboloBrasil.esIgualA(simbolo)).thenReturn(true);

        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        tarjetas.add(tarjetaBrasil);
        tarjetas.add(tarjetaColombia);

        assertTrue(tarjetaArgentina.tienenTodasMismoSimbolo(tarjetas));
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
        when(simbolo.esIgualA(simbolo)).thenReturn(false);
        when(simboloBrasil.esIgualA(simbolo)).thenReturn(true);

        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        tarjetas.add(tarjetaBrasil);
        tarjetas.add(tarjetaColombia);
        tarjetas.add(tarjetaArgentina);

        assertNull(Tarjeta.grupoCanjeable(tarjetas));
    }

    @Test
    public void obtenerGrupoCanjeableConTresTarjetasConMismoSimboloLasDevuelve() {
        when(simbolo.esIgualA(simbolo)).thenReturn(true);
        when(simboloBrasil.esIgualA(simbolo)).thenReturn(true);
        when(simbolo.esIgualA(simboloBrasil)).thenReturn(true);

        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        tarjetas.add(tarjetaBrasil);
        tarjetas.add(tarjetaColombia);
        tarjetas.add(tarjetaArgentina);

        ArrayList<Tarjeta> canje = Tarjeta.grupoCanjeable(tarjetas);

        assertTrue(canje.size() == 3 && canje.contains(tarjetaColombia) && canje.contains(tarjetaBrasil) && canje.contains(tarjetaArgentina));
    }

    @Test
    public void obtenerGrupoCanjeableConTresTarjetasConDistintoSimboloLasDevuelve() {

    }
}