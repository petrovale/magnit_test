package com.company.export;

import com.company.util.XsltProcessor;

import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.company.Main.firstDestinationFilename;

public class SecondXmlWriter {
    private static final String nameXslFile = "2.xsl";

    public void writeToXml(String nameFileDestination) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream xslInputStream = classLoader.getResource(nameXslFile).openStream();
             InputStream xmlInputStream = Files.newInputStream(Paths.get(firstDestinationFilename))) {

            XsltProcessor processor = new XsltProcessor(xslInputStream);

            StreamResult sr = new StreamResult(new File(nameFileDestination));

            processor.transform(xmlInputStream, sr);
        }
    }
}
