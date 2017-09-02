package com.company.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Parsing2xml {

    private Parsing2xml() {
    }

    public static long getArithmeticSumOfValues() throws FileNotFoundException, XMLStreamException {

        long sum = 0;

        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(new FileInputStream(new File("xml\\2.xml")))) {

            while (processor.doUntil(XMLEvent.START_ELEMENT, "entry")) {
                final String field = processor.getAttribute("field");
                sum+= Integer.parseInt(field.substring(field.lastIndexOf(" ") + 1));
            }
        }

        return sum;
    }
}
