package edu.fiuba.algo3.util;

import edu.fiuba.algo3.App;
import javafx.scene.media.Media;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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

    public static Media sound( String soundFile) {

        String url = "src/main/resources/sonidos/"+soundFile;
        return new Media(new File(url).toURI().toString());
    }
}
