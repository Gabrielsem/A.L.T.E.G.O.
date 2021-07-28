module edu.fiuba.algo3 {
    requires javafx.controls;
    requires com.google.gson;
    requires javafx.fxml;

    exports edu.fiuba.algo3.errores;
    exports edu.fiuba.algo3.modelo;
    exports edu.fiuba.algo3;
    opens edu.fiuba.algo3 to javafx.fxml;
    exports edu.fiuba.algo3.interfaz;
    opens edu.fiuba.algo3.interfaz to javafx.fxml;
}