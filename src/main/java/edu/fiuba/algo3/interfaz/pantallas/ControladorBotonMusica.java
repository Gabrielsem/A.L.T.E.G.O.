package edu.fiuba.algo3.interfaz.pantallas;

import edu.fiuba.algo3.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class ControladorBotonMusica implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        ToggleButton botonMusica = (ToggleButton) actionEvent.getSource();

        if (botonMusica.isSelected()) App.detenerCancion();
        else App.reproducirCancion();
    }
}
