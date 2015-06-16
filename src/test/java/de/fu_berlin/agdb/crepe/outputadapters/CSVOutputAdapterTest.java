/**
 * 
 */
package de.fu_berlin.agdb.crepe.outputadapters;

import static org.junit.Assert.*;

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
import de.fu_berlin.agdb.crepe.data.IEvent;
import de.fu_berlin.agdb.crepe.outputadapters.CSVOutputAdapter;

/**
 * @author Ralf Oechsner
 *
 */
public class CSVOutputAdapterTest {
	
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
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		
		this.testEvents = new ArrayList<Event>();
		this.timeStampFormat = "EEE MMM dd HH:mm:ss z yyyy";
		
		// compare it to these events
		try {
			this.addTestEvent("Mon Dec 31 23:01:00 CET 2012", "25", "60");
			this.addTestEvent("Fri Dec 28 10:31:00 CET 2012", "15", "40");
			this.addTestEvent("Sat Dec 29 14:02:12 CET 2012", "33", "66");
			this.addTestEvent("Sun Dec 30 17:12:23 CET 2012", "-2", "5");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWithCaptions() {
		
		String text = "Time;Temperature;Humidity\n" + 
				"Mon Dec 31 23:01:00 CET 2012;25;60\n" + 
				"Fri Dec 28 10:31:00 CET 2012;15;40\n" + 
				"Sat Dec 29 14:02:12 CET 2012;33;66\n" + 
				"Sun Dec 30 17:12:23 CET 2012;-2;5\n";
		
		CSVOutputAdapter outputAdapter = new CSVOutputAdapter("Time", this.timeStampFormat, ";", new String[]{"Time", "Temperature", "Humidity"});
		outputAdapter.load(this.testEvents.toArray(new IEvent[this.testEvents.size()]));
		System.out.println(outputAdapter.getOutput());
		
		assertEquals(text, outputAdapter.getOutput());
	}

	@Test
	public void testWithoutCaptions() {
		
		String text = "Time;Temperature;Humidity\n" + 
				"Mon Dec 31 23:01:00 CET 2012;25;60\n" + 
				"Fri Dec 28 10:31:00 CET 2012;15;40\n" + 
				"Sat Dec 29 14:02:12 CET 2012;33;66\n" + 
				"Sun Dec 30 17:12:23 CET 2012;-2;5\n";
		
		CSVOutputAdapter outputAdapter = new CSVOutputAdapter("Time", this.timeStampFormat, ";");
		outputAdapter.load(this.testEvents.toArray(new IEvent[this.testEvents.size()]));
		System.out.println(outputAdapter.getOutput());
		
		assertEquals(text, outputAdapter.getOutput());
	}
}
