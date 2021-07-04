package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PaisTests {

    @Test
    public void paisTieneNombreCorrecto() {
        Pais pais = new Pais("carlitos", "rodolfo");

        assertEquals(pais.nombre(), "carlitos");
    }

    @Test
    public void paisTieneContinenteCorrecto() {
        Pais pais = new Pais("carlitos", "rodolfo");

        assertEquals(pais.continente(), "rodolfo");
    }

    @Test
    public void paisAsignaFichasCorrectamente() {
        Pais pais = new Pais("carlitos", "rodolfo");

        Jugador jug = new Jugador();
        pais.ocupadoPor(jug, 3);

        assertEquals(pais.cantidadFichas(), 3);
    }

    @Test
    public void paisAvisaAPropietarioAnteriorAlSerOcupado() {
        Pais pais = new Pais("carlitos", "rodolfo");

        Jugador jug_anterior = mock(Jugador.class);
        Jugador jug_nuevo = new Jugador();
        pais.ocupadoPor(jug_anterior, 3);
        pais.ocupadoPor(jug_nuevo, 1);

        verify(jug_anterior,times(1)).desocupar(pais);
    }

    @Test
    public void paisNoPuedeAtacarAOtroNoVecino() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = new Pais("España", "Europa");

        // En principio no se necesitan mocks ya que lo único que debería hacer País es fijarse si es el mismo
        Jugador jugAtc = new Jugador();
        Jugador jugDef = new Jugador();

        atacante.ocupadoPor(jugAtc, 5);
        defensor.ocupadoPor(jugDef, 5);

        assertThrows(PaisSoloPuedeAtacarVecinos.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoPuedeAtacarAOtroDeMismoPropietario() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = new Pais("España", "Europa");

        Jugador jug = new Jugador();

        atacante.agregarVecino(defensor);
        defensor.agregarVecino(atacante);
        atacante.ocupadoPor(jug, 5);
        defensor.ocupadoPor(jug, 5);

        assertThrows(PaisDelMismoPropietarioNoPuedeSerAtacado.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoPuedeAtacarSiFaltanFichas() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = new Pais("España", "Europa");

        Jugador jugAtc = new Jugador();
        Jugador jugDef = new Jugador();

        atacante.agregarVecino(defensor);
        defensor.agregarVecino(atacante);
        atacante.ocupadoPor(jugAtc, 1);
        defensor.ocupadoPor(jugDef, 5);

        assertThrows(PaisNoTieneFichasSuficientes.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoMueveEjercitosSinPropietario() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = new Pais("España", "Europa");

        assertThrows(PaisNoTienePropietario.class, () -> atacante.moverEjercitos(defensor));
    }

    @Test
    public void paisPideInvadirASuPropietarioAlMoverEjercitos() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = new Pais("España", "Europa");

        Jugador prop = mock(Jugador.class);
        when(prop.invadir(atacante, defensor)).thenReturn(2);
        atacante.ocupadoPor(prop, 5);
        atacante.moverEjercitos(defensor);

        verify(prop,times(1)).invadir(atacante, defensor);
    }

    @Test
    public void paisNoPuedeMoverEjercitosSiNoLeAlcanzan() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = new Pais("España", "Europa");

        Jugador prop = mock(Jugador.class);
        when(prop.invadir(atacante, defensor)).thenReturn(5);
        atacante.ocupadoPor(prop, 5);

        assertThrows(PaisNoTieneFichasSuficientes.class, () -> atacante.moverEjercitos(defensor));
    }

    @Test
    public void paisRestaFichasCorrectamenteAlMoverEjercitos() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = new Pais("España", "Europa");

        Jugador prop = mock(Jugador.class);
        when(prop.invadir(atacante, defensor)).thenReturn(3);
        atacante.ocupadoPor(prop, 10);
        atacante.moverEjercitos(defensor);

        assertEquals(atacante.cantidadFichas(), 7);
    }

    @Test
    public void paisActualizaConquistadoAlMoverEjercitos() {
        Pais atacante = new Pais("Mexico", "America");
        Pais defensor = mock(Pais.class);

        Jugador prop = mock(Jugador.class);
        when(prop.invadir(atacante, defensor)).thenReturn(5);
        atacante.ocupadoPor(prop, 10);
        atacante.moverEjercitos(defensor);

        verify(defensor, times(1)).ocupadoPor(prop, 5);
    }

    @Test
    public void paisPierdeFichasCorrectamente() {
        Pais pais = new Pais("Mexico", "America");

        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador();

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(3, batalla);

        assertEquals(pais.cantidadFichas(), 2);
    }

    @Test
    public void paisNoDerrotadoNoAvisaABatalla() {
        Pais pais = new Pais("Mexico", "America");

        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador();

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(3, batalla);

        verify(batalla, times(0)).murioDefensor();
    }

    @Test
    public void paisDerrotadoAvisaABatalla() {
        Pais pais = new Pais("Mexico", "America");

        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador();

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(5, batalla);

        verify(batalla, times(1)).murioDefensor();
    }
}
