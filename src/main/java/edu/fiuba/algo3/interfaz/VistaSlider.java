package edu.fiuba.algo3.interfaz;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class VistaSlider {
    Slider slider;
    Button botonSiguiente;

    public VistaSlider(Slider slider, Button botonSiguiente) {
        this.slider = slider;
        this.botonSiguiente = botonSiguiente;
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
            botonSiguiente.setText(botonSiguiente.getText() + " con 1 ficha");
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
