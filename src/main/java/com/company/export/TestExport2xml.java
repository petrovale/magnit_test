package com.company.export;

import com.company.util.XsltProcessor;

import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class TestExport2xml {

    private String configFile;

    public void setFile(String configFile) {
        this.configFile = configFile;
    }

    public void save() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream xslInputStream = classLoader.getResource("2.xsl").openStream();
             InputStream xmlInputStream = new File("xml\\1.xml").toURI().toURL().openStream()) {

            XsltProcessor processor = new XsltProcessor(xslInputStream);

            StreamResult sr = new StreamResult(new File(configFile));

            processor.transform(xmlInputStream, sr);
        }
    }
}
