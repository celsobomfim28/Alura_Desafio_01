package org.celso.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Manipulador {
    public Properties getProp() throws IOException, URISyntaxException {
        Properties props = new Properties();
        String fileName = "variaveis.properties";

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        File file = new File(resource.toURI());

        FileInputStream fileis = new FileInputStream(file);
        props.load(fileis);
        return props;
    }
}
