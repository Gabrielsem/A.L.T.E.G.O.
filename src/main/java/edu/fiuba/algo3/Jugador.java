package edu.fiuba.algo3;

import java.util.HashSet;
import java.util.List;

public class Jugador {
    //Esto estaría bueno que sea un diccionario con clave -> nombrePais, valor -> Pais para poder buscarlo
    HashSet<Pais> paisesConquistados = new HashSet<Pais>();
    int numero;

    public Jugador(){}

    public Jugador(int numeroDeJugador){
        numero = numeroDeJugador;
    }

    public void desocupar(Pais unPais) {
        paisesConquistados.remove(unPais);
    }

    public void ocupar(Pais unPais){
        paisesConquistados.add(unPais);
    }

    public int invadir(Pais atacante, Pais defensor) {
        // pedir un dato al jugador y devolverlo
        return 0;
    }

    public void atacar(Pais atacante, Pais defensor, int cantFichas){
        if( !paisesConquistados.contains(atacante) ) throw new JugadorNoTienePais(String.format("El jugador no puede atacar con el pais %s porque no es suyo",atacante.nombre()));
        if( paisesConquistados.contains(defensor) ) throw new PaisDelMismoPropietarioNoPuedeSerAtacado(String.format("El jugador no puede atacar a el pais %s porque ya es suyo",defensor.nombre()));

        atacante.atacar(defensor,cantFichas);
    }
    /*
    public int agregarFichas( int cantFichas ){
        Pais receptorDeFichas;
        while(cantFichas>0){
            receptorDeFichas=new Pais("input","input",null);//input
            if( paisesConquistados.contains(receptorDeFichas) ){
                receptorDeFichas.agregarFichas(1);
                cantFichas--;
            }else{
                //muestra algo o no hace nada ?
            }
        }
        return 0;
    } No habría que crear un país, hay que acceder a los que tiene conquistados el jugador
    La idea de "agregarFichas" como lo pensamos, es que se le permita el jugador agregar las fichas
    que quiera en los paises que ya tiene conquistados.

    Nico Z - Me lo imagine asi :
        El jugador clickea un pais en el mapa, si es suyo le agrega una ficha.
        Repetir hasta no tener fichas.
    En la parte donde "crea" un pais, se supone que lo recibe como el input ( Hay que ver como seria con la UI )
     */

    public void agregarFichas(int cantFichas){
        /* Algo así tendŕia que ser creemos
        while(cantFichas > 0) {
            String paisElegido = pedir pais
            int cantFichasElegidas = pedir cant de fichas
            if(paisesConquistados.containsKey(paisElegido)){
                if(cantFichasElegidas <= cantFichas){
                    paisesConquistados.get(paisElegido).agregarFichas(cantFichasElegidas);
                    cantFichas = cantFichas - cantFichasElegidas;
                }
                else{
                    error
                }
            }
            else{
                error
            }
        }
        */
        /* Z - Parece parecido a lo que plantee salvo que
            recibe el nombre del pais en vez del objeto
            pide la cant de fichas
        */
        // Hardcodeadísimo para probar
        for (Pais pais : paisesConquistados) {
            pais.agregarFichas(1);
            cantFichas = cantFichas - 1;
            if (cantFichas == 0) break;
        }
    }

    public int obtenerCantidadPaises(){
        return paisesConquistados.size();
    }

    public void turnoAtaque(){
        //TODO - Marce & Gabo
    }

    public void turnoReagrupacion(){
        //TODO - Marce & Gabo
    }

    public void turnoColocacion(){
        //TODO - Z
    }

    public int canjearTarjetas(){
        //TODO - Z
        return 0;
    }

    public int realizarCanje(){
        //TODO - Z
        return 0;
    }

    public int fichasPorConquista(){
        //TODO - Z
        return 0;
    }
}
