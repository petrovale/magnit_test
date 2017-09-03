package com.company.export;


import com.company.model.Test;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;
import java.util.List;

public class TestExport1xml {
    public static final String entries = "entries";
    public static final String entry = "entry";
    public static final String field = "field";
    public static final String fieldValue = "значение поля ";

    private String configFile;

    public void setFile(String configFile) {
        this.configFile = configFile;
    }

    public void saveConfig(List<Test> tests) throws Exception {
        // create an XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // create XMLEventWriter
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(configFile));
        // create an EventFactory
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);
        eventWriter.add(end);

        // create config open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", entries);
        eventWriter.add(configStartElement);
        eventWriter.add(end);

        StartElement configStartEntry = eventFactory.createStartElement("",
                "", entry);

        for (Test test : tests) {
            eventWriter.add(tab);
            eventWriter.add(configStartEntry);
            eventWriter.add(end);
            // Write the different nodes
            createNode(eventWriter, field, String.valueOf(fieldValue + test.getField()));

            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", entry));
            eventWriter.add(end);
        }

        eventWriter.add(eventFactory.createEndElement("", "", entries));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());

        eventWriter.close();
    }

    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);

    }

}