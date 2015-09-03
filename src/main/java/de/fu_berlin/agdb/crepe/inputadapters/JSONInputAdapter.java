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
		if(text != null){
			JSONArray eventArray = new JSONArray(text);
			for(int i = 0; i < eventArray.length(); i++){
				JSONObject arrayEntry = eventArray.getJSONObject(i);
				
				Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();
				
				attributes.put("timestamp", new Attribute(arrayEntry.getLong("timestamp")));
				addStationMetaDataAttributes(arrayEntry.getJSONObject("stationMetaData"), attributes);
				attributes.put("dataType", new Attribute(arrayEntry.getString("dataType")));
				
				attributes.put("date", new Attribute(arrayEntry.getString("date")));
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
				attributes.put("temperatureHigh", new Attribute(arrayEntry.getDouble("temperatureHigh")));
				attributes.put("temperatureLow", new Attribute(arrayEntry.getDouble("temperatureLow")));
				
				attributes.put("qualityLevel", new Attribute(arrayEntry.getInt("qualityLevel")));
				attributes.put("steamPressure", new Attribute(arrayEntry.getDouble("steamPressure")));
				attributes.put("cloudage", new Attribute(arrayEntry.getDouble("cloudage")));
				attributes.put("minimumAirGroundTemperature", new Attribute(arrayEntry.getDouble("minimumAirGroundTemperature")));
				attributes.put("maximumWindSpeed", new Attribute(arrayEntry.getDouble("maximumWindSpeed")));
				attributes.put("precipitationDepth", new Attribute(arrayEntry.getDouble("precipitationDepth")));
				attributes.put("sunshineDuration", new Attribute(arrayEntry.getDouble("sunshineDuration")));
				attributes.put("snowHeight", new Attribute(arrayEntry.getDouble("snowHeight")));
				
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
		attributes.put("locationId", new Attribute(stationMetaData.getLong("locationId")));
		attributes.put("locationDescription", new Attribute(stationMetaData.getString("locationDescription")));
		attributes.put("locationPosition", new Attribute(stationMetaData.getString("locationPosition")));
		
		attributes.put("stationId", new Attribute(stationMetaData.getLong("stationId")));
		attributes.put("fromDate", new Attribute(stationMetaData.getString("fromDate")));
		attributes.put("untilDate", new Attribute(stationMetaData.getString("untilDate")));
		attributes.put("stationHeight", new Attribute(stationMetaData.getInt("stationHeight")));
		attributes.put("federalState", new Attribute(stationMetaData.getString("federalState")));
	}

}
