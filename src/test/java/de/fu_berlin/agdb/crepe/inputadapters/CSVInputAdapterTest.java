/**
 * 
 */
package de.fu_berlin.agdb.crepe.inputadapters;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.fu_berlin.agdb.crepe.data.Attribute;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.inputadapters.CSVInputAdapter;
import de.fu_berlin.agdb.crepe.inputadapters.IInputAdapter;

/**
 * Test for the CSVInputAdapter.
 * @author Ralf Oechsner
 *
 */
public class CSVInputAdapterTest {

	private List<Event> testEvents;
	private String timeStampFormat;
	
	/**
	 * Adds hard coded event.
	 * @param time time of event
	 * @param temperature temperature (example of attribute) 
	 * @param humidity humidity (second example of attribute)
	 * @throws ParseException in case the time stamp can't be parsed (wrong time stamp format).
	 */
	private void addTestEvent(String time, String temperature, String humidity) throws ParseException {
	
		DateFormat df = new SimpleDateFormat(timeStampFormat);
		
		Event event = new Event();
		event.setTimeStamp(df.parse(time));
		Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();
		attributes.put("Temperature", new Attribute(temperature));
		attributes.put("Humidity", new Attribute(humidity));
		event.setAttributes(attributes);
		
		testEvents.add(event);
	}
	
	/**
	 * Compares hard coded events to imported events.
	 * @param inputAdapter inputAdapter.
	 */
	private void compare(IInputAdapter inputAdapter) {
		
		for (int i = 0; i < this.testEvents.size(); i++) {

			assertTrue(this.testEvents.get(i).equals(inputAdapter.getEvents().get(i)));
		}
	}
	
	@Before
	public void setUp() throws ParseException {
		
		testEvents = new ArrayList<Event>();
		timeStampFormat = "EEE MMM dd HH:mm:ss z yyyy";
		
		// compare it to these events (must be in same order!)
		this.addTestEvent("Mon Dec 31 23:01:00 CET 2012", "25", "60");
		this.addTestEvent("Fri Dec 28 10:31:00 CET 2012", "15", "40");
		this.addTestEvent("Sat Dec 29 14:02:12 CET 2012", "33", "66");
		this.addTestEvent("Sun Dec 30 17:12:23 CET 2012", "-2", "5");
	}
	
	@Test
	public void testSemicolon() throws ParseException {
		
		String text = "Time;Temperature;Humidity\n" + 
				"Mon Dec 31 23:01:00 CET 2012;25;60\n" + 
				"Fri Dec 28 10:31:00 CET 2012;15;40\n" + 
				"Sat Dec 29 14:02:12 CET 2012;33;66\n" + 
				"Sun Dec 30 17:12:23 CET 2012;-2;5";
		
		CSVInputAdapter inputAdapter = new CSVInputAdapter("Time", this.timeStampFormat, ";");
		inputAdapter.load(text);
		
		this.compare(inputAdapter);
	}
	
	@Test
	public void testComma() throws ParseException {
		
		String text = "Time,Temperature,Humidity\n" + 
				"Mon Dec 31 23:01:00 CET 2012,25,60\n" + 
				"Fri Dec 28 10:31:00 CET 2012,15,40\n" + 
				"Sat Dec 29 14:02:12 CET 2012,33,66\n" + 
				"Sun Dec 30 17:12:23 CET 2012,-2,5";
		
		CSVInputAdapter inputAdapter = new CSVInputAdapter("Time", this.timeStampFormat, ",");
		inputAdapter.load(text);
		
		this.compare(inputAdapter);
	}
	
	@Test
	public void testColumnOrder() throws ParseException {
		
		String text = "Temperature;Time;Humidity\n" + 
				"25;Mon Dec 31 23:01:00 CET 2012;60\n" + 
				"15;Fri Dec 28 10:31:00 CET 2012;40\n" + 
				"33;Sat Dec 29 14:02:12 CET 2012;66\n" + 
				"-2;Sun Dec 30 17:12:23 CET 2012;5";
		
		CSVInputAdapter inputAdapter = new CSVInputAdapter("Time", this.timeStampFormat, ";");
		inputAdapter.load(text);
		
		this.compare(inputAdapter);
	}
}
