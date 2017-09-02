package com.company.util;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XsltProcessor {
    private static TransformerFactory FACTORY = TransformerFactory.newInstance();
    private final Transformer xformer;

    public XsltProcessor(InputStream xslInputStream) {
        this(new BufferedReader(new InputStreamReader(xslInputStream, StandardCharsets.UTF_8)));
    }

    public XsltProcessor(Reader xslReader) {
        try {
            Templates template = FACTORY.newTemplates(new StreamSource(xslReader));
            xformer = template.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new IllegalStateException("XSLT transformer creation failed: " + e.toString(), e);
        }
    }

    public void transform(InputStream xmlInputStream, StreamResult result) throws TransformerException {
        xformer.transform(new StreamSource(new BufferedReader(new InputStreamReader(xmlInputStream, StandardCharsets.UTF_8))), result);
    }
}
