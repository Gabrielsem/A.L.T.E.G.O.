package edu.fiuba.algo3.util;

import edu.fiuba.algo3.App;
import javafx.scene.media.Media;

import java.io.*;

public class FileLoader {
    public static InputStream resourceInputStream( String resource ) throws IOException {
        InputStream is = App.class.getResourceAsStream("/"+ resource);
        return is;
    }

    public static BufferedReader resourceReader( String resource ) throws IOException {
        InputStream is = App.class.getResourceAsStream("/"+ resource);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        return bf;
    }

    public static Media sound( String rutaLocal) {
        String url = "/sonidos/"+rutaLocal;
        try {
            return new Media(App.class.getResource(url).toExternalForm());
        } catch (Throwable ignored){ System.out.println("\u001B[32m FileLoader: F1 \u001B[0m"); }
        try {
            return new Media(App.class.getResource(url).toURI().toString());
        } catch (Throwable ignored){ System.out.print("\u001B[32m F2 \u001B[0m"); }
        try {
            return new Media(App.class.getResource(url).toURI().toURL().toString());
        } catch (Throwable ignored){ System.out.print("\u001B[32m F3 \u001B[0m"); }
        try {
            return new Media(App.class.getResource(url).toURI().toURL().toExternalForm());
        } catch (Throwable ignored){ System.out.print("\u001B[32m F4 \u001B[0m"); }
        throw new RuntimeException("FileLoader.sound ERROR");
    }
}
