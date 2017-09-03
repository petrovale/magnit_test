package com.company.export;

import com.company.util.XsltProcessor;

import javax.xml.transform.stream.StreamResult;
import java.io.*;

import static com.company.Main.firstDestinationFilename;

public class SecondXmlWriter {
    private static final String nameXslFile = "2.xsl";

    private String configFile;

    public void setFile(String configFile) {
        this.configFile = configFile;
    }

    public void save() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream xslInputStream = classLoader.getResource(nameXslFile).openStream();
             InputStream xmlInputStream = new File(firstDestinationFilename).toURI().toURL().openStream()) {

            XsltProcessor processor = new XsltProcessor(xslInputStream);

            StreamResult sr = new StreamResult(new File(configFile));

            processor.transform(xmlInputStream, sr);
        }
    }
}
