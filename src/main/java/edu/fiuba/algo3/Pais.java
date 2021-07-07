package edu.fiuba.algo3;

import java.util.*;
import java.util.stream.Collectors;

public class Pais {
    private final String nombre;
    private final String continente;
    private final Set<String> vecinos;
    private int fichas = 0;
    private Jugador propietario = null;

    public Pais(String nombrePais, String nombreContinente, Collection<String> limitrofes) {
        nombre = nombrePais;
        continente = nombreContinente;
        vecinos = limitrofes.stream().map(String::toLowerCase).collect(Collectors.toSet());
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

    public void ocupadoPor(Jugador jugador, int cantidadFichas) {
        if (propietario != null) {
            propietario.desocupar(this);
        }

        jugador.ocupar(this);
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
        if (!vecinos.contains(defensor.nombre.toLowerCase())) {
            throw new PaisSoloPuedeAtacarVecinos(
                String.format("El país %s no es vecino de %s", defensor.nombre(), nombre));
        }
        if (defensor.propietario.equals(propietario)) {
            throw new PaisDelMismoPropietarioNoPuedeSerAtacado(
                String.format("El país %s no puede atacar a %s", nombre, defensor.nombre()));
        }
        this.verificarAlcanzanFichas(cantidadFichas);

        Batalla batalla = new Batalla(defensor, this, cantidadFichas);
    }

    public void perderFichas(int cantidadFichas, Batalla batalla) {
        fichas -= cantidadFichas;

        // Si sucede esto, el país fue conquistado
        if (fichas <= 0) {
            batalla.murioDefensor();
        }
    }

    public void agregarFichas(int cantidadFichas) {
        fichas += cantidadFichas;
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
