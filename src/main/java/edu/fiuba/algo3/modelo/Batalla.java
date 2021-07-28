package edu.fiuba.algo3.modelo;
import java.util.Arrays;

public class Batalla {
    private final Pais defensor;
    private final Pais atacante;
    private final int cantFichasAtaque;

    public Batalla(Pais def, Pais atk, int FichasAtaque) {
        defensor = def;
        atacante = atk;
        cantFichasAtaque = FichasAtaque;
        efectuarBatalla();
    }

    private int[] lanzarDados(int cantidad){
        int min = 1;
        int max = 6;
        int[] dados = new int[3];
        cantidad--;
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
        int[] dadosAtacante = lanzarDados(cantFichasAtaque);
        int[] dadosDefensor = lanzarDados(defensor.cantidadFichas());
        int perdidaAtacante = 0;
        int perdidaDefensor = 0;
        for(int i = 0; i < 3;i++){
            if(dadosAtacante[i] > dadosDefensor[i]){ perdidaDefensor ++; }
            else{ perdidaAtacante ++; }
        }
        atacante.perderFichas(perdidaAtacante, this);
        defensor.perderFichas(perdidaDefensor, this);
    }

    public void murioDefensor() {
        atacante.moverEjercitos(defensor);
    }//FIXME - Ya no mas, lo checkea la fase
}
