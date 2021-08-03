package edu.fiuba.algo3.interfaz;

import javafx.scene.Scene;
import javafx.scene.control.Slider;

public class VistaSlider {
    Slider slider;

    public VistaSlider(Slider slider) {
        this.slider = slider;
        ocultar();
    }

    // Muestra el slider si el max es mas de uno, sino lo deja oculto pero con valor 1
    public void mostrar(int max) {
        if (max > 1) {
            slider.setMax(max);
            slider.setManaged(true);
            slider.setVisible(true);
        } else {
            ocultar();
        }

        slider.adjustValue(1);
    }

    public void ocultar() {
        slider.setManaged(false);
        slider.setVisible(false);
    }

    public int getValue() {
        return (int) slider.getValue();
    }
}
