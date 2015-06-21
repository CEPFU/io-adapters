package de.fu_berlin.agdb.crepe.inputadapters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import de.fu_berlin.agdb.crepe.data.Attribute;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

public class JSONInputAdapter implements IInputAdapter{

	private ArrayList<IEvent> events;
	
	public JSONInputAdapter() {
		events = new ArrayList<IEvent>();
	}
	
	@Override
	public void load(String text) {
		JSONArray eventArray = new JSONArray(text);
		for(int i = 0; i < eventArray.length(); i++){
			JSONObject arrayEntry = eventArray.getJSONObject(i);
			
			Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();
			attributes.put("date", new Attribute(arrayEntry.getString("stationId")));
			attributes.put("windChill", new Attribute(arrayEntry.getDouble("windChill")));
			attributes.put("windDirection", new Attribute(arrayEntry.getDouble("windDirection")));
			attributes.put("windSpeed", new Attribute(arrayEntry.getDouble("windSpeed")));
			attributes.put("atmosphereHumidity", new Attribute(arrayEntry.getDouble("atmosphereHumidity")));
			attributes.put("atmospherePressure", new Attribute(arrayEntry.getDouble("atmospherePressure")));
			attributes.put("atmosphereRising", new Attribute(arrayEntry.getDouble("atmosphereRising")));
			attributes.put("atmosphereVisibility", new Attribute(arrayEntry.getDouble("atmosphereVisibility")));
			attributes.put("astronomySunrise", new Attribute(arrayEntry.getString("astronomySunrise")));
			attributes.put("astronomySunset", new Attribute(arrayEntry.getString("astronomySunset")));
			attributes.put("temperature", new Attribute(arrayEntry.getDouble("temperature")));
			addStationMetaDataAttributes(arrayEntry.getJSONObject("stationMetaData"), attributes);
			events.add(new Event(new Date(arrayEntry.getLong("timestamp")), attributes));
			
			JSONArray forecastEntryArray = arrayEntry.getJSONArray("forecastEntrys");
			for(int ii = 0; ii < forecastEntryArray.length(); i++){
				JSONObject forecastArrayEntry = forecastEntryArray.getJSONObject(i);
				
				Map<String, IAttribute> forecastEntrysAttributes = new HashMap<String, IAttribute>();
				forecastEntrysAttributes.put("date", new Attribute(forecastArrayEntry.getString("date")));
				forecastEntrysAttributes.put("high", new Attribute(forecastArrayEntry.getDouble("high")));
				forecastEntrysAttributes.put("low", new Attribute(forecastArrayEntry.getDouble("low")));
				addStationMetaDataAttributes(arrayEntry.getJSONObject("stationMetaData"), forecastEntrysAttributes);
				events.add(new Event(new Date(arrayEntry.getLong("timestamp")), attributes));
			}
		}
	}

	@Override
	public List<IEvent> getEvents() {
		ArrayList<IEvent> tmp = events;
		events = null;
		return tmp;
	}
	
	private void addStationMetaDataAttributes(JSONObject stationMetaData, Map<String, IAttribute> attributes) {
		attributes.put("stationId", new Attribute(stationMetaData.getLong("stationId")));
		attributes.put("stationPosition", new Attribute(stationMetaData.getString("stationPosition")));
		attributes.put("fromDate", new Attribute(stationMetaData.getString("fromDate")));
		attributes.put("untilDate", new Attribute(stationMetaData.getString("untilDate")));
		attributes.put("stationHeight", new Attribute(stationMetaData.getInt("stationHeight")));
		attributes.put("stationName", new Attribute(stationMetaData.getString("stationName")));
		attributes.put("federalState", new Attribute(stationMetaData.getString("federalState")));
	}

}
