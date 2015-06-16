package de.fu_berlin.agdb.crepe.data;

import java.util.Date;
import java.util.Map;

/**
 * Interface for generic events (used for algebra).
 * @author Ralf Oechsner
 *
 */
public interface IEvent {

	/**
	 * Time stamp of event.
	 * @return time stamp of event.
	 */
	public Date getTimeStamp();
	
	/**
	 * Set time stamp of event.
	 * @param timeStamp time stamp of event
	 */
	public void setTimeStamp(Date timeStamp);
	
	/**
	 * Map that contains all attributes of an event. Every attribute has a name
	 * that is used as a key in the map.
	 * @return all attributes of the event
	 */
	public Map<String, IAttribute> getAttributes();
}
