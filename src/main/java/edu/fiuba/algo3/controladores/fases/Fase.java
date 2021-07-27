package edu.fiuba.algo3.controladores.fases;

public interface Fase {
    public void iniciar();
    public Fase tocoSiguiente();
    public void tocoPais(String nombrePais);
}
