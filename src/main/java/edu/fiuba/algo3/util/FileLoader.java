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
        System.out.print("\n\u001B[34m FileLoader: F1 \u001B[0m");
        String url = "/sonidos/"+rutaLocal;
        String uri = "URI ERROR X";
        try {
            uri = "URI ERROR";
            uri = App.class.getResource(url).toExternalForm();
            return new Media(uri);
        } catch (Throwable ignored){ System.out.println("\n\u001B[36m FileLoader: FAILURE 1 : \u001B[34m"+uri+" \u001B[0m"); }
        try {
            uri = "URI ERROR";
            uri = App.class.getResource(url).toURI().toString();
            return new Media(uri);
        } catch (Throwable ignored){ System.out.println("\n\u001B[36m FileLoader: FAILURE 2 : \u001B[34m"+uri+" \u001B[0m"); }
        try {
            uri = "URI ERROR";
            uri = App.class.getResource(url).toURI().toURL().toString();
            return new Media(uri);
        } catch (Throwable ignored){ System.out.println("\n\u001B[36m FileLoader: FAILURE 3 : \u001B[34m"+uri+" \u001B[0m"); }
        try {
            uri = "URI ERROR";
            uri = App.class.getResource(url).toURI().toURL().toExternalForm();
            return new Media(uri);
        } catch (Throwable ignored){ System.out.println("\n\u001B[36m FileLoader: FAILURE 4 : \u001B[34m"+uri+" \u001B[0m"); }
        throw new java.lang.UnsatisfiedLinkError("FileLoader.sound ERROR");
    }
}
