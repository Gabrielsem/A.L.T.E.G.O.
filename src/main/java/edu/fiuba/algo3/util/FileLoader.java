package edu.fiuba.algo3.util;

import edu.fiuba.algo3.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader {
    public static BufferedReader resourceReader( String resource ) throws IOException {
        //BufferedReader bReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/"+ rutaArchivo)));
        InputStream is = App.class.getResourceAsStream("/"+ resource);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        return bf;
    }
}
