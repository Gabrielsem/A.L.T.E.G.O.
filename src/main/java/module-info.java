module edu.fiuba.algo3 {
    requires javafx.controls;
    requires com.google.gson;
    requires javafx.fxml;
    requires javafx.media;

    exports edu.fiuba.algo3.errores;
    exports edu.fiuba.algo3.modelo;
    exports edu.fiuba.algo3;
    opens edu.fiuba.algo3 to javafx.fxml;
    exports edu.fiuba.algo3.modelo.objetivos;
    exports edu.fiuba.algo3.interfaz.pantallas;
    opens edu.fiuba.algo3.interfaz.pantallas to javafx.fxml;
    exports edu.fiuba.algo3.interfaz.vistas;
    opens edu.fiuba.algo3.interfaz.vistas to javafx.fxml;
}