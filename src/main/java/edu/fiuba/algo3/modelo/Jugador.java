package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.JugadorNoTienePais;
import edu.fiuba.algo3.errores.PaisDelMismoPropietarioNoPuedeSerAtacado;
import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;

import java.util.*;

import static java.lang.Math.max;

public class Jugador {
    //Esto estaría bueno que sea un diccionario con clave -> nombrePais, valor -> Pais para poder buscarlo
    // no deberia ser privado todo esto?

    HashMap<String, Pais> paisesConquistados = new HashMap<>();
    ArrayList<Tarjeta> tarjetas = new ArrayList<>();
    int numero;
    Juego juego;
    int canjesRealizados = 0;

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
        return 3;
    }

    public void atacar(Pais atacante, Pais defensor, int cantFichas){
        if( !paisesConquistados.containsValue(atacante) ) throw new JugadorNoTienePais(String.format("El jugador no puede atacar con el pais %s porque no es suyo",atacante.nombre()));
        if( paisesConquistados.containsValue(defensor) ) throw new PaisDelMismoPropietarioNoPuedeSerAtacado(String.format("El jugador no puede atacar a el pais %s porque ya es suyo",defensor.nombre()));
        atacante.atacar(defensor,cantFichas);
    }

    public void agregarFichas(int cantFichas){
        Scanner entrada = new Scanner(System.in);
        String paisReceptor;
        int cantFichasElegidas;

        while(cantFichas > 0) {
            System.out.println("introduzca el pais que recibe las fichas: ");
            paisReceptor = entrada.nextLine();
            System.out.printf("introduzca la cantidad de fichas para agregar a %s: %n", paisReceptor);
            cantFichasElegidas = Integer.parseInt(entrada.nextLine());
            if(!paisesConquistados.containsKey(paisReceptor)) throw new JugadorNoTienePais(String.format("El jugador no puede agregar fichas al pais %s porque no es suyo",paisReceptor));
            //Por ahí habría que hacer otro error para cuando la cantidad es inválida, (x ej menor a 0)
            if(cantFichasElegidas > cantFichas || cantFichasElegidas < 0) throw new JugadorNoTieneFichasSuficientes(String.format("El jugador no puede agregar %d fichas al pais %s porque no tiene suficientes",cantFichasElegidas,paisReceptor));

            paisesConquistados.get(paisReceptor).agregarFichas(cantFichasElegidas);
            cantFichas = cantFichas - cantFichasElegidas;
        }
    }

    public int obtenerCantidadPaises(){
        return paisesConquistados.size();
    }

    public void recibirTarjeta( Tarjeta unaTarjeta ) {
        tarjetas.add( unaTarjeta );
    }

    public void turnoAtaque(){
        int cantInicialPaises = paisesConquistados.size();
        Scanner entrada = new Scanner(System.in);
        String nombreAtacante;
        String nombreDefensor;
        int cantFichas;

        while (true)  {
            System.out.println("introduzca cantidad de fichas para atacar: ");
            cantFichas = Integer.parseInt(entrada.nextLine());
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

        if (paisesConquistados.size() > cantInicialPaises) {
            recibirTarjeta( juego.pedirTarjeta() );
        }
    }

    public void turnoReagrupacion(){

        Scanner entrada = new Scanner(System.in);
        String nombreOrigen;
        String nombreDestino;
        int cantFichas;

        while (true) {
            System.out.println("introduzca cantidad de fichas a mover: ");
            cantFichas = Integer.parseInt(entrada.nextLine());
            if(cantFichas == 0) break;
            System.out.println("introduzca el origen: ");
            nombreOrigen = entrada.nextLine();
            System.out.println("introduzca el destino: ");
            nombreDestino = entrada.nextLine();

            if( !paisesConquistados.containsKey(nombreOrigen) ) throw new JugadorNoTienePais(String.format("El jugador no mover fichas desde el pais %s porque no es suyo",nombreOrigen));
            if( !paisesConquistados.containsKey(nombreDestino) ) throw new JugadorNoTienePais(String.format("El jugador no puede mover fichas al pais %s porque no es suyo",nombreDestino));

            Pais Origen = paisesConquistados.get(nombreOrigen);
            Pais Destino = paisesConquistados.get(nombreDestino);

            Origen.reagruparA(Destino, cantFichas);
        }
    }

    public void turnoColocacion(){
        int fichas = 0;

        fichas += canjearTarjetas();

        fichas += fichasPorConquista();
        agregarFichas( fichas );
    }

    public int canjearTarjetas(){
        for( Tarjeta tarjeta : tarjetas )
            if( paisesConquistados.containsKey( tarjeta.pais() ) )
                tarjeta.activar();

        ArrayList<Tarjeta> grupoCanjeable = Tarjeta.grupoCanjeable( tarjetas );

        if( Objects.nonNull(grupoCanjeable) ){
            tarjetas.removeAll( grupoCanjeable );
            juego.devolverTarjetas( grupoCanjeable );
            canjesRealizados++;
            return Tarjeta.cantidadFichasCanje(canjesRealizados - 1);
        }

        return 0;
    }

    public int fichasPorConquista(){
        int fichas = 0;

        fichas += max( paisesConquistados.size()/2 ,3);

        fichas += juego.fichasSegunContinentes(paisesConquistados.keySet());

        return fichas;
    }
}
