package edu.fiuba.algo3.UI;

import edu.fiuba.algo3.modelo.Juego;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    String version = "0.3";
    int cantJugadores = 2;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("TEG "+version);
        scene = new Scene(loadFXML("menu"));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static Scene getScene(){
        return scene;
    }

}