package edu.fiuba.algo3;

import edu.fiuba.algo3.interfaz.pantallas.ControladorPantallaInicial;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static MediaPlayer reproductor;
    String version = "1.2.1";

    private static Stage appStage;
    private static Popup popup;
    private static Popup popupWindow;
    private static VBox popupTray;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("TEG "+version);
        Scene scene = new Scene(new Label(""), 854, 480);
        stage.setScene(scene);
        inicializarEscena(stage, scene);

        appStage = stage;
        popup = new Popup();
        popup.setAutoHide(true);
        popupTray = new VBox();
        popup.getContent().add( popupTray );

        new ControladorPantallaInicial(scene);
    }

    private void inicializarEscena(Stage stage, Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles-dark.css")).toExternalForm());
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        stage.setWidth(1200);
        stage.setHeight(800);
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void notificacion(String mensaje){

        if( Objects.isNull(appStage) ) return;

        HBox msjBox = new HBox( new Label(mensaje) );
        msjBox.getStyleClass().add("popupMessage");
        msjBox.setPadding( new Insets(10) );
        popupTray.getChildren().clear();// Para stackear: Reemplazar x una llamada que elimine
        popupTray.getChildren().add( msjBox );
        popup.show(appStage,appStage.getX()+30,appStage.getY()+100);

    }

    public static void popUpWindow( VBox window ) {

        if( Objects.isNull(appStage) ) return;

        popupWindow = new Popup();
        popupWindow.setAutoHide(true);
        popupWindow.getContent().add(window);

        double offsetX = appStage.getX() + 15 ;
        double offsetY = appStage.getY() + appStage.getHeight() - 223;

        popupWindow.show(appStage,offsetX,offsetY);
    }

    public static void clearPopUps(){
        popup.hide();
        popup.getContent().clear();
        if(popupWindow != null){
            popupWindow.hide();
            popupWindow.getContent().clear();
        }
    }

    public static MediaPlayer sonido(String sonido, double volumen) {

        String url = "src/main/resources/sonidos/"+sonido+".mp3";
        try {
            Media sound = new Media(new File(url).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            mediaPlayer.setVolume(volumen);
            return mediaPlayer;
        } catch (Exception e ) {
            System.out.println("Cannot play: "+url);
        }
        return null;
    }

    public static void cancion(String cancion) {

        //Fade out de cancion previa
        if (App.reproductor != null) (new Timeline(new KeyFrame(Duration.seconds(5), new KeyValue(App.reproductor.volumeProperty(), 0)))).play();

        MediaPlayer reproductor = sonido(cancion, 0.2);

        if (reproductor == null) return;

        reproductor.setCycleCount(MediaPlayer.INDEFINITE);
        reproductor.pause();

        App.reproductor = reproductor;
    }

    public static void detenerCancion() { App.reproductor.pause(); }

    public static void reproducirCancion() { App.reproductor.play(); }

    public static boolean hayMusica() { return App.reproductor.getStatus() == MediaPlayer.Status.PLAYING; }

    public static void cambiarVista(Scene scene, ToggleButton botonCambiarVista) {
        scene.getStylesheets().clear();
        if (botonCambiarVista.isSelected()){
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles-clear.css")).toExternalForm());
        } else {
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles-dark.css")).toExternalForm());
        }
    }

    public static void loadFXML(String nombre, Object controlador) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(nombre));
        fxmlLoader.setController(controlador);
        appStage.getScene().setRoot(fxmlLoader.load());
    }
}