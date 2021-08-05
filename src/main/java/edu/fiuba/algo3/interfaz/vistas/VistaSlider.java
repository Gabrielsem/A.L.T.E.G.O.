package edu.fiuba.algo3.interfaz.vistas;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class VistaSlider implements ChangeListener<Number> {
    private Slider slider;
    private Button botonSiguiente;
    private String textoBoton = "";

    public VistaSlider(Slider slider, Button botonSiguiente) {
        this.slider = slider;
        this.botonSiguiente = botonSiguiente;
        ocultar();

        slider.valueProperty().addListener(this);
    }

    // Muestra el slider si el max es mas de uno, sino lo deja oculto pero con valor 1
    public void mostrar(int max) {
        textoBoton = botonSiguiente.getText();
        if (max > 1) {
            slider.setMax(max);
            slider.setManaged(true);
            slider.setVisible(true);
            slider.setMajorTickUnit(max - 1);
            slider.setMinorTickCount(max - 2);
        } else {
            ocultar();
        }

        slider.adjustValue(1);
        changed(slider.valueProperty(), 0, 1);
    }

    public void ocultar() {
        slider.setManaged(false);
        slider.setVisible(false);
    }

    public int getValue() {
        return (int) slider.getValue();
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        String agregado = String.format(" con %d ficha", newValue.intValue());
        botonSiguiente.setText(textoBoton + agregado + (newValue.intValue() == 1 ? "" : "s"));
    }
}
