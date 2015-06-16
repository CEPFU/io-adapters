/**
 * 
 */
package de.fu_berlin.agdb.crepe.inputadapters;

import static org.junit.Assert.assertEquals;

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
import de.fu_berlin.agdb.crepe.inputadapters.IInputAdapter;
import de.fu_berlin.agdb.crepe.inputadapters.RSSInputAdapter;



/**
 * @author Ralf Oechsner
 *
 */
public class RSSInputAdapterTest {
	
	private List<Event> testEvents;
	private String timeStampFormat;

	private void addTestEvent(String time, String title, String description, String link, String guid) throws ParseException {
		
		DateFormat df = new SimpleDateFormat(timeStampFormat);
		
		Event event = new Event();
		event.setTimeStamp(df.parse(time));
		Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();
		attributes.put("title", new Attribute(title));
		attributes.put("description", new Attribute(description));
		attributes.put("link", new Attribute(link));
		attributes.put("guid", new Attribute(guid));
		
		event.setAttributes(attributes);
		
		testEvents.add(event);
	}
	
	/**
	 * Compares hard coded events to imported events.
	 * @param inputAdapter InputAdapter.
	 */
	private void compare(IInputAdapter inputAdapter) {
		
		for (int i = 0; i < this.testEvents.size(); i++) {

			assertEquals("Events don't match!", this.testEvents.get(i), inputAdapter.getEvents().get(i));
		}
	}
	
	@Before
	public void setUp() throws ParseException {
		
		this.testEvents = new ArrayList<Event>();
		this.timeStampFormat = "EEE, dd MMM YYYY HH:mm:ss z";
		this.addTestEvent("Mon, 06 Sep 2009 16:20:00 +0000", "Example entry", "Here is some text containing an interesting description.", "http://www.wikipedia.org/", "unique string per item");
		this.addTestEvent("Tue, 23 Oct 2013 18:21:00 +0001", "Example 2", "More interesting descriptions.", "http://www.wikimedia.org/", "also very unique");
	}
	
    /**
     * Test loading an HTTP file.
     */
	@Test
    public void testRSSImport() {
    	
    	String xmlText = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + 
    			"<rss version=\"2.0\">\n" + 
    			"<channel>\n" + 
    			" <title>RSS Title</title>\n" + 
    			" <description>This is an example of an RSS feed</description>\n" + 
    			" <link>http://www.someexamplerssdomain.com/main.html</link>\n" + 
    			" <lastBuildDate>Mon, 06 Sep 2010 00:01:00 +0000 </lastBuildDate>\n" + 
    			" <pubDate>Mon, 06 Sep 2009 16:20:00 +0000 </pubDate>\n" + 
    			" <ttl>1800</ttl>\n" + 
    			" \n" + 
    			" <item>\n" + 
    			"  <title>Example entry</title>\n" + 
    			"  <description>Here is some text containing an interesting description.</description>\n" + 
    			"  <link>http://www.wikipedia.org/</link>\n" + 
    			"  <guid>unique string per item</guid>\n" + 
    			"  <pubDate>Mon, 06 Sep 2009 16:20:00 +0000 </pubDate>\n" + 
    			" </item>\n" + 
    			" <item>\n" + 
    			"  <title>Example 2</title>\n" + 
    			"  <description>More interesting descriptions.</description>\n" + 
    			"  <link>http://www.wikimedia.org/</link>\n" + 
    			"  <guid>also very unique</guid>\n" + 
    			"  <pubDate>Tue, 23 Oct 2013 18:21:00 +0001 </pubDate>\n" + 
    			" </item>\n" + 
    			" \n" + 
    			"</channel>\n" + 
    			"</rss>";	
		
		
    	RSSInputAdapter inputAdapter = new RSSInputAdapter();
    	inputAdapter.load(xmlText);

    	this.compare(inputAdapter);
    }
}
