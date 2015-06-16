package de.fu_berlin.agdb.crepe.inputadapters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.fu_berlin.agdb.crepe.data.Attribute;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * InputAdapter for RSS feeds. Turns RSS xml files into RSSEvents.
 * @author Ralf Oechsner
 *
 */
public class RSSInputAdapter implements IInputAdapter {

	private List<IEvent> events;
	
	/**
	 * Construct RSSEvents from XML-Text.
	 * @param xmlText XML-Text.
	 */
	public RSSInputAdapter() {

		this.events = new ArrayList<IEvent>();
	}
	
	/**
	 * Parses XML document and generate event list from it.
	 */
	private void parseXML(String xmlText) {
			
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(xmlText.getBytes("UTF-8"));
			Document doc = dBuilder.parse(is);
			
			NodeList items = doc.getElementsByTagName("item");
			
			for (int i = 0; i < items.getLength(); i++) {
				
				Node nNode = items.item(i);
				Element eElement = (Element) nNode;
				
				// generate an event from all tags and add it to the list
				this.events.add(this.parseAllTags(eElement));
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Generate an event from a node and put all it's children into the event's attribute list. 
	 * @param parent node to parse
	 * @return event
	 */
	private IEvent parseAllTags(Element parent) {
		
		Date timeStamp = null;
		
		Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();
		
		NodeList nodes = parent.getChildNodes();
		
		// every node is added as an attribute
		for (int i = 0; i < nodes.getLength(); i++) {
			
			// skip text nodes
			if (nodes.item(i).getNodeType() == Node.TEXT_NODE)
				continue;
			
			String tag = nodes.item(i).getNodeName();
			String text = nodes.item(i).getTextContent();
			
			// special case is pubDate which represents the time stamp
			if (tag.equalsIgnoreCase("pubDate")) {
				DateFormat df = new SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss z");
				try {
					timeStamp = df.parse(text);
				} catch (ParseException e) {
					// wrong format so ignored
				}
			}
			else {
				
				attributes.put(tag, new Attribute(text));
			}
		}
		
		// if no time stamp is found or could be parsed set it to current date
		if (timeStamp == null)
			new Date();
		
		return new Event(timeStamp, attributes);
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.inputadapters.IInputAdapter#getEvents()
	 */
	@Override
	public List<IEvent> getEvents() {
		
		return this.events;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.inputadapters.IInputAdapter#load(java.lang.String)
	 */
	@Override
	public void load(String text) {

		this.parseXML(text);
	}	
}
