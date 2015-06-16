package de.fu_berlin.agdb.crepe.outputadapters;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Writes events to JSON
 * @author Simon Kalt
 */
public class JSONOutputAdapter implements IOutputAdapter {
    public static final ObjectMapper OBJECT_MAPPER;
    private JsonEvents jsonEvents = new JsonEvents();

    /*
    JSON ObjectMapper initialization
     */
    static {
        OBJECT_MAPPER = new ObjectMapper();
        //OBJECT_MAPPER.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Serialize Type Information
        OBJECT_MAPPER.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
        OBJECT_MAPPER.addMixIn(IEvent.class, TypeInfoMixIn.class);
        OBJECT_MAPPER.addMixIn(IAttribute.class, TypeInfoMixIn.class);

        // Module to which we can add custom serializers
        SimpleModule module = new SimpleModule(
                "JsonOutputAdapter",
                new Version(1, 0, 0, null,
                        "de.fu_berlin.agdb", "outputadapters")
        );
        // module.addSerializer(Event.class, new EventSerializer());
        OBJECT_MAPPER.registerModule(module);
    }



    @Override
    public void load(IEvent[] events) {
        jsonEvents.addAll(events);
    }

    @Override
    public String getOutput() {
        try {
            return OBJECT_MAPPER.writeValueAsString(jsonEvents);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Provides common configuration for JSON (de-)serialization.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
    public static abstract class TypeInfoMixIn {}

    public static class JsonEvents {
        private List<IEvent> events = new ArrayList<>();
        private String user;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public List<IEvent> getEvents() {
            return events;
        }

        public void setEvents(List<IEvent> events) {
            this.events = events;
        }

        public boolean add(IEvent event) {
            return events.add(event);
        }

        public void addAll(IEvent[] c) {
            for (IEvent event : c) {
                add(event);
            }
        }
    }
}
