package com.company.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.company.export.FirstXmlWriter.entry;
import static com.company.export.FirstXmlWriter.field;


public class Parsing2xml {
    private static final String pathToDestinationFile = "xml\\2.xml";

    private Parsing2xml() {
    }

    public static long getArithmeticSumOfValues() throws FileNotFoundException, XMLStreamException {

        long sum = 0;

        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(new FileInputStream(new File(pathToDestinationFile)))) {

            while (processor.doUntil(XMLEvent.START_ELEMENT, entry)) {
                final String fieldValue = processor.getAttribute(field);
                sum+= Integer.parseInt(fieldValue.substring(fieldValue.lastIndexOf(" ") + 1));
            }
        }

        return sum;
    }
}
