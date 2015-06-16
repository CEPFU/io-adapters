/**
 * 
 */
package de.fu_berlin.agdb.crepe.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Event structure for all events.
 * @author Ralf Oechsner
 *
 */
public class Event implements IEvent {

	private Date timeStamp;
	private Map<String, IAttribute> attributes;
	
	/**
	 * Empty event. Time stamp and at least one Attribute has to be set later!
	 */
	public Event() {
		
	}
	
	/**
	 * Create event with time stamp and attributes.
	 * @param timestamp time stamp of event.
	 * @param attributes attribute map of event.
	 */
	public Event(Date timestamp, Map<String, IAttribute> attributes) {
		
		this.timeStamp = timestamp;
		this.attributes = attributes;
	}
	
	/**
	 * Creates an event with one attribute and it's value. Time stamp is set to the current
	 * time.
	 * @param attribute attribute of event
	 * @param attributeValue value of the attribute
	 */
	public Event(String attribute, Object attributeValue) {
		
		this.timeStamp = new Date();  // current time in milliseconds
		this.attributes = new HashMap<String, IAttribute>();
		this.attributes.put(attribute, new Attribute(attributeValue));
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.eve.data.IEvent#getTimeStamp()
	 */
	public Date getTimeStamp() {

		return this.timeStamp;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.data.IEvent#setTimeStamp(java.util.Date)
	 */
	@Override
	public void setTimeStamp(Date timeStamp) {
		
		this.timeStamp = timeStamp;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.eve.data.IEvent#getAttributes()
	 */
	public Map<String, IAttribute> getAttributes() {

		return attributes;
	}
	
	/**
	 * Setter for attribute map.
	 * @param attributes attributes.
	 */
	public void setAttributes(Map<String, IAttribute> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * Returns string with time stamp and every attribute on a new line.
	 * Every line has the form: Key: Value + newline.
	 */
	@Override
	public String toString() {
		
		String nl = System.getProperty("line.separator");
		String s = "Timestamp: " + this.timeStamp + nl;
		
		int i = 0;
		for (Map.Entry<String, IAttribute> entry : this.attributes.entrySet()) {
			
			s += entry.getKey() + ": " + entry.getValue();
			if (i++ != this.attributes.entrySet().size() - 1)
				s += nl;
		}
		
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result
				+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}	
}
