module edu.fiuba.algo3 {
    requires javafx.controls;
    requires com.google.gson;
    requires javafx.fxml;

    opens edu.fiuba.algo3.UI to javafx.fxml;
    exports edu.fiuba.algo3.errores;
    exports edu.fiuba.algo3.modelo;
    exports edu.fiuba.algo3.UI;
}