package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaisTests {

    Pais pais;
    Collection<String> limitrofes;

    @BeforeEach
    public void setUp(){
        limitrofes = Arrays.asList("Argentina", "Uruguay");
        pais = new Pais("Brasil", "América", limitrofes);
    }

    @Test
    public void paisTieneNombreCorrecto() {
        assertEquals(pais.nombre(), "Brasil");
    }

    @Test
    public void paisTieneVecinosCorrectos() {
        assertEquals(limitrofes, new LinkedList<>(pais.getVecinos()) );
    }

    @Test
    public void paisTieneContinenteCorrecto() {
        assertEquals(pais.continente(), "América");
    }

    @Test
    public void paisAsignaFichasCorrectamenteAlOcuparse() {
        Jugador jug = new Jugador(1, mock(Juego.class));
        pais.ocupadoPor(jug, 3);

        assertEquals(pais.cantidadFichas(), 3);
    }

    @Test
    public void paisAgregaFichasCorrectamente() {
        Jugador jug = new Jugador(1, mock(Juego.class));
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
        Jugador jug_nuevo = new Jugador(1, mock(Juego.class));
        pais.ocupadoPor(jug_anterior, 3);
        pais.ocupadoPor(jug_nuevo, 1);

        verify(jug_anterior,times(1)).desocupar(pais);
    }

    @Test
    public void paisNoPuedeAtacarAOtroNoVecino() {
        Pais atacante = new Pais("Mexico", "America", Arrays.asList("a", "b"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("a", "b"));

        // En principio no se necesitan mocks ya que lo único que debería hacer País es fijarse si es el mismo
        Jugador jugAtc = new Jugador(1, mock(Juego.class));
        Jugador jugDef = new Jugador(1, mock(Juego.class));

        atacante.ocupadoPor(jugAtc, 5);
        defensor.ocupadoPor(jugDef, 5);

        assertThrows(PaisSoloPuedeAtacarVecinos.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoPuedeAtacarAOtroDeMismoPropietario() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador jug = new Jugador(1, mock(Juego.class));

        atacante.ocupadoPor(jug, 5);
        defensor.ocupadoPor(jug, 5);

        assertThrows(PaisDelMismoPropietarioNoPuedeSerAtacado.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void paisNoPuedeAtacarSiFaltanFichas() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador jugAtc = new Jugador(1, mock(Juego.class));
        Jugador jugDef = new Jugador(1, mock(Juego.class));

        atacante.ocupadoPor(jugAtc, 1);
        defensor.ocupadoPor(jugDef, 5);

        assertThrows(PaisNoTieneFichasSuficientes.class, () -> atacante.atacar(defensor, 2));
    }

    @Test
    public void sePierdenFichasCorrectamenteAlAtacar() {
        Pais atacante = new Pais("México", "América", Arrays.asList("España", "a"));
        Pais defensor = new Pais("España", "Europa", Arrays.asList("México", "b"));

        Jugador jugAtc = new Jugador(1, mock(Juego.class));
        Jugador jugDef = new Jugador(2, mock(Juego.class));

        int fichasTotales = 1000;

        atacante.ocupadoPor(jugAtc, fichasTotales/2);
        defensor.ocupadoPor(jugDef, fichasTotales/2);

        for( int i = 0; i<10;i++ ) {
            int fichasAtaque = new Random().nextInt(3) +1;
            atacante.atacar(defensor,fichasAtaque);
            fichasTotales -= fichasAtaque;
            assertEquals(fichasTotales, atacante.cantidadFichas()+ defensor.cantidadFichas() );
        }
    }

    @Test
    public void paisPierdeFichasCorrectamente() {
        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador(1, mock(Juego.class));

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(3);

        assertEquals(pais.cantidadFichas(), 2);
    }

    @Test
    public void paisNoDerrotadoNoEsInvadible() {
        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador(1, mock(Juego.class));

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(3);

        assertFalse( pais.invadible() );
    }

    @Test
    public void paisDerrotadoEsInvadible() {
        Batalla batalla = mock(Batalla.class);
        Jugador jugador = new Jugador(1, mock(Juego.class));

        pais.ocupadoPor(jugador, 5);
        pais.perderFichas(5);

        assertTrue( pais.invadible() );
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

    @Test
    public void paisesConLosMismosDatosSonIguales() {
        Pais p1 = new Pais("P","Q",Arrays.asList("A","B","C"));
        Pais p2 = new Pais("P","Q",Arrays.asList("A","B","C"));

        assertEquals(p1,p2);
    }

    @Test
    public void paisesConLosDistintosDatosSonDistintos() {
        Pais p1 = new Pais("P1","Q1",Arrays.asList("A","B","C"));
        Pais p2 = new Pais("P2","Q2",Arrays.asList("A","B","C"));

        assertNotEquals(p1,p2);
        assertNotEquals(p1, null);
    }
}