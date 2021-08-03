package edu.fiuba.algo3.interfaz.fases.reagrupacion;

import edu.fiuba.algo3.interfaz.fases.Fase;
import edu.fiuba.algo3.interfaz.fases.FaseConSeleccionables;
import edu.fiuba.algo3.interfaz.fases.ataque.AtaqueAtacante;
import edu.fiuba.algo3.interfaz.fases.colocacion.Colocacion;
import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.Pais;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ReagrupacionOrigen extends FaseConSeleccionables {
    private Juego juego;
    private Label instruccion;
    private Button botonSiguiente;

    public ReagrupacionOrigen(Scene scene, Juego juego) {
        super.scene = scene;
        this.juego = juego;
        instruccion = (Label) scene.lookup("#instruccion");
        botonSiguiente = (Button) scene.lookup("#botonSiguiente");
        iniciar();
    }

    public void iniciar() {
        instruccion.setText("Clickea el país desde el que quieras mover fichas");
        botonSiguiente.setVisible(true);
        botonSiguiente.setText("Pasar turno");
        super.setSeleccionables(juego.turnoActual().paisesParaReagrupar());
        juego.turnoActual().activarTarjetas();
    }

    @Override
    public Fase tocoBoton(Button unBoton) {
        if (juego.turnosCompletados()) {
            return new Colocacion(scene, juego);
        }

        juego.siguienteTurno();
        return new AtaqueAtacante(scene, juego);
    }

    @Override
    protected Fase tocoSeleccionable(Node nodoPais) {
        return new ReagrupacionDestino(scene, juego, (Pais) nodoPais.getUserData());
    }
}
