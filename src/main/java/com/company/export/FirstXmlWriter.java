package com.company.export;

import com.company.model.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class FirstXmlWriter {
    public static final String entries = "entries";
    public static final String entry = "entry";
    public static final String field = "field";
    public static final String fieldValue = "значение поля ";
    public static final String newLine = "\n";

    public void writeToXml(Path path, List<Test> tests) throws IOException, XMLStreamException {
        try (OutputStream os = Files.newOutputStream(path)) {
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter writer = null;
            try {
                writer = outputFactory.createXMLStreamWriter(os, "utf-8");
                writeTestsElem(writer, tests);
            } finally {
                if (writer != null)
                    writer.close();
            }
        }
    }

    private void writeTestsElem(XMLStreamWriter writer, List<Test> tests) throws XMLStreamException {
        writer.writeStartDocument("utf-8", "1.0");
        writer.writeComment("Describes list of tests");

        writer.writeCharacters(newLine);

        writer.writeStartElement(entries);

        writer.writeCharacters(newLine);

        for (Test test : tests)
            writeTestElem(writer, test);
        writer.writeEndElement();

        writer.writeEndDocument();
    }

    private void writeTestElem(XMLStreamWriter writer, Test test) throws XMLStreamException {
        writer.writeStartElement(entry);
        writer.writeCharacters(newLine);

        writer.writeStartElement(field);
        writer.writeCharacters(String.valueOf(fieldValue + test.getField()));
        writer.writeEndElement();
        writer.writeCharacters(newLine);

        writer.writeEndElement();
        writer.writeCharacters(newLine);
    }
}
