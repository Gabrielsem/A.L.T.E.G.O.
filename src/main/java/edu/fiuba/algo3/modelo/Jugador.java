package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.JugadorNoTienePais;
import edu.fiuba.algo3.errores.PaisDelMismoPropietarioNoPuedeSerAtacado;
import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.PaisNoEsDeEsteJugador;

import java.util.*;

import static java.lang.Math.max;

public class Jugador {

    private HashMap<String, Pais> paisesConquistados = new HashMap<>();
    private ArrayList<Tarjeta> tarjetas = new ArrayList<>();
    private int numero;
    private Juego juego;
    private int canjesRealizados = 0;
    private int fichasDisponibles = 0;
    static final private int minFichas = 3;
    private boolean debeRecibirTarjeta = false;
    private Objetivo objetivoComun;
    private Objetivo objetivoSecreto;

    public Jugador(int numeroDeJugador, Juego claseJuego){
        numero = numeroDeJugador;
        juego = claseJuego;
    }

    public boolean tienePais(String nombrePais) {
        return paisesConquistados.containsKey(nombrePais);
    }

    public void desocupar(Pais unPais) {
        paisesConquistados.remove(unPais.nombre());
    }

    public void ocupar(Pais unPais){
        paisesConquistados.put(unPais.nombre(),unPais);
        if (debeRecibirTarjeta) {
            recibirTarjeta(juego.pedirTarjeta());//TODO: Actualizar uml con esta secuencia (ver este bool en codigo)
            debeRecibirTarjeta = false;
        }
    }


    public void atacar(Pais atacante, Pais defensor, int cantFichas){
        if( !paisesConquistados.containsValue(atacante) ) throw new JugadorNoTienePais(String.format("El jugador no puede atacar con el pais %s porque no es suyo",atacante.nombre()));
        if( paisesConquistados.containsValue(defensor) ) throw new PaisDelMismoPropietarioNoPuedeSerAtacado(String.format("El jugador no puede atacar a el pais %s porque ya es suyo",defensor.nombre()));
        atacante.atacar(defensor,cantFichas);
    }

    public void darFichas(int cantidadFichas) {
        fichasDisponibles += cantidadFichas;
    }

    public boolean tieneFichas() {
        return fichasDisponibles != 0;
    }

    public void ponerFichas(String nombrePais, int cantFichas){
        if (!paisesConquistados.containsKey(nombrePais)) {
            throw new PaisNoEsDeEsteJugador(String.format("El país %s no es del jugador %d", nombrePais, numero));
        }
        if (fichasDisponibles <= 0) {
            throw new JugadorNoTieneFichasSuficientes(String.format("El jugador %d no tiene mas fichas", numero));
        }
        paisesConquistados.get(nombrePais).agregarFichas(cantFichas);
        fichasDisponibles -= cantFichas;
    }

    public void prepararAtaques() {
        debeRecibirTarjeta = true;
    }

    public int obtenerCantidadPaises(){
        return paisesConquistados.size();
    }

    public void recibirTarjeta( Tarjeta unaTarjeta ) {
        tarjetas.add( unaTarjeta );
    }

    public void reagrupar(String origen, String destino, int cantFichas) {
        if( !paisesConquistados.containsKey(origen) )
            throw new JugadorNoTienePais(String.format("El jugador no mover fichas desde el pais %s porque no es suyo",origen));
        if( !paisesConquistados.containsKey(destino) )
            throw new JugadorNoTienePais(String.format("El jugador no puede mover fichas al pais %s porque no es suyo",destino));


        paisesConquistados.get(origen).reagruparA(paisesConquistados.get(destino), cantFichas);
    }

    public void actualizarFichas() {
        fichasDisponibles += fichasPorConquista() + canjearTarjetas(); //FIXME: estos metodos no deberian ser privados?
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

    public void asignarObjetivos(Objetivo unObjetivoComun, Objetivo unObjetivoSecreto){
        this.objetivoComun = unObjetivoComun;
        this.objetivoSecreto = unObjetivoSecreto;
    }

    public boolean gane() {
        return (this.objetivoComun.gano(this) || this.objetivoSecreto.gano(this));
    }

    public String descripcionObjetivos() {
        return this.objetivoComun.descripcion() + "\n" + this.objetivoSecreto.descripcion();
    }

    public int numero() {
        return numero;
    }

    public int cantidadFichas() {
        return fichasDisponibles;
    }

    public Collection<String> paisesAtacables(Collection<String> listaPaises) {
        listaPaises.removeAll(paisesConquistados.keySet());
        return listaPaises;
    } //TODO: probar esto

    public Collection<String> paisesConquistados() {
        return  paisesConquistados.keySet();
    }
}
