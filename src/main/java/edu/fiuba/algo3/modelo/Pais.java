package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.*;

import java.util.*;
import java.util.stream.Collectors;

public class Pais extends Observable {
    private final String nombre;
    private final String continente;
    private final Set<String> vecinos;
    private int fichas = 0;
    private Jugador propietario = null;
    Pais paisInvadido;

    public Pais(String nombrePais, String nombreContinente, Collection<String> limitrofes) {
        nombre = nombrePais;
        continente = nombreContinente;
        vecinos = new HashSet<>(limitrofes);
    }

    public Jugador getPropietario() {
        return propietario;
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

        setChanged();notifyObservers();
    }

    private void verificarAlcanzanFichas(int cantidad) {
        if (cantidad > fichas - 1) {
            throw new PaisNoTieneFichasSuficientes(
                    String.format("El país %s solo tiene %d fichas disponibles", nombre, fichas - 1));
        }
    }

    public void atacar(Pais defensor, int cantidadFichas) {
        if (!vecinos.contains(defensor.nombre)) {
            throw new PaisSoloPuedeAtacarVecinos(
                String.format("El país %s no es vecino de %s", defensor.nombre(), nombre));
        }
        if (defensor.propietario.equals(propietario)) {
            throw new PaisDelMismoPropietarioNoPuedeSerAtacado(
                String.format("El país %s no puede atacar a %s (mismo propietario)", nombre, defensor.nombre()));
        }
        this.verificarAlcanzanFichas(cantidadFichas);

        Batalla batalla = new Batalla(defensor, this, cantidadFichas);
    }

    public void perderFichas(int cantidadFichas, Batalla batalla) {
        agregarFichas(-cantidadFichas);

        // Si sucede esto, el país fue conquistado
        if (fichas <= 0) {
            batalla.murioDefensor();
        }
    }

    public void agregarFichas(int cantidadFichas) {
        fichas += cantidadFichas;
        setChanged();notifyObservers();
    }

    public boolean ataqueExitoso() {
        return paisInvadido != null;
    }//FIXME probar estas cosas

    public void moverEjercitos(Pais paisConquistado) {
        if (propietario == null) {
            throw new PaisNoTienePropietario(String.format("El pais %s no tiene propietario", nombre));
        }
        paisInvadido = paisConquistado;
    }

    public void invadirPaisConquistado(int cantFichas) {
        if (!ataqueExitoso()) {
            throw new RuntimeException("No se invadio ningún país");//FIXME crear excepcion custom
        }
        this.verificarAlcanzanFichas(cantFichas);

        paisInvadido.ocupadoPor(propietario, cantFichas);
        agregarFichas(-cantFichas);
        paisInvadido = null;
    }

    public void reagruparA(Pais paisDestino, int cantFichas){
        if( !this.esVecino(paisDestino.nombre())) throw new PaisNoPuedeReagruparAPaisNoVecino(String.format("El pais %s no es vecino de %s", paisDestino.nombre(), nombre));
        verificarAlcanzanFichas(cantFichas);

        agregarFichas(-cantFichas);
        paisDestino.agregarFichas(cantFichas);
    }

    public boolean esVecino(String nombrePais){
        return vecinos.contains(nombrePais);
    }

    public int getNumeroPropietario() {
        return propietario.numero();
    }

    public void notificar() {
        setChanged();notifyObservers();
    }

    public Collection<String> getVecinos() {
        return vecinos;
    }
}
