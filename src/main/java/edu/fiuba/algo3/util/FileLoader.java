package edu.fiuba.algo3.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.fiuba.algo3.App;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.io.*;
import java.util.Objects;

public class FileLoader {
    public static InputStream resourceInputStream( String resource ) throws IOException {
        InputStream is = App.class.getResourceAsStream("/"+ resource);
        return is;
    }

    public static BufferedReader resourceReader( String resource ) throws IOException {
        //BufferedReader bReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/"+ rutaArchivo)));
        InputStream is = App.class.getResourceAsStream("/"+ resource);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        return bf;
    }

    public static Media sound( String rutaLocal) {
        return new Media(Objects.requireNonNull(Objects.requireNonNull(App.class.getResource("/sonidos/" + rutaLocal)).toExternalForm()));
    }

    public static ImageView imagen(String rutaLocal) {
        return new ImageView(Objects.requireNonNull(Objects.requireNonNull(App.class.getResource("/imagenes/" + rutaLocal)).toExternalForm()));
    }
}
