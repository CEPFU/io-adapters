package de.fu_berlin.agdb.crepe.outputadapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fu_berlin.agdb.crepe.data.Attribute;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class JSONOutputAdapterTest {

    private static final String timeStampFormat = "EEE MMM dd HH:mm:ss z yyyy";
    private List<Event> testEvents;
    private ObjectMapper objectMapper;

    private static Event generateTestEvent(String time, String temperature, String humidity) throws ParseException {
        DateFormat df = new SimpleDateFormat(timeStampFormat, Locale.ENGLISH);

        Event event = new Event();
        event.setTimeStamp(df.parse(time));
        Map<String, IAttribute> attributes = new HashMap<>();
        attributes.put("Temperature", new Attribute(temperature));
        attributes.put("Humidity", new Attribute(humidity));
        event.setAttributes(attributes);
        return event;
    }

    /**
     * Adds hard coded event.
     *
     * @param time        time of event
     * @param temperature temperature (example of attribute)
     * @param humidity    humidity (second example of attribute)
     * @throws ParseException in case the time stamp can't be parsed (wrong time stamp format).
     */
    private void addTestEvent(String time, String temperature, String humidity) throws ParseException {
        testEvents.add(generateTestEvent(time, temperature, humidity));
    }

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        this.testEvents = new ArrayList<>();

        // compare it to these events
        try {
            this.addTestEvent("Mon Dec 31 23:01:00 CET 2012", "25", "60");
            //this.addTestEvent("Fri Dec 28 10:31:00 CET 2012", "15", "40");
            //this.addTestEvent("Sat Dec 29 14:02:12 CET 2012", "33", "66");
            //this.addTestEvent("Sun Dec 30 17:12:23 CET 2012", "-2", "5");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEmptyOutput() throws Exception {
        JsonNode expectedTree = objectMapper.readTree("{\"events\":[], \"user\": null}");
        IOutputAdapter output = new JSONOutputAdapter();
        String result = output.getOutput();

        JsonNode resultTree = objectMapper.readTree(result);
        assertEquals("Output JSON incorrect.", expectedTree, resultTree);
    }

    @Test
    public void testGetOutput() throws Exception {
        IOutputAdapter output = new JSONOutputAdapter();

        JsonNode expectedTree = objectMapper.readTree("{\n" +
                "  \"events\": [\n" +
                "    {\n" +
                "      \"@class\": \"Event\",\n" +
                "      \"timeStamp\": 1356991260000,\n" +
                "      \"attributes\": {\n" +
                "        \"Temperature\": {\n" +
                "          \"@class\": \"Attribute\",\n" +
                "          \"value\": \"25\"\n" +
                "        },\n" +
                "        \"Humidity\": {\n" +
                "          \"@class\": \"Attribute\",\n" +
                "          \"value\": \"60\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"user\": null\n" +
                "}");

        output.load(this.testEvents.toArray(new IEvent[this.testEvents.size()]));

        String result = output.getOutput();
        JsonNode resultTree = objectMapper.readTree(result);
        assertEquals("Output JSON incorrect.", expectedTree, resultTree);
    }
}
