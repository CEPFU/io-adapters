package de.fu_berlin.agdb.crepe.writers;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertTrue;

/**
 * Test for RestWriter
 */
public class RestWriterTest {

    private RestWriter<String> writer;

    @Before
    public void setUp() throws Exception {
        // This currently requires the crepe rest service to be running
        // TODO: Choose another service?
        writer = RestWriter.makeStdWriter(URI.create("http://localhost:8080/event"));
    }

    @Test
    public void testWrite() throws Exception {
        String json = "{\n" +
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
                "}";

        writer.setText(json);
        writer.setHeader("Content-type", "application/json");
        boolean result = writer.write();
        assertTrue("REST result is not okay.", result);
    }
}
