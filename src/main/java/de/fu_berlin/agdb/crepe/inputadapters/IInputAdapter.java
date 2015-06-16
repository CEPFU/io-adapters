package de.fu_berlin.agdb.crepe.inputadapters;

import java.util.List;

import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Interface for input adapters.
 * @author Ralf Oechsner
 *
 */
public interface IInputAdapter {

	/**
	 * Loads source text and generates events from it.
	 * @param text source text.
	 */
	public void load(String text);
	
	/**
	 * List of imported events.
	 * @return imported events.
	 */
	public List<IEvent> getEvents();
}
