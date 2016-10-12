package de.fu_berlin.agdb.crepe.inputadapters;

import de.fu_berlin.agdb.crepe.data.Attribute;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class GridDataInputAdapter implements IInputAdapter{

	private ArrayList<IEvent> events;

	public GridDataInputAdapter() {
		events = new ArrayList<IEvent>();
	}
	
	@Override
	public void load(String text) {
		if(text != null){
			JSONObject arrayEntry = new JSONObject(text);
			
			Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();
			
			putLongIfExists(arrayEntry, "timestamp", attributes);
			addStationMetaDataAttributes(arrayEntry, attributes);
			putStringIfExists(arrayEntry, "dataType", attributes);
			
			putStringIfExists(arrayEntry, "date", attributes);
			putDoubleIfExists(arrayEntry, "windChill", attributes);
			putDoubleIfExists(arrayEntry, "windDirection", attributes);
			putDoubleIfExists(arrayEntry, "windSpeed", attributes);
			putDoubleIfExists(arrayEntry, "atmosphereHumidity", attributes);
			putDoubleIfExists(arrayEntry, "atmospherePressure", attributes);
			putDoubleIfExists(arrayEntry, "atmosphereRising", attributes);
			putDoubleIfExists(arrayEntry, "atmosphereVisibility", attributes);
			putStringIfExists(arrayEntry, "astronomySunrise", attributes);
			putStringIfExists(arrayEntry, "astronomySunset", attributes);
			putDoubleIfExists(arrayEntry, "temperature", attributes);
			putDoubleIfExists(arrayEntry, "temperatureHigh", attributes);
			putDoubleIfExists(arrayEntry, "temperatureLow", attributes);
			
			putIntIfExists(arrayEntry, "qualityLevel", attributes);
			putDoubleIfExists(arrayEntry, "steamPressure", attributes);
			putDoubleIfExists(arrayEntry, "cloudage", attributes);
			putDoubleIfExists(arrayEntry, "minimumAirGroundTemperature", attributes);
			putDoubleIfExists(arrayEntry, "maximumWindSpeed", attributes);
			putDoubleIfExists(arrayEntry, "precipitationDepth", attributes);
			putDoubleIfExists(arrayEntry, "sunshineDuration", attributes);
			putDoubleIfExists(arrayEntry, "snowHeight", attributes);
			
			events.add(new Event(new Date(arrayEntry.getLong("timestamp")), attributes));
		}
	}
	
	private void addStationMetaDataAttributes(JSONObject arrayEntry, Map<String, IAttribute> attributes) {
		JSONObject stationMetaData = arrayEntry.getJSONObject("stationMetaData");
		
		putLongIfExists(stationMetaData, "locationId", attributes);
		putStringIfExists(stationMetaData, "locationDescription", attributes);
		putStringIfExists(stationMetaData, "locationPosition", attributes);
		
		putLongIfExists(stationMetaData, "stationId", attributes);
		putStringIfExists(stationMetaData, "fromDate", attributes);
		putStringIfExists(stationMetaData, "untilDate", attributes);
		putIntIfExists(stationMetaData, "stationHeight", attributes);
		putStringIfExists(stationMetaData, "federalState", attributes);

		putDoubleIfExists(stationMetaData, "gridLat", attributes);
		putDoubleIfExists(stationMetaData, "gridLon", attributes);
	}
	
	private void putLongIfExists(JSONObject source, String key, Map<String, IAttribute> attributes){
		try {
			long longValue = source.getLong(key);
			attributes.put(key, new Attribute(longValue));
		} catch (JSONException e) {
			// swallow exception
		}
	}
	
	private void putIntIfExists(JSONObject source, String key, Map<String, IAttribute> attributes){
		try {
			int intValue = source.getInt(key);
			attributes.put(key, new Attribute(intValue));
		} catch (JSONException e) {
			// swallow exception
		}
	}
	
	private void putDoubleIfExists(JSONObject source, String key, Map<String, IAttribute> attributes){
		try {
			double doubleValue = source.getDouble(key);
			attributes.put(key, new Attribute(doubleValue));
		} catch (JSONException e) {
			// swallow exception
		}
	}
	
	private void putStringIfExists(JSONObject source, String key, Map<String, IAttribute> attributes){
		try {
			String stringValue = source.getString(key);
			attributes.put(key, new Attribute(stringValue));
		} catch (JSONException e) {
			// swallow exception
		}
	}

	@Override
	public List<IEvent> getEvents() {
		return events;
	}
}
