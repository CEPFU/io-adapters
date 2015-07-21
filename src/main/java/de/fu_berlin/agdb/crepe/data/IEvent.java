package de.fu_berlin.agdb.crepe.data;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

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

	/**
	 * Returns the value of the given attribute, if available.
	 * @param attribute Name of the attribute.
	 * @return An optional containing the attribute value, if it was available.
	 */
	public default Optional<Object> getAttributeValue(String attribute) {
        return Optional.ofNullable(getAttributes())
                .map((attributes) -> attributes.get(attribute))
                .map(IAttribute::getValue);
	}
}
