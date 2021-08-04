package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.errores.JugadorNoTieneFichasSuficientes;
import edu.fiuba.algo3.errores.JugadorNoTienePais;
import edu.fiuba.algo3.errores.PaisDelMismoPropietarioNoPuedeSerAtacado;
import edu.fiuba.algo3.interfaz.VistaJugador;
import edu.fiuba.algo3.modelo.objetivos.Objetivo;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class Jugador extends Observable {

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

    public void desocupar(Pais unPais) {
        paisesConquistados.remove(unPais.nombre());
        notificarObservadores();
    }

    public void ocupar(Pais unPais){
        paisesConquistados.put(unPais.nombre(),unPais);
        if (debeRecibirTarjeta) {
            recibirTarjeta(juego.pedirTarjeta());//TODO: Actualizar uml con esta secuencia (ver este bool en codigo)
            debeRecibirTarjeta = false;
        }
        notificarObservadores();
    }


    public void atacar(Pais atacante, Pais defensor, int cantFichas){
        if( !paisesConquistados.containsValue(atacante) ) throw new JugadorNoTienePais(String.format("El jugador no puede atacar con el pais %s porque no es suyo",atacante.nombre()));
        if( paisesConquistados.containsValue(defensor) ) throw new PaisDelMismoPropietarioNoPuedeSerAtacado(String.format("El jugador no puede atacar a el pais %s porque ya es suyo",defensor.nombre()));
        atacante.atacar(defensor, cantFichas);
    }

    public void darFichas(int cantidadFichas) {
        fichasDisponibles += cantidadFichas;
    }

    public boolean tieneFichas() {
        return fichasDisponibles != 0;
    }

    public void ponerFichas(String nombrePais, int cantFichas){
        if (!paisesConquistados.containsKey(nombrePais)) {
            throw new JugadorNoTienePais(String.format("El país %s no es del jugador %d", nombrePais, numero));
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
        setChanged();notifyObservers( VistaJugador.Aviso.nuevaTarjeta.nuevo(unaTarjeta) );
    }

    public void notificarObservadores() { setChanged();notifyObservers(); }

    public void reagrupar(String origen, String destino, int cantFichas) {

        if( !paisesConquistados.containsKey(origen) )
            throw new JugadorNoTienePais(String.format("El jugador no mover fichas desde el pais %s porque no es suyo",origen));
        if( !paisesConquistados.containsKey(destino) )
            throw new JugadorNoTienePais(String.format("El jugador no puede mover fichas al pais %s porque no es suyo",destino));

        paisesConquistados.get(origen).reagruparA(paisesConquistados.get(destino), cantFichas);
    }

    public void actualizarFichas() {
        fichasDisponibles += fichasPorConquista() + canjearTarjetas();
    }

    public void activarTarjetas() {
        for( Tarjeta tarjeta : tarjetas )
            if( paisesConquistados.containsKey( tarjeta.pais() ) )
                tarjeta.activar();
    }

    public int canjearTarjetas(){//FIXME: estos metodos no deberian ser privados?
        ArrayList<Tarjeta> grupoCanjeable = Tarjeta.grupoCanjeable( tarjetas );

        if( Objects.nonNull(grupoCanjeable) ){
            tarjetas.removeAll( grupoCanjeable );
            juego.devolverTarjetas( grupoCanjeable );
            canjesRealizados++;
            setChanged();notifyObservers( VistaJugador.Aviso.canjeTarjetas.nuevo( grupoCanjeable ) );
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

    public Collection<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void asignarObjetivos(Objetivo unObjetivoComun, Objetivo unObjetivoSecreto){
        this.objetivoComun = unObjetivoComun;
        this.objetivoSecreto = unObjetivoSecreto;
    }

    public boolean gane() {
        return (this.objetivoComun.gano(this) || this.objetivoSecreto.gano(this));
    }

    public String descripcionObjetivos() {
        return "Objetivo Comun:\n" + this.objetivoComun.descripcion() + "\n\nObjetivo Secreto: \n" + this.objetivoSecreto.descripcion();
    }

    public int numero() {
        return numero;
    }

    public int cantidadFichas() {
        return fichasDisponibles;
    }

    public HashMap<String, Integer> paisesPorContinente() {
        HashMap<String, Integer> paises = new HashMap<>();
        for (Pais pais : paisesConquistados.values()) {
            String continente = pais.continente();
            paises.put(continente, paises.getOrDefault(continente, 0) +1);
        }
        return paises;
    }

    public Collection<String> paisesDisponiblesParaAtacar() {
        Set<String> disponiblesParaAtacar = new HashSet<>(paisesConquistados.keySet());
        disponiblesParaAtacar.removeIf(nombrePais->(paisesConquistados.get(nombrePais).cantidadFichas() <= 1));
        disponiblesParaAtacar.removeIf(nombrePais->(paisesConquistados.get(nombrePais).getPaisesAtacables().size() == 0));
        return disponiblesParaAtacar;
    }

    public boolean tienePais(String nombrePais) {
        return paisesConquistados.containsKey(nombrePais);
    }

    // Devuelve diccionario Pais
    public Collection<String> paisesParaReagrupar() {
        HashSet<String> disponiblesParaReagrupar = new HashSet<>();
        for (Pais p : paisesConquistados.values()) {
            if (p.cantidadFichas() <= 1) continue;
            if (paisesConquistados.keySet().stream().noneMatch(p::esVecino)) continue;
            disponiblesParaReagrupar.add(p.nombre());
        }

        return disponiblesParaReagrupar;
    }

    public Collection<String> reagrupablesDesde(String unPais) {
        if (!paisesConquistados.containsKey(unPais)) throw new RuntimeException("El jugador no tiene ese Pais");
        // TODO excepcion custom?
        Pais pais = paisesConquistados.get(unPais);

        return paisesConquistados.keySet().stream().filter(pais::esVecino).collect(Collectors.toSet());
    }

    public Collection<String> paises() {
        return paisesConquistados.keySet();
    }
}
