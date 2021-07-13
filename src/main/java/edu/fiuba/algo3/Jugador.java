package edu.fiuba.algo3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Jugador {
    //Esto estaría bueno que sea un diccionario con clave -> nombrePais, valor -> Pais para poder buscarlo
    HashMap<String, Pais> paisesConquistados = new HashMap<String, Pais>();
    HashSet<Tarjeta> tarjetas = new HashSet<Tarjeta>();
    int numero;
    Juego juego;

    public Jugador(){}

    public Jugador(int numeroDeJugador, Juego claseJuego){
        numero = numeroDeJugador;
        juego = claseJuego;
    }

    public void desocupar(Pais unPais) {
        paisesConquistados.remove(unPais.nombre());
    }

    public void ocupar(Pais unPais){
        paisesConquistados.put(unPais.nombre(),unPais);
    }

    public int invadir(Pais atacante, Pais defensor) {
        // pedir un dato al jugador y devolverlo
        return 0;
    }

    public void atacar(Pais atacante, Pais defensor, int cantFichas){
        if( !paisesConquistados.containsValue(atacante) ) throw new JugadorNoTienePais(String.format("El jugador no puede atacar con el pais %s porque no es suyo",atacante.nombre()));
        if( paisesConquistados.containsValue(defensor) ) throw new PaisDelMismoPropietarioNoPuedeSerAtacado(String.format("El jugador no puede atacar a el pais %s porque ya es suyo",defensor.nombre()));
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
        for (String nombrePais : paisesConquistados.keySet()) {
            paisesConquistados.get(nombrePais).agregarFichas(1);
            cantFichas = cantFichas - 1;
            if (cantFichas == 0) break;
        }
    }

    public int obtenerCantidadPaises(){
        return paisesConquistados.size();
    }



    public void turnoAtaque(){
        int cantInicialPaises = paisesConquistados.size();
        Scanner entrada = new Scanner(System.in);
        String nombreAtacante;
        String nombreDefensor;
        int cantFichas;

        while (true)  {
            System.out.println("introduzca cantidad de fichas para atacar: ");
            cantFichas = entrada.nextInt();
            if(cantFichas == 0) break;
            System.out.println("introduzca atacante: ");
            nombreAtacante = entrada.nextLine();
            System.out.println("introduzca defensor: ");
            nombreDefensor = entrada.nextLine();

            if( !paisesConquistados.containsKey(nombreAtacante) ) throw new JugadorNoTienePais(String.format("El jugador no puede atacar con el pais %s porque no es suyo",nombreAtacante));
            Pais atacante = paisesConquistados.get(nombreAtacante);
            Pais defensor = juego.obtenerPais(nombreDefensor);
            atacante.atacar(defensor, cantFichas);
        }

        if(paisesConquistados.size() > cantInicialPaises) {
            tarjetas.add(juego.pedirTarjeta());
        }
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
