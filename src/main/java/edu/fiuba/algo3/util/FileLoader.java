package edu.fiuba.algo3.util;

import edu.fiuba.algo3.App;
import javafx.scene.media.Media;

import java.io.*;
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

        /*final String ruta = "/sonidos/"+soundFile;
        System.out.println("RUTA: "+ruta);

        URL url = App.class.getResource(ruta);
        System.out.println("URL: "+url);

        URI uri = null;
        try {
            uri = url.toURI();
            System.out.println("uri success");
        } catch (URISyntaxException e) { System.out.println("URI ERROR"); return null; }
        System.out.println("URI: "+uri);

        final String file = uri.toString();
        System.out.println("FILE: "+file);

        Media media = new Media(file);
        System.out.println("MEDIA: "+media);

        return media;*/
        String url = "src/main/resources/sonidos/"+soundFile;
        return new Media(new File(url).toURI().toString());
    }
}
