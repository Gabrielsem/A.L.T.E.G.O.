package edu.fiuba.algo3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pais {
    private final String nombre;
    private final String continente;
    private final Set<Pais> vecinos = new HashSet<>();
    private int fichas = 0;
    private Jugador propietario = null;

    public Pais(String nombrePais, String nombreContinente) {
        nombre = nombrePais;
        continente = nombreContinente;
    }

    public String nombre() {
        return nombre;
    }

    public String continente() {
        return continente;
    }

    public int cantidadFichas() {
        return fichas;
    }

    public void agregarVecino(Pais vecino) {
        vecinos.add(vecino);
    }

    public void ocupadoPor(Jugador jugador, int cantidadFichas) {
        if (propietario != null) {
            propietario.desocupar(this);
        }

        propietario = jugador;
        fichas = cantidadFichas;
    }

    private void verificarAlcanzanFichas(int cantidad) {
        if (cantidad >= fichas - 1) {
            throw new PaisNoTieneFichasSuficientes(
                    String.format("El país %s solo tiene %d fichas disponibles", nombre, fichas - 1));
        }
    }

    public void atacar(Pais defensor, int cantidadFichas) {
        if (!vecinos.contains(defensor)) {
            throw new PaisSoloPuedeAtacarVecinos(
                String.format("El país %s no es vecino de %s", defensor.nombre(), nombre));
        }
        if (defensor.propietario == propietario) {
            throw new PaisDelMismoPropietarioNoPuedeSerAtacado(
                String.format("El país %s no puede atacar a %s", nombre, defensor.nombre()));
        }
        this.verificarAlcanzanFichas(cantidadFichas);

        Batalla batalla = new Batalla(defensor, this, cantidadFichas);
        batalla.efectuarBatalla();
    }

    public void perderFichas(int cantidadFichas, Batalla batalla) {
        fichas -= cantidadFichas;

        // Si sucede esto, el país fue conquistado
        if (fichas <= 0) {
            batalla.murioDefensor();
        }
    }

    public void moverEjercitos(Pais paisConquistado) {
        if (propietario == null) {
            throw new PaisNoTienePropietario(String.format("El pais %s no tiene propietario", nombre));
        }

        int fichasAMover = propietario.invadir(this, paisConquistado);
        this.verificarAlcanzanFichas(fichasAMover);

        paisConquistado.ocupadoPor(propietario, fichasAMover);
        fichas -= fichasAMover;
    }
}
