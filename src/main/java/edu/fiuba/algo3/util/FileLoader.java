package edu.fiuba.algo3.util;

import edu.fiuba.algo3.App;
import javafx.scene.media.Media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FileLoader {
    public static BufferedReader resourceReader( String resource ) throws IOException {
        //BufferedReader bReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/"+ rutaArchivo)));
        InputStream is = App.class.getResourceAsStream("/"+ resource);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        return bf;
    }

    public static Media sound( String soundFile) {

        final String ruta = "/sonidos/"+soundFile;
        URL url = App.class.getResource(ruta);
        URI uri = null;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) { System.out.println("URI ERROR"); return null; }

        final String file = uri.toString();

        return new Media(file);
    }
}
