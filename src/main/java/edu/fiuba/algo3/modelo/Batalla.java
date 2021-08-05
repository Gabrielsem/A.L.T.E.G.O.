package edu.fiuba.algo3.modelo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Observer;

import static java.lang.Math.min;


public class Batalla extends Observable{
    private final Pais defensor;
    private final Pais atacante;
    private final int cantFichasAtaque;
    private static HashSet<Observer> observers = new HashSet<>();

    private int[] dadosAtacante;
    public int[] dadosAtacante() { return dadosAtacante;}
    private int[] dadosDefensor;
    public int[] dadosDefensor() { return dadosDefensor;}

    public Batalla(Pais def, Pais atk, int FichasAtaque) {
        defensor = def;
        atacante = atk;
        cantFichasAtaque = FichasAtaque;
        efectuarBatalla();
    }

    public static void agregarObservador(Observer observer) {
        observers.add( observer );
    }

    private int[] lanzarDados(int cantidad){
        int min = 1;
        int max = 6;
        int[] dados = new int[3];
        cantidad = min(cantidad,3);
        for (int i = 0; i < cantidad; i++) {
            dados[i] = (int)(Math.random() * ((max - min) + 1)) + min;
        }
        Arrays.sort(dados);
        // doy vuelta el array para que quede ordenado ascendente
        int aux;
        for (int i = 0; i < 3 / 2; i++) {
            aux = dados[i];
            dados[i] = dados[3 - i - 1];
            dados[3 - i - 1] = aux;
        }
        return dados;
    }

    private void efectuarBatalla() {

        dadosAtacante = lanzarDados(cantFichasAtaque);
        System.out.println("B"+dadosAtacante);
        dadosDefensor = lanzarDados(defensor.cantidadFichas());
        int cantidad = min(3,min(cantFichasAtaque,defensor.cantidadFichas()) );

        int perdidaAtacante = 0;
        int perdidaDefensor = 0;

        for(int i = 0; i < cantidad ;i++){
            if(dadosAtacante[i] > dadosDefensor[i]){ perdidaDefensor ++; }
            else{ perdidaAtacante ++; }
        }

        atacante.perderFichas(perdidaAtacante);
        defensor.perderFichas(perdidaDefensor);

        for( Observer observer : observers ) this.addObserver(observer);
    }
}
