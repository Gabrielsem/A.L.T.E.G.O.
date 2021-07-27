module edu.fiuba.algo3 {
    requires javafx.controls;
    requires com.google.gson;
    requires javafx.fxml;

    opens edu.fiuba.algo3.controladores to javafx.fxml;
    exports edu.fiuba.algo3.errores;
    exports edu.fiuba.algo3.modelo;
    exports edu.fiuba.algo3.controladores;
    exports edu.fiuba.algo3;
    opens edu.fiuba.algo3 to javafx.fxml;
    exports edu.fiuba.algo3.controladores.fases;
    opens edu.fiuba.algo3.controladores.fases to javafx.fxml;
    exports edu.fiuba.algo3.vista;
    opens edu.fiuba.algo3.vista to javafx.fxml;
}