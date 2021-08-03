package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.*;

import java.util.*;

public class Pais extends Observable {
    private final String nombre;
    private final String continente;
    private final Set<String> vecinos;
    private int fichas = 0;
    private Jugador propietario = null;

    public Pais(String nombrePais, String nombreContinente, Collection<String> limitrofes) {
        nombre = nombrePais;
        continente = nombreContinente;
        vecinos = new HashSet<>(limitrofes);
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

    public void perderFichas(int cantidadFichas) {//FIXME - deprecated (?)
        agregarFichas(- cantidadFichas);
    }

    public void agregarFichas(int cantidadFichas) {
        fichas += cantidadFichas;
        setChanged();notifyObservers();
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

    public Collection<String> getVecinos() {
        return vecinos;
    }

    public boolean invadible() { return fichas<= 0; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return nombre.equals(pais.nombre) && continente.equals(pais.continente) && Objects.equals(vecinos, pais.vecinos);
    }

    public Collection<String> getPaisesAtacables() {
        Set<String> listaPaisesAtacables = new HashSet<>(vecinos);
        listaPaisesAtacables.removeIf(nombreVecino->(propietario.tienePais(nombreVecino)));
        return listaPaisesAtacables;
    }
}
