package de.fu_berlin.agdb.crepe.loader;

/**
 * Interface for loaders.
 * @author Ralf Oechsner
 *
 */
public interface ILoader {

	/**
	 * Load source.
	 * @return true if successful, false otherwise.
	 */
	public boolean load();
	
	/**
	 * Content of loaded source as a string.
	 * @return content of source.
	 */
	public String getText();
	
	/**
	 * Indicates if the loader has more data
	 */
	public boolean hasMoreData();
}
