package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaisTests {

    Pais pais;

    @BeforeEach
    public void setUp(){
        Collection<String> limitrofes = Arrays.asList("Argentina", "Uruguay");
        pais = new Pais("Brasil", "América", limitrofes);
    }

    @Test
    public void paisTieneNombreCorrecto() {
        assertEquals(pais.nombre(), "Brasil");
    }

    @Test
    public void paisTieneContinenteCorrecto() {
        assertEquals(pais.continente(), "América");
    }

    @Test
    public void paisAsignaFichasCorrectamenteAlOcuparse() {
        Jugador jug = new Jugador();
        pais.ocupadoPor(jug, 3);

        assertEquals(pais.cantidadFichas(), 3);
    }

    @Test
    public void paisAgregaFichasCorrectamente() {
        Jugador jug = new Jugador();
        pais.ocupadoPor(jug, 1);

        pais.agregarFichas(37);

        assertEquals(pais.cantidadFichas(), 38);
    }

    @Test
    public void paisAvisaANuevoPropietarioQueSeOcupo() {
        Jugador jugador= mock(Jugador.class);
        pais.ocupadoPor(jugador, 3);

        verify(jugador,times(1)).ocupar(pais);
    }

    @Test
    public void paisAvisaAPropietarioAnteriorAlSerOcupado() {
        Jugador jug_anterior = mock(Jugador.class);
        Jugador jug_nuevo = new Jugador();
        pais.ocupadoPor(jug_anterior, 3);
        pais.ocupadoPor(jug_nuevo, 1);

        verify(jug_anterior,times(1)).desocupar(pais);
    }

    @Test
    public void paisNoPuedeAtacarAOtroNoVecino() {
        Pais atacante = new Pais("Mexico", "America", Arrays.asList("a", "b"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("a", "b"));

        // En principio no se necesitan mocks ya que lo único que debería hacer País es fijarse si es el mismo
        Jugador jugAtc = new Jugador();
        Jugador jugDef = new Jugador();

        atacante.ocupadoPor(jugAtc, 5);
        defensor.ocupadoPor(jugDef, 5);

        assertThrows(PaisSoloPuedeAtacarVecinos.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoPuedeAtacarAOtroDeMismoPropietario() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador jug = new Jugador();

        atacante.ocupadoPor(jug, 5);
        defensor.ocupadoPor(jug, 5);

        assertThrows(PaisDelMismoPropietarioNoPuedeSerAtacado.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoPuedeAtacarSiFaltanFichas() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador jugAtc = new Jugador();
        Jugador jugDef = new Jugador();

        atacante.ocupadoPor(jugAtc, 1);
        defensor.ocupadoPor(jugDef, 5);

        assertThrows(PaisNoTieneFichasSuficientes.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoMueveEjercitosSinPropietario() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        assertThrows(PaisNoTienePropietario.class, () -> atacante.moverEjercitos(defensor));
    }

    @Test
    public void paisPideInvadirASuPropietarioAlMoverEjercitos() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador prop = mock(Jugador.class);
        when(prop.invadir(atacante, defensor)).thenReturn(2);
        atacante.ocupadoPor(prop, 5);
        atacante.moverEjercitos(defensor);

        verify(prop,times(1)).invadir(atacante, defensor);
    }

    @Test
    public void paisNoPuedeMoverEjercitosSiNoLeAlcanzan() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador prop = mock(Jugador.class);
        when(prop.invadir(atacante, defensor)).thenReturn(5);
        atacante.ocupadoPor(prop, 5);

        assertThrows(PaisNoTieneFichasSuficientes.class, () -> atacante.moverEjercitos(defensor));
    }

    @Test
    public void paisRestaFichasCorrectamenteAlMoverEjercitos() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador prop = mock(Jugador.class);
        when(prop.invadir(atacante, defensor)).thenReturn(3);
        atacante.ocupadoPor(prop, 10);
        atacante.moverEjercitos(defensor);

        assertEquals(atacante.cantidadFichas(), 7);
    }

    @Test
    public void paisPierdeFichasCorrectamente() {
        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador();

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(3, batalla);

        assertEquals(pais.cantidadFichas(), 2);
    }

    @Test
    public void paisNoDerrotadoNoAvisaABatalla() {
        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador();

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(3, batalla);

        verify(batalla, times(0)).murioDefensor();
    }

    @Test
    public void paisDerrotadoAvisaABatalla() {
        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador();

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(5, batalla);

        verify(batalla, times(1)).murioDefensor();
    }

    @Test
    public void paisReagrupa2FichasAVecino(){
        Pais destino = new Pais("Argentina", "América", Arrays.asList("Brasil", "Uruguay"));

        assertTrue(pais.esVecino("Argentina"));

        pais.agregarFichas(3);
        pais.reagruparA(destino, 2);



        assertEquals(destino.cantidadFichas(), 2);
        assertEquals(pais.cantidadFichas(), 1);
    }

    @Test
    public void paisNoPuedeReagruparSinFichasSuficientes(){
        Pais destino = new Pais("Argentina", "América", Arrays.asList("Brasil", "Uruguay"));
        pais.agregarFichas(2);

        assertThrows(PaisNoTieneFichasSuficientes.class, () -> pais.reagruparA(destino, 2));
    }

    @Test
    public void paisNoPuedeReagruparAPaisNoVecino(){
        Pais destino = new Pais("Marruecos", "Juan", Arrays.asList("Cancun", "Uruguay"));
        pais.agregarFichas(6);

        assertThrows(PaisNoPuedeReagruparAPaisNoVecino.class, () -> pais.reagruparA(destino, 2));
    }
}
