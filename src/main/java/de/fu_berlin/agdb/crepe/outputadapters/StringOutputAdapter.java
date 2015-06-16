/**
 * 
 */
package de.fu_berlin.agdb.crepe.outputadapters;

import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Output adapter for formatted strings.
 * @author Ralf Oechsner
 *
 */
public class StringOutputAdapter implements IOutputAdapter {

	private String format;
	private String[] attributes;
	private String output;
	
	/**
	 * Output adapter for formatted strings.
	 * @param format format string
	 * @param attributes attribute names (use "Timestamp" to get the time stamp)
	 */
	public StringOutputAdapter(String format, String ... attributes) {
		
		this.format = format;
		this.attributes = attributes;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.outputadapters.IOutputAdapter#load(de.fu_berlin.agdb.crepe.data.IEvent[])
	 */
	@Override
	public void load(IEvent[] events) {

		Object[] args = new Object[this.attributes.length];
		for (int i = 0; i < events.length; i++) {
			IAttribute a = events[i].getAttributes().get(this.attributes[i]);
			if (a != null) {
				args[i] = a.getValue();
			}
			else {
				if (this.attributes[i].equals("Timestamp"))
					args[i] = events[i].getTimeStamp().toString();
				else
					args[i] = "";
			}
		}
		this.output = String.format(format, args); 
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.outputadapters.IOutputAdapter#getOutput()
	 */
	@Override
	public String getOutput() {
		
		return this.output;
	}

}
